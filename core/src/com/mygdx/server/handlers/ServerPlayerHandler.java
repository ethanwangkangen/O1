package com.mygdx.server.handlers;

import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerHandler {

    public static Map<String, Connection> connectionTable;


    public PlayerHandler() {
        connectionTable = new HashMap<>();
    };

    public static void addPlayer(String userId, Connection connection) {
        connectionTable.put(userId, connection);
    }


    public static Connection getConnectionById(String id) {
        return connectionTable.get(id);
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
