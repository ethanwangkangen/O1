package com.mygdx.game.entities;

import java.util.HashMap;
import java.util.Map;

public class Dragon extends Creature {

    public Dragon() {
        super(100, 100, "Dragon", "dragon.png");
        skill1 = new Skill("Firebreath", 20);
        skill2 = new Skill("Stomp", 50);
        skill3 = new Skill("Roast", 30);
        setType("Doge");
    }
}
