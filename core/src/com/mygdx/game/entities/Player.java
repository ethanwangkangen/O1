package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.mygdx.game.DarwinsDuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import com.mygdx.game.handlers.UserPlayerHandler;
import com.mygdx.game.interfaces.AuthService;


public class Player extends Entity implements Serializable{

    /**
     * Username to be displayed. Not unique. DO NOT use for identification!
     */
    public String username;

    public ArrayList<Creature> battlePets = new ArrayList<>();
    public ArrayList<Creature> reservePets = new ArrayList<>();

    /**
     * Unique ID taken from firebase to identify players.
     * Use this for identification
     */
    public String userId;
    public String path = "player1";
    public PetNum currentPetNum;


    public enum PetNum {
        PET1,
        PET2,
        PET3
    }


    /**
     * No arg constructor for serialisation
     */
    public Player() {
        currentPetNum = PetNum.PET1;
        battlePets.add(new MeowmadAli());
        battlePets.add(new MouseHunter());
        battlePets.add(new Froggy());

    }

    public ArrayList<Creature> getBattlePets() {
        return battlePets;
    }

    public Boolean hasPet(Creature newPet) {
        // return true if player already has pet
        for (Creature pet : battlePets) {
            if (Objects.equals(pet.getType(), newPet.getType())) {
                return true;
            }
        }
        for (Creature pet : reservePets) {
            if (Objects.equals(pet.getType(), newPet.getType())) {
                return true;
            }
        }

        return false;
    }

    public void addPet(Creature pet) {
        // Adds a new pet to reservePets
        if (!hasPet(pet)) { // Checks if player already has the pet
            System.out.println("Adding pet to player: " + pet.getName());
            reservePets.add(pet);
        }
    }

    public ArrayList<Creature> getReservePets() {return reservePets;}

    public Boolean takeDamage(Skill skill) {

        // Returns true if petchange (ie a pet has died)
        getCurrentPet().takeDamage(skill);
        System.out.println(getCurrentPet().getHealth());
        if (!getCurrentPet().isAlive()) {
            System.out.println("changing to next pet");
            changeNextPet();
            return true;
        }
        return false;
    }

    public Boolean updateStatus() {
        // Returns true if a pet has died

        boolean petDied = false;
        System.out.println("Updating status");

        for (Creature pet : getBattlePets()) {
            if (pet.updateStatus()) {
                // A pet has died
                petDied = true;
                changeNextPet();
            }
        }
        return petDied;
    }

    public void absorb(int dmg) {
        getCurrentPet().absorb(dmg);
    }

    public void setCurrentPet(PetNum p) {
        this.currentPetNum = p;
    }

    public void setBattlePets(ArrayList<Creature> battlePets) {
        this.battlePets = battlePets;
    }

    public void setReservePets(ArrayList<Creature> battlePets) {
        this.reservePets = battlePets;
    }

    public boolean isAlive() {
        for (Creature pet : battlePets) {
            if (pet.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public Creature getCurrentPet() {
        return battlePets.get(currentPetNum.ordinal());
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    public void changeCurrentPet(Player.PetNum petNum) {
        System.out.println("ChangeCurrentPet function");
        // Change the current pet being used in battle
        if (isValidPet(petNum)) {
            System.out.println("Changing current pet from " + currentPetNum + " to " + petNum);
            currentPetNum = petNum;
        }
    }

    private boolean isValidPet(Player.PetNum petNum) {
        System.out.println("isValidPet function");
        // Checks if list has the petNum, or if its empty
        int index = petNum.ordinal(); // Assuming PET1 is 0, PET2 is 1, PET3 is 2
        return index < battlePets.size();
    }

    public void changeNextPet() {
        // Change to next available pet when a pet dies
        System.out.println("ChangeNextPet function");

        for (int i = 0; i < battlePets.size(); i++) {
            Creature pet = battlePets.get(i);
            if (pet != null && pet.isAlive()) {
                changeCurrentPet(PetNum.values()[i]);
                break;
            }
        }
    }

    public void update(Player player) {
        // To update pet info during battle
        if (!Objects.equals(player.getUserId(), userId)) {
            System.err.println("id not the same");
        }

        for (int i = 0; i < battlePets.size(); i++) {
            Creature pet = battlePets.get(i);
            Creature playerPet = player.getBattlePets().get(i);
            if (pet != null && playerPet != null) {
                pet.update(playerPet);
            }
        }
        changeCurrentPet(player.getCurrentPetNum());
    }

    public PetNum getCurrentPetNum() {
        return currentPetNum;
    }

    public void updatePets(ArrayList<Creature> pets1, ArrayList<Creature> pets2) {
        // To update pet info after PetChangeScreen
        battlePets = pets1;
        reservePets = pets2;

        System.out.print("Updating pets: ");
        for (Creature pet : pets1) {
            System.out.println(pet.getName() + ", ");
        }
        System.out.println();

        UserPlayerHandler.sendToFirebase();
    }

    public Skill npcAttack(int turns) {
        // For NPC only
        System.err.println("Bug: Player class is calling method exclusive for NPC (npcAttack).");
        return null;
    }

    public void wonBattle() {
        for (Creature pet : battlePets) {
            pet.gainEXP(100);
        }
        UserPlayerHandler.sendToFirebase();
    }

    public void lostBattle() {
        for (Creature pet : battlePets) {
            pet.gainEXP(30);
        }
        UserPlayerHandler.sendToFirebase();
    }

}