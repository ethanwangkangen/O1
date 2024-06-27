package com.mygdx.game.entities;

import java.util.HashMap;
import java.util.Map;

public class Doge extends Creature {

    public Doge() {
        super(100, 100, "Doge", Element.EARTH);
        skill1 = new Skill("Bark", 20, this.element);
        skill2 = new Skill("Smile", 50, this.element);
        skill3 = new Skill("Rug pull", 30, this.element);
        setType("Doge");
    }
}
