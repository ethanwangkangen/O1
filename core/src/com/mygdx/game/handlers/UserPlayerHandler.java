package com.mygdx.game.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.entities.Player;


public class PlayerHandler {
    private static Player player;

    public static String getUserId() {return player.getUserId(); }

    public static String getUsername() {
        return player.getUsername();
    }

    public static void updatePlayer(Player update) {
        player = update;
    }

    public static Texture getTexture() {
        player.loadTexture();
        return player.getTexture();
    }

    public static Player getPlayer() {
        return player;
    }

    public static void updateIdOfPlayer(String id) {
        player.setUserId(id);
    }


}