package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryonet.Connection;

import java.io.Serializable;
import java.util.UUID;

public class Player extends Entity implements Serializable{

    //private Texture texture;
    private String username;
    public Creature pet1 = null;
    private Creature pet2 = null;
    private Creature pet3 = null;
    private UUID id;
    private transient Texture texturePath;
    private String path;


    private Creature[] pets = {pet1, pet2, pet3};
    private Creature CurrentPet = pets[0];

    //int skill (0, 1, or 2): corresponds to the skill used
    public void takeDamage(Skill skill) {
        CurrentPet.takeDamage(skill);
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
    public Creature CurrentPet() {
        return CurrentPet;
    }

    // consider replacing pets array to reservePets array in future
    // to better display pet screen
//    public void switchpet(int target) {
//        CurrentPet = pets[target];
//    }


    public Player(String username){
        this.username = username;
        this.pet1 = new MeowmadAli();
        this.id = UUID.randomUUID();
        path = "player1(1).png";
        texturePath = new Texture ("player1(1).png");
    }

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

    public void loadTextures() {
        for (Creature pet: pets) {
            if (pet != null) {
                pet.loadTexture();
            }
        }
    }
}
