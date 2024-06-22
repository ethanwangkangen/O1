package com.mygdx.game;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mygdx.game.interfaces.GameCommunication;

import com.badlogic.gdx.Gdx;

/**
 * Used to relay messages between components of Android.
 * eg. between the Map activity and the libgdx game itself
 */
public class MyBroadcastReceiver extends BroadcastReceiver{

    /**
     * gameCommunication is the game instance.
     * when MapActivity wants to send info to the game, i will send out a broadcast which is caught here.
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("sending playerUserId".equals(action)) {
            String playerUserId = intent.getStringExtra("playerUserId");
            //Use static reference to communicate with the LibGDX game
            GameCommunication gameCommunication = AndroidLauncher.getGameCommunication();
            if (gameCommunication != null) {
                gameCommunication.onPlayerInfoReceived(playerUserId);
            }
        } else if ("quit map activity".equals(action)) {
            GameCommunication gameCommunication = AndroidLauncher.getGameCommunication();
            if (gameCommunication != null) {
                gameCommunication.onQuitMapActivity();
            }
        }
    }
}