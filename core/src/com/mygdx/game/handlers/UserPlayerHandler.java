package com.mygdx.game.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.Player;
import com.mygdx.game.interfaces.AuthService;

import java.util.ArrayList;


public class UserPlayerHandler {
    private static Player player;

    public static String getUserId() {return player.getUserId(); }

    public static String getUsername() {
        return player.getUsername();
    }

    public static void updatePlayer(Player update) {
        player = update;
    }

//    public static Texture getTexture() {
//        return player.getTexture();
//    }

//    public static void loadTextures(Runnable callback) {
//        try{
//            player.loadTextures(callback);
//            System.out.println("PlayerHandler textures loading");
//        } catch (Exception e) {
//            System.err.println("PlayerHandler texture loading error");
//        }
//    }

    public static Player getPlayer() {
        return player;
    }

    public static void updateIdOfPlayer(String id) {
        player.setUserId(id);
    }

    public static ArrayList<Creature> getBattlePets() {
        return player.getBattlePets();
    }
    public static ArrayList<Creature> getReservePets() {
        return player.getReservePets();
    }

    public static void updatePets(ArrayList<Creature> pets1, ArrayList<Creature> pets2) {
        // update player info from PetChangeScreen
        player.updatePets(pets1, pets2);
    }

    public static void wonBattle() {
        player.wonBattle();
    }

    public static void lostBattle() {
        player.lostBattle();
    }

    public static void sendToFirebase() {
        DarwinsDuel gameObj = DarwinsDuel.getInstance();
        AuthService authService1;
        authService1 = gameObj.authService;
        authService1.sendPlayerToFirebase(player);
    }

}