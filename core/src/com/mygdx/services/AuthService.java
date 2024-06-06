package com.mygdx.services;

public interface AuthService {
    void signUp(String email, String password);
    void signIn(String email, String password);
    boolean isUserSignedIn();
    void signOut();
}
