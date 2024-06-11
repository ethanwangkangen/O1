package com.mygdx.global;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;

/**
 * Image does not support flipping of graphics which we need for the MysticWoods assets
 * because we need to flip the graphics to make the characters look left or right.
 *
 * The draw function is the same as the one of Image but with support to draw a flipped version of the drawable.
 */
public class FlipImage extends Image {
    private boolean flipX = false;

    public boolean isFlipX() {
        return flipX;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        TransformDrawable toDraw = (TransformDrawable) getDrawable();
        if (toDraw != null && (getScaleX() != 1f || getScaleY() != 1f || getRotation() != 0f)) {
            toDraw.draw(
                    batch,
                    flipX ? getX() + getImageX() * getScaleX() : getX() + getImageX(),
                    getY() + getImageY(),
                    getOriginX() - getImageX(),
                    getOriginY() - getImageY(),
                    getImageWidth(),
                    getImageHeight(),
                    flipX ? -getScaleX() : getScaleX(),
                    getScaleY(),
                    getRotation()
            );
        } else {
            if (toDraw != null) {
                toDraw.draw(
                        batch,
                        flipX ? getX() + getImageX() + getImageWidth() * getScaleX() : getX() + getImageX(),
                        getY() + getImageY(),
                        flipX ? -getImageWidth() * getScaleX() : getImageWidth() * getScaleX(),
                        getImageHeight() * getScaleY()
                );
            }
        }
    }
}
