package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Creature extends Entity {


    private boolean alive;
    private int health;
    private int mana;
    private Texture texturePath; //path to texture file
    private int level;

    //private ArrayList<Skill> skillList = new ArrayList();
    private Skill skill1 = null;
    private Skill skill2 = null;
    private Skill skill3 = null;
    private Skill[] skills = {skill1, skill2, skill3};

    private SpriteBatch batch;

    public Creature(int health, int mana, Texture texturePath) {
        this.health = health;
        this.mana = mana;
        this.texturePath = texturePath;
        this.alive = true;
        this.level = 1;
    }

    public abstract void attack1();
    //public abstract void attack2();
    //public abstract void attack3();

    public void takeDamage(Skill skill) {
        int x = skill.damage;
        this.health -= x;
        if (this.health <= 0) {
            this.health = 0;
            this.alive = false;
        }
    }

//    public void addSkill(int damage) {
//        Skill newSkill = new Skill(damage);
//        skillList.add(newSkill);
//    }

    public void render(SpriteBatch batch) {

        //batch.draw(texturePath, xpos, ypos, 100, 100);

    }
}
