package com.mygdx.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.mygdx.game.entities.Player;

public class PlayerHandler {

    static public Player player1;
    static public Player player2;

    public PlayerHandler() {
        player1 = null;
        player2 = null;
    };

    public void createPlayer(Player player) {
        if (player1 == null) {
            player1 = player;
        } else if (player2 == null) {
            player2 = player;
        }
    }


    /*public Player getPlayerByConnection(Connection connection) {
        if (player1.getConnection = connection) {
            return player1;
        } else if (player2.getConnection = connection) {
            return player2;
        } else {
            return null;
            // error
        }
    }*/
}
