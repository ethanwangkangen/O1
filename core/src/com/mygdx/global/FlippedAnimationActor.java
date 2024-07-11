package com.mygdx.global;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FlippedAnimationActor extends AnimationActor {
    private boolean flipped = true;
    public FlippedAnimationActor(String texturePathIdle, String jsonPathIdle, String texturePathAttack, String jsonPathAttack) {
        super(texturePathIdle, jsonPathIdle, texturePathAttack, jsonPathAttack);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame;
        if (currentState == AnimationState.ATTACK) {
            currentFrame = attackAnimation.getKeyFrame(stateTime, false);
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }

        // Flip the texture region if necessary
        if (flipped) {
            if (!currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        } else {
            if (currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }

        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

}