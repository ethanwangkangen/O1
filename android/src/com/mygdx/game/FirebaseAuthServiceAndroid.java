package com.mygdx.game;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;
import com.badlogic.gdx.Gdx;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.entities.Player;

import javax.security.auth.callback.Callback;

public class FirebaseAuthServiceAndroid implements AuthService {
    public FirebaseAuth auth;
    private DatabaseReference database;

    public FirebaseAuthServiceAndroid() {
        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }

    public void sendPlayerToFirebase(Player player) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference playerRef = database.child("users").child(userId).child("player");
        playerRef.setValue(player);
    }

    public void getPlayerFromFirebase(PlayerCallback playerCallback) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference playerRef = database.child("users").child(userId).child("player");
        playerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Player player = dataSnapshot.getValue(Player.class);
                playerCallback.onCallback(player);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Failed to retrieve player data.");
                playerCallback.onCallback(null); // Pass null or handle error appropriately
            }
        });
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

    @Override
    public boolean isUserSignedIn() {
        return auth.getCurrentUser() != null;
    }

    @Override
    public void signOut() {
        auth.signOut();
    }

//    private void handleTaskResult(Task<AuthResult> task, String action) {
//        if (task.isSuccessful()) {
//            Gdx.app.log(action, "Successful");
//        } else {
//            Gdx.app.log(action, "Failed: " + task.getException().getMessage());
//        }
//    }
}