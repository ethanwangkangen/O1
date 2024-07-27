package com.mygdx.game;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mygdx.game.interfaces.GameCommunication;

/**
 * Used to relay messages between components of Android;
 * from Map activity to libgdx. Instantiated in AndroidManifest.xml
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    private static GameCommunication staticGameCommunication;
    private GameCommunication gameCommunication;

    public MyBroadcastReceiver() {
        // Default constructor for use with AndroidManifest.xml
    }

    // Constructor for dependency injection
    public MyBroadcastReceiver(GameCommunication gameCommunication) {
        this.gameCommunication = gameCommunication;
    }

    // Static method to set the game communication instance
    public static void setStaticGameCommunication(GameCommunication gameCommunication) {
        MyBroadcastReceiver.staticGameCommunication = gameCommunication;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        System.out.println("Action is " + action);

        GameCommunication currentGameCommunication = gameCommunication != null ? gameCommunication : staticGameCommunication;

        if (currentGameCommunication != null) {
            if ("sending battle req".equals(action)) {
                String playerUserId = intent.getStringExtra("playerUserId");
                currentGameCommunication.onEnemyInfoReceived(playerUserId);
            } else if ("quit map activity".equals(action)) {
                currentGameCommunication.onQuitMapActivity();
            } else if ("sending NPC req".equals(action)) {
                currentGameCommunication.onNPCReqReceived();
            } else if ("attribute activity".equals(action)) {
                currentGameCommunication.onAttributeActivity();
            }
        }
    }
}