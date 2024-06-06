package com.mygdx.game;

import android.os.Bundle;

import android.util.Log;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.DarwinsDuel;
import com.google.firebase.FirebaseApp;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new DarwinsDuel(new FireBaseAndroid()), config);

		// Initialize Firebase
		FirebaseApp.initializeApp(this);

		// Log Firebase initialization status
		if (FirebaseApp.getInstance() != null) {
			System.out.println("Firebase initialized successfully");
		} else {
			System.out.println("Firebase initialization failed");
		}



	}
}
