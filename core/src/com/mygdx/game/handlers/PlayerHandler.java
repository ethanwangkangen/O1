package com.mygdx.game.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerHandler {
    private static Player player;

    public static UUID getId() {
        return player.getId();
    }

    public static String getIdString() {return player.getIdString(); }

    public static String getUsername() {
        return player.username();
    }

    public static void updatePlayer(Player update) {
        player = update;
    }

    public static Player getPlayer() {
        return player;
    }

    public static Texture getTexture() {
        player.loadTexture();
        return player.getTexture();
    }

    public static void loadTextures(Runnable callback) {
        try{
            player.loadTextures(callback);
            System.out.println("PlayerHandler textures loading");
        } catch (Exception e) {
            System.err.println("PlayerHandler texture loading error");
        }
    }

    public static ArrayList<Creature> getBattlePets() {
        return player.getBattlePets();
    }
    public static ArrayList<Creature> getReservePets() {
        return player.getReservePets();
    }

    public static void updatePets(ArrayList<Creature> pets1, ArrayList<Creature> pets2) {
        player.updatePets(pets1, pets2);
    }

    // used to store info about player

}