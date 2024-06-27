package com.mygdx.game.entities;

import java.io.Serializable;

public abstract class Creature extends Entity implements Serializable{


    //private boolean alive;
    private int maxhealth;
    private int health;
    private int maxmana;
    private int mana;
    private int exp;
    private int maxexp;
    private int level;
    private String name;
    private String type;
    public Element element;

    //private ArrayList<Skill> skillList = new ArrayList();
    public Skill skill1;
    public Skill skill2;
    public Skill skill3 ;

    public enum Element {
        FIRE,
        WATER,
        EARTH,
    }


    public Creature(){}

    public Creature(int health, int mana, String name) {
        this.maxhealth = health;
        this.health = health;
        this.maxmana = mana;
        this.mana = mana;
        //this.alive = true;
        this.level = 1;
        this.name = name;
    }

    public Creature(int health, int mana, String name, Element element) {
        this.maxhealth = health;
        this.health = health;
        this.maxmana = mana;
        this.mana = mana;
        this.exp = 0;
        this.maxexp = 10;

        this.level = 1;
        this.name = name;
        this.element = element;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private int calculateModifiedDamage(Skill skill) {
        Element skillElement = skill.element;
        Element petElement = this.element;

        System.out.println("Skill Element: " + skillElement + ", Pet Element: " + petElement);

        if ((petElement == Element.FIRE && skillElement == Element.WATER) ||
                (petElement == Element.WATER && skillElement == Element.EARTH) ||
                (petElement == Element.EARTH && skillElement == Element.FIRE)) {
            // Skill is not effective against pet
            return (int) (1.35 * skill.getDamage());
        } else if ((petElement == Element.FIRE && skillElement == Element.EARTH) ||
                (petElement == Element.WATER && skillElement == Element.FIRE) ||
                (petElement == Element.EARTH && skillElement == Element.WATER)) {
            // Skill is effective against pet
            return (int) (0.65 * skill.getDamage());
        } else {
            // Skill and pet's element are neutral
            return skill.getDamage();
        }
    }

    public void takeDamage(Skill skill) {
        int x = calculateModifiedDamage(skill);
        System.out.println("Skill damage: " + x);
        this.health -= x;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

//    public void addSkill(int damage) {
//        Skill newSkill = new Skill(damage);
//        skillList.add(newSkill);
//    }

//    public Map<String, Object> toMap() {
//        Map<String, Object> result = new HashMap<>();
//        result.put("type", getType());
//        // Add other properties
//        return result;
//    }

//    public void loadTexture(Runnable callback) {
//        Gdx.app.postRunnable(() -> {
//            try {
//                System.out.println("loading texture of creature");
//                System.out.println("path is: " + path);
//                this.texturePath = new Texture(path);
//                if (this.texturePath == null) {
//                    System.out.println("texturePath is null????");
//                } else {
//                    System.out.println("texturePath ok");
//                }
//                if (callback != null) {
//                    callback.run();
//                }
//                System.out.println("finished loading creature texture");
//
//            } catch (Exception e) { // Catching general Exception for simplicity
//                System.out.println(e.getMessage());
//                System.out.println("Creature texture not loaded");
//            }
//        });
//    }

    public int getMaxHealth() {
        return maxhealth;
    }

    public int getHealth() {
        return health;
    }

    public void update(Creature pet) {
        health = pet.getHealth();
//        alive = pet.isAlive();
//      update mana, level, skills in the future
    }

    public void gainEXP(int i) {
        this.exp += i;
        while (exp >= maxexp) {
            this.levelUp();
        }
    }

    private void levelUp() {
        System.out.println(this.name + " has levelled up");

        exp -= maxexp;
        maxexp += 10;

        if (exp < 0) {
            System.err.println("Bug: exp is less than 0");
            exp = 0;
            return;
        }

        this.level += 1;
        this.maxhealth += 20;
        health = maxhealth;
        this.maxmana += 20;
        mana = maxmana;

        if (skill1 != null) {
            skill1.levelUp();
            System.out.println(skill1.name + " has levelled up");
        }
        if (skill2 != null) {
            skill2.levelUp();
            System.out.println(skill2.name + " has levelled up");
        }
        if (skill3 != null) {
            skill3.levelUp();
            System.out.println(skill3.name + " has levelled up");
        }

    }

}
