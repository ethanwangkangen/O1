package com.mygdx.game.entities;

import java.util.HashMap;
import java.util.Map;

public class MouseHunter extends Creature {

    public MouseHunter() {
        super(100, 100, "Mouse Hunter");
        skill1 = new Skill("Nibble", 20);
        skill2 = new Skill("Poke", 50);
        skill3 = new Skill("Tail swipe", 30);
        setType("MouseHunter");
    }
}
