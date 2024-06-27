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


    public Player() {
        currentPetNum = PetNum.PET1;
        battlePets.add(new MeowmadAli());
        battlePets.add(new CrocLesnar());
        battlePets.add(new Froggy());

    } //no arg constructor for serialisation

    public ArrayList<Creature> getBattlePets() {
        return battlePets;
    }

    public void addPet(Creature pet) {
        // adds a new pet to reservePets
        reservePets.add(pet);
    }

    public ArrayList<Creature> getReservePets() {return reservePets;}

//    public Map<String, Object> toMap() {
//        Map<String, Object> result = new HashMap<>();
//        result.put("username", username);
//        result.put("userId", userId);
//        result.put("currentPetNum", currentPetNum.toString());
//
//        List<Map<String, Object>> battlePetsList = new ArrayList<>();
//        for (Creature pet : battlePets) {
//            battlePetsList.add(pet.toMap());
//        }
//        result.put("battlePets", battlePetsList);
//
//        List<Map<String, Object>> reservePetsList = new ArrayList<>();
//        for (Creature pet : reservePets) {
//            reservePetsList.add(pet.toMap());
//        }
//        result.put("reservePets", reservePetsList);
//
//        return result;
//    }

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
        // checks if list has the petNum, or if its empty
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

    public int getNumPets() {
        return battlePets.size();
    }

    public void update(Player player) {
        // to update pet info during battle
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
        // to update pet info after PetChangeScreen
        battlePets = pets1;
        reservePets = pets2;

        System.out.print("Updating pets: ");
        for (Creature pet : pets1) {
            System.out.println(pet.getName() + ", ");
        }
        System.out.println();
    }

    public Skill npcAttack(int turns) {
        // for NPC only
        System.err.println("Bug: Player class is calling method exclusive for NPC (npcAttack).");
        return null;
    }

    public void wonBattle() {
        for (Creature pet : battlePets) {
            pet.gainEXP(100);
        }
    }

    public void lostBattle() {
        for (Creature pet : battlePets) {
            pet.gainEXP(30);
        }

    }

}