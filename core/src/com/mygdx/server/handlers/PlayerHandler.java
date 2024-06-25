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

    public static String getOther(String userId) {
        for (Map.Entry<String, Connection> entry : connectionTable.entrySet()) {
            if (!Objects.equals(entry.getKey(), userId)) {
                return entry.getKey();
            }
        }
        return null;
    }

}