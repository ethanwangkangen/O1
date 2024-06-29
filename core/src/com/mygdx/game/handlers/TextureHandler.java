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
        manager.load("badlogic.jpg", Texture.class);
        manager.load("croc lesnar.png", Texture.class);
        manager.load("froggy.png", Texture.class);
        manager.load("meowmad_ali.png", Texture.class);
        manager.load("doge.png", Texture.class);
        manager.load("dragon.png", Texture.class);
        manager.load("mouse hunter.png", Texture.class);
        manager.load("meowmad_ali.png", Texture.class);

        manager.load("player1.png", Texture.class);
    }

    public Texture getTexture(String type) {
        switch (type) {
            case "MeowmadAli":
                return this.getAssetManager().get("meowmad_ali.png", Texture.class);
            case "CrocLesnar":
                return this.getAssetManager().get("croc lesnar.png", Texture.class);
            case "Doge":
                return this.getAssetManager().get("doge.png", Texture.class);
            case "MouseHunter":
                return this.getAssetManager().get("mouse hunter.png", Texture.class);
            case "Dragon":
                return this.getAssetManager().get("dragon.png", Texture.class);
            case "Froggy":
                return this.getAssetManager().get("froggy.png", Texture.class);
        }
        return null; //change this
    }

    public AssetManager getAssetManager() {
        return manager;
    }
}
