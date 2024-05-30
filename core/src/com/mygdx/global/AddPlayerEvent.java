package com.mygdx.global;

import com.mygdx.game.entities.Player;

public class AddPlayerEvent {

    public Player player;

    public AddPlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    //    private String username;
//    String pet1 = null;
//    String pet2 = null;
//    String pet3 = null;
//    String pets[] = {pet1, pet2, pet3};
//
//    public AddPlayerEvent(String username) {
//        this.username = username;
//    }
//
//    public AddPlayerEvent(String username, String pet1) {
//        this.username = username;
//        this.pet1 = pet1;
//    }
//
//    public AddPlayerEvent(String username, String pet1, String pet2) {
//        this.username = username;
//        this.pet1 = pet1;
//        this.pet2 = pet2;
//    }
//
//    public AddPlayerEvent(String username, String pet1, String pet2, String pet3) {
//        this.username = username;
//        this.pet1 = pet1;
//        this.pet2 = pet2;
//        this.pet3 = pet3;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String[] getPets() {
//        return pets;
//    }

}
