package com.mygdx.game.entities;

public class Froggy extends Creature {
    public Froggy() {
        super(100, 100, "Froggy", Element.WATER);
        skill1 = new Skill("Jump", 10, this.element);
        skill2 = new Skill("Acid", 30, this.element);
        skill3 = null;
        setType("Froggy");
    }
}
