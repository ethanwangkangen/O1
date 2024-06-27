package com.mygdx.game;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Random;

public class NPCLocations {
    ArrayList<LatLng> sgLocations = new ArrayList<LatLng>();
    Random rand = new Random();

    public NPCLocations() {
        sgLocations.add(new LatLng(1.3554, 103.8164));
        sgLocations.add(new LatLng(1.3478, 103.8713));
        sgLocations.add(new LatLng(1.3605, 103.7007));
        sgLocations.add(new LatLng(1.3941, 103.7069));
        sgLocations.add(new LatLng(1.3320, 103.8973));
    }

    public LatLng getRandomLocation() {
        return sgLocations.get(rand.nextInt(sgLocations.size()));
    }


}
