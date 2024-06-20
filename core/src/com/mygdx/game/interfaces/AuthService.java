package com.mygdx.game.interfaces;

import com.mygdx.game.callbacks.AuthResultCallback;
import com.mygdx.game.callbacks.PlayerCallback;
import com.mygdx.game.entities.Player;

public interface AuthService {
    public void signUp(String email, String password, AuthResultCallback callback);
    public void signIn(String email, String password, AuthResultCallback callback);
    public boolean isUserSignedIn();
    public void signOut();
    public void sendPlayerToFirebase(Player player);
    public void getPlayerFromFirebase(PlayerCallback playerCallback);
    public String getUserId();

    public void sendLocationToFirebase(double latitude, double longitude);

}