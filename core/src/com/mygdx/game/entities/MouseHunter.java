package com.mygdx.game.entities;

import java.util.HashMap;
import java.util.Map;

public class MouseHunter extends Creature {

    public MouseHunter() {
        super(100, 100, "Mouse Hunter", Element.EARTH);
        skill1 = new Skill("Nibble", 20, this.element);
        skill2 = new Skill("Poke", 50, this.element);
        skill3 = new Skill("Tail swipe", 30, this.element);
        setType("MouseHunter");
    }
}
