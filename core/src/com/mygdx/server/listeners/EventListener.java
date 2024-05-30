package com.mygdx.server.listeners;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.Player;
import com.mygdx.global.*;
import com.mygdx.server.ServerFoundation;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.global.BattleState;

public class EventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {
        Server server = ServerFoundation.getServer();
        Connection[] connections = server.getConnections();
        BattleState battleState = BattleState.INSTANCE;

        if (object instanceof JoinRequestEvent) {
            System.out.println("JoinRequestEvent received by server");
            JoinRequestEvent joinObj = (JoinRequestEvent) object;
            try {
                Player player = ((JoinRequestEvent) object).player; //changed
                ServerFoundation.battleState.addPlayer(player);
                JoinResponseEvent event = new JoinResponseEvent();
                event.battleState = ServerFoundation.battleState;
                connection.sendTCP(event);
                System.out.println("sending battleState to user");
            } catch (Exception e) {
                //todo: battleState.addPlayer should throw some exception if more than 2 try to join
                System.out.println(e.getMessage());
            }
        } else if (object instanceof AddPlayerEvent) {
            System.out.println("AddplayerEvent received by server");
            AddPlayerEvent addPlayerEvent = (AddPlayerEvent) object;
            battleState.addPlayer(addPlayerEvent.getPlayer());
            server.sendToAllTCP(battleState);
            /*for (Connection i : connections) {
                if (i != connection) {
                    i.sendTCP(addPlayerEvent);
                }
            }*/
        } else if (object instanceof AttackEvent) {
            System.out.println("AttackEvent received by server");
            AttackEvent attackEvent = (AttackEvent) object;
            battleState.attack(attackEvent.id, attackEvent.skill);
            server.sendToAllTCP(battleState);
        }
        else {
            System.out.println("unknown object received.");
        }
        super.received(connection, object);
    }
}