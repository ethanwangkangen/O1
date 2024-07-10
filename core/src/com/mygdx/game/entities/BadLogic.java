package com.mygdx.game.entities;

public class BadLogic extends Creature {

    public BadLogic() {
        super(100, 100, "Bad Logic", "badlogic.jpg", Element.EARTH);
        skill1 = new Skill("Poor coding practice", 20, Element.EARTH, Skill.Status.NIL);
        skill2 = new Skill("Coding bug", 50, Element.EARTH, Skill.Status.NIL);
        skill3 = new Skill("Error 101", 30, Element.EARTH, Skill.Status.NIL);
    }

}
