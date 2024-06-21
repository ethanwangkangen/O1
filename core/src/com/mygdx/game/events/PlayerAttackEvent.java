package com.mygdx.game.events;

import com.mygdx.game.entities.Skill;

import java.io.Serializable;

public class PlayerAttackEvent implements Serializable {
    // consider attack, health reduction, pet death
    public Skill skill;
    public String id; // of attacker
    public String battleId;

    public PlayerAttackEvent(){}

    public PlayerAttackEvent(String battleId, String id, Skill skill) {
        this.skill = skill;
        this.id = id;
        this.battleId = battleId;
    }
}
