package com.mygdx.game.entities;

import java.util.HashMap;
import java.util.Map;

public class CrocLesnar extends Creature {

    public CrocLesnar() {
        super(100, 100, "Croc Lesnar", Element.WATER);
        skill1 = new Skill("Claw Scratch", 20, Element.WATER);
        skill2 = new Skill("Croc bite", 50, Element.WATER);
        skill3 = new Skill("UFC punch", 30, Element.WATER);
        setType("CrocLesnar");
    }
}
