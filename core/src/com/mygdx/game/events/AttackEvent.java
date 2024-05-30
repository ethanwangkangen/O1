package com.mygdx.game.events;

import com.mygdx.game.entities.Skill;

import java.io.Serializable;

public class AttackEvent implements Serializable {
    // consider attack, health reduction, pet death
    Skill skill;
}
