package com.mygdx.game.listeners;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.events.PlayerAcceptBattleEvent;
import com.mygdx.game.events.PlayerRequestBattleEvent;
import com.mygdx.game.handlers.*;
import com.mygdx.game.interfaces.BattleResponseListener;
import com.mygdx.game.interfaces.MapInterface;
import com.mygdx.global.EndBattleEvent;
import com.mygdx.global.BattleState;
import com.mygdx.global.StartBattleEvent;

public class UserEventListener extends Listener {
    private static UserEventListener instance = new UserEventListener();

    private BattleResponseListener responseListener; // To be used by Map Activity
    public void setResponseListener(BattleResponseListener listener) {
        this.responseListener = listener;
    }

    public BattleResponseListener getResponseListener() {
        return responseListener;
    }

    public static UserEventListener getInstance() {
        return instance;
    }
    @Override
    public void received(Connection connection, final Object object) {
        if (object instanceof PlayerRequestBattleEvent) {
            System.out.println("Player Request received");
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                MapInterface map = (MapInterface) Gdx.app;
                System.out.println("Showing accept reject screen");
                map.acceptOrReject();
            }
            //todo wait for response from Map Activity
            setResponseListener(new BattleResponseListener() {
                @Override
                public void onBattleResponse(boolean accepted) {
                    PlayerRequestBattleEvent request = (PlayerRequestBattleEvent) object;
                    PlayerAcceptBattleEvent accept = new PlayerAcceptBattleEvent();
                    accept.opponentPlayer = UserPlayerHandler.getPlayer();
                    accept.requesterPlayer = request.requesterPlayer;

                    if (accepted) {
                        // User accepted the battle request
                        new Thread(() -> {
                            connection.sendTCP(accept);
                        }).start();

                    } else {
                        // User rejected the battle request
                        // Optionally handle rejection logic
                    }
                }
            });
        }

        if (object instanceof StartBattleEvent) {
            StartBattleEvent event = (StartBattleEvent) object;
            UserBattleHandler.setBattleId(event.battleId);
            UserBattleHandler.newBattleState(event.battleState);
            DarwinsDuel.gameState = DarwinsDuel.GameState.BATTLE;
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                MapInterface map = (MapInterface) Gdx.app;
                map.stopMap();
            }
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


}

