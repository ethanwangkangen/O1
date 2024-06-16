package com.mygdx.game.oldEvents;

import com.mygdx.game.entities.Player;

public class AddPlayerEvent {

    public String username;
    public Player player;

    public AddPlayerEvent() {
    }

    public Player getPlayer() {
        return player;
    }


}
