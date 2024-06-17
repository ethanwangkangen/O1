package com.mygdx.global;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.entities.Creature;

public class TextImageButton extends ImageTextButton {
    Creature pet = null;
    public TextImageButton(String text, Skin skin, Texture texture) {
        super(text, skin);
        clearChildren();
        add(new Image(texture));
        add(getLabel());
    }
    public TextImageButton(String text, Skin skin) {
        super(text, skin);
        clearChildren();
        add(getLabel());
    }
    public TextImageButton(Creature pet, Skin skin) {
        super(pet.getName(), skin);
        clearChildren();
        add(new Image(pet.getTexturePath()));
        add(getLabel());
        this.pet = pet;
    }
    public Creature getPet() {
        return this.pet;
    }

    public void dispose() {
        if (pet.getTexturePath() != null) {
            pet.getTexturePath().dispose();
        }
    }

}
