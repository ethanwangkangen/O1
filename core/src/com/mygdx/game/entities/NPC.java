package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.DarwinsDuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class NPC extends Player implements Serializable {

    public String username = "npc";
    public String path = "player1";

    private static final Map<String, Class<? extends Creature>> creatureMap = new HashMap<>();
    private static final List<String> creatureTypes = Arrays.asList("CrocLesnar", "Dragon", "Doge");
    static {
        // Register all Creature subclasses here
        creatureMap.put("CrocLesnar", NPCCrocLesnar.class);
        creatureMap.put("Dragon", NPCDragon.class);
        creatureMap.put("Doge", NPCDoge.class);
    }

    public NPC() {
        super(); // Call the parent class constructor
    }

    public void initialise() {
        // Clear the battlePets list and add only one pet
        this.battlePets.clear();
        this.battlePets.add(pickRandomPet());

        System.out.println("NPC pet has been created: " + battlePets.get(0).getName());
    }

    public NPC(Creature pet) {
        super(); // Call the parent class constructor

        // Clear the battlePets list and add only one pet
        this.battlePets.clear();
        this.battlePets.add(pet);
    }

    private static Creature pickRandomPet() {
        Random random = new Random();
        String randomType = creatureTypes.get(random.nextInt(creatureTypes.size()));
        return createNewPet(randomType);
    }

    private static Creature createNewPet(String type) {
        Class<? extends Creature> creatureClass = creatureMap.get(type);
        if (creatureClass != null) {
            try {
                return creatureClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
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
//        if (npcPet.skill1 == null || npcPet.skill2 == null || npcPet.skill3 == null) {
//            System.out.println("One of the NPC skills is null");
//            return null;
//        }

        // only for NPC use
        // NPC attacks; skill used depends on count variable and cycles through skills
        int skillIndex = turns % 3; // This will cycle between 0, 1, and 2
        if (skillIndex == 0 && npcPet.skill3 != null) {
            return npcPet.skill3;
        } else if (skillIndex == 1 && npcPet.skill2 != null) {
            return npcPet.skill2;
        } else {
            return npcPet.skill1;
        }
    }

}
