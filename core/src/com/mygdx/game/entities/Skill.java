package com.mygdx.game.entities;

public class Skill {
    public String name;
    public int damage;
    public Creature.Element element;


    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public Skill(String name, int damage, Creature.Element element) {
        this.name = name;
        this.damage = damage;
        this.element = element;
    }

    public Skill(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public Skill() {}

    public void levelUp() {
        this.damage += 1;
    }


}
