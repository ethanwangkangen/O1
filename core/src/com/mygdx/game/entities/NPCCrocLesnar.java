package com.mygdx.game.entities;

public class NPCCrocLesnar extends Creature {

    private int dmgFactor = 2;

    public NPCCrocLesnar() {
        super(100, 100, "Croc Lesnar", Element.WATER);
        skill1 = new Skill("Claw Scratch", 20 * dmgFactor, Element.WATER);
        skill2 = new Skill("Croc bite", 50 * dmgFactor, Element.WATER);
        skill3 = new Skill("UFC punch", 30 * dmgFactor, Element.WATER);
        setType("CrocLesnar");
        setLevel(0);
    }

    @Override
    public void gainEXP(int i) {
        // so that NPC does not level up
    }
}