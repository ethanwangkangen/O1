package com.mygdx.game.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision._btMprSupport_t;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.global.AddPlayerEvent;
import com.mygdx.global.JoinRequestEvent;
import com.mygdx.global.*;
import com.mygdx.game.handlers.*;

public class EventListener extends Listener {

    public void received(Connection connection, final Object object) {
        if (object instanceof JoinResponseEvent) {
            JoinResponseEvent joinObj = (JoinResponseEvent) object;
            BattleHandler.updateBattleState(joinObj.battleState); //update the battleState of BattleHandler
            System.out.println("client has received the battleState");
        } else if (object instanceof BattleState) {
            BattleState joinObj = (BattleState) object;
            BattleHandler.updateBattleState(joinObj);
            BattleHandler.updatePetInfo = true;
            System.out.println("Client has received the battleState");
        } else if (object instanceof AddPlayerEvent) {
            AddPlayerEvent joinObj = (AddPlayerEvent) object;
            PlayerHandler.updatePlayer(joinObj.getPlayer());
            System.out.println("Client has received the Player Info");
            // todo change to game screen
        } else if (object instanceof StartBattleEvent) {
            DarwinsDuel.gameState = DarwinsDuel.GameState.BATTLE;
        } else if (object instanceof EndBattleEvent) {
            BattleHandler.battleEnd = true;
            System.out.println("Client has received EndBattleEvent");
        }
    }
}
