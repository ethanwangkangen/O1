package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;

public class MeowmadAli extends Creature{
    public MeowmadAli() {
        super(100, 100, "MeowmadAli", Element.FIRE);
        skill1 = new Skill("Cat Scratch", 20, Element.FIRE);
        skill2 = new Skill("Bite", 20, Element.FIRE);
        skill3 = null;
        setType("MeowmadAli");
    }

}
