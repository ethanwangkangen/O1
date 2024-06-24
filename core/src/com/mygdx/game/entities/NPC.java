package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.DarwinsDuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class NPC extends Player implements Serializable {

    //private Texture texture;
    public String username = "npc";

    private int count = 0;

    /**/

    private transient UUID id;

    public String battleId;

    private String idString;

    private String path;

    public NPC() {
        super(); // Call the parent class constructor
        this.id = UUID.randomUUID();
        this.idString = id.toString();
        this.path = "player1(1).png";

        // Clear the battlePets list and add only one pet
        this.battlePets.clear();
        this.battlePets.add(new CrocLesnar());
    } //no arg constructor for serialisation

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

    public int getCount() {
        // counter to rotate through skills of the pet
        count += 1;
        return count;
    }

    @Override
    public boolean isAlive() {
        return battlePets.get(0).isAlive();
    }

    @Override
    public Creature getCurrentPet() {
        return battlePets.get(0);
    }

    public String getIdString() {return idString;}

}
