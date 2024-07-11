package com.mygdx.game.events;

import com.mygdx.game.entities.Skill;

import java.io.Serializable;

public class PlayerSkipEvent implements Serializable {
    // consider attack, health reduction, pet death
    public Skill skill;
    public String id; // of attacker
    public String battleId;

    public PlayerSkipEvent(){}

}
