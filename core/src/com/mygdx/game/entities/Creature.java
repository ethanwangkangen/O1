package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DarwinsDuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class Creature extends Entity implements Serializable{


    //private boolean alive;
    private int maxhealth;
    private int health;
    private int maxmana;
    private int mana;
    private int level;
    private String name;
    private String type;

    //private ArrayList<Skill> skillList = new ArrayList();
    public Skill skill1;
    public Skill skill2;
    public Skill skill3 ;
    public ArrayList<Skill> skills = new ArrayList<>();

    public ArrayList<Skill> getSkills() {
        return skills;
    }


    public Creature(){}

    public Creature(int health, int mana, String name) {
        this.maxhealth = health;
        this.health = health;
        this.maxmana = mana;
        this.mana = mana;
        //this.alive = true;
        this.level = 1;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void takeDamage(Skill skill) {
        int x = skill.getDamage();
        this.health -= x;
//        if (this.health <= 0) {
//            this.health = 0;
//            this.alive = false;
//        }
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

//    public void addSkill(int damage) {
//        Skill newSkill = new Skill(damage);
//        skillList.add(newSkill);
//    }

//    public Map<String, Object> toMap() {
//        Map<String, Object> result = new HashMap<>();
//        result.put("type", getType());
//        // Add other properties
//        return result;
//    }

//    public void loadTexture(Runnable callback) {
//        Gdx.app.postRunnable(() -> {
//            try {
//                System.out.println("loading texture of creature");
//                System.out.println("path is: " + path);
//                this.texturePath = new Texture(path);
//                if (this.texturePath == null) {
//                    System.out.println("texturePath is null????");
//                } else {
//                    System.out.println("texturePath ok");
//                }
//                if (callback != null) {
//                    callback.run();
//                }
//                System.out.println("finished loading creature texture");
//
//            } catch (Exception e) { // Catching general Exception for simplicity
//                System.out.println(e.getMessage());
//                System.out.println("Creature texture not loaded");
//            }
//        });
//    }

    public int getMaxhealth() {
        return maxhealth;
    }

    public int getHealth() {
        return health;
    }

    public void update(Creature pet) {
        health = pet.getHealth();
//        alive = pet.isAlive();
//      update mana, level, skills in the future
    }

}
