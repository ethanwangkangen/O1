package com.mygdx.game.callbacks;

public interface AuthResultCallback {
    void onSuccess();
    void onFailure(Exception exception);
}