package com.mygdx.game.entities;

import java.util.HashMap;
import java.util.Map;

public class Doge extends Creature {

    public Doge() {
        super(100, 100, "Doge", "doge.png");
        skill1 = new Skill("Bark", 20);
        skill2 = new Skill("Smile", 50);
        skill3 = new Skill("Rug pull", 30);
        setType("Doge");
    }
}
