package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.io.Serializable;
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
    private String path;

    //private ArrayList<Skill> skillList = new ArrayList();
    private Skill skill1 = null;
    private Skill skill2 = null;
    private Skill skill3 = null;
    private Skill[] skills = {skill1, skill2, skill3};

    private transient SpriteBatch batch;

    public Skill[] getSkills() {
        return skills;
    }

    public Creature(int health, int mana, String name, String path) {
        this.maxhealth = health;
        this.health = health;
        this.maxmana = mana;
        this.mana = mana;
        this.path = path;
        this.alive = true;
        this.level = 1;
        this.name = name;
        this.texturePath = new Texture(path);
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

    public void loadTexture() {
        texturePath = new Texture(path);
    }

    public Texture getTexturePath() {
        return texturePath;
    }

    public int getMaxhealth() {
        return maxhealth;
    }

    public int getHealth() {
        return health;
    }

}
