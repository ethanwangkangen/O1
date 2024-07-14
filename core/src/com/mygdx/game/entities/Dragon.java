package com.mygdx.game.entities;

import java.util.HashMap;
import java.util.Map;

public class Dragon extends Creature {

    public Dragon() {
        super(100, 100, "Dragon", Element.FIRE);
        skill1 = new Skill("Firebreath", 20, Element.FIRE, Skill.Status.STUN);
        skill2 = new Skill("Stomp", 50, Element.FIRE, Skill.Status.NIL);
        skill3 = new Skill("Roast", 30, Element.FIRE, Skill.Status.POISON);
        setType("Dragon");
    }
}
