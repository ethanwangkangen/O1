package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DarwinsDuel;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public abstract class Creature extends Entity implements Serializable{

    public Creature(){}

    //private boolean alive;
    private int maxhealth;
    private int health;
    private int maxmana;
    private int mana;
    private int exp;
    private int maxexp;
    private transient Texture texturePath; //path to texture file
    private int level;
    private String name;
    public String path;
    public Element element;

    //private ArrayList<Skill> skillList = new ArrayList();
    public Skill skill1;
    public Skill skill2;
    public Skill skill3 ;
    public Skill[] skills = {skill1, skill2, skill3};

    private transient SpriteBatch batch;
    public enum Element {
        FIRE,
        WATER,
        EARTH,
    }

    public Skill[] getSkills() {
        return skills;
    }

    public Creature(int health, int mana, String name, String path) {
        this.maxhealth = health;
        this.health = health;
        this.maxmana = mana;
        this.mana = mana;
        this.level = 1;
        this.exp = 0;
        this.maxexp = 10;
        this.name = name;
        this.path = path;
    }

    public Creature(int health, int mana, String name, String path, Element element) {
        this.maxhealth = health;
        this.health = health;
        this.maxmana = mana;
        this.mana = mana;
        this.level = 1;
        this.exp = 0;
        this.maxexp = 10;
        this.name = name;
        this.path = path;
        this.element = element;
    }

    private int calculateModifiedDamage(Skill skill) {
        Element skillElement = skill.element;
        Element petElement = this.element;

        if ((petElement == Element.FIRE && skillElement == Element.WATER) ||
                (petElement == Element.WATER && skillElement == Element.EARTH) ||
                (petElement == Element.EARTH && skillElement == Element.FIRE)) {
            // Skill is not effective against pet
            return (int) (1.35 * skill.getDamage());
        } else if ((petElement == Element.FIRE && skillElement == Element.EARTH) ||
                (petElement == Element.WATER && skillElement == Element.FIRE) ||
                (petElement == Element.EARTH && skillElement == Element.WATER)) {
            // Skill is effective against pet
            return (int) (0.65 * skill.getDamage());
        } else {
            // Skill and pet's element are neutral
            return skill.getDamage();
        }
    }

    public void takeDamage(Skill skill) {
        int x = calculateModifiedDamage(skill);
        System.out.println("Skill damage: " + x);
        this.health -= x;
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

    public void render(SpriteBatch batch) {
        //batch.draw(texturePath, xpos, ypos, 100, 100);
    }



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
    public Texture getTexturePath() {
        try {
            return DarwinsDuel.getInstance().manager.get(path, Texture.class);
        } catch (Exception e) {
            System.err.println("Cannot find texturePath: " + name);
            return null;
        }
    }

    public int getMaxhealth() {
        return maxhealth;
    }

    public int getHealth() {
        return health;
    }

    public void update(Creature pet) {
        health = pet.getHealth();
//        alive = petNum.isAlive();
//      update mana, level, skills in the future
    }

    public void gainEXP(int i) {
        this.exp += i;
        while (exp >= maxexp) {
            this.levelUp();
        }
    }

    private void levelUp() {
        System.out.println(this.name + " has levelled up");

        exp -= maxexp;
        maxexp += 10;

        if (exp < 0) {
            System.err.println("Bug: exp is less than 0");
            exp = 0;
            return;
        }

        this.level += 1;
        this.maxhealth += 20;
        health = maxhealth;
        this.maxmana += 20;
        mana = maxmana;

        if (skill1 != null) {
            skill1.levelUp();
            System.out.println(skill1.name + " has levelled up");
        }
        if (skill2 != null) {
            skill2.levelUp();
            System.out.println(skill2.name + " has levelled up");
        }
        if (skill3 != null) {
            skill3.levelUp();
            System.out.println(skill3.name + " has levelled up");
        }

        // todo increase hp, mana, skill damage
    }

}
