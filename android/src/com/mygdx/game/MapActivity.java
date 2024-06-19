package com.mygdx.game;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.AdvancedMarker;
import com.google.android.gms.maps.model.AdvancedMarkerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private double initialLatitude = 1.2431;
    private double initialLongitude = 103.8198;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();

    private DatabaseReference database;
    private Map<String, Marker> playerMarkers = new HashMap<>(); //map userId to marker

    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Retrieve the latitude and longitude from the intent
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude", 0);
        double longitude = intent.getDoubleExtra("longitude", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        //initialise database
        database = FirebaseDatabase.getInstance().getReference().child("users");
    }


    /**
     * called automatically when AndroidLauncher starts its MapActivity.
     * @param map
     */
    @Override
    public void onMapReady(GoogleMap map) {
        LatLng position = new LatLng(initialLatitude, initialLongitude);
        this.googleMap = map;

        // Add current player?
        // Todo refactor into new function. set custom marker for own player
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(initialLatitude, initialLongitude))
                .title("Marker"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10));

        displayAll();
    }

    /**
     * Add all players (except self)
     */
    public void displayAll() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> previousUsers = new HashSet<>(playerMarkers.keySet()); // Set of userIds of the "existing" players
                Set<String> currentUsers = new HashSet<>(); // To add: all the current users

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey();

                    if (userId == currentUser.getUid()) { // Skip self
                        continue;
                    }

                    currentUsers.add(userId);

                    DataSnapshot locationSnapshot = userSnapshot.child("location");

                    if (locationSnapshot.exists()) {
                        double latitude = locationSnapshot.child("latitude").getValue(Double.class);
                        double longitude = locationSnapshot.child("longitude").getValue(Double.class);

                        LatLng playerLocation = new LatLng(latitude, longitude);

                        if (playerMarkers.containsKey(userId)) {
                            // Update existing marker position
                            playerMarkers.get(userId).setPosition(playerLocation);
                        } else {
                            // Marker doesn't exist, so create a new marker
                            Marker marker = googleMap.addMarker(new MarkerOptions()
                                    .position(playerLocation)
                                    .title(userId));
                            playerMarkers.put(userId, marker);
                        }
                        // todo make button for menuscreen
                    }
                }

                // Now remove all the old users that might have disconnected
                previousUsers.removeAll(currentUsers); // previousUsers now only contains disconnected users
                for (String userId : previousUsers) {
                    Marker marker = playerMarkers.remove(userId);
                    // Set marker to the one mapped to this userId and remove the mapping from the Map

                    if (marker != null) {
                        marker.remove();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //pass
            }
        });
    }


    //todo in future: change logic such that only nearby players can be fought
    @Override
    public boolean onMarkerClick(Marker marker) {
        String playerUserId = marker.getTitle();
        sendInfoToLibGDX(playerUserId); //send userId of target enemy to client side libgdx
        return false;
    }

    private void sendInfoToLibGDX(String playerUserId) {
        Intent intent = new Intent("com.example.ACTION_SEND_INFO");
        intent.putExtra("playerUserId", playerUserId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}