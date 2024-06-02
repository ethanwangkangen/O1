package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryonet.Connection;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends Entity implements Serializable{

    //private Texture texture;
    public String username;
    public Creature pet1;
    private Creature pet2;
    private Creature pet3;
    private transient UUID id;
    private String idString;
    private transient Texture texturePath;
    private String path;

    private Creature[] pets = {pet1, pet2, pet3};


    //int skill (0, 1, or 2): corresponds to the skill used
    public void takeDamage(Skill skill) {
        pet1.takeDamage(skill);
    }

    public boolean isAlive() {
        for (Creature pet: pets) {
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
        return this.pet1;
    }

    // consider replacing pets array to reservePets array in future
    // to better display pet screen
//    public void switchpet(int target) {
//        CurrentPet = pets[target];
//    }

    public Player() {
        this.pet1 = new MeowmadAli();
        this.id = UUID.randomUUID();
        this.idString = id.toString();
        path = "player1(1).png";
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
