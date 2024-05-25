package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Creature extends Entity {


    private boolean alive;
    private int health;
    private int mana;
    private Texture texturePath; //path to texture file

    private SpriteBatch batch;

    public Creature(int health, int mana, Texture texturePath) {
        this.health = health;
        this.mana = mana;
        this.texturePath = texturePath;
        this.alive = true;
    }

    public abstract void attack1();

    public void takeDamage(int x) {
        this.health -= x;
        if (this.health <= 0) {
            this.health = 0;
            this.alive = false;
        }
    }

    public void render(SpriteBatch batch) {

        batch.draw(texturePath, xpos, ypos, 100, 100);

    }
}
