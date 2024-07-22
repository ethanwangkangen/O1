package com.mygdx.game.entities;

import java.util.HashMap;
import java.util.Map;

public class MouseHunter extends Creature {

    public MouseHunter() {
        super(100, "Mouse Hunter", Element.EARTH);
        skill1 = new Skill("Nibble", 20, Element.EARTH, Skill.Status.POISON);
        skill2 = new Skill("Poke", 10, Element.EARTH, Skill.Status.STUN);
        skill3 = new Skill("Tail swipe", 10, Element.EARTH, Skill.Status.NIL);
        setType("MouseHunter");
    }
}
