package com.mygdx.game;

import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.CrocLesnar;
import com.mygdx.game.entities.Doge;
import com.mygdx.game.entities.Dragon;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Froggy;
import com.mygdx.game.entities.MeowmadAli;
import com.mygdx.game.entities.MouseHunter;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.NPCDoge;
import com.mygdx.game.entities.NPCDragon;
import com.mygdx.game.entities.NPCCrocLesnar;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Skill;
import com.mygdx.game.events.PlayerAcceptBattleEvent;
import com.mygdx.game.events.PlayerAttackEvent;
import com.mygdx.game.events.PlayerChangePetEvent;
import com.mygdx.game.events.PlayerJoinServerEvent;
import com.mygdx.game.events.PlayerNPCBattleEvent;
import com.mygdx.game.events.PlayerRequestBattleEvent;
import com.mygdx.game.events.PlayerSkipEvent;
import com.mygdx.game.handlers.UserPlayerHandler;
import com.mygdx.game.listeners.UserEventListener;
import com.mygdx.global.*;

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
        myClient.getKryo().register(PlayerNPCBattleEvent.class); // added
        myClient.getKryo().register(EndBattleEvent.class);
        myClient.getKryo().register(AddPetEvent.class); // added
        myClient.getKryo().register(PlayerSkipEvent.class);

        // Add all creatures
        myClient.getKryo().register(Entity.class);
        myClient.getKryo().register(MeowmadAli.class);
        myClient.getKryo().register(CrocLesnar.class);
        myClient.getKryo().register(Froggy.class);
        myClient.getKryo().register(Dragon.class);
        myClient.getKryo().register(Doge.class);
        myClient.getKryo().register(MouseHunter.class);
        myClient.getKryo().register(NPCDoge.class);
        myClient.getKryo().register(NPCDragon.class);
        myClient.getKryo().register(NPCCrocLesnar.class);

        myClient.getKryo().register(BattleState.class);
        myClient.getKryo().register(BattleState.Turn.class);
        myClient.getKryo().register(Player.class);
        myClient.getKryo().register(Player.PetNum.class);
        myClient.getKryo().register(NPC.class); // added
        myClient.getKryo().register(ArrayList.class);
        myClient.getKryo().register(Creature.class);
        myClient.getKryo().register(Creature.Element.class);
        myClient.getKryo().register(Skill.class);
        myClient.getKryo().register(Skill.Status.class);
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
            String host = "42.60.220.147"; // Server's IP address if not running locally

            int tcpPort = 55555;       // Must match the server's TCP port
            int udpPort = 55555;       // Must match the server's UDP port

            try {
                myClient.connect(5000, host, tcpPort, udpPort);
                System.out.println("Connected to the server.");
            } catch (IOException e) {
                System.err.println("Error connecting to the server: " + e.getMessage());
                e.printStackTrace();
                //errorLabel.setText(e.getMessage());
            }
        });
        connectThread.start(); // Start the thread
        return connectThread;

    }

    public static void sendJoinServerEvent() {
        PlayerJoinServerEvent playerJoinServerEvent = new PlayerJoinServerEvent();
        playerJoinServerEvent.userId = UserPlayerHandler.getUserId();
        try {
            new Thread(() -> {
                DarwinsDuel.client.sendTCP(playerJoinServerEvent);
                System.out.println("Sent playerJoinServerEvent from client");
            }).start();
        } catch (Exception e) {
            System.out.println("Error sending playerJoinServerEvent" + e.getMessage());
        }

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


    public static void sendNPCReq() {
        try {
            new Thread(() -> {
                PlayerNPCBattleEvent event = new PlayerNPCBattleEvent();
                event.player = UserPlayerHandler.getPlayer();
                DarwinsDuel.getClient().sendTCP(event);
                System.out.println("Sent PlayerNPCBattleEvent from client");
            }).start();
        } catch (Exception e) {
            System.out.println("Error sending PlayerNPCBattleEvent" + e.getMessage());
        }
    }
}
