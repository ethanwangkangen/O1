package com.mygdx.global;

import com.mygdx.game.entities.Skill;
import java.util.UUID;
import java.io.Serializable;

public class AttackEvent implements Serializable {
    // consider attack, health reduction, petNum death
    public Skill skill;
    public String id; // of attacker
    public String battleId;

    public AttackEvent(){}

    public AttackEvent(String battleId, String id, Skill skill) {
        this.skill = skill;
        this.id = id;
        this.battleId = battleId;
    }
}
