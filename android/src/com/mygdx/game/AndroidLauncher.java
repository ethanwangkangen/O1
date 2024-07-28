package com.mygdx.game;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.FirebaseApp;
import com.mygdx.game.interfaces.GameCommunication;
import com.mygdx.game.interfaces.MapInterface;

/**
 * The entry point of the application.
 * Implements MapInterface such that methods like showMap() can be called from game instance.
 */
public class AndroidLauncher extends AndroidApplication implements MapInterface {

	private static GameCommunication gameCommunication; //will be the game instance
	private MyBroadcastReceiver receiver;

	private Boolean isMapOn;

	/**
	 * Called when AndroidLauncher is started.
	 * @param savedInstanceState If the activity is being re-initialized after
	 *     previously being shut down then this Bundle contains the data it most
	 *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
	 *
	 */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		DarwinsDuel game = new DarwinsDuel(new FirebaseAuthServiceAndroid());

		isMapOn = false;

		// Initialize the game
		initialize(game, config);

		//Initialise GameCommunication
		this.gameCommunication = game;

		// Register the broadcast receiver
		receiver = new MyBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("sending battle req");
		intentFilter.addAction("quit map activity");
		intentFilter.addAction("sending NPC req");
		intentFilter.addAction("attribute activity");
		LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

		// Initialize Firebase
		FirebaseApp.initializeApp(this);

		// Log Firebase initialization status
		if (FirebaseApp.getInstance() != null) {
			System.out.println("Firebase initialized successfully");
		} else {
			System.out.println("Firebase initialization failed");
		}
		// FirebaseDatabase.getInstance().setPersistenceEnabled(true);

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

	/**
	 * Called from within MapScreen of libgdx game to start the Map Activity.
	 */
	@Override
	public void showMap() {
		System.out.println("Showing the map");
		isMapOn = true;
		Intent intent = new Intent(this, MapActivity.class);
		intent.putExtra("latitude", 37.7749); // Example latitude
		intent.putExtra("longitude", -122.4194); // Example longitude
		startActivity(intent);
	}

	/**
	 * Called to stop showing the Map Activity.
	 */
	@Override
	public void stopMap() {
		if (isMapOn) {
			Intent intent = new Intent("finish map");
			LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
			isMapOn = false;
		}
	}

	/**
	 * Tells the Map Activity to show the accept or reject battle popup
	 */
	@Override
	public void acceptOrReject() {
		Intent intent = new Intent("accept or reject");
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	@Override
	public Boolean mapOn() {
		return isMapOn;
	}


}