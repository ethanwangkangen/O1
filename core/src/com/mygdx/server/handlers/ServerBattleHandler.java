package com.mygdx.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.mygdx.game.entities.*;
import com.mygdx.global.AddPetEvent;
import com.mygdx.global.BattleState;
import com.mygdx.global.EndBattleEvent;
import com.mygdx.global.StartBattleEvent;

import javax.xml.parsers.SAXParser;

import java.util.*;

public class ServerBattleHandler {

    public static Map<String, BattleState> battleStateList = new HashMap<>();
    // Map p1UID to the BattleState.

    public static Boolean checkPlayerFree(Player p) {
        if (Objects.equals(p.getUserId(), "NPC")) {
            System.out.println("Player is NPC");
            return true;
        }

        // If not an NPC, is a real player. Check that is not currently battling
        for (Map.Entry<String, BattleState> entry : battleStateList.entrySet()) {
            if (Objects.equals(entry.getValue().getPlayer1().getUserId(), p.getUserId())) {
                System.out.println("Player1 is in a battle");
                return false;
            } else if (Objects.equals(entry.getValue().getPlayer2().getUserId(), p.getUserId())) {
                System.out.println("Player2 is in a battle");
                return false;
            }
        }
        System.out.println("No players are in a battle");
        return true;
    }

    public static String initialiseBattle(Player p1Player, Player p2Player) {
        BattleState battleState = new BattleState(p1Player, p2Player);
        String battleId = p1Player.getUserId();
        battleStateList.put(battleId, battleState);
        System.out.println("New battle state created");
        return battleId;
    }

    public static String initialiseBattle(Player player, NPC npc) {
        // For battling against NPC

        BattleState battleState = new BattleState(player, npc);
        String battleId = player.getUserId();
        battleStateList.put(battleId, battleState);
        System.out.println("New NPC battleState created");
        return battleId;
    }

    public static BattleState getBattleState(String battleId) {
        return battleStateList.get(battleId);
    }

    public static void sendBattleState(String battleId) {
        // Sends battle state to both players
        BattleState battleState = battleStateList.get(battleId);
        System.out.println("Sending battleState");

        // Sends battleState to player1
        Connection connection1 = ServerPlayerHandler.getConnectionById(battleState.getPlayer1().getUserId());
        connection1.sendTCP(battleState);

        if (!battleState.isAgainstNPC()) {
            // Not against NPC; send battleState to player2
            Connection connection2 = ServerPlayerHandler.getConnectionById(battleState.getPlayer2().getUserId());
            connection2.sendTCP(battleState);
        }

        // Reset battleState attributes for next turn
        battleState.petAttacked = false;
        battleState.petChanged = false;
    }

    public static void sendStartBattle(String battleId) {
        BattleState battleState = battleStateList.get(battleId);

        StartBattleEvent event = new StartBattleEvent();
        event.battleId = battleId;
        event.battleState = battleState;
        System.out.println("Sending start battle event");

        // Send StartBattleEvent to player1
        Connection connection1 = ServerPlayerHandler.getConnectionById(battleState.getPlayer1().getUserId());
        connection1.sendTCP(event);

        if (!battleState.isAgainstNPC()) {
            // Not against NPC; send StartBattleEvent to player2
            Connection connection2 = ServerPlayerHandler.getConnectionById(battleState.getPlayer2().getUserId());
            connection2.sendTCP(event);
        }
    }

    public static void sendEndBattle(String battleId) {
        // Sends end battle event to both players
        BattleState battleState = battleStateList.get(battleId);
        EndBattleEvent event = new EndBattleEvent();
        System.out.println("Sending EndBattleEvent");

        // Send EndBattleEvent to player1
        Connection connection1 = ServerPlayerHandler.getConnectionById(battleState.getPlayer1().getUserId());
        connection1.sendTCP(event);

        if (!battleState.isAgainstNPC()) {
            // Not against NPC; send EndBattleEvent to player2
            Connection connection2 = ServerPlayerHandler.getConnectionById(battleState.getPlayer2().getUserId());
            connection2.sendTCP(event);
        } else if (battleState.getPlayer1().isAlive()) {
            // Player has won against NPC -> player gains new pet
            System.out.println("Player has won against NPC");
            Creature NPCPet = battleState.getPlayer2().getCurrentPet();
            Creature newPet = createNewPet(NPCPet.getType());

            System.out.println(NPCPet.getType());
            System.out.println(newPet.getType());

            if (!battleState.getPlayer1().hasPet(newPet)) {
                // Player does not own set pet -> send AddPetEvent
                AddPetEvent petEvent = new AddPetEvent();
                petEvent.pet = newPet;
                System.out.println("Sending AddPetEvent");
                connection1.sendTCP(petEvent);
            }
        }

        battleStateList.remove(battleId);
    }

    private static Creature createNewPet(String type) {
        switch (type) {
            case "MouseHunter":
                return new MouseHunter();
            case "CrocLesnar":
                return new CrocLesnar();
            case "Doge":
                return new Doge();
            case "Dragon":
                return new Dragon();
            case "MeowmadAli":
                return new MeowmadAli();
            case "Froggy":
                return new Froggy();
            default:
                return null;
        }
    }
}
