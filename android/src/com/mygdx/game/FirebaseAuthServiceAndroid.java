package com.mygdx.game;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;
import com.badlogic.gdx.Gdx;

import javax.security.auth.callback.Callback;

public class FirebaseAuthServiceAndroid implements AuthService {
    public FirebaseAuth auth;

    public FirebaseAuthServiceAndroid() {
        auth = FirebaseAuth.getInstance();
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