package com.mygdx.game.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.global.*;
import com.mygdx.game.handlers.*;

public class EventListener extends Listener {
    public void received(Connection connection, final Object object) {

        if (object instanceof BattleState) {
            BattleState joinObj = (BattleState) object;
            BattleHandler.updateBattleState(joinObj);
            if (!joinObj.firstRound) {
                BattleHandler.updatePetInfo = true;
            }
            System.out.println("Client has received the battleState");

        } else if (object instanceof ServerStartBattleEvent) {
            DarwinsDuel.gameState = DarwinsDuel.GameState.BATTLE;

        } else if (object instanceof EndBattleEvent) {
            BattleHandler.battleEnd = true;
            System.out.println("Client has received EndBattleEvent");
        } else if (object instanceof PlayerRequestBattleEvent) {
            //todo: accept vs reject screen.
            //For now: just accept

            //note: here "I" am the "opponent"
            PlayerRequestBattleEvent request = (PlayerRequestBattleEvent) object;
            PlayerAcceptBattleEvent accept = new PlayerAcceptBattleEvent();
            accept.opponentPlayer = PlayerHandler.getPlayer();
            accept.requesterPlayer = request.requesterPlayer;
            connection.sendTCP(accept);
        }
    }
}
