package com.mygdx.game.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

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

    public String getDamageDescription() {
        if (damage == 10) {
            return "mild";
        } else if (damage == 20) {
            return "moderate";
        } else if (damage == 30) {
            return "high";
        } else if (damage == 50) {
            return "extreme";
        } else {
            return "Description not available";
        }
    }

    public String getStatusDescription() {

//        if (status == Status.ABSORB) {
//            return "ABSORB";
//        } else if (status == Status.STUN) {
//            return "STUN";
//        } else if (status == Status.POISON) {
//            return "POISON";
//        } else {
//            return "NIL";
//        }


        if (status == Status.ABSORB) {
            return "Inflicts " + getDamageDescription() + " damage, and recover a portion of the damage dealt to the target as HP.";
        } else if (status == Status.POISON) {
            return "Inflicts " + getDamageDescription() + " damage, and poisons the target for 3 turns.\n" +
                    "Poison deals damage equals to a portion of the skill's damage each turn.";
        } else if (status == Status.STUN) {
            return "Inflicts " + getDamageDescription() + " damage, and stuns the target for 3 turns.\n" +
                    "Target cannot attack while stunned. Opponent can still change pets or skip turn.";
        } else {
            return "Inflicts " + getDamageDescription() + " damage.";
        }
    }
}
