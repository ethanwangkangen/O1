package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import java.io.Serializable;

public class Player extends Entity implements Serializable{

    //private Texture texture;

    public Creature pet1 = null;
    private Creature pet2 = null;
    private Creature pet3 = null;

    //private Creature[] pets = {pet1, pet2, pet3};
    //private Creature CurrentPet = pets[0];

    //int skill (0, 1, or 2): corresponds to the skill used
//    public void attack(Player opponent, int skillNumber) {
//        Skill skill = CurrentPet.getSkills()[skillNumber];
//        opponent.CurrentPet.takeDamage(skill);
//    }

    // consider replacing pets array to reservePets array in future
    // to better display pet screen
//    public void switchpet(int target) {
//        CurrentPet = pets[target];
//    }


    public Player(){}

    public void Move() {
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) xpos -= 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) xpos += 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) ypos += 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) ypos -= 200 * Gdx.graphics.getDeltaTime();
    }


}
