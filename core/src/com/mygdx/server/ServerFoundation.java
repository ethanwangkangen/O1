package com.mygdx.server;

import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.entities.*;
import com.mygdx.global.BattleState;
import com.mygdx.global.JoinRequestEvent;
import com.mygdx.global.JoinResponseEvent;
import com.mygdx.server.handlers.PlayerHandler;
import com.mygdx.server.listeners.EventListener;

import java.io.IOException;

public class ServerFoundation {
    private static Server server;
    public static ServerFoundation instance;
    public static BattleState battleState = new BattleState();
    public static PlayerHandler players = new PlayerHandler();

    public static void main(String[] args) {
        ServerFoundation instance = new ServerFoundation();
        instance.startServer();
    }

    public ServerFoundation() {
        this.server = new Server();

        // Add all global events
        this.server.getKryo().register(JoinRequestEvent.class);
        this.server.getKryo().register(JoinResponseEvent.class);
        this.server.getKryo().register(BattleState.class);
        this.server.getKryo().register(Player.class);
        this.server.getKryo().register(Entity.class);
        this.server.getKryo().register(MeowmadAli.class);
        this.server.getKryo().register(Creature.class);
        this.server.getKryo().register(Creature[].class);
        this.server.getKryo().register(Skill.class);
        this.server.getKryo().register(BattleState.Turn.class);

        // Add all listeners of server
        this.server.addListener(new EventListener());
    }

    public void startServer() {
        // Start the server in a separate thread
        Thread serverThread = new Thread(() -> {
            try {
                this.server.bind(54455, 54477); // Arbitrary TCP and UDP ports
                this.server.start();
                System.out.println("Server started.");

                // Keep the server running until interrupted
                while (true) {
                    Thread.sleep(1000); // Sleep to avoid consuming too much CPU
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    public static Server getServer() {
        return server;
    }

}
