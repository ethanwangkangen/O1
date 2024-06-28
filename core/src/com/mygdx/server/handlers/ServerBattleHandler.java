package com.mygdx.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.mygdx.game.entities.*;
import com.mygdx.global.AddPetEvent;
import com.mygdx.global.BattleState;
import com.mygdx.global.EndBattleEvent;
import com.mygdx.global.StartBattleEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerBattleHandler {

    public static Map<String, BattleState> battleStateList = new HashMap<>();
    //map p1UID to the BattleState.


    public static String initialiseBattle(Player p1Player, Player p2Player) {
        BattleState battleState = new BattleState(p1Player, p2Player);
        String battleId = p1Player.getUserId();
        battleStateList.put(battleId, battleState);
        System.out.println("New battle state created");
        return battleId;
    }

    public static String initialiseBattle(Player player, NPC npc) {
        // for battling against NPC

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
        // sends battle state to both players
        BattleState battleState = battleStateList.get(battleId);
        System.out.println("Sending battleState");

        // sends battleState to player1
        Connection connection1 = ServerPlayerHandler.getConnectionById(battleState.getPlayer1().getUserId());
        connection1.sendTCP(battleState);

        if (!battleState.isAgainstNPC()) {
            // not against NPC; send battleState to player2
            Connection connection2 = ServerPlayerHandler.getConnectionById(battleState.getPlayer2().getUserId());
            connection2.sendTCP(battleState);
        }

        // reset battleState attributes for next turn
        battleState.petAttacked = false;
        battleState.petChanged = false;
    }

    public static void sendStartBattle(String battleId) {
        BattleState battleState = battleStateList.get(battleId);

        StartBattleEvent event = new StartBattleEvent();
        event.battleId = battleId;
        event.battleState = battleState;
        System.out.println("Sending start battle event");

        // send StartBattleEvent to player1
        Connection connection1 = ServerPlayerHandler.getConnectionById(battleState.getPlayer1().getUserId());
        connection1.sendTCP(event);

        if (!battleState.isAgainstNPC()) {
            // not against NPC; send StartBattleEvent to player2
            Connection connection2 = ServerPlayerHandler.getConnectionById(battleState.getPlayer2().getUserId());
            connection2.sendTCP(event);
        }
    }

    public static void sendEndBattle(String battleId) {
        // sends end battle event to both players
        BattleState battleState = battleStateList.get(battleId);
        EndBattleEvent event = new EndBattleEvent();
        System.out.println("Sending EndBattleEvent");

        // send EndBattleEvent to player1
        Connection connection1 = ServerPlayerHandler.getConnectionById(battleState.getPlayer1().getUserId());
        connection1.sendTCP(event);

        if (!battleState.isAgainstNPC()) {
            // not against NPC; send EndBattleEvent to player2
            Connection connection2 = ServerPlayerHandler.getConnectionById(battleState.getPlayer2().getUserId());
            connection2.sendTCP(event);
        } else if (battleState.getPlayer1().isAlive()) {
            // player has won against NPC -> player gains new pet
            Creature NPCPet = battleState.getPlayer2().getCurrentPet();
            Creature newPet = createNewPet(NPCPet.getType());
            if (!battleState.getPlayer1().hasPet(newPet)) {
                // player does not own set pet -> send AddPetEvent
                AddPetEvent petEvent = new AddPetEvent();
                petEvent.pet = newPet;
                connection1.sendTCP(petEvent);
            }
        }

        // todo delete battleState from ServerBattleHandler
        //battleStateList.remove(battleId);
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
