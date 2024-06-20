package com.mygdx.game.events;

import com.mygdx.game.entities.Player;

public class PlayerAcceptBattleEvent {
    public String requesterUID;
    public String opponentUID;
    public Player requesterPlayer;
    public Player opponentPlayer;
    public PlayerAcceptBattleEvent(){};
}
