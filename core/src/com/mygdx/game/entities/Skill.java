package com.mygdx.game.entities;

public class Skill {
    public String name;
    public int damage;
    public Creature.Element element;
    public enum Status {
        ABSORB,
        POISON,
        STUN,
        NIL,
    }
    public Status status;

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public Skill(String name, int damage, Creature.Element element, Status status) {
        this.name = name;
        this.damage = damage;
        this.element = element;
        this.status = status;
    }

    public Skill() {}

    public void levelUp() {
        this.damage += 1;
    }

}
