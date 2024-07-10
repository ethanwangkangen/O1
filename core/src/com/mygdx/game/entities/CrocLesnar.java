package com.mygdx.game.entities;

public class CrocLesnar extends Creature {

    public CrocLesnar() {
        super(100, 100, "Croc Lesnar", "croc lesnar.png", Element.EARTH);
        skill1 = new Skill("Claw Scratch", 20, Element.EARTH, Skill.Status.POISON);
        skill2 = new Skill("Croc bite", 50, Element.EARTH, Skill.Status.POISON);
        skill3 = new Skill("UFC punch", 30, Element.EARTH, Skill.Status.POISON);
    }

}
