package com.mygdx.game.entities;

public class BadLogic extends Creature {

    public BadLogic() {
        super(100, 100, "Bad Logic", "badlogic.png");
        skill1 = new Skill("Poor coding practice", 20);
        skill2 = new Skill("Coding bug", 50);
        skill3 = new Skill("Error 101", 30);
    }

}
