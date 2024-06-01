package com.mygdx.game.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.entities.Player;

import java.util.UUID;

public class PlayerHandler {
    private static Player player;

    public static UUID getId() {
        return player.getId();
    }

    public static String getUsername() {
        return player.username();
    }

    public static void updatePlayer(Player update) {
        player = update;
    }

    public static Texture getTexture() {
        player.loadTexture();
        return player.getTexture();
    }

    // used to store info about player

}