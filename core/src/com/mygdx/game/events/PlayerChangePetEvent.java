package com.mygdx.game.events;

import com.mygdx.game.entities.Player;

public class PlayerChangePetEvent {
    public PlayerChangePetEvent() {
    }

    public Player.PetNum petNum;
    public String playerId; // of player changing pets
    public String battleId;


}
