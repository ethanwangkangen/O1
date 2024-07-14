package com.mygdx.game.entities;

public class NPCDoge extends Creature {

    private int dmgFactor = 2;

    public NPCDoge() {
        super(200, 200, "Doge", Element.EARTH);
        skill1 = new Skill("Bark", 20 * dmgFactor, Element.EARTH, Skill.Status.STUN);
        skill2 = new Skill("Smile", 50 * dmgFactor, Element.EARTH, Skill.Status.NIL);
        skill3 = new Skill("Rug pull", 30 * dmgFactor, Element.EARTH, Skill.Status.ABSORB);
        setType("Doge");
        setLevel(0);
    }

    @Override
    public void gainEXP(int i) {
        // so that NPC does not level up
    }
}
