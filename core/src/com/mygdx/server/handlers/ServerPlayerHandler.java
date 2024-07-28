package com.mygdx.server.handlers;

import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServerPlayerHandler {

    public static Map<String, Connection> connectionTable;


    public ServerPlayerHandler() {
        connectionTable = new HashMap<>();
    };

    public static void addPlayer(String userId, Connection connection) {
        connectionTable.put(userId, connection);
    }


    public static Connection getConnectionById(String id) {
        return connectionTable.get(id);
    }

}
