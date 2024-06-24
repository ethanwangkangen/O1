package com.mygdx.server;

import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.entities.*;
import com.mygdx.game.events.PlayerAcceptBattleEvent;
import com.mygdx.game.events.PlayerAttackEvent;
import com.mygdx.game.events.PlayerChangePetEvent;
import com.mygdx.game.events.PlayerJoinServerEvent;
import com.mygdx.game.events.PlayerRequestBattleEvent;
import com.mygdx.global.EndBattleEvent;
import com.mygdx.global.*;
import com.mygdx.server.handlers.ServerPlayerHandler;
import com.mygdx.server.listeners.ServerEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class ServerFoundation {
    private static Server server;
    public static ServerFoundation instance;
    public static ServerPlayerHandler serverPlayerHandler = new ServerPlayerHandler();

    public static void main(String[] args) {
        ServerFoundation instance = new ServerFoundation();
        instance.startServer();
    }

    public ServerFoundation() {
        this.server = new Server();

        // Add all global events
        server.getKryo().register(PlayerAcceptBattleEvent.class);
        server.getKryo().register(PlayerRequestBattleEvent.class);
        server.getKryo().register(PlayerAttackEvent.class);
        server.getKryo().register(PlayerChangePetEvent.class);
        server.getKryo().register(PlayerJoinServerEvent.class);

        server.getKryo().register(Entity.class);
        server.getKryo().register(MeowmadAli.class);
        server.getKryo().register(CrocLesnar.class);
        server.getKryo().register(Froggy.class);
        server.getKryo().register(Dragon.class);
        server.getKryo().register(Doge.class);
        server.getKryo().register(MouseHunter.class);

        server.getKryo().register(BattleState.class);
        server.getKryo().register(Player.class);
        server.getKryo().register(Player.PetNum.class);
        server.getKryo().register(ArrayList.class);
        server.getKryo().register(Creature.class);
        server.getKryo().register(Skill.class);
        server.getKryo().register(BattleState.Turn.class);
        server.getKryo().register(TextImageButton.class);
        server.getKryo().register(String.class);

        // Add all listeners of server
        server.addListener(new ServerEventListener());
    }

    public void startServer() {
        // Start the server in a separate thread
        Thread serverThread = new Thread(() -> {
            try {
                this.server.bind(55555, 55577); // Arbitrary TCP and UDP ports
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
