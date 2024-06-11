package com.mygdx.global;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class FlippedImage extends Image {

    // Constructor that takes a Texture
    public FlippedImage(Texture texture) {
        super(new TextureRegionDrawable(new TextureRegion(texture)));
        flipHorizontally();
    }

    // Method to flip the texture region horizontally
    private void flipHorizontally() {
        TextureRegionDrawable drawable = (TextureRegionDrawable) getDrawable();
        if (drawable != null) {
            TextureRegion region = drawable.getRegion();
            region.flip(true, false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        TextureRegionDrawable drawable = (TextureRegionDrawable) getDrawable();
        if (drawable != null) {
            TextureRegion region = drawable.getRegion();
            batch.draw(
                    region,
                    getX(),
                    getY(),
                    getOriginX(),
                    getOriginY(),
                    getWidth(),
                    getHeight(),
                    getScaleX(),
                    getScaleY(),
                    getRotation()
            );
        }
    }
}
