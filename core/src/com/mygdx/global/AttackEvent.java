package com.mygdx.global;

import com.mygdx.game.entities.Skill;
import java.util.UUID;
import java.io.Serializable;

public class AttackEvent implements Serializable {
    // consider attack, health reduction, pet death
    public Skill skill;
    public UUID id; // of attacker

    public AttackEvent(){}

    public AttackEvent(UUID id, Skill skill) {
        this.skill = skill;
        this.id = id;
    }
}
