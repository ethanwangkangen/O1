package com.mygdx.global;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.entities.Creature;

public class HealthBar extends ProgressBar {

    /**
     * creates a basic healthBar
     * width 100 : height 20 ratio recommended
     *
     * @param width of the health bar
     * @param height of the health bar
     */

    public HealthBar(int width, int height, Creature pet) {
        super(0f, pet.getMaxHealth(), 0.02f, false, new ProgressBarStyle());
        getStyle().background = getColoredDrawable(width, height, Color.RED);
        getStyle().knob = getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = getColoredDrawable(width, height, Color.GREEN);

        setWidth(width);
        setHeight(height);

        setAnimateDuration(0.0f);
        setValue(Math.max(pet.getHealth(), 0));

        setAnimateDuration(1f); // Set animate duration for future updates
    }

    public void setHealth(int health) {
        super.setValue(health);
    }

    public Drawable getColoredDrawable(int width, int height, Color color) {

        /**
         * Creates an image of determined size filled with determined color.
         *
         * @param width of an image.
         * @param height of an image.
         * @param color of an image fill.
         * @return {@link Drawable} of determined size filled with determined color.
         */

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        pixmap.dispose();

        return drawable;
    }
}