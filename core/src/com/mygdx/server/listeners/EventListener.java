package com.mygdx.server.listeners;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.Player;
import com.mygdx.global.*;
import com.mygdx.server.ServerFoundation;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.global.BattleState;
import com.mygdx.server.handlers.PlayerHandler;
import com.mygdx.server.handlers.BattleHandler;

public class EventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {
        Server server = ServerFoundation.getServer();
        //Connection[] connections = server.getConnections();
        BattleState battleState = ServerFoundation.battleState;
        PlayerHandler playerHandler = ServerFoundation.playerHandler;

        if (object instanceof JoinRequestEvent) {
            System.out.println("JoinRequestEvent received by server");
            JoinRequestEvent joinObj = (JoinRequestEvent) object;
            try {
                JoinResponseEvent event = new JoinResponseEvent();
                connection.sendTCP(event); //changed
                System.out.println("Connection established");
            } catch (Exception e) {
                //todo: battleState.addPlayer should throw some exception if more than 2 try to join
                System.out.println(e.getMessage());
            }
        } else if (object instanceof AddPlayerEvent) {
            System.out.println("AddPlayerEvent received by server");
            AddPlayerEvent addPlayerEvent = (AddPlayerEvent) object;

            Player newPlayer = new Player();
            newPlayer.username = addPlayerEvent.username;

            playerHandler.addPlayer(newPlayer);
            battleState.addPlayer(newPlayer);
            //todo move to startbattleevent

            AddPlayerEvent playerInfo = new AddPlayerEvent();
            playerInfo.player = newPlayer;
            connection.sendTCP(playerInfo);
            System.out.println("sending playerInfo to user");

            /*for (Connection i : connections) {
                if (i != connection) {
                    i.sendTCP(addPlayerEvent);
                }
            }*/
        } else if (object instanceof AttackEvent) {
            System.out.println("AttackEvent received by server");
            AttackEvent attackEvent = (AttackEvent) object;
            battleState.attack(attackEvent.id, attackEvent.skill);
            System.out.println("Sending battleState");
            server.sendToAllTCP(battleState);

            if (!battleState.playersAlive()){
                System.out.println("Sending EndBattleEvent");
                server.sendToAllTCP(new EndBattleEvent());
            }
            battleState.petAttacked = false;
            battleState.petChanged = false;
        } else if (object instanceof StartBattleEvent) {
            System.out.println("StartBattleEvent received by server");
            if (battleState.getNumPlayers() == 2) {
                server.sendToAllTCP(battleState);
                System.out.println("sending battleState to user");
                battleState.firstRound = false;
                server.sendToAllTCP(new StartBattleEvent());
                System.out.println("Starting Battle");
            } //todo change
        } else if (object instanceof ChangePetEvent) {
            System.out.println("ChangePetEvent received by server");
            ChangePetEvent changePetEvent = (ChangePetEvent) object;
            battleState.changePet(changePetEvent.id, changePetEvent.pet);

            System.out.println("Sending battleState");
            server.sendToAllTCP(battleState);

            battleState.petAttacked = false;
            battleState.petChanged = false;
        } else if (object instanceof PlayerRequestBattleEvent) {
            System.out.println("Player has requested to fight an opponent.");
            PlayerRequestBattleEvent request = (PlayerRequestBattleEvent) object;
            String opponentUID = request.opponentUID;
//            String requesterUID = request.requesterUID;
            Connection enemyConnection = ServerFoundation.connectionTable.get(opponentUID);
            enemyConnection.sendTCP(request);
        } else if (object instanceof PlayerAcceptBattleEvent) {
            System.out.println("Battle starting between 2 players");
            PlayerAcceptBattleEvent event = (PlayerAcceptBattleEvent) object;
            Player p1Player = event.opponentPlayer;
            String p1UID = event.opponentUID;
            Player p2Player = event.requesterPlayer;
            String p2UID =  event.requesterUID;
            BattleHandler.initialiseBattle(p1Player, p1UID, p2Player, p2UID);
            //todo sk continue from here, send ServerStartBattleEvent to players etc.
        } else {
            System.out.println("unknown object received.");
        }
        super.received(connection, object);
    }
}