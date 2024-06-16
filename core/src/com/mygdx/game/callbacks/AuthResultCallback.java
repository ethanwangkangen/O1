package com.mygdx.game;

public interface AuthResultCallback {
    void onSuccess();
    void onFailure(Exception exception);
}