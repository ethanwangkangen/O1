package com.mygdx.global;

import com.mygdx.game.entities.Player;

/**
 * Send by a player to request to battle an enemy.
 */
public class PlayerRequestBattleEvent {
    public Player requesterPlayer;
    public PlayerRequestBattleEvent(){};
}
