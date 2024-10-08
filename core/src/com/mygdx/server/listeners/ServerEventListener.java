package com.mygdx.server.listeners;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.CrocLesnar;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.NPCDragon;
import com.mygdx.game.entities.Player;
import com.mygdx.game.events.PlayerAcceptBattleEvent;
import com.mygdx.game.events.PlayerAttackEvent;
import com.mygdx.game.events.PlayerChangePetEvent;
import com.mygdx.game.events.PlayerJoinServerEvent;
import com.mygdx.game.events.PlayerNPCBattleEvent;
import com.mygdx.game.events.PlayerRequestBattleEvent;
import com.mygdx.game.events.PlayerSkipEvent;
import com.mygdx.server.ServerFoundation;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.server.handlers.ServerBattleHandler;
import com.mygdx.server.handlers.ServerPlayerHandler;

public class ServerEventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {
        if (object instanceof PlayerJoinServerEvent) {
            // Add player-connection map to connectionTable
            System.out.println("Player has requested to join the server.");
            PlayerJoinServerEvent playerJoinServerEvent = (PlayerJoinServerEvent) object;
            ServerPlayerHandler.addPlayer(playerJoinServerEvent.userId, connection);
        }

        if (object instanceof PlayerRequestBattleEvent) {
            System.out.println("Player has requested to fight an opponent.");
            PlayerRequestBattleEvent request = (PlayerRequestBattleEvent) object;
            String opponentUID = request.opponentUID;

            try {
                Connection enemyConnection = ServerPlayerHandler.connectionTable.get(opponentUID);
                enemyConnection.sendTCP(request);
            } catch (Exception e) {
                System.err.println("Enemy connection is null/has error. Unable to send PlayerRequestBattleEvent");
            }
        }

        if (object instanceof PlayerAcceptBattleEvent) {
            System.out.println("Battle starting between 2 players");
            PlayerAcceptBattleEvent event = (PlayerAcceptBattleEvent) object;
            Player p1Player = event.opponentPlayer;
            Player p2Player = event.requesterPlayer;

            //Check if players are free
            if (ServerBattleHandler.checkPlayerFree(p1Player) && ServerBattleHandler.checkPlayerFree(p2Player)) {
                // Create new battleState
                String battleId = ServerBattleHandler.initialiseBattle(p1Player, p2Player);
                ServerBattleHandler.sendStartBattle(battleId);
            }

        }

        if (object instanceof PlayerAttackEvent) {
            System.out.println("AttackEvent received by server");
            PlayerAttackEvent attackEvent = (PlayerAttackEvent) object;
            String battleId = attackEvent.battleId;

            ServerBattleHandler.getBattleState(battleId).playerAttack(attackEvent.id, attackEvent.skill);
            ServerBattleHandler.sendBattleState(battleId);

            // If players are all dead -> battle has ended
            if (!ServerBattleHandler.getBattleState(battleId).playersAlive()){
                ServerBattleHandler.sendEndBattle(battleId);
                return;
            }

            // Players still alive -> checking if against NPC
            if (ServerBattleHandler.getBattleState(battleId).isAgainstNPC()) {
                // Fighting against NPC; NPC's turn
                System.out.println("NPC attacking");
                ServerBattleHandler.getBattleState(battleId).scheduleNPCAttack(battleId);
            }
        }

        if (object instanceof PlayerSkipEvent) {
            System.out.println("PlayerSkipEvent received by server");
            PlayerSkipEvent skipEvent = (PlayerSkipEvent) object;
            String battleId = skipEvent.battleId;

            ServerBattleHandler.getBattleState(battleId).changeTurn();
            // Change turn manually since nothing happens that initiates the change turn

            ServerBattleHandler.sendBattleState(battleId);

            // If players are all dead -> battle has ended
            if (!ServerBattleHandler.getBattleState(battleId).playersAlive()){
                ServerBattleHandler.sendEndBattle(battleId);
                return;
            }

            // Players still alive -> checking if against NPC
            if (ServerBattleHandler.getBattleState(battleId).isAgainstNPC()) {
                // Fighting against NPC; NPC's turn
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
                // Fighting against NPC; NPC's turn
                System.out.println("NPC attacking");
                ServerBattleHandler.getBattleState(battleId).scheduleNPCAttack(battleId);
            }
        }

        if (object instanceof PlayerNPCBattleEvent) {
            // Start battle against NPC
            System.out.println("Battle starting between player and NPC");
            PlayerNPCBattleEvent event = (PlayerNPCBattleEvent) object;
            NPC npc = new NPC();
            npc.initialise();
            String battleId = ServerBattleHandler.initialiseBattle(event.player, npc);
            ServerBattleHandler.sendStartBattle(battleId);
        }
    }

//        super.received(connection, object);

}