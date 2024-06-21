package com.mygdx.server.listeners;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.Player;
import com.mygdx.game.events.PlayerAcceptBattleEvent;
import com.mygdx.game.events.PlayerAttackEvent;
import com.mygdx.game.events.PlayerChangePetEvent;
import com.mygdx.game.events.PlayerJoinServerEvent;
import com.mygdx.game.events.PlayerRequestBattleEvent;
import com.mygdx.server.ServerFoundation;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.server.handlers.ServerBattleHandler;
import com.mygdx.server.handlers.ServerPlayerHandler;

public class ServerEventListener extends Listener {

    Server server = ServerFoundation.getServer();
    @Override
    public void received(Connection connection, final Object object) {
        if (object instanceof PlayerJoinServerEvent) {
            // add player-connection map to connectionTable
            System.out.println("Player has requested to join the server.");
            PlayerJoinServerEvent playerJoinServerEvent = (PlayerJoinServerEvent) object;
            ServerPlayerHandler.addPlayer(playerJoinServerEvent.userId, connection);
        }

        if (object instanceof PlayerRequestBattleEvent) {
            System.out.println("Player has requested to fight an opponent.");
            PlayerRequestBattleEvent request = (PlayerRequestBattleEvent) object;
            String opponentUID = request.opponentUID;
            String requesterUID = request.requesterUID;

            Connection enemyConnection = ServerPlayerHandler.connectionTable.get(opponentUID);
            enemyConnection.sendTCP(request);
        }

        if (object instanceof PlayerAcceptBattleEvent) {
            System.out.println("Battle starting between 2 players");
            PlayerAcceptBattleEvent event = (PlayerAcceptBattleEvent) object;
            Player p1Player = event.opponentPlayer;
            Player p2Player = event.requesterPlayer;

            // create new battleState
            String battleId = ServerBattleHandler.initialiseBattle(p1Player, p2Player);

            ServerBattleHandler.sendStartBattle(battleId);
        }

        if (object instanceof PlayerAttackEvent) {
            System.out.println("AttackEvent received by server");
            PlayerAttackEvent attackEvent = (PlayerAttackEvent) object;
            String battleId = attackEvent.battleId;

            ServerBattleHandler.getBattleState(battleId).attack(attackEvent.id, attackEvent.skill);
            ServerBattleHandler.sendBattleState(battleId);

            // if players are all dead -> battle has ended
            if (!ServerBattleHandler.getBattleState(battleId).playersAlive()){
                ServerBattleHandler.sendEndBattle(battleId);
            }
        }

        if (object instanceof PlayerChangePetEvent) {
            System.out.println("ChangePetEvent received by server");
            PlayerChangePetEvent changePetEvent = (PlayerChangePetEvent) object;
            String battleId = changePetEvent.battleId;
            ServerBattleHandler.getBattleState(battleId).changePet(changePetEvent.playerId, changePetEvent.pet);

            ServerBattleHandler.sendBattleState(battleId);
        }

        if (object instanceof PlayerAcceptBattleEvent) {
            System.out.println("Battle starting between 2 players");
            PlayerAcceptBattleEvent event = (PlayerAcceptBattleEvent) object;

            // create new battleState
            String battleId = ServerBattleHandler.initialiseBattle(event.requesterPlayer, event.opponentPlayer);

            ServerBattleHandler.sendStartBattle(battleId);
        }
    }


//    @Override
//    public void received(Connection connection, final Object object) {
//        Server server = ServerFoundation.getServer();
//        //Connection[] connections = server.getConnections();
//        BattleState battleState = ServerFoundation.battleState;
//        PlayerHandler playerHandler = ServerFoundation.playerHandler;
//
//        if (object instanceof JoinRequestEvent) {
//            System.out.println("JoinRequestEvent received by server");
//            JoinRequestEvent joinObj = (JoinRequestEvent) object;
//            try {
//                JoinResponseEvent event = new JoinResponseEvent();
//                connection.sendTCP(event); //changed
//                System.out.println("Connection established");
//            } catch (Exception e) {
//                //todo: battleState.addPlayer should throw some exception if more than 2 try to join
//                System.out.println(e.getMessage());
//            }
//        } else if (object instanceof AddPlayerEvent) {
//            System.out.println("AddPlayerEvent received by server");
//            AddPlayerEvent addPlayerEvent = (AddPlayerEvent) object;
//
//            Player newPlayer = new Player();
//            newPlayer.username = addPlayerEvent.username;
//
//            playerHandler.addPlayer(newPlayer); //ignore
//            battleState.addPlayer(newPlayer);
//            //todo move to startbattleevent
//
//            AddPlayerEvent playerInfo = new AddPlayerEvent();
//            playerInfo.player = newPlayer;
//            connection.sendTCP(playerInfo);
//            System.out.println("sending playerInfo to user");
//
//            /*for (Connection i : connections) {
//                if (i != connection) {
//                    i.sendTCP(addPlayerEvent);
//                }
//            }*/
//        } else if (object instanceof AttackEvent) {
//            System.out.println("AttackEvent received by server");
//            AttackEvent attackEvent = (AttackEvent) object;
//            battleState.attack(attackEvent.id, attackEvent.skill);
//            System.out.println("Sending battleState");
//            server.sendToAllTCP(battleState);
//
//            if (!battleState.playersAlive()){
//                System.out.println("Sending EndBattleEvent");
//                server.sendToAllTCP(new EndBattleEvent());
//            }
//            battleState.petAttacked = false;
//            battleState.petChanged = false;
//        } else if (object instanceof StartBattleEvent) {
//            System.out.println("StartBattleEvent received by server");
//            if (battleState.getNumPlayers() == 2) {
//                server.sendToAllTCP(battleState);
//                System.out.println("sending battleState to user");
//                battleState.firstRound = false;
//                server.sendToAllTCP(new StartBattleEvent());
//                System.out.println("Starting Battle");
//            } //todo change
//        } else if (object instanceof ChangePetEvent) {
//            System.out.println("ChangePetEvent received by server");
//            ChangePetEvent changePetEvent = (ChangePetEvent) object;
//            battleState.changePet(changePetEvent.id, changePetEvent.pet);
//
//            System.out.println("Sending battleState");
//            server.sendToAllTCP(battleState);
//
//            battleState.petAttacked = false;
//            battleState.petChanged = false;
//        } else {
//            System.out.println("unknown object received.");
//        }
//        super.received(connection, object);
//    }
}