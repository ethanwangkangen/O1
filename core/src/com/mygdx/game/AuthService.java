package com.mygdx.game;

import com.mygdx.game.entities.Player;

import javax.security.auth.callback.Callback;

public interface AuthService {
    public int x = 3;
    public void signUp(String email, String password, AuthResultCallback callback);
    public void signIn(String email, String password, AuthResultCallback callback);
    public boolean isUserSignedIn();
    public void signOut();
    public void sendPlayerToFirebase(Player player);
    public void getPlayerFromFirebase(PlayerCallback playerCallback);
}