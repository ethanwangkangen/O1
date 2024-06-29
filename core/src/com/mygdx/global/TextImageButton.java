package com.mygdx.global;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.handlers.TextureHandler;

public class TextImageButton extends ImageTextButton {
    Creature pet = null;
    public TextImageButton(String text, Skin skin, Texture texture) {
        super(text, skin);
        clearChildren();
        add(new Image(texture)).pad(5);
        add(getLabel());
    }
    public TextImageButton(String text, Skin skin) {
        super(text, skin);
        clearChildren();
        add(getLabel());
    }
//    public TextImageButton(Creature pet, Skin skin) {
//        super(pet.getName(), skin);
//        clearChildren();
//        add(new Image(TextureHandler.getInstance().getTexture(pet.getType())));
//        add(getLabel());
//        this.pet = pet;
//    }

    public TextImageButton(Creature pet, Skin skin) {
        super(pet.getName(), skin);
        this.pet = pet;

        // Create labels
        Label nameLabel = new Label(pet.getName(), skin);
        Label additionalLabel = new Label("(" + pet.getLevel() + ") " + pet.element, skin);

        // Clear children from the button
        clearChildren();

        // Create an image from the pet's texture
        Image petImage = new Image(new TextureRegion(TextureHandler.getInstance().getTexture(pet.getType())));

        // Use a Table to arrange the elements
        Table table = new Table();
        Table textTable = new Table();

        textTable.add(nameLabel).row();
        textTable.add(additionalLabel).row();

        table.add(petImage);
        table.add(textTable);
        // Add the table to the button
        add(table).expand().fill();
    }

    public Creature getPet() {
        return this.pet;
    }
    }
