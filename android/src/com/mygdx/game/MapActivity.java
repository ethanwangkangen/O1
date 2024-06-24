package com.mygdx.game;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.Manifest;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import com.mygdx.game.interfaces.BattleResponseListener;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, BattleResponseListener {
    private double selfLatitude = 1.2431;
    private double selfLongitude = 103.8198;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private long lastUpdateTime = 0;
    private static final long MIN_TIME_BETWEEN_UPDATES = 5000; // 5 seconds
    private Dialog dialog; // Fight failed popup
    private Dialog acceptOrReject; // Accept or reject battle popup
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = auth.getCurrentUser();
    private String myUserId = currentUser.getUid();
    private String enemyUID;
    private DatabaseReference database;
    private Map<String, Marker> playerMarkers = new HashMap<>(); //map userId to marker
    private GoogleMap googleMap;
    private BroadcastReceiver receiver;

    /**
     * called automatically when AndroidLauncher starts its MapActivity.
     * @param map
     */
    @Override
    public void onMapReady(GoogleMap map) {
        LatLng position = new LatLng(selfLatitude, selfLongitude);
        this.googleMap = map;

        // Add current player?
        // Todo refactor into new function. set custom marker for own player
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10));

        //Add other players
        displayAll();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                // todo tell player to allow location tracking
            }
        }
    }
    private void startLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(5000)
                .setMaxUpdateDelayMillis(1000)
                .build();

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    sendLocationToFirebase(location);
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void sendLocationToFirebase(Location location) {

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime < MIN_TIME_BETWEEN_UPDATES) {
            System.out.println("LocationUpdate" + "Location update blocked - too fast");
            return;
        }
        lastUpdateTime = currentTime;
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Send to firebase
        // Set location
        DatabaseReference locationRef = database.child(myUserId).child("location");

        // Update location data in database
        locationRef.child("latitude").setValue(latitude);
        locationRef.child("longitude").setValue(longitude);

        // Update local location data
        selfLatitude = latitude;
        selfLongitude = longitude;

        System.out.println("sending location" + latitude + " " + longitude);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        // Choose pet button
        Button exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exit the activity
                sendQuitToLibGDX();
                finish();
            }
        });

        // Fight button
        Button fightButton = findViewById(R.id.fight_button);
        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enemyUID != null) {
                    sendBattleReqToEnemy(enemyUID);
                } else {
                    showDialog();
                    // Tell player to select an enemy first before requesting fight
                }
            }

        });

        // Initialise database
        database = FirebaseDatabase.getInstance().getReference().child("users");

        // If permission for location not granted, ask for it
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
            // Result of this is passed to onRequestPermissionsResult
        } else {

            // If permission granted start updating location (send to firebase constantly)
            startLocationUpdates();
        }

        // Register the broadcast receiver (for accept or reject screen). Broadcasted from AndroidLauncher.
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("accept or reject")) {
                    showAcceptOrReject();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter("accept or reject");
    }

    public void showAcceptOrReject(){
        // Show the accept or reject popup.
        acceptOrReject = new Dialog(this);
        acceptOrReject.setContentView(R.layout.accept); // Create a custom layout for your dialog
        Button acceptButton = dialog.findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBattleResponse(true);
            }
        });

        Button rejectButton = dialog.findViewById(R.id.reject_button);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBattleResponse(false);
            }
        });
        acceptOrReject.show();
    }

    @Override
    public void onBattleResponse(boolean accepted) { // Nothing here. Listener in UserEventListener will handle.
    }

    /**
     * Popup asking player to select an enemy before fighting
     */
    public void showDialog() { //
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        Button dismissButton = dialog.findViewById(R.id.dismiss_button);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });

        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }


    /**
     * Add all players (except self)
     */
    public void displayAll() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("Data change detected");
                Set<String> previousUsers = new HashSet<>(playerMarkers.keySet()); // Set of userIds of the "existing" players
                Set<String> currentUsers = new HashSet<>(); // To add: all the current users

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    String username = userSnapshot.child("player").child("username").getValue(String.class);

                    currentUsers.add(userId);

                    DataSnapshot locationSnapshot = userSnapshot.child("location");

                    if (locationSnapshot.exists()) {
                        System.out.println("Location found");
                        Double latitude = locationSnapshot.child("latitude").getValue(Double.class);
                        Double longitude = locationSnapshot.child("longitude").getValue(Double.class);

                        if (latitude != null && longitude != null) {
                            LatLng playerLocation = new LatLng(latitude, longitude);

                            if (playerMarkers.containsKey(userId)) {
                                // Update existing marker position
                                playerMarkers.get(userId).setPosition(playerLocation);
                                System.out.println("updating key. location is " + latitude + " " + longitude);
                            } else {
                                // Marker doesn't exist, so create a new marker
                                BitmapDescriptor enemy = BitmapDescriptorFactory.fromResource(R.drawable.player1);
                                BitmapDescriptor self = BitmapDescriptorFactory.fromResource(R.drawable.playerself);
                                Marker marker;

                                if (Objects.equals(userId, myUserId)) {
                                    marker = googleMap.addMarker(new MarkerOptions()
                                            .position(playerLocation)
                                            .title("You")
                                            .icon(self));
                                } else {
                                    marker = googleMap.addMarker(new MarkerOptions()
                                            .position(playerLocation)
                                            .title(username)
                                            .icon(enemy));
                                }
                                playerMarkers.put(userId, marker);
                                System.out.println("Player marker added");

                            }

                        } else {
                            // Handle the case where latitude or longitude is null
                            System.out.println("Latitude or Longitude is null");
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
                        System.out.println("Marker removed");
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
        String playerUsername = marker.getTitle();
        for (Map.Entry entry : playerMarkers.entrySet()) {
            if (entry.getValue().equals(marker)) {
                String playerUserId = (String) entry.getKey();
                setTargetEnemy(playerUserId);
                return false;
            }
        }
        //sendInfoToLibGDX(playerUserId); //send userId of target enemy to client side libgdx
        return false;
    }
    private void setTargetEnemy(String playerUserId) {
        this.enemyUID = playerUserId;
    }
    private void sendBattleReqToEnemy(String playerUserId) {
        Intent intent = new Intent("sending battle req");
        intent.putExtra("playerUserId", playerUserId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendQuitToLibGDX() {
        Intent intent = new Intent("quit map activity");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


}