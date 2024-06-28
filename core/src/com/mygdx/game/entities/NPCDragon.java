package com.mygdx.game.entities;

public class NPCDragon extends Creature {

    private int dmgFactor = 2;

    public NPCDragon() {

        super(100, 100, "Dragon", Creature.Element.FIRE);
        skill1 = new Skill("Firebreath", 20 * dmgFactor, Creature.Element.FIRE);
        skill2 = new Skill("Stomp", 50 * dmgFactor, Creature.Element.FIRE);
        skill3 = new Skill("Roast", 30 * dmgFactor, Creature.Element.FIRE);

        setType("Dragon");
        setLevel(0);
    }

    @Override
    public void gainEXP(int i) {
    }
}
