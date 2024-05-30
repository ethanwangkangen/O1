package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;

public class MeowmadAli extends Creature{
    public MeowmadAli() {
        super(100, 100, "MeowmadAli", "meowmad_ali.png", new Texture("meowmad_ali.png"));
    }

    Skill skill1 = new Skill("basic attack", 1);

    public final String name = "MeowmadAli";

}
