package com.mygdx.game;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import androidx.appcompat.app.AppCompatActivity;



public class AndroidLauncher extends AndroidApplication implements MapInterface, OnMapReadyCallback{

//	private WebView webView;
//	private WebAppInterface webAppInterface;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new DarwinsDuel(new FirebaseAuthServiceAndroid()), config);

		// Initialize Firebase
		FirebaseApp.initializeApp(this);

		// Log Firebase initialization status
		if (FirebaseApp.getInstance() != null) {
			System.out.println("Firebase initialized successfully");
		} else {
			System.out.println("Firebase initialization failed");
		}

	}

	public void showMap() {
//		setContentView(R.layout.activity_main);

		// Get a handle to the fragment and register the callback.
//		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//				.findFragmentById(R.id.map);
//		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(@NonNull GoogleMap googleMap) {
		googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(0, 0))
				.title("Marker"));
	}


}