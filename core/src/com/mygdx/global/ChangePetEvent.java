package com.mygdx.global;

import com.mygdx.game.entities.Player;

public class ChangePetEvent {
    public ChangePetEvent() {
    }

    public Player.PetNum petNum;
    public String playerId; // of player changing pets
    public String battleId;


}
