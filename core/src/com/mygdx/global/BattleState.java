package com.mygdx.global;

import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Skill;

import java.io.Serializable;
import java.util.UUID;

public class BattleState {

    public static final BattleState INSTANCE = new BattleState();
    public Player player1, player2;

    public enum Turn {
        PLAYERONETURN,
        PLAYERTWOTURN,
    }

    private Turn turn;
    private int numPlayers;
    private Boolean battleStarted;
    private int numofTurns;

    public BattleState() {
        this.turn = Turn.PLAYERONETURN;
        this.numPlayers = 0;
        this.numofTurns = 1;
        battleStarted = false;
    }

    public int getNumPlayers() {
        return this.numPlayers;
    }

    public boolean playerAlive() {
        return player1.isAlive() || player2.isAlive();
    }

    public void addPlayer(Player Player) {
        if (this.player1 == null) {
            this.player1 = Player;
            this.numPlayers = 1;
        } else if (this.player2 == null) {
            this.player2 = Player;
            this.numPlayers = 2;
        } else {
            System.out.println("max players"); //to do: throw error
        }

        if (this.numPlayers == 2) {
            battleStarted = true;
        }
    }

    public Player getPlayerTurn() {
        if (this.turn == Turn.PLAYERONETURN) {
            return this.player1;
        } else {
            return this.player2;
        }
    }

    public void changeTurn() {
        if (this.turn == Turn.PLAYERONETURN) {
            this.turn = Turn.PLAYERTWOTURN;
        } else {
            this.turn = Turn.PLAYERONETURN;
            this.numofTurns += 1;
        }
    }

    public boolean hasStarted() {
        return this.battleStarted;
    }
    public void startBattle() {
        battleStarted = true;
    }

    public void attack(UUID id, Skill skill) {
        if (player1.getId() != id) {
            player1.takeDamage(skill);
        } else {
            player2.takeDamage(skill);
        }
    }

}
