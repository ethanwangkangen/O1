package com.mygdx.global;

import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Skill;

import java.io.Serializable;
import java.nio.file.FileSystemNotFoundException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
    public Boolean battleEnded;
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


    //first player to connect to server will be set as player1, second set as player2
    public void addPlayer(Player player) {
        if (this.player1 == null) {
            this.player1 = player;
            this.numPlayers = 1;
        } else if (this.player2 == null) {
            this.player2 = player;
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

    public void attack(String id, Skill skill) {
        if (!Objects.equals(player1.getIdString(), id)) {
            player1.takeDamage(skill);
        } else {
            player2.takeDamage(skill);
        }
        changeTurn();
        System.out.println("Changing turns");

        if (!playerAlive()) {
            System.out.println("Changing battleEnded to true");
            battleEnded = true;
        }
    }

    public void loadTextures(Runnable callback) {
        player1.loadTextures(() -> {
            player2.loadTextures(callback);
        });
        System.out.println("battlestate textures loading");

    }

    public void changePet(String id, Player.Pet pet) {
        if (Objects.equals(player1.getIdString(), id)) {
            player1.changePet(pet);
        } else if (Objects.equals(player2.getIdString(), id)) {
            player2.changePet(pet);
        }
    }

}
