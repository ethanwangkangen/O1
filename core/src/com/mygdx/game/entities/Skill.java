package com.mygdx.game.entities;

public class Skill {
    private String name;
    private int damage;

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
}
