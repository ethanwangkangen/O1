package com.mygdx.game;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.mygdx.game.interfaces.GameCommunication;
import com.mygdx.game.interfaces.MapInterface;


public class AndroidLauncher extends AndroidApplication implements MapInterface {

	private static GameCommunication gameCommunication; //will be the game instance
	private MyBroadcastReceiver receiver;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		DarwinsDuel game = new DarwinsDuel(new FirebaseAuthServiceAndroid());

		// Initialize the game
		initialize(game, config);

		//Initialise GameCommunication
		this.gameCommunication = game;

		// Register the broadcast receiver
		receiver = new MyBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("sending playerUserId");
		intentFilter.addAction("quit map activity");
		LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

		// Initialize Firebase
		FirebaseApp.initializeApp(this);

		// Log Firebase initialization status
		if (FirebaseApp.getInstance() != null) {
			System.out.println("Firebase initialized successfully");
		} else {
			System.out.println("Firebase initialization failed");
		}
		//FirebaseDatabase.getInstance().setPersistenceEnabled(true);

	}

	protected void onDestroy() {
		super.onDestroy();
		// Unregister the broadcast receiver when no longer needed
		if (receiver != null) {
			LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
		}
	}

	public static GameCommunication getGameCommunication() {
		return gameCommunication;
	}

	@Override
	public void showMap() {
		Intent intent = new Intent(this, MapActivity.class);
		intent.putExtra("latitude", 37.7749); // Example latitude
		intent.putExtra("longitude", -122.4194); // Example longitude
		startActivity(intent);
	}

	@Override
	public void acceptOrReject() {
		Intent intent = new Intent("accept or reject");
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}


}