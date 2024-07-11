package com.mygdx.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class AnimationHandler {
    private Texture spritesheet;
    private Animation<TextureRegion> animation;
    private float frameDuration;

    public AnimationHandler(String texturePath, String jsonPath) {
        loadAnimation(texturePath, jsonPath);
    }

    private void loadAnimation(String texturePath, String jsonPath) {
        // Load the texture
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

        animation = new Animation<>(frameDuration, animationFrames);
    }

    public TextureRegion getCurrentFrame(float stateTime) {
        return animation.getKeyFrame(stateTime, true);
    }
    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void dispose() {
        spritesheet.dispose();
    }

}
