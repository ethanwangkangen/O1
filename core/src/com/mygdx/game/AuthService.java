package com.mygdx.game;

public interface AuthService {
    public int x = 3;
    public void signUp(String email, String password);
    public void signIn(String email, String password);
    public boolean isUserSignedIn();
    public void signOut();
}