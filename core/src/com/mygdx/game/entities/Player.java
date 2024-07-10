package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.DarwinsDuel;

import java.io.Serializable;
import java.util.*;


public class Player extends Entity implements Serializable{

    //private Texture texture;
    public String username;

    public ArrayList<Creature> battlePets = new ArrayList<>();
    public ArrayList<Creature> reservePets = new ArrayList<>();

    private transient UUID id;
    private String idString;
    private transient Texture texturePath;
    private String path;
    private PetNum currentPetNum;

    public enum PetNum {
        PET1,
        PET2,
        PET3
    }

    public Player() {
        this.id = UUID.randomUUID();
        this.idString = id.toString();
        path = "player1(1).png";
        currentPetNum = PetNum.PET1;
        battlePets.add(new Froggy());
        battlePets.add(new MeowmadAli());
        battlePets.add(new CrocLesnar());
        reservePets.add(new BadLogic());
    } //no arg constructor for serialisation

    public ArrayList<Creature> getBattlePets() {
        return battlePets;
    }
    public ArrayList<Creature> getReservePets() {return reservePets;}

    //int skill (0, 1, or 2): corresponds to the skill used
    public Boolean takeDamage(Skill skill) {
        // returns true if petchange (ie a petNum has died)

        getCurrentPet().takeDamage(skill);
        if (!getCurrentPet().isAlive()) {
            changeNextPet();
            return true;
        }
        return false;
    }

    public void absorb(int dmg) {
        getCurrentPet().absorb(dmg);
    }

    public boolean isAlive() {
        for (Creature pet : battlePets) {
            if (pet.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public UUID getId() {
        return id;
    }

    public String getIdString() {return idString;}

    public Creature getCurrentPet() {
        return battlePets.get(currentPetNum.ordinal());
    }

    public PetNum getPet() {
        return currentPetNum;
    }

    public Texture getTexture() {
        return DarwinsDuel.getInstance().getAssetManager().get(path, Texture.class);
    }

    public String username() {
        return this.username;
    }

    public void changeCurrentPet(PetNum petNum) {
        if (isValidPet(petNum)) {
            currentPetNum = petNum;
        }
    }

    private boolean isValidPet(PetNum petNum) {
        // checks if list has the petNum, or if its empty
        int index = petNum.ordinal(); // Assuming PET1 is 0, PET2 is 1, PET3 is 2
        return index < battlePets.size();
    }

    public void changeNextPet() {
        for (int i = 0; i < battlePets.size(); i++) {
            Creature pet = battlePets.get(i);
            if (pet != null && pet.isAlive()) {
                changeCurrentPet(PetNum.values()[i]);
                break;
            }
        }
    }

    public void update(Player player) {
        // to update pet info during battle
//        System.out.println("battlePets size: " + battlePets.size());
//        System.out.println("player battlePets size: " + player.getBattlePets().size());

        for (int i = 0; i < battlePets.size(); i++) {
            Creature pet = battlePets.get(i);
            Creature playerPet = player.getBattlePets().get(i);
            if (pet != null && playerPet != null) {
                pet.update(playerPet);
            }
        }
        changeCurrentPet(player.getPet());
    }

    public void updatePets(ArrayList<Creature> pets1, ArrayList<Creature> pets2) {
        // to update petNum info after PetChangeScreen
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
