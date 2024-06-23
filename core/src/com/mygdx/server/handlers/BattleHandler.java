package com.mygdx.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.global.BattleState;
import com.mygdx.global.EndBattleEvent;
import com.mygdx.global.ServerStartBattleEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BattleHandler {

    public static Map<String, BattleState> battleStateList = new HashMap<>();
    //map p1UID to the BattleState.


    public static String initialiseBattle(Player p1Player, Player p2Player) {
        BattleState battleState = new BattleState(p1Player, p2Player);
        String battleId = p1Player.getIdString();
        battleStateList.put(battleId, battleState);
        System.out.println("New battleState created");
        return battleId;
    }

    public static String initialiseBattle(Player player, NPC npc) {
        // for battling against NPC

        BattleState battleState = new BattleState(player, npc);
        String battleId = player.getIdString();
        battleStateList.put(battleId, battleState);
        System.out.println("New NPC battleState created");
        return battleId;
    }

    public static BattleState getBattleState(String battleId) {

        return battleStateList.get(battleId);
    }

    public static void sendBattleState(String battleId) {
        BattleState battleState = battleStateList.get(battleId);
        System.out.println("Sending battleState");

        // sends battle state to player1
        Connection connection1 = PlayerHandler.getConnectionById(battleState.getPlayer1().getIdString());
        connection1.sendTCP(battleState);

        if (!battleState.isAgainstNPC()) {
            // not against NPC; send battleState to player2
            Connection connection2 = PlayerHandler.getConnectionById(battleState.getPlayer2().getIdString());
            connection2.sendTCP(battleState);
        }

        // reset battleState attributes for next turn
        battleState.petAttacked = false;
        battleState.petChanged = false;
    }

    public static void sendStartBattle(String battleId) {
        BattleState battleState = battleStateList.get(battleId);

        ServerStartBattleEvent event = new ServerStartBattleEvent();
        event.battleId = battleId;
        event.battleState = battleState;
        System.out.println("Sending start battle event");

        // send StartBattleEvent to player1
        Connection connection1 = PlayerHandler.getConnectionById(battleState.getPlayer1().getIdString());
        connection1.sendTCP(event);

        if (!battleState.isAgainstNPC()) {
            // not against NPC; send StartBattleEvent to player2
            Connection connection2 = PlayerHandler.getConnectionById(battleState.getPlayer2().getIdString());
            connection2.sendTCP(event);
        }
    }

    public static void sendEndBattle(String battleId) {
        // sends end battle event to both players
        BattleState battleState = battleStateList.get(battleId);
        EndBattleEvent event = new EndBattleEvent();
        System.out.println("Sending EndBattleEvent");

        // send EndBattleEvent to player1
        Connection connection1 = PlayerHandler.getConnectionById(battleState.getPlayer1().getIdString());
        connection1.sendTCP(event);

        if (!battleState.isAgainstNPC()) {
            // not against NPC; send EndBattleEvent to player2
            Connection connection2 = PlayerHandler.getConnectionById(battleState.getPlayer2().getIdString());
            connection2.sendTCP(event);
        }
    }
}
