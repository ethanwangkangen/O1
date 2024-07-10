package com.mygdx.game.entities;

public class Froggy extends Creature {
    public Froggy() {
        super(100, 100, "Froggy", "froggy.png", Element.WATER);
        skill1 = new Skill("Jump", 10, Element.WATER, Skill.Status.POISON);
        skill2 = new Skill("Acid", 30, Element.WATER, Skill.Status.POISON);
        skill3 = null;
    }
}
