package com.mygdx.game.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.events.PlayerAcceptBattleEvent;
import com.mygdx.game.events.PlayerRequestBattleEvent;
import com.mygdx.game.handlers.*;
import com.mygdx.global.EndBattleEvent;
import com.mygdx.global.BattleState;
import com.mygdx.global.StartBattleEvent;

public class UserEventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {
        if (object instanceof PlayerRequestBattleEvent) {
            //todo: accept vs reject screen.
            //For now: just accept

            //note: here "I" am the "opponent"
            PlayerRequestBattleEvent request = (PlayerRequestBattleEvent) object;
            PlayerAcceptBattleEvent accept = new PlayerAcceptBattleEvent();
            accept.opponentPlayer = UserPlayerHandler.getPlayer();
            accept.requesterPlayer = request.requesterPlayer;
            connection.sendTCP(accept);
        }

        if (object instanceof StartBattleEvent) {
            StartBattleEvent event = (StartBattleEvent) object;
            UserBattleHandler.setBattleId(event.battleId);
            UserBattleHandler.updateBattleState(event.battleState);
            DarwinsDuel.gameState = DarwinsDuel.GameState.BATTLE;
            System.out.println("Client has received the StartBattleEvent");
        }

        if (object instanceof BattleState) {
            BattleState joinObj = (BattleState) object;
            UserBattleHandler.updateBattleState(joinObj);
            UserBattleHandler.updatePetInfo = true;
            System.out.println("Client has received the battleState");
        }

        if (object instanceof EndBattleEvent) {
            UserBattleHandler.battleEnd = true;
            System.out.println("Client has received EndBattleEvent");
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
//        } else if (object instanceof EndBattleEvent) {
//            BattleHandler.battleEnd = true;
//            //send new Player object to the firebase database to save it under account
//
//            System.out.println("Client has received EndBattleEvent");
//        }
    }

