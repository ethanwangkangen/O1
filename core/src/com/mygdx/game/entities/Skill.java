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


    public String damageDescription;

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

        this.damageDescription = setDamageDescription(damage);
    }

    public String setDamageDescription(int damage) {
        if (damage == 10) {
            return damageDescription = "MILD";
        } else if (damage == 20) {
            return damageDescription = "MODERATE";
        } else if (damage == 30) {
            return damageDescription = "HIGH";
        } else if (damage == 50) {
            return damageDescription = "EXTREME";
        } else {
            return damageDescription = "NIL";
        }
    }

    public Skill() {}

    public void levelUp() {
        this.damage += 1;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public String getStatusDescription() {

        if (status == Status.ABSORB) {
            return "Inflicts " + getDamageDescription() + " damage, and recover a portion of the damage dealt to target as HP.";
        } else if (status == Status.POISON) {
            return "Inflicts " + getDamageDescription() + " damage, and poisons target for 1 turn.\n" +
                    "Poison deals damage equals to a portion of skill's damage each turn.";
        } else if (status == Status.STUN) {
            return "Inflicts " + getDamageDescription() + " damage, and possibly stuns target for 1 turn.\n" +
                    "Pet cannot perform attacks when stunned.";
        } else {
            return "Inflicts " + getDamageDescription() + " damage.";
        }
    }
}
