package com.mygdx.game.entities;

public class Skill {
    public String name;
    public int damage;

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public Skill(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
    public Skill() {}
}
