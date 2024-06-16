package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


public class Player extends Entity implements Serializable{

    //private Texture texture;

    /**
     * Username to be displayed. Not unique. DO NOT use for identification!
     */
    public String username;
    public Creature pet1;
    public Creature pet2;
    public Creature pet3;

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
        this.pet1 = new MeowmadAli();
        this.pet2 = new CrocLesnar();
        path = "player1(1).png";
        currentPet = Pet.PET1;
    } //no arg constructor for serialisation

    //int skill (0, 1, or 2): corresponds to the skill used
    public Boolean takeDamage(Skill skill) {
        // returns true if petchange (ie a pet has died)
        if (currentPet == Pet.PET1) {
            pet1.takeDamage(skill);
            if (!pet1.isAlive()) {
                changeNextPet();
                return true;
            }
        } else if (currentPet == Pet.PET2) {
            pet2.takeDamage(skill);
            if (!pet2.isAlive()) {
                changeNextPet();
                return true;
            }
        } else if (currentPet == Pet.PET3) {
            pet3.takeDamage(skill);
            if (!pet3.isAlive()) {
                changeNextPet();
                return true;
            }
        }
        if (currentPet == Pet.PET2) {
            System.out.println("Pet 2 is current pet");
        }
        return false;
    }

    public boolean isAlive() {
        if (pet1 != null && pet1.isAlive()) {
            return true;
        } else if (pet2 != null && pet2.isAlive()) {
            return true;
        } else if (pet3 != null && pet3.isAlive()) {
            return true;
        }
        return false;
    }

    public Creature getCurrentPet() {
        if (currentPet == Pet.PET1) {
            return pet1;
        } else if (currentPet == Pet.PET2) {
            return pet2;
        } else {
            return pet3;
        }
    }

    public Pet getPet() {
        return currentPet;
    }

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
        if (pet1 != null) {
            pet1.loadTexture(() -> {
                if (loadedCreatureCount.incrementAndGet() == this.getNumPets()) {
                    // All creatures' textures are loaded, invoke callback
                    callback.run();
                }
            });
        }

        if (pet2 != null) {
            pet2.loadTexture(() -> {
                if (loadedCreatureCount.incrementAndGet() == this.getNumPets()) {
                    // All creatures' textures are loaded, invoke callback
                    callback.run();
                }
            });
        }

        if (pet3 != null) {
            pet3.loadTexture(() -> {
                if (loadedCreatureCount.incrementAndGet() == this.getNumPets()) {
                    // All creatures' textures are loaded, invoke callback
                    callback.run();
                }
            });
        }
    }

    public void changePet(Pet pet) {
        if (pet == Pet.PET1 && pet1 != null) {
            currentPet = Pet.PET1;
        } else if (pet == Pet.PET2 && pet2 != null) {
            currentPet = Pet.PET2;
        } else if (pet == Pet.PET3 && pet3 != null) {
            currentPet = Pet.PET3;
        }
    }

    public void changeNextPet() {
        if (pet1 != null && pet1.isAlive()) {
            changePet(Pet.PET1);
        } else if (pet2 != null && pet2.isAlive()) {
            changePet(Pet.PET2);
        } else if (pet3 != null && pet3.isAlive()) {
            changePet(Pet.PET3);
        }
    }

    public int getNumPets() {
        int numPets = 0;
        if (pet1 != null) {
            numPets += 1;
        }
        if (pet2 != null) {
            numPets += 1;
        }
        if (pet3 != null) {
            numPets += 1;
        }
        return numPets;
    }

    public void update(Player player) {
        if (pet1 != null) {
            pet1.update(player.pet1);
        }
        if (pet2 != null) {
            pet2.update(player.pet2);
            System.out.println("Crocs health is:" + player.pet2.getHealth());
        }
        if (pet3 != null) {
            pet3.update(player.pet3);
        }
        changePet(player.getPet());
    }
}
