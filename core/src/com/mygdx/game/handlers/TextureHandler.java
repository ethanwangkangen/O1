package com.mygdx.game.handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TextureHandler {
    private static TextureHandler instance = new TextureHandler();
    private AssetManager manager = new AssetManager();

    public static TextureHandler getInstance() {
        return instance;
    }

    public void loadTextures() {
        // UI assets
        manager.load("buttons/uiskin.json", Skin.class);

        // background textures
        manager.load("border.png", Texture.class);
        manager.load("crossedbox.png", Texture.class);
        manager.load("mainscreen.png", Texture.class);
        manager.load("Pixel_art_grass_image.png", Texture.class);

        // Creatures textures
        manager.load("CrocLesnar.png", Texture.class);
        manager.load("froggy.png", Texture.class);
        manager.load("MeowmadAli2.png", Texture.class);
        manager.load("Doge.png", Texture.class);
        manager.load("Dragon.png", Texture.class);
        manager.load("MouseHunter.png", Texture.class);

        manager.load("player1.png", Texture.class);
    }

    public Texture getTexture(String type) {
        switch (type) {
            case "MeowmadAli":
                return this.getAssetManager().get("MeowmadAli2.png", Texture.class);
            case "CrocLesnar":
                return this.getAssetManager().get("CrocLesnar.png", Texture.class);
            case "Doge":
                return this.getAssetManager().get("Doge.png", Texture.class);
            case "MouseHunter":
                return this.getAssetManager().get("MouseHunter.png", Texture.class);
            case "Dragon":
                return this.getAssetManager().get("Dragon.png", Texture.class);
            case "Froggy":
                return this.getAssetManager().get("froggy.png", Texture.class);
        }
        return null; //change this
    }

    public String getAnimationTexture(String type) {
        switch (type) {
            case "MeowmadAli":
                return "MeowmadAli2Idle.png";
            case "CrocLesnar":
                return "CrocLesnarIdle.png";
            case "Doge":
                return "DogeIdle.png";
            case "MouseHunter":
                return "mouseHunterIdle.png";
            case "Dragon":
                return "DragonIdle.png";
            case "Froggy":
                return "froggyIdle.png";
        }
        return null; //change this
    }

    public String getAnimationJson(String type) {
        switch (type) {
            case "MeowmadAli":
                return "MeowmadAli2Idle.json";
            case "CrocLesnar":
                return "CrocLesnarIdle.json";
            case "Doge":
                return "DogeIdle.json";
            case "MouseHunter":
                return "mouseHunterIdle.json";
            case "Dragon":
                return "DragonIdle.json";
            case "Froggy":
                return "froggyIdle.json";
        }
        return null; //change this
    }

    public AssetManager getAssetManager() {
        return manager;
    }
}
