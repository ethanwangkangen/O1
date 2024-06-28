package com.mygdx.game.entities;

public class NPCMouseHunter extends Creature {

    private int dmgFactor = 2;

    public NPCMouseHunter() {
        super(1000, 1000, "Mouse Hunter", Element.EARTH);
        skill1 = new Skill("Nibble", 20 * dmgFactor, Element.EARTH);
        skill2 = new Skill("Poke", 50 * dmgFactor, Element.EARTH);
        skill3 = new Skill("Tail swipe", 30 * dmgFactor, Element.EARTH);
        setType("MouseHunter");
        setLevel(0);
    }

    @Override
    public void gainEXP(int i) {
        // so that NPC does not level up
    }
}