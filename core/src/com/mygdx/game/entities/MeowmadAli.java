package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;

public class MeowmadAli extends Creature{
    public MeowmadAli() {
        super(100, 100, "MeowmadAli");
        skill1 = new Skill("Cat Scratch", 20);
        skill2 = new Skill("Bite", 20);
        skill3 = null;
        setType("MeowmadAli");
    }

}
