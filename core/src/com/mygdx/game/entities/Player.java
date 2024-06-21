package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


public class Player extends Entity implements Serializable{

    //private Texture texture;

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
    private String userId;
    private transient Texture texturePath;
    private String path;
    private Pet currentPet;

    private Creature[] pets = {pet1, pet2, pet3};

    public enum Pet {
        PET1,
        PET2,
        PET3
    }


    public Player() {
        path = "player1(1).png";
        currentPet = Pet.PET1;
        battlePets.add(new MeowmadAli());
        battlePets.add(new CrocLesnar());
        battlePets.add(new Froggy());
        reservePets.add(new BadLogic());
    } //no arg constructor for serialisation

    public ArrayList<Creature> getBattlePets() {
        return battlePets;
    }
    public ArrayList<Creature> getReservePets() {return reservePets;}

    //int skill (0, 1, or 2): corresponds to the skill used
    public Boolean takeDamage(Skill skill) {
        // returns true if petchange (ie a pet has died)

        getCurrentPet().takeDamage(skill);
        if (!getCurrentPet().isAlive()) {
            changeNextPet();
            return true;
        }
        return false;

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
        if (currentPet == Pet.PET1) {
            return battlePets.get(0);
        } else if (currentPet == Pet.PET2) {
            return battlePets.get(1);
        } else {
            return battlePets.get(2);
        }
    }

    public Pet getPet() {
        return currentPet;
    }

    // consider replacing battlePets array to reservePets array in future
    // to better display pet screen
//    public void switchpet(int target) {
//        CurrentPet = battlePets[target];
//    }

    public void loadTexture() {
        texturePath = new Texture(path);
    }
    public Texture getTexture() {
        return texturePath;
    }
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getUserId() {return this.userId;}
    public void setUserId(String id) {
        this.userId = id;
    }


    public void loadTextures(Runnable callback) {
        AtomicInteger loadedCreatureCount = new AtomicInteger(0);

        // Load textures for each pet
        int petNum = battlePets.size() + reservePets.size();

        for (Creature pet : battlePets) {
            pet.loadTexture(() -> {
                if (loadedCreatureCount.incrementAndGet() == petNum) {
                    callback.run();
                }
            });
        }
        for (Creature pet : reservePets) {
            pet.loadTexture(() -> {
                if (loadedCreatureCount.incrementAndGet() == petNum) {
                    callback.run();
                }
            });
        }
    }

    public void changePet(Pet pet) {
        int index = pet.ordinal();
        if (isValidPet(index)) {
            currentPet = pet;
        }
    }

    private boolean isValidPet(int index) {
        return index >= 0 && index < battlePets.size() && battlePets.get(index) != null;
    }

    public void changeNextPet() {
        for (int i = 0; i < battlePets.size(); i++) {
            Creature pet = battlePets.get(i);
            if (pet != null && pet.isAlive()) {
                changePet(Pet.values()[i]);
                break;
            }
        }
    }

    public int getNumPets() {
        return battlePets.size();
    }

    public void update(Player player) {
        for (int i = 0; i < battlePets.size(); i++) {
            Creature pet = battlePets.get(i);
            Creature playerPet = player.getBattlePets().get(i);
            if (pet != null && playerPet != null) {
                pet.update(playerPet);
                if (i == 1) {
                    System.out.println("Crocs health is:" + playerPet.getHealth());
                }
            }
        }
        changePet(player.getPet());
    }

    public void updatePets(ArrayList<Creature> pets1, ArrayList<Creature> pets2) {
        battlePets = pets1;
        reservePets = pets2;

    }
}
