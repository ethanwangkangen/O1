package com.mygdx.game.events;

import com.mygdx.game.entities.Player;

/**
 * Send by a player to request to battle an enemy.
 */
public class PlayerRequestBattleEvent {
    public String opponentUID;
    public Player requesterPlayer;
    public PlayerRequestBattleEvent(){};
}
