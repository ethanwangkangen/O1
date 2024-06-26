package com.mygdx.server.listeners;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.events.PlayerAcceptBattleEvent;
import com.mygdx.game.events.PlayerAttackEvent;
import com.mygdx.game.events.PlayerChangePetEvent;
import com.mygdx.game.events.PlayerJoinServerEvent;
import com.mygdx.game.events.PlayerNPCBattleEvent;
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

            ServerBattleHandler.getBattleState(battleId).playerAttack(attackEvent.id, attackEvent.skill);
            ServerBattleHandler.sendBattleState(battleId);

            // if players are all dead -> battle has ended
            if (!ServerBattleHandler.getBattleState(battleId).playersAlive()){
                ServerBattleHandler.sendEndBattle(battleId);
            }

            if (ServerBattleHandler.getBattleState(battleId).isAgainstNPC()) {
                // fighting against NPC; NPC's turn
                System.out.println("NPC attacking");
                ServerBattleHandler.getBattleState(battleId).scheduleNPCAttack(battleId);
            }
        }

        if (object instanceof PlayerChangePetEvent) {
            System.out.println("ChangePetEvent received by server");
            PlayerChangePetEvent changePetEvent = (PlayerChangePetEvent) object;
            String battleId = changePetEvent.battleId;

            ServerBattleHandler.getBattleState(battleId).changePet(changePetEvent.playerId, changePetEvent.petNum);
            ServerBattleHandler.sendBattleState(battleId);

            if (ServerBattleHandler.getBattleState(battleId).isAgainstNPC()) {
                // fighting against NPC; NPC's turn
                System.out.println("NPC attacking");
                ServerBattleHandler.getBattleState(battleId).scheduleNPCAttack(battleId);
            }
        }

        if (object instanceof PlayerAcceptBattleEvent) {
            System.out.println("Battle starting between 2 players");
            PlayerAcceptBattleEvent event = (PlayerAcceptBattleEvent) object;

            // create new battleState
            String battleId = ServerBattleHandler.initialiseBattle(event.requesterPlayer, event.opponentPlayer);

            ServerBattleHandler.sendStartBattle(battleId);
        }

        if (object instanceof PlayerNPCBattleEvent) {
            // start battle against NPC
            System.out.println("Battle starting between player and NPC");
            PlayerNPCBattleEvent event = (PlayerNPCBattleEvent) object;
            String battleId = ServerBattleHandler.initialiseBattle(event.player, new NPC());
            ServerBattleHandler.sendStartBattle(battleId);
        }
    }

//        super.received(connection, object);

}