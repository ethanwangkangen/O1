package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public abstract class Creature extends Entity implements Serializable{

    public Creature(){}

    private boolean alive;
    private int maxhealth;
    private int health;
    private int maxmana;
    private int mana;
    private transient Texture texturePath; //path to texture file
    private int level;
    private String name;
    public String path;

    //private ArrayList<Skill> skillList = new ArrayList();
    public Skill skill1;
    public Skill skill2;
    public Skill skill3;
    public Skill[] skills = {skill1, skill2, skill3};

    private transient SpriteBatch batch;

    public Skill[] getSkills() {
        return skills;
    }

    public Creature(int health, int mana, String name, String path) {
        this.maxhealth = health;
        this.health = health;
        this.maxmana = mana;
        this.mana = mana;
        this.alive = true;
        this.level = 1;
        this.name = name;
        this.path = path;
        //this.texturePath = new Texture(path);
    }

    //public abstract void attack1();
    //public abstract void attack2();
    //public abstract void attack3();

    public void takeDamage(Skill skill) {
        int x = skill.getDamage();
        this.health -= x;
        if (this.health <= 0) {
            this.health = 0;
            this.alive = false;
        }
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public boolean isAlive() {
        return alive;
    }

//    public void addSkill(int damage) {
//        Skill newSkill = new Skill(damage);
//        skillList.add(newSkill);
//    }

    public void render(SpriteBatch batch) {
        //batch.draw(texturePath, xpos, ypos, 100, 100);
    }



    public void loadTexture(Runnable callback) {
        System.out.println("here.");
        Gdx.app.postRunnable(() -> {
            try {
                System.out.println("loading texture of creature");
                System.out.println("path is: " + path);
                this.texturePath = new Texture(path);
                if (this.texturePath == null) {
                    System.out.println("texturePath is null????");
                } else {
                    System.out.println("texturePath ok");
                }
                if (callback != null) {
                    callback.run();
                }
                System.out.println("finished loading creature texture");

            } catch (Exception e) { // Catching general Exception for simplicity
                System.out.println(e.getMessage());
                System.out.println("Creature texture not loaded");
            }
        });

    }
    public Texture getTexturePath() {
        if (texturePath == null) {
            System.out.println("cannot find texturePath");
        }
        return texturePath;
    }

    public int getMaxhealth() {
        return maxhealth;
    }

    public int getHealth() {
        return health;
    }

}
