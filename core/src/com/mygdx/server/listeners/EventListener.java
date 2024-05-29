package com.mygdx.server.listeners;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.Player;
import com.mygdx.global.JoinRequestEvent;
import com.mygdx.global.JoinResponseEvent;
import com.mygdx.server.ServerFoundation;

public class EventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {

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
        } else {
            System.out.println("unknown object received.");
        }
        super.received(connection, object);
    }
}