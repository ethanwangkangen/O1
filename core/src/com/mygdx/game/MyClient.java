package com.mygdx.game;

import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.CrocLesnar;
import com.mygdx.game.entities.Doge;
import com.mygdx.game.entities.Dragon;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Froggy;
import com.mygdx.game.entities.MeowmadAli;
import com.mygdx.game.entities.MouseHunter;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Skill;
import com.mygdx.game.events.PlayerAcceptBattleEvent;
import com.mygdx.game.events.PlayerAttackEvent;
import com.mygdx.game.events.PlayerChangePetEvent;
import com.mygdx.game.events.PlayerJoinServerEvent;
import com.mygdx.game.events.PlayerRequestBattleEvent;
import com.mygdx.game.handlers.UserPlayerHandler;
import com.mygdx.game.listeners.UserEventListener;
import com.mygdx.global.BattleState;
import com.mygdx.global.StartBattleEvent;
import com.mygdx.global.TextImageButton;
import com.mygdx.server.listeners.ServerEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class MyClient {
    private static com.esotericsoftware.kryonet.Client myClient;
    public static void connectToServer() {
        myClient = new com.esotericsoftware.kryonet.Client();


        // Add all global events
        myClient.getKryo().register(PlayerAcceptBattleEvent.class);
        myClient.getKryo().register(PlayerRequestBattleEvent.class);
        myClient.getKryo().register(PlayerAttackEvent.class);
        myClient.getKryo().register(PlayerChangePetEvent.class);
        myClient.getKryo().register(PlayerJoinServerEvent.class);
        myClient.getKryo().register(StartBattleEvent.class);
//        myClient.getKryo().register(NPCBattleEvent.class);

        // Add all creatures

        myClient.getKryo().register(Entity.class);
        myClient.getKryo().register(MeowmadAli.class);
        myClient.getKryo().register(CrocLesnar.class);
        myClient.getKryo().register(Froggy.class);
        myClient.getKryo().register(Dragon.class);
        myClient.getKryo().register(Doge.class);
        myClient.getKryo().register(MouseHunter.class);

        myClient.getKryo().register(BattleState.class);
        myClient.getKryo().register(BattleState.Turn.class);
        myClient.getKryo().register(Player.class);
        myClient.getKryo().register(Player.PetNum.class);
//        myClient.getKryo().register(NPC.class); // added
        myClient.getKryo().register(ArrayList.class);
        myClient.getKryo().register(Creature.class);
//        myClient.getKryo().register(Creature.Element.class); // added
        myClient.getKryo().register(Skill.class);
        myClient.getKryo().register(TextImageButton.class);
        myClient.getKryo().register(String.class);


        // Add all listeners of server
        myClient.addListener(UserEventListener.getInstance());

        //start the client
        myClient.start();
        DarwinsDuel.client = myClient;
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
            String host = "192.168.68.71"; // Server's IP address if not running locally
            // sk: 192.168.68.71
            // ethan: ***REMOVED***
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

    public static void sendBattleRequest(String opponentId) {
        PlayerRequestBattleEvent playerRequestBattleEvent = new PlayerRequestBattleEvent();
        playerRequestBattleEvent.opponentUID = opponentId;
        playerRequestBattleEvent.requesterPlayer = UserPlayerHandler.getPlayer();
        try {
            new Thread(() -> {
                DarwinsDuel.getClient().sendTCP(playerRequestBattleEvent);
                System.out.println("Sent playerRequestBattleEvent from client");
            }).start();
        } catch (Exception e) {
            System.out.println("Error sending playerRequestBattleEvent" + e.getMessage());
        }
    }
}
