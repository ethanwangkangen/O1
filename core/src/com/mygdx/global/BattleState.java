package com.mygdx.global;

import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Skill;

import java.util.Objects;

public class BattleState{

    //public static final BattleState INSTANCE = new BattleState();
    private Player p1Player, p2Player;

    public enum Turn {
        PLAYERONETURN,
        PLAYERTWOTURN,
    }

    public Turn turn;
    private int numPlayers;
    private Boolean battleStarted;
    public Boolean battleEnded;
    public Boolean firstRound;
    public Boolean petChanged;
    public Boolean petAttacked;
    private int numofTurns;

    public BattleState() {
        this.turn = Turn.PLAYERONETURN;
        this.numPlayers = 0;
        this.numofTurns = 1;
        battleStarted = false;
        battleEnded = false;
//        firstRound = true;
        petChanged = false;
        petAttacked = false;
    }

    public BattleState(Player p1Player, Player p2Player) {
        this.turn = Turn.PLAYERONETURN;
        this.numPlayers = 0;
        this.numofTurns = 1;
        battleStarted = false;
        battleEnded = false;
//        firstRound = true;
        petChanged = false;
        petAttacked = false;

        this.p1Player = p1Player;
        this.p2Player = p2Player;
    }

    public int getNumPlayers() {
        return this.numPlayers;
    }

    public Player getPlayer1() {
        return p1Player;
    }

    public Player getPlayer2() {
        return p2Player;
    }

    public boolean playersAlive() {
        return p1Player.isAlive() && p2Player.isAlive();
    }


    //first player to connect to server will be set as player1, second set as player2
    public void addPlayer(Player player) {
        if (this.p1Player == null) {
            this.p1Player = player;
            this.numPlayers = 1;
        } else if (this.p2Player == null) {
            this.p2Player = player;
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
        if (!Objects.equals(p1Player.getUserId(), id)) {
            if (p1Player.takeDamage(skill)) {
                // takeDamage is true if pet had died from attack
                petChanged = true;
            }
        } else {
            if (p2Player.takeDamage(skill)) {
                petChanged = true;
            }
        }

        changeTurn();
        petAttacked = true;
        System.out.println("Changing turns");

//        if (!playersAlive()) {
//            System.out.println("Changing battleEnded to true");
//            battleEnded = true;
//        }
    }

    public void loadTextures(Runnable callback) {
        p1Player.loadTextures(() -> {
            p2Player.loadTextures(callback);
        });
        System.out.println("battlestate textures loading");

    }

//    public void changePet(String id, Player.Pet pet) {
//        if (Objects.equals(p1Player.getUserId(), id)) {
//            p1Player.changePet(pet);
//        } else if (Objects.equals(p2Player.getUserId(), id)) {
//            p2Player.changePet(pet);
//        }
//        changeTurn();
//        petChanged = true;
//    }

    public void update(BattleState newState) {
        p1Player.update(newState.p1Player);
        p2Player.update(newState.p2Player);
        turn = newState.turn;
        battleEnded = newState.battleEnded;
        battleStarted = newState.battleStarted;
//        firstRound = newState.firstRound;
        petChanged = newState.petChanged;
        petAttacked = newState.petAttacked;
    }

}
