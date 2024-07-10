package com.mygdx.game.entities;

public class Froggy extends Creature {
    public Froggy() {
        super(100, 100, "Froggy", Element.WATER);
        skill1 = new Skill("Jump", 10, Element.WATER, Skill.Status.NIL);
        skill2 = new Skill("Acid", 30, Element.WATER, Skill.Status.STUN);
        skill3 = null;
        setType("Froggy");
    }
}
