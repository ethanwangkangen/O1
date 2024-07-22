package com.mygdx.game.entities;

import java.io.Serializable;

public abstract class Creature extends Entity implements Serializable{

    private static final int MAX_LEVEL = 30;

    //private boolean alive;
    private int maxhealth;
    private int health;
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

    public int poisonTurn = 0;
    public int poisonDamage = 0;
    public int stunTurn = 0;

    public Creature(){}

    public Creature(int health, String name) {
        this.maxhealth = health;
        this.health = health;
        //this.alive = true;
        this.level = 1;
        this.name = name;
    }

    public Creature(int health, String name, Element element) {
        this.maxhealth = health;
        this.health = health;
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

    public String getElementString() {
        if (this.element == Creature.Element.FIRE) {
            return "FIRE";
        } else if (this.element == Creature.Element.EARTH) {
            return "EARTH";
        } else {
            return "WATER";
        }
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

    public void absorb(int dmg) {
        this.health += dmg;

        if (this.health > this.maxhealth) {
            this.health = this.maxhealth;
        }
    }

    public Boolean updateStatus() {
        if (stunTurn > 0) {
            stunTurn -= 1;
        }

        if (poisonTurn > 0 && isAlive()) {
            // pet is poisoned
            poisonTurn -= 1;
            this.health -= poisonDamage;
            System.out.println("Poison damage dealt: " + poisonDamage + " to " + getName());
            return !isAlive(); // returns true if pet has died
        }
        return false;
    }

    public Boolean isStunned() {
        // returns true if pet is stunned
        return stunTurn > 0;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return this.level;
    }

    public String getLevelString() {
        if (this.level >= MAX_LEVEL) {
            return "MAX";
        } else {
            return Integer.toString(this.level);
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public int getMaxHealth() {
        return maxhealth;
    }
    public void setMaxHealth(int maxhealth) {
        this.maxhealth = maxhealth;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public void update(Creature pet) {
        this.health = pet.getHealth();
        this.maxhealth = pet.getMaxHealth();

        this.poisonTurn = pet.poisonTurn;
        this.stunTurn = pet.stunTurn;
    }

    public void gainEXP(int i) {
        if (this.level < MAX_LEVEL) {
            this.exp += i;
        }

        while (exp >= maxexp) {
            this.levelUp();
        }
    }

    private void levelUp() {
        if (this.level < MAX_LEVEL) {
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

    public void setLevel(int i) {
        // for NPC use only
        level = i;
    }

}
