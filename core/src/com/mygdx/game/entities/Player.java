package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.DarwinsDuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class Player extends Entity implements Serializable{

    //private Texture texture;

    /**
     * Username to be displayed. Not unique. DO NOT use for identification!
     */
    public String username;
    public ArrayList<Creature> battlePets = new ArrayList<>();
    public ArrayList<Creature> reservePets = new ArrayList<>();

    /**
     * Unique ID taken from firebase to identify players.
     * Use this for identification
     */
    public String userId;
//    private transient Texture texturePath;
    public String path = "player1(1).png";
    public PetNum currentPetNum;


    public enum PetNum {
        PET1,
        PET2,
        PET3
    }


    public Player() {
        currentPetNum = PetNum.PET1;
        battlePets.add(new MeowmadAli());
        battlePets.add(new CrocLesnar());
        battlePets.add(new Froggy());

    } //no arg constructor for serialisation

    public ArrayList<Creature> getBattlePets() {
        return battlePets;
    }
    public ArrayList<Creature> getReservePets() {return reservePets;}

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("userId", userId);
        result.put("currentPetNum", currentPetNum.toString());

        List<Map<String, Object>> battlePetsList = new ArrayList<>();
        for (Creature pet : battlePets) {
            battlePetsList.add(pet.toMap());
        }
        result.put("battlePets", battlePetsList);

        List<Map<String, Object>> reservePetsList = new ArrayList<>();
        for (Creature pet : reservePets) {
            reservePetsList.add(pet.toMap());
        }
        result.put("reservePets", reservePetsList);

        return result;
    }

    //int skill (0, 1, or 2): corresponds to the skill used
    public Boolean takeDamage(Skill skill) {
        // Returns true if petchange (ie a pet has died)

        getCurrentPet().takeDamage(skill);
        if (!getCurrentPet().isAlive()) {
            changeNextPet();
            return true;
        }
        return false;
    }

    public void setCurrentPet(PetNum p) {

    }
    public void setBattlePets(ArrayList<Creature> battlePets) {
        this.battlePets = battlePets;
    }

    public void setReservePets(ArrayList<Creature> battlePets) {
        this.reservePets = battlePets;
    }

    public boolean isAlive() {
        for (Creature pet : battlePets) {
            if (pet.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public Creature getCurrentPet() {
        return battlePets.get(currentPetNum.ordinal());
    }


//    public void loadTexture() {
//        texturePath = new Texture(path);
//    }
    public Texture getTexture() {
        return DarwinsDuel.getInstance().getAssetManager().get(path, Texture.class);
    }
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getUserId() {return this.userId;}

    public void setUserId(String id) {
        this.userId = id;
    }


//    public void loadTextures(Runnable callback) {
//        AtomicInteger loadedCreatureCount = new AtomicInteger(0);
//
//        // Load textures for each pet
//        int petNum = battlePets.size() + reservePets.size();
//
//        for (Creature pet : battlePets) {
//            pet.loadTexture(() -> {
//                if (loadedCreatureCount.incrementAndGet() == petNum) {
//                    callback.run();
//                }
//            });
//        }
//        for (Creature pet : reservePets) {
//            pet.loadTexture(() -> {
//                if (loadedCreatureCount.incrementAndGet() == petNum) {
//                    callback.run();
//                }
//            });
//        }
//    }

    public void changeCurrentPet(Player.PetNum petNum) {
        // Change the current pet being used in  battle
        if (isValidPet(petNum)) {
            currentPetNum = petNum;
        }
    }

    private boolean isValidPet(Player.PetNum petNum) {
        // checks if list has the petNum, or if its empty
        int index = petNum.ordinal(); // Assuming PET1 is 0, PET2 is 1, PET3 is 2
        return index < battlePets.size();
    }

    public void changeNextPet() {
        // Change to next available pet when a pet dies

        for (int i = 0; i < battlePets.size(); i++) {
            Creature pet = battlePets.get(i);
            if (pet != null && pet.isAlive()) {
                changeCurrentPet(PetNum.values()[i]);
                break;
            }
        }
    }

    public int getNumPets() {
        return battlePets.size();
    }

    public void update(Player player) {
        // to update pet info during battle
        for (int i = 0; i < battlePets.size(); i++) {
            Creature pet = battlePets.get(i);
            Creature playerPet = player.getBattlePets().get(i);
            if (pet != null && playerPet != null) {
                pet.update(playerPet);
            }
        }
        changeCurrentPet(player.getCurrentPetNum());
    }

    public PetNum getCurrentPetNum() {
        return currentPetNum;
    }

    public void updatePets(ArrayList<Creature> pets1, ArrayList<Creature> pets2) {
        // to update pet info after PetChangeScreen
        battlePets = pets1;
        reservePets = pets2;
    }
}