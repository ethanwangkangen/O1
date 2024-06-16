package com.mygdx.game.interfaces;

/**
 * Used to facilitate communication between Map.activity and libgdx game instance
 */
public interface GameCommunication {
    void onPlayerInfoReceived(String playerUserId);
}

/*
Full explanation of how the broadcasting works:
1. AndroidLauncher Has a BroadcastReceiver
The AndroidLauncher activity registers a BroadcastReceiver to listen for specific broadcasts (intents).
This BroadcastReceiver acts like a listener waiting for specific events to happen.
2. MapActivity Sends a Broadcast
When a certain action occurs in MapActivity (like clicking a marker), it creates an Intent with the necessary data and broadcasts it using LocalBroadcastManager.
3. BroadcastReceiver Catches the Broadcast
The BroadcastReceiver registered in AndroidLauncher catches this broadcast.
Upon receiving the broadcast, the BroadcastReceiver retrieves the data from the intent.
4. Call the Method Implemented by the Game Instance
The BroadcastReceiver then calls a method (e.g., onPlayerInfoReceived) on the game instance, which implements the GameCommunication interface.
This method can then process the data as needed, bypassing module dependency restrictions.
 */