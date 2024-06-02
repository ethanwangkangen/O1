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
    public Creature pet1 = new MeowmadAli();
    private Creature pet2;
    private Creature pet3;
    private transient UUID id;
    private String idString;
    private transient Texture texturePath;
    private String path;


    private Creature[] pets = {pet1, pet2, pet3};
    private Creature currentPet = pet1;

    //int skill (0, 1, or 2): corresponds to the skill used
    public void takeDamage(Skill skill) {
        currentPet.takeDamage(skill);
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
        return currentPet;
    }

    // consider replacing pets array to reservePets array in future
    // to better display pet screen
//    public void switchpet(int target) {
//        CurrentPet = pets[target];
//    }


    public Player(String username){
        this.username = username;
        //this.pet1 = new MeowmadAli();
        this.id = UUID.randomUUID();
        this.idString = id.toString();
        path = "player1(1).png";
        //texturePath = new Texture ("player1(1).png");
    }

    public Player() {
        //this.pet1 = new MeowmadAli();
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

    public void loadTextures() { //please change this method name
        if (pet1 != null) {
            pet1.loadTexture();
        }
        if (pet2 != null) {
            pet2.loadTexture();
        }
        if (pet3 != null) {
            pet3.loadTexture();
        }

    }
}
