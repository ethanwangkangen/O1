package com.mygdx.global;

import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Skill;

import java.io.Serializable;
import java.util.UUID;

public class BattleState{

    //public static final BattleState INSTANCE = new BattleState();
    private Player player1, player2;

    public enum Turn {
        PLAYERONETURN,
        PLAYERTWOTURN,
    }

    private Turn turn;
    private int numPlayers;
    private Boolean battleStarted;
    private Boolean battleEnded;
    private int numofTurns;

    public BattleState() {
        this.turn = Turn.PLAYERONETURN;
        this.numPlayers = 0;
        this.numofTurns = 1;
        battleStarted = false;
        battleEnded = false;
    }

    public int getNumPlayers() {
        return this.numPlayers;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean playerAlive() {
        return player1.isAlive() || player2.isAlive();
    }

    public void createPlayer(String username) {
        if (this.player1 == null) {
            this.player1 = new Player(username);
            this.numPlayers = 1;
        } else if (this.player2 == null) {
            this.player2 = new Player(username);
            this.numPlayers = 2;
        } else {
            System.out.println("max players"); //to do: throw error
        }

        if (this.numPlayers == 2) {
            battleStarted = true;
        }
    }

    public Turn getPlayerTurn() {
        return turn;
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

        if (!playerAlive()) {
            battleEnded = true;
        }
    }

    public void loadTextures() {
        player1.loadTextures();
        player2.loadTextures();
    }

}
