package com.mygdx.global;

import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Skill;
import com.mygdx.game.handlers.UserBattleHandler;
import com.mygdx.server.handlers.ServerBattleHandler;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BattleState{
    private Player p1Player, p2Player;

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

    private transient ScheduledExecutorService npcAttackScheduler = Executors.newScheduledThreadPool(1);

    public BattleState() {
        this.turn = Turn.PLAYERONETURN;
        this.numofTurns = 1;

        battleEnded = false;
        petChanged = false;
        petAttacked = false;
        againstNPC = false;
    }

    public BattleState(Player p1Player, Player p2Player) {
        this.turn = Turn.PLAYERONETURN;
        this.numofTurns = 1;

        battleEnded = false;
        petChanged = false;
        petAttacked = false;
        againstNPC = false;

        this.p1Player = p1Player;
        this.p2Player = p2Player;
    }

    public BattleState(Player player, NPC npc) {
        this.turn = Turn.PLAYERONETURN;
        this.numofTurns = 1;

        battleEnded = false;
        petChanged = false;
        petAttacked = false;
        againstNPC = true; // Unique for NPC

        this.p1Player = player;
        this.p2Player = npc;
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

    public Boolean isAgainstNPC() {
        return againstNPC;
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
        System.out.println("Checking status");
        checkStatus();
    }

    public void checkStatus() {
        // Applies status effects on pets
        if (p1Player.updateStatus()) {
            petChanged = true;
        }
        if (p2Player.updateStatus()) {
            petChanged = true;
        }
    }

    public void playerAttack(String id, Skill skill) {
        // Online multiplayer pvp
        if (Objects.equals(p1Player.getUserId(), id)) {
            // Player1 has launched the attack

            if (skill.status == Skill.Status.ABSORB) {
                // Absorbs 0.3 * damage dealt to enemy pet
                int dmg = (int)(skill.damage * 0.3);
                p1Player.absorb(Math.min(dmg, (int)(p2Player.getCurrentPet().getHealth() * 0.3)));
            }
            if (skill.status == Skill.Status.STUN) {
                Random random = new Random();

                // Generate a random number between 0 and 1
                int chance = random.nextInt(2); // 0 or 1

                // Set stunTurn based on the random outcome
                if (chance == 1) {
                    // Player is stunned
                    p2Player.getCurrentPet().stunTurn = 3;
                }
            }

            if (skill.status == Skill.Status.POISON) {
                p2Player.getCurrentPet().poisonTurn = 3;
                p2Player.getCurrentPet().poisonDamage = (int)(skill.getDamage() * 0.3);
            }

            if (p2Player.takeDamage(skill)) {
                // takeDamage is true if petNum had died from attack
                petChanged = true;
            }
        } else {
            // Player2 has launched the attack

            if (skill.status == Skill.Status.ABSORB) {
                // Absorbs 0.3 * damage dealt to enemy pet
                int dmg = (int)(skill.damage * 0.3);
                p2Player.absorb(Math.min(dmg, (int)(p1Player.getCurrentPet().getHealth() * 0.3)));
            }
            if (skill.status == Skill.Status.STUN) {
                Random random = new Random();

                // Generate a random number between 0 and 1
                int chance = random.nextInt(2); // 0 or 1

                // Set stunTurn based on the random outcome
                if (chance == 1) {
                    // Player is stunned
                    p1Player.getCurrentPet().stunTurn = 3;
                }
            }
            if (skill.status == Skill.Status.POISON) {
                p1Player.getCurrentPet().poisonTurn = 3;
                p1Player.getCurrentPet().poisonDamage = (int)(skill.getDamage() * 0.3);
            }

            if (p1Player.takeDamage(skill)) {
                petChanged = true;
            }
        }

        changeTurn();
        petAttacked = true;
        System.out.println("Changing turns");
    }

    public void NPCAttack(String battleId) {
        // Player vs NPC

        if (!p2Player.getCurrentPet().isStunned()) {
            // NPC pet is not stunned

            Skill skill = p2Player.npcAttack(numofTurns);

            if (skill.status == Skill.Status.ABSORB) {
                // Absorbs 0.3 * damage dealt to enemy pet
                int dmg = (int)(skill.damage * 0.3);
                p2Player.absorb(Math.min(dmg, (int)(p1Player.getCurrentPet().getHealth() * 0.3)));
            }
            if (skill.status == Skill.Status.STUN) {
                p1Player.getCurrentPet().stunTurn = 3;
            }
            if (skill.status == Skill.Status.POISON) {
                p1Player.getCurrentPet().poisonTurn = 3;
                p1Player.getCurrentPet().poisonDamage = (int)(skill.getDamage() * 0.3);
            }

            if (p1Player.takeDamage(skill)) {
                petChanged = true;
            }
        }

        changeTurn();
        petAttacked = true;
        System.out.println("Changing turns");

        ServerBattleHandler.sendBattleState(battleId);

        // If battle has ended
        if (!ServerBattleHandler.getBattleState(battleId).playersAlive()){
            ServerBattleHandler.sendEndBattle(battleId);
        }
    }


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
        // For updating client's battleState with newState during battle
        p1Player.update(newState.p1Player);
        p2Player.update(newState.p2Player);
        turn = newState.turn;
        battleEnded = newState.battleEnded;
        petChanged = newState.petChanged;
        petAttacked = newState.petAttacked;
    }

    public void scheduleNPCAttack(String battleId) {
        // Schedule NPCAttackRunnable to run after 4 seconds
        if (isAgainstNPC() && p2Player.isAlive()) {
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

}
