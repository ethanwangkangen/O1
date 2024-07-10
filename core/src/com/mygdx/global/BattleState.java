package com.mygdx.global;

import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Skill;
import com.mygdx.server.handlers.BattleHandler;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private transient ScheduledExecutorService npcAttackScheduler = Executors.newScheduledThreadPool(1);

    public void scheduleNPCAttack(String battleId) {
        // Schedule NPCAttackRunnable to run after 4 seconds
        if (isAgainstNPC() && player2.isAlive()) {
            npcAttackScheduler.schedule(new NPCAttackRunnable(battleId), 3, TimeUnit.SECONDS);
        }
    }

    // Runnable implementation to execute NPCAttack with battleId
    private class NPCAttackRunnable implements Runnable {
        private String battleId;

        public NPCAttackRunnable(String battleId) {
            this.battleId = battleId;
        }

        @Override
        public void run() {
            NPCAttack(battleId); // Call NPCAttack with the provided battleId
        }
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
        System.out.println("Checking status");
        checkStatus();
    }

    public void checkStatus() {
        // applies status effects on pets
        if (player1.updateStatus()) {
            petChanged = true;
        }
        if (player2.updateStatus()) {
            petChanged = true;
        }
    }

    public void playerAttack(String id, Skill skill) {
        // online multiplayer pvp
        if (Objects.equals(player1.getIdString(), id)) {
            // player1 has launched the attack

            if (skill.status == Skill.Status.ABSORB) {
                // absorbs 0.3 * damage dealt to enemy pet
                int dmg = (int)(skill.damage * 0.3);
                player1.absorb(Math.min(dmg, (int)(player2.getCurrentPet().getHealth() * 0.3)));
            }
            if (skill.status == Skill.Status.STUN) {
                player2.getCurrentPet().stunTurn = 3;
            }
            if (skill.status == Skill.Status.POISON) {
                player2.getCurrentPet().poisonTurn = 3;
                player2.getCurrentPet().poisonDamage = (int)(skill.getDamage() * 0.3);
            }

            if (player2.takeDamage(skill)) {
                // takeDamage is true if petNum had died from attack
                petChanged = true;
            }
        } else {
            // player2 has launched the attack

            if (skill.status == Skill.Status.ABSORB) {
                // absorbs 0.3 * damage dealt to enemy pet
                int dmg = (int)(skill.damage * 0.3);
                player2.absorb(Math.min(dmg, (int)(player1.getCurrentPet().getHealth() * 0.3)));
            }
            if (skill.status == Skill.Status.STUN) {
                player1.getCurrentPet().stunTurn = 3;
            }
            if (skill.status == Skill.Status.POISON) {
                player1.getCurrentPet().poisonTurn = 3;
                player1.getCurrentPet().poisonDamage = (int)(skill.getDamage() * 0.3);
            }

            if (player1.takeDamage(skill)) {
                petChanged = true;
            }
        }

        changeTurn();
        petAttacked = true;
        System.out.println("Changing turns");
    }

    public void NPCAttack(String battleId) {
        // Player vs NPC
//        System.out.println("player2 name: " + player2.username);
        Skill skill = player2.npcAttack(numofTurns);

//        if (skill == null) {
//            System.out.println("NPC skill is null");
//        }

        if (skill.status == Skill.Status.ABSORB) {
            // absorbs 0.3 * damage dealt to enemy pet
            int dmg = (int)(skill.damage * 0.3);
            player2.absorb(Math.min(dmg, (int)(player1.getCurrentPet().getHealth() * 0.3)));
        }
        if (skill.status == Skill.Status.STUN) {
            player1.getCurrentPet().stunTurn = 3;
        }
        if (skill.status == Skill.Status.POISON) {
            player1.getCurrentPet().poisonTurn = 3;
            player1.getCurrentPet().poisonDamage = (int)(skill.getDamage() * 0.3);
        }

        if (player1.takeDamage(skill)) {
            petChanged = true;
        }

        changeTurn();
        petAttacked = true;
        System.out.println("Changing turns");

        BattleHandler.sendBattleState(battleId);

        // if battle has ended
        if (!BattleHandler.getBattleState(battleId).playersAlive()){
            BattleHandler.sendEndBattle(battleId);
        }
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
