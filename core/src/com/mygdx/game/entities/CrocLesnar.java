package com.mygdx.game.entities;

public class CrocLesnar extends Creature {

    public CrocLesnar() {
        super(100, 100, "Croc Lesnar", "croc lesnar.png");
        skill1 = new Skill("Claw Scratch", 20);
        skill2 = null;
        skill3 = null;
    }

}