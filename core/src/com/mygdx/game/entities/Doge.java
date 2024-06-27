package com.mygdx.game.entities;

import java.util.HashMap;
import java.util.Map;

public class Doge extends Creature {

    public Doge() {
        super(100, 100, "Doge", Element.EARTH);
        skill1 = new Skill("Bark", 20, Element.EARTH);
        skill2 = new Skill("Smile", 50, Element.EARTH);
        skill3 = new Skill("Rug pull", 30, Element.EARTH);
        setType("Doge");
    }
}
