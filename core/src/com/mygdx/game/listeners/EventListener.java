package com.mygdx.game.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision._btMprSupport_t;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.global.AddPlayerEvent;
import com.mygdx.global.JoinRequestEvent;
import com.mygdx.global.*;
import com.mygdx.game.handlers.BattleHandler;

public class EventListener extends Listener {

    public void received(Connection connection, final Object object) {
        if (object instanceof JoinResponseEvent) {
            JoinResponseEvent joinObj = (JoinResponseEvent) object;
            BattleHandler.battleState = joinObj.battleState; //update the battleState of BattleHandler
            System.out.println("client has received the battleState");
        } else if (object instanceof BattleState) {
            BattleHandler.battleState = (BattleState) object;
            System.out.println("Client has received the battleState");
        }
    }
}
