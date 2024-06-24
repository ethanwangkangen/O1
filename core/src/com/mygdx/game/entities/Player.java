package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.DarwinsDuel;

import java.io.Serializable;
import java.util.*;


public class Player extends Entity implements Serializable{

    //private Texture texture;
    public String username;

    public ArrayList<Creature> battlePets = new ArrayList<>();
    public ArrayList<Creature> reservePets = new ArrayList<>();

    private transient UUID id;
    private String idString;
    private transient Texture texturePath;
    private String path;
    private PetNum currentPetNum;

    public enum PetNum {
        PET1,
        PET2,
        PET3
    }

    public Player() {
        this.id = UUID.randomUUID();
        this.idString = id.toString();
        path = "player1(1).png";
        currentPetNum = PetNum.PET1;
        battlePets.add(new Froggy());
        battlePets.add(new MeowmadAli());
        battlePets.add(new CrocLesnar());
        reservePets.add(new BadLogic());
    } //no arg constructor for serialisation

    public ArrayList<Creature> getBattlePets() {
        return battlePets;
    }
    public ArrayList<Creature> getReservePets() {return reservePets;}

    //int skill (0, 1, or 2): corresponds to the skill used
    public Boolean takeDamage(Skill skill) {
        // returns true if petchange (ie a petNum has died)

        getCurrentPet().takeDamage(skill);
        if (!getCurrentPet().isAlive()) {
            changeNextPet();
            return true;
        }
        return false;

    }

    public boolean isAlive() {
        for (Creature pet : battlePets) {
            if (pet.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public UUID getId() {
        return id;
    }

    public String getIdString() {return idString;}

    public Creature getCurrentPet() {
        return battlePets.get(currentPetNum.ordinal());
    }

    public PetNum getPet() {
        return currentPetNum;
    }

    // consider replacing battlePets array to reservePets array in future
    // to better display petNum screen
//    public void switchpet(int target) {
//        CurrentPet = battlePets[target];
//    }

//    public void loadTexture() {
//        texturePath = new Texture(path);
//    }
    public Texture getTexture() {
        return DarwinsDuel.getInstance().getAssetManager().get(path, Texture.class);
    }

    public void Move() {
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) xpos -= 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) xpos += 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) ypos += 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) ypos -= 200 * Gdx.graphics.getDeltaTime();
    }


    public String username() {
        return this.username;
    }

//    public void loadTextures(Runnable callback) {
//        AtomicInteger loadedCreatureCount = new AtomicInteger(0);
//
//        // Load textures for each petNum
//        int petNum = battlePets.size() + reservePets.size();
//
//        for (Creature petNum : battlePets) {
//            petNum.loadTexture(() -> {
//                if (loadedCreatureCount.incrementAndGet() == petNum) {
//                    callback.run();
//                }
//            });
//        }
//        for (Creature petNum : reservePets) {
//            petNum.loadTexture(() -> {
//                if (loadedCreatureCount.incrementAndGet() == petNum) {
//                    callback.run();
//                }
//            });
//        }
//    }

    public void changeCurrentPet(PetNum petNum) {
        if (isValidPet(petNum)) {
            currentPetNum = petNum;
        }
    }

    private boolean isValidPet(PetNum petNum) {
        // checks if list has the petNum, or if its empty
        int index = petNum.ordinal(); // Assuming PET1 is 0, PET2 is 1, PET3 is 2
        return index < battlePets.size();
    }

    public void changeNextPet() {
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
        // to update petNum info during battle
        System.out.println("battlePets size: " + battlePets.size());
        System.out.println("player battlePets size: " + player.getBattlePets().size());

        for (int i = 0; i < battlePets.size(); i++) {
            Creature pet = battlePets.get(i);
            Creature playerPet = player.getBattlePets().get(i);
            if (pet != null && playerPet != null) {
                pet.update(playerPet);
            }
        }
        changeCurrentPet(player.getPet());
    }

    public void updatePets(ArrayList<Creature> pets1, ArrayList<Creature> pets2) {
        // to update petNum info after PetChangeScreen
        battlePets = pets1;
        reservePets = pets2;

        System.out.print("Updating pets: ");
        for (Creature pet : pets1) {
            System.out.println(pet.getName() + ", ");
        }
        System.out.println();
    }

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
