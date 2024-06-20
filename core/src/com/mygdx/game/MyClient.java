package com.mygdx.game;

import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.MeowmadAli;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Skill;
import com.mygdx.game.events.PlayerJoinServerEvent;
import com.mygdx.game.events.PlayerRequestBattleEvent;
import com.mygdx.game.handlers.UserPlayerHandler;
import com.mygdx.game.listeners.UserEventListener;
import com.mygdx.global.BattleState;

import java.io.IOException;

public class MyClient {
    private static com.esotericsoftware.kryonet.Client myClient;
    public static void connectToServer() {
        DarwinsDuel.client = new com.esotericsoftware.kryonet.Client();
        myClient = DarwinsDuel.getClient();

        myClient.addListener(new UserEventListener());

//        myClient.getKryo().register(AddPlayerEvent.class);
//        myClient.getKryo().register(AttackEvent.class);
//        myClient.getKryo().register(BattleState.class);
//        myClient.getKryo().register(EndBattleEvent.class);
//        myClient.getKryo().register(JoinRequestEvent.class);
//        myClient.getKryo().register(JoinResponseEvent.class);
//        myClient.getKryo().register(StartBattleEvent.class);
//        myClient.getKryo().register(java.util.UUID.class);

        myClient.getKryo().register(Player.class);
        myClient.getKryo().register(Entity.class);
        myClient.getKryo().register(MeowmadAli.class);
        myClient.getKryo().register(Creature.class);
        myClient.getKryo().register(Creature[].class);
        myClient.getKryo().register(Skill.class);
        myClient.getKryo().register(Skill[].class);
        myClient.getKryo().register(BattleState.Turn.class);
        myClient.getKryo().register(BattleState.class);
        myClient.getKryo().register(PlayerJoinServerEvent.class);

        //start the client
        myClient.start();
        // Connect to the server in a separate thread
        Thread connectThread = getThread(myClient);
        try {
            connectThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Thread getThread(com.esotericsoftware.kryonet.Client myClient) {
        Thread connectThread = new Thread(() -> {
            String host = "localhost"; // Server's IP address if not running locally
            int tcpPort = 55555;       // Must match the server's TCP port
            int udpPort = 55577;       // Must match the server's UDP port

            try {
                myClient.connect(5000, host, tcpPort, udpPort);
                System.out.println("Connected to the server.");

                PlayerJoinServerEvent playerJoinServerEvent = new PlayerJoinServerEvent();
                playerJoinServerEvent.userId = UserPlayerHandler.getUserId();
                myClient.sendTCP(playerJoinServerEvent);
                System.out.println("playerJoinServerEvent sent");

            } catch (IOException e) {
                System.err.println("Error connecting to the server: " + e.getMessage());
                e.printStackTrace();
                //errorLabel.setText(e.getMessage());
            }
        });
        connectThread.start(); // Start the thread
        return connectThread;
    }

    public static void sendBattleRequest(String userId) {
        PlayerRequestBattleEvent playerRequestBattleEvent = new PlayerRequestBattleEvent();
        playerRequestBattleEvent.requesterUID = UserPlayerHandler.getUserId();
        playerRequestBattleEvent.opponentUID = userId;
        playerRequestBattleEvent.requesterPlayer = UserPlayerHandler.getPlayer();
        myClient.sendTCP(playerRequestBattleEvent);
    }
}
