package com.mygdx.global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class AnimationActor extends Actor {
    enum AnimationState {
        IDLE, ATTACK
    }
    protected Animation<TextureRegion> idleAnimation, attackAnimation;
    protected float stateTime;
    private boolean isAttacking = false;
    private float attackDuration = 5.0f;
    protected AnimationState currentState = AnimationState.IDLE;

    public AnimationActor(String texturePathIdle, String jsonPathIdle, String texturePathAttack, String jsonPathAttack) {
        this.idleAnimation = loadAnimation(texturePathIdle, jsonPathIdle);
        this.attackAnimation = loadAnimation(texturePathAttack, jsonPathAttack);;
        this.stateTime = 0f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        if (isAttacking) {
            Gdx.app.log("AnimationActor", "Attack Animation Playing: stateTime = " + stateTime);
            if (stateTime >= attackDuration) {
                isAttacking = false;
                currentState = AnimationState.IDLE;
                stateTime = 0f;
                Gdx.app.log("AnimationActor", "Switching to IDLE state");
            }
        } else {
            Gdx.app.log("AnimationActor", "Idle Animation Playing: stateTime = " + stateTime);
        }
    }

    private Animation<TextureRegion> loadAnimation(String texturePath, String jsonPath) {
        // Load the texture
        Texture spritesheet;
        float frameDuration = 0.0f; // magic.
        spritesheet = new Texture(Gdx.files.internal(texturePath));

        // Parse the JSON file
        JsonReader jsonReader = new JsonReader();
        JsonValue json = jsonReader.parse(Gdx.files.internal(jsonPath));

        // Extract frame data
        JsonValue frames = json.get("frames");

        TextureRegion[] animationFrames = new TextureRegion[frames.size];
        int index = 0;
        for (JsonValue frame : frames) {
            JsonValue frameData = frame.get("frame");
            int x = frameData.getInt("x");
            int y = frameData.getInt("y");
            int w = frameData.getInt("w");
            int h = frameData.getInt("h");
            animationFrames[index++] = new TextureRegion(spritesheet, x, y, w, h);
            if (index == 1) { // Assume all frames have the same duration
                frameDuration = frame.getInt("duration") / 1000f; // Convert milliseconds to seconds
            }
        }

        // Create the animation
        Animation<TextureRegion> animation = new Animation<>(frameDuration, animationFrames);
        return animation;
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame;
        if (currentState == AnimationState.ATTACK) {
            currentFrame = attackAnimation.getKeyFrame(stateTime, false);
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    public void startAttack() {
        currentState = AnimationState.ATTACK;
        System.out.println("ATTACK ANIMATION");
        stateTime = 0f;
        isAttacking = true;
    }

}
