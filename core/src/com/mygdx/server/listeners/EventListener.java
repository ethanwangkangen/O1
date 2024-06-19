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

        if (object instanceof AttackEvent) {
            System.out.println("AttackEvent received by server");
            AttackEvent attackEvent = (AttackEvent) object;
            String battleId = attackEvent.battleId;

            BattleHandler.getBattleState(battleId).attack(attackEvent.id, attackEvent.skill);
            System.out.println("Sending battleState");
            BattleHandler.sendBattleState(battleId);

            if (!BattleHandler.getBattleState(battleId).playersAlive()){
                System.out.println("Sending EndBattleEvent");
                BattleHandler.sendEndBattle(battleId);
            }
        }

        if (object instanceof ChangePetEvent) {
            System.out.println("ChangePetEvent received by server");
            ChangePetEvent changePetEvent = (ChangePetEvent) object;
            String battleId = changePetEvent.battleId;
            BattleHandler.getBattleState(battleId).changePet(changePetEvent.playerId, changePetEvent.pet);

            System.out.println("Sending battleState");
            BattleHandler.sendBattleState(battleId);
        }

        if (object instanceof PlayerJoinServerEvent) {
            System.out.println("Player has requested to join the server.");
            PlayerJoinServerEvent playerJoinServerEvent = (PlayerJoinServerEvent) object;
            PlayerHandler.addPlayer(playerJoinServerEvent.userId, connection);
        } else if (object instanceof PlayerRequestBattleEvent) {
            System.out.println("Player has requested to fight an opponent.");
            PlayerRequestBattleEvent request = (PlayerRequestBattleEvent) object;
//            String requesterUID = request.requesterUID;
            String opponentUID = PlayerHandler.getOther(request.requesterPlayer.getIdString());
            Connection enemyConnection = PlayerHandler.connectionTable.get(opponentUID);
            enemyConnection.sendTCP(request);
        } else if (object instanceof PlayerAcceptBattleEvent) {
            System.out.println("Battle starting between 2 players");
            PlayerAcceptBattleEvent event = (PlayerAcceptBattleEvent) object;

            // create new battleState
            Player p1Player = event.opponentPlayer;
            Player p2Player = event.requesterPlayer;
            String battleId = BattleHandler.initialiseBattle(p1Player, p2Player);

            ServerStartBattleEvent newEvent = new ServerStartBattleEvent();
            newEvent.battleId = battleId;
            BattleState newState = BattleHandler.getBattleState(battleId);
            Connection connection1 = PlayerHandler.getConnectionById(newState.getPlayer1().getIdString());
            Connection connection2 = PlayerHandler.getConnectionById(newState.getPlayer2().getIdString());

            System.out.println("Sending start battle event");
            connection1.sendTCP(newEvent);
            connection2.sendTCP(newEvent);

            //todo sk continue from here, send ServerStartBattleEvent to players etc.
        } else {
            System.out.println("unknown object received.");
        }
        super.received(connection, object);
    }
}