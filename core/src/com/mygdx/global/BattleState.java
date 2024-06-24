package com.mygdx.global;

import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Skill;

import java.util.Objects;

public class BattleState{

    //public static final BattleState INSTANCE = new BattleState();
    private Player player1, player2;

    public enum Turn {
        PLAYERONETURN,
        PLAYERTWOTURN,
    }

    public Turn turn;
    private int numofTurns;

    private Boolean againstNPC;
    public Boolean battleEnded;

    public Boolean petChanged;
    public Boolean petAttacked;

    public BattleState() {
        this.turn = Turn.PLAYERONETURN;
        this.numofTurns = 1;

        battleEnded = false;
        petChanged = false;
        petAttacked = false;
        againstNPC = false;
    }

    public BattleState(Player player, NPC npc) {
        this.turn = Turn.PLAYERONETURN;
        this.numofTurns = 1;
        battleEnded = false;
        petChanged = false;
        petAttacked = false;

        againstNPC = true; // unique for NPC

        this.player1 = player;
        this.player2 = npc;

        System.out.println(player1.battlePets.get(0).getName());
        System.out.println(player2.battlePets.get(0).getName());
    }

    public BattleState(Player p1Player, Player p2Player) {
        this.turn = Turn.PLAYERONETURN;
        this.numofTurns = 1;

        battleEnded = false;
        petChanged = false;
        petAttacked = false;
        againstNPC = false;

        this.player1 = p1Player;
        this.player2 = p2Player;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean playersAlive() {
        return player1.isAlive() && player2.isAlive();
    }

    public Turn getPlayerTurn() {
        return turn;
    }

    public Boolean isAgainstNPC() {
        return againstNPC;
    }

    public void changeTurn() {
        if (this.turn == Turn.PLAYERONETURN) {
            this.turn = Turn.PLAYERTWOTURN;
        } else {
            this.turn = Turn.PLAYERONETURN;
            this.numofTurns += 1;
        }
    }

    public void playerAttack(String id, Skill skill) {
        // online multiplayer pvp
        if (Objects.equals(player1.getIdString(), id)) {
            // player1 has launched the attack
            if (player2.takeDamage(skill)) {
                // takeDamage is true if petNum had died from attack
                petChanged = true;
            }
        } else {
            if (player1.takeDamage(skill)) {
                petChanged = true;
            }
        }

        changeTurn();
        petAttacked = true;
        System.out.println("Changing turns");
    }

    public void NPCAttack() {
        // Player vs NPC
        System.out.println("player2 name: " + player2.username);
        Skill skill = player2.npcAttack(numofTurns);

        if (skill == null) {
            System.out.println("NPC skill is null");
        }
        if (player1.takeDamage(skill)) {
            petChanged = true;
        }

        changeTurn();
        petAttacked = true;
        System.out.println("Changing turns");
    }


    public void changePet(String id, Player.PetNum petNum) {
        if (Objects.equals(player1.getIdString(), id)) {
            // player1 changing pets
            player1.changeCurrentPet(petNum);
        } else if (Objects.equals(player2.getIdString(), id)) {
            // player2 changing pets
            player2.changeCurrentPet(petNum);
        }
        changeTurn();
        petChanged = true;
    }

    public void update(BattleState newState) {
        // for updating client's battleState with newState during battle
        player1.update(newState.player1);
        player2.update(newState.player2);
        turn = newState.turn;
        battleEnded = newState.battleEnded;
        petChanged = newState.petChanged;
        petAttacked = newState.petAttacked;
    }

}
