package com.mygdx.server.listeners;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.Player;
import com.mygdx.global.*;
import com.mygdx.server.ServerFoundation;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.global.BattleState;
import com.mygdx.server.handlers.PlayerHandler;

public class EventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {
        Server server = ServerFoundation.getServer();
        //Connection[] connections = server.getConnections();
        BattleState battleState = ServerFoundation.battleState;
        PlayerHandler players = ServerFoundation.players;

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
            System.out.println("AddplayerEvent received by server");
            AddPlayerEvent addPlayerEvent = (AddPlayerEvent) object;

            Player newPlayer = new Player(addPlayerEvent.username);
            players.createPlayer(newPlayer);
            AddPlayerEvent playerInfo = new AddPlayerEvent(newPlayer);
            connection.sendTCP(playerInfo);
            System.out.println("sending playerInfo to user");

            battleState.createPlayer(newPlayer);
            //todo move to startbattleevent

            /*for (Connection i : connections) {
                if (i != connection) {
                    i.sendTCP(addPlayerEvent);
                }
            }*/
        } else if (object instanceof AttackEvent) {
            System.out.println("AttackEvent received by server");
            AttackEvent attackEvent = (AttackEvent) object;
            battleState.attack(attackEvent.id, attackEvent.skill);
            if (battleState.playerAlive()) {
                server.sendToAllTCP(battleState);
            } else {
                server.sendToAllTCP(new EndBattleEvent());
            }
        } else if (object instanceof StartBattleEvent) {
            System.out.println("StartBattleEvent received by server");
            if (battleState.getNumPlayers() == 2) {
                server.sendToAllTCP(battleState);
                System.out.println("sending battleState to user");
                server.sendToAllTCP(new StartBattleEvent());
                System.out.println("Starting Battle");
            } //todo change
        } else {
            System.out.println("unknown object received.");
        }
        super.received(connection, object);
    }
}