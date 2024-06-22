package com.mygdx.game.entities;

public class Froggy extends Creature {
    public Froggy() {
        super(100, 100, "Froggy", "froggy.png");
        skill1 = new Skill("Jump", 10);
        skill2 = new Skill("Acid", 30);
        skill3 = null;
        setType("Froggy");
    }
}
