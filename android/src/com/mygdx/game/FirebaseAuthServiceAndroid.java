package com.mygdx.game;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.callbacks.AuthResultCallback;
import com.mygdx.game.callbacks.PlayerCallback;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.CrocLesnar;
import com.mygdx.game.entities.Doge;
import com.mygdx.game.entities.Dragon;
import com.mygdx.game.entities.Froggy;
import com.mygdx.game.entities.MeowmadAli;
import com.mygdx.game.entities.MouseHunter;
import com.mygdx.game.entities.Player;
import com.mygdx.game.interfaces.AuthService;
import java.util.ArrayList;
import java.util.List;

/**
 * Authenticator service within the Android module that implements methods that will be called from game instance.
 */
public class FirebaseAuthServiceAndroid implements AuthService {
    public FirebaseAuth auth;
    private DatabaseReference database;

    public FirebaseAuthServiceAndroid() {
        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void sendPlayerToFirebase(Player player) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference playerRef = database.child("users").child(userId).child("player");
        playerRef.setValue(player);
    }


    /**
     * Retrieves the player data from firebase, passes Player instance to the PlayerCallback for the callback to handle.
     * @param playerCallback
     */
    @Override
    public void getPlayerFromFirebase(PlayerCallback playerCallback) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference playerRef = database.child("users").child(userId).child("player");
        playerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Player player = new Player();

                // Basic player data
                player.setUsername(dataSnapshot.child("username").getValue(String.class));
                player.setUserId(dataSnapshot.child("userId").getValue(String.class));
                player.setCurrentPet(Player.PetNum.valueOf(dataSnapshot.child("currentPetNum").getValue(String.class)));

                // Deserialize battlePets
                List<Creature> battlePets = new ArrayList<>();
                for (DataSnapshot petSnapshot : dataSnapshot.child("battlePets").getChildren()) {
                    Creature pet = deserializeCreature(petSnapshot);
                    if (pet != null) {
                        battlePets.add(pet);
                    }
                }
                player.setBattlePets((ArrayList<Creature>) battlePets);

                // Deserialize reservePets
                List<Creature> reservePets = new ArrayList<>();
                for (DataSnapshot petSnapshot : dataSnapshot.child("reservePets").getChildren()) {
                    Creature pet = deserializeCreature(petSnapshot);
                    if (pet != null) {
                        reservePets.add(pet);
                    }
                }
                player.setReservePets((ArrayList<Creature>) reservePets);

                playerCallback.onCallback(player);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Failed to retrieve player data: " + databaseError.getMessage());
                playerCallback.onFailure(); // Pass null or handle error appropriately
            }
        });
    }


    private Creature deserializeCreature(DataSnapshot dataSnapshot) {
        String type = dataSnapshot.child("type").getValue(String.class);

        switch (type) {
            case "MeowmadAli":
                return dataSnapshot.getValue(MeowmadAli.class);
            case "CrocLesnar":
                return dataSnapshot.getValue(CrocLesnar.class);
            case "Froggy":
                return dataSnapshot.getValue(Froggy.class);
            case "MouseHunter":
                return dataSnapshot.getValue(MouseHunter.class);
            case "Doge":
                return dataSnapshot.getValue(Doge.class);
            case "Dragon":
                return dataSnapshot.getValue(Dragon.class);
            default:
                return null; // Handle unknown types or return appropriate default
        }
    }

    @Override
    public String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void signUp(String email, String password, final AuthResultCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    @Override
    public void signIn(String email, String password, final AuthResultCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }




}