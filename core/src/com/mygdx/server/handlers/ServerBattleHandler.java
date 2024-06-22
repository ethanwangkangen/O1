package com.mygdx.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.mygdx.game.entities.Player;
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
        String battleId = UUID.randomUUID().toString();
        battleStateList.put(battleId, battleState);
        System.out.println("New battle state created");
        return battleId;
    }

    public static BattleState getBattleState(String battleId) {
        return battleStateList.get(battleId);
    }

    public static void sendBattleState(String battleId) {
        // sends battle state to both players
        BattleState battleState = battleStateList.get(battleId);
        Connection connection1 = ServerPlayerHandler.getConnectionById(battleState.getPlayer1().getUserId());
        Connection connection2 = ServerPlayerHandler.getConnectionById(battleState.getPlayer2().getUserId());

        System.out.println("Sending battleState");
        connection1.sendTCP(battleState);
        connection2.sendTCP(battleState);
    }

    public static void sendStartBattle(String battleId) {
        BattleState battleState = battleStateList.get(battleId);
        Connection connection1 = ServerPlayerHandler.getConnectionById(battleState.getPlayer1().getUserId());
        Connection connection2 = ServerPlayerHandler.getConnectionById(battleState.getPlayer2().getUserId());

        StartBattleEvent event = new StartBattleEvent();
        event.battleId = battleId;
        event.battleState = battleState;
        System.out.println("Sending start battle event");
        connection1.sendTCP(event);
        connection2.sendTCP(event);
    }

    public static void sendEndBattle(String battleId) {
        // sends end battle event to both players
        BattleState battleState = battleStateList.get(battleId);
        Connection connection1 = ServerPlayerHandler.getConnectionById(battleState.getPlayer1().getUserId());
        Connection connection2 = ServerPlayerHandler.getConnectionById(battleState.getPlayer2().getUserId());

        EndBattleEvent event = new EndBattleEvent();
        System.out.println("Sending EndBattleEvent");
        connection1.sendTCP(event);
        connection2.sendTCP(event);
    }
}
