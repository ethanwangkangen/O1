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
    private int numofTurns;

    public Boolean battleEnded;

    public Boolean petChanged;
    public Boolean petAttacked;

    public BattleState() {
        this.turn = Turn.PLAYERONETURN;
        this.numofTurns = 1;

        battleEnded = false;
        petChanged = false;
        petAttacked = false;
    }

    public BattleState(Player p1Player, Player p2Player) {
        this.turn = Turn.PLAYERONETURN;
        this.numofTurns = 1;

        battleEnded = false;
        petChanged = false;
        petAttacked = false;

        this.p1Player = p1Player;
        this.p2Player = p2Player;
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

//    public void loadTextures(Runnable callback) {
//        p1Player.loadTextures(() -> {
//            p2Player.loadTextures(callback);
//        });
//        System.out.println("battlestate textures loading");
//
//    }

    public void changePet(String id, Player.PetNum pet) {
        if (Objects.equals(p1Player.getUserId(), id)) {
            p1Player.changeCurrentPet(pet);
        } else if (Objects.equals(p2Player.getUserId(), id)) {
            p2Player.changeCurrentPet(pet);
        }
        changeTurn();
        petChanged = true;
    }

    public void update(BattleState newState) {
        p1Player.update(newState.p1Player);
        p2Player.update(newState.p2Player);
        turn = newState.turn;
        battleEnded = newState.battleEnded;
        petChanged = newState.petChanged;
        petAttacked = newState.petAttacked;
    }

}
