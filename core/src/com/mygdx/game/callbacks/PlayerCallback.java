package com.mygdx.game.callbacks;

import com.mygdx.game.entities.Player;

public interface PlayerCallback {
    void onCallback(Player player);
    void onFailure();
}