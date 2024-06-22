package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;

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
    private transient Texture texturePath;
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
        reservePets.add(new BadLogic());
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
        if (currentPetNum == PetNum.PET1) {
            return battlePets.get(0);
        } else if (currentPetNum == PetNum.PET2) {
            return battlePets.get(1);
        } else {
            return battlePets.get(2);
        }
    }


    public void loadTexture() {
        texturePath = new Texture(path);
    }
    public Texture getTexture() {
        return texturePath;
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


    public void loadTextures(Runnable callback) {
        AtomicInteger loadedCreatureCount = new AtomicInteger(0);

        // Load textures for each pet
        int petNum = battlePets.size() + reservePets.size();

        for (Creature pet : battlePets) {
            pet.loadTexture(() -> {
                if (loadedCreatureCount.incrementAndGet() == petNum) {
                    callback.run();
                }
            });
        }
        for (Creature pet : reservePets) {
            pet.loadTexture(() -> {
                if (loadedCreatureCount.incrementAndGet() == petNum) {
                    callback.run();
                }
            });
        }
    }

    public void changeCurrentPet(int i) {
        // Change the current pet being used in  battle
        if (isValidPet(i)) {
            if (i == 1) {
                currentPetNum = PetNum.PET1;
            } if (i == 2) {
                currentPetNum = PetNum.PET2;
            } if (i == 3) {
                currentPetNum = PetNum.PET3;
            }
        }
    }

    private boolean isValidPet(int index) {
        return index >= 0 && index < battlePets.size() && battlePets.get(index) != null;
    }

    public void changeNextPet() {
        // Automatically when pet dies



        //don't know what the below is supposed to be lol
//        for (int i = 0; i < battlePets.size(); i++) {
//            Creature pet = battlePets.get(i);
//            if (pet != null && pet.isAlive()) {
//                changePet(Pet.values()[i]);
//                break;
//            }
//        }
    }

    public int getNumPets() {
        return battlePets.size();
    }

    public void update(Player player) {
        for (int i = 0; i < battlePets.size(); i++) {
            Creature pet = battlePets.get(i);
            Creature playerPet = player.getBattlePets().get(i);
            if (pet != null && playerPet != null) {
                pet.update(playerPet);
                if (i == 1) {
                    System.out.println("Crocs health is:" + playerPet.getHealth());
                }
            }
        }
        //changePet(player.getPet());
    }

    public void updatePets(ArrayList<Creature> pets1, ArrayList<Creature> pets2) {
        battlePets = pets1;
        reservePets = pets2;
    }
}