package com.mygdx.game.entities;

import java.util.HashMap;
import java.util.Map;

public class Dragon extends Creature {

    public Dragon() {
        super(100, 100, "Dragon", Element.FIRE);
        skill1 = new Skill("Firebreath", 20, this.element);
        skill2 = new Skill("Stomp", 50, this.element);
        skill3 = new Skill("Roast", 30, this.element);
        setType("Dragon");
    }
}
