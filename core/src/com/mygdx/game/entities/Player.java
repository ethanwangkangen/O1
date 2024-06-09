package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryonet.Connection;
import com.mygdx.global.ChangePetEvent;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends Entity implements Serializable{

    //private Texture texture;
    public String username;
    public Creature pet1;
    public Creature pet2;
    public Creature pet3;
    private transient UUID id;
    private String idString;
    private transient Texture texturePath;
    private String path;
    private Pet currentPet;

    private Creature[] pets = {pet1, pet2, pet3};

    public enum Pet {
        PET1,
        PET2,
        PET3
    }


    //int skill (0, 1, or 2): corresponds to the skill used
    public void takeDamage(Skill skill) {
        if (currentPet == Pet.PET1) {
            pet1.takeDamage(skill);
            if (!pet1.isAlive()) {
                changeNextPet();
            }
        } else if (currentPet == Pet.PET2) {
            pet2.takeDamage(skill);
            if (!pet2.isAlive()) {
                changeNextPet();
            }
        } else if (currentPet == Pet.PET3) {
            pet3.takeDamage(skill);
            if (!pet3.isAlive()) {
                changeNextPet();
            }
        }

    }

    public boolean isAlive() {
        if (pet1 != null) {
            return pet1.isAlive();
        }
        return false;
    }

    public UUID getId() {
        return id;
    }
    public String getIdString() {return idString;}
    public Creature getCurrentPet() {
        if (currentPet == Pet.PET1) {
            return pet1;
        } else if (currentPet == Pet.PET2) {
            return pet2;
        } else {
            return pet3;
        }
    }

    // consider replacing pets array to reservePets array in future
    // to better display pet screen
//    public void switchpet(int target) {
//        CurrentPet = pets[target];
//    }

    public Player() {
        this.pet1 = new MeowmadAli();
        this.pet2 = new CrocLesnar();
        this.id = UUID.randomUUID();
        this.idString = id.toString();
        path = "player1(1).png";
        currentPet = Pet.PET1;
    } //no arg constructor for serialisation

    public void loadTexture() {
        texturePath = new Texture(path);
    }
    public Texture getTexture() {
        return texturePath;
    }

    public void Move() {
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) xpos -= 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) xpos += 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) ypos += 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) ypos -= 200 * Gdx.graphics.getDeltaTime();
    }


    public String username() {
        return this.username;
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

}
