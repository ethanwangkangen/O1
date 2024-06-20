package com.mygdx.game.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.events.PlayerAcceptBattleEvent;
import com.mygdx.game.events.PlayerRequestBattleEvent;
import com.mygdx.game.handlers.*;

public class UserEventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {
        if (object instanceof PlayerRequestBattleEvent) {
            //todo: accept vs reject screen.
            //For now: just accept

            //note: here "I" am the "opponent"
            PlayerRequestBattleEvent request = (PlayerRequestBattleEvent) object;
            PlayerAcceptBattleEvent accept = new PlayerAcceptBattleEvent();
            accept.opponentUID = request.opponentUID;
            accept.requesterUID = request.requesterUID;
            accept.opponentPlayer = UserPlayerHandler.getPlayer();
            accept.requesterPlayer = request.requesterPlayer;
            connection.sendTCP(accept);
        }
    }
//    public void received(Connection connection, final Object object) {
//        if (object instanceof JoinResponseEvent) {
//            JoinResponseEvent joinObj = (JoinResponseEvent) object;
//            BattleHandler.updateBattleState(joinObj.battleState); //update the battleState of BattleHandler
//            System.out.println("client has received the battleState");
//
//        } else if (object instanceof BattleState) {
//            BattleState joinObj = (BattleState) object;
//            BattleHandler.updateBattleState(joinObj);
//            if (!joinObj.firstRound) {
//                BattleHandler.updatePetInfo = true;
//            }
//            System.out.println("Client has received the battleState");
//
//        } else if (object instanceof AddPlayerEvent) {
//            AddPlayerEvent joinObj = (AddPlayerEvent) object;
//            PlayerHandler.updatePlayer(joinObj.getPlayer());
//            System.out.println("Client has received the Player Info");
//
//            // todo change to game screen
//        } else if (object instanceof StartBattleEvent) {
//            DarwinsDuel.gameState = DarwinsDuel.GameState.BATTLE;
//
//        } else if (object instanceof EndBattleEvent) {
//            BattleHandler.battleEnd = true;
//            //send new Player object to the firebase database to save it under account
//
//            System.out.println("Client has received EndBattleEvent");
//        }
    }

