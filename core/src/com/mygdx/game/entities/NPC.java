package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.DarwinsDuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class NPC extends Player implements Serializable {

    public String username = "npc";
    public String path = "player1";

    public NPC() {
        super(); // Call the parent class constructor

        // Clear the battlePets list and add only one pet
        this.battlePets.clear();
        this.battlePets.add(new NPCDragon());
    }

    public NPC(Creature pet) {
        super(); // Call the parent class constructor

        // Clear the battlePets list and add only one pet
        this.battlePets.clear();
        this.battlePets.add(pet);
    }

    @Override
    public Boolean takeDamage(Skill skill) {
        // returns true if petchange (ie a petNum has died)
        getCurrentPet().takeDamage(skill);

        if (!getCurrentPet().isAlive()) {
            // pet has died
            return true;
        }

        // pet has not died
        return false;

    }

    @Override
    public boolean isAlive() {
        return battlePets.get(0).isAlive();
    }

    @Override
    public Creature getCurrentPet() {
        return battlePets.get(0);
    }

    @Override
    public Skill npcAttack(int turns) {
        Creature npcPet = battlePets.get(0);
        if (npcPet.skill1 == null || npcPet.skill2 == null || npcPet.skill3 == null) {
            System.out.println("One of the NPC skills is null");
            return null;
        }

        // only for NPC use
        // NPC attacks; skill used depends on count variable and cycles through skills
        int skillIndex = turns % 3; // This will cycle between 0, 1, and 2
        if (skillIndex == 0 && battlePets.get(0).skill3 != null) {
            return battlePets.get(0).skill3;
        } else if (skillIndex == 1 && battlePets.get(0).skill2 != null) {
            return battlePets.get(0).skill2;
        } else {
            return battlePets.get(0).skill1;
        }
    }

}
