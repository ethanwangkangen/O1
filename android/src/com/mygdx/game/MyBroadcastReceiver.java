package com.mygdx.game;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mygdx.game.interfaces.GameCommunication;

/**
 * Used to relay messages between components of Android;
 * from Map activity to libgdx.
 */
public class MyBroadcastReceiver extends BroadcastReceiver{

    private static MyBroadcastReceiver instance = new MyBroadcastReceiver();
    /**
     * gameCommunication is the game instance.
     * when MapActivity wants to send info to the game, i will send out a broadcast which is caught here.
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        System.out.println("Action is " + action);
        if ("sending battle req".equals(action)) {
            System.out.println("Sending battle req (in broadcast receiver)");
            String playerUserId = intent.getStringExtra("playerUserId");
            // Use static reference to communicate with the LibGDX game
            GameCommunication gameCommunication = AndroidLauncher.getGameCommunication();
            if (gameCommunication != null) {

                gameCommunication.onEnemyInfoReceived(playerUserId);
            }
        } else if ("quit map activity".equals(action)) {
            System.out.println("Quitting map");
            GameCommunication gameCommunication = AndroidLauncher.getGameCommunication();
            if (gameCommunication != null) {
                gameCommunication.onQuitMapActivity();
            }
        }
    }
}