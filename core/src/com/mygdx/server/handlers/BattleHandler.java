package com.mygdx.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.mygdx.game.entities.Player;
import com.mygdx.global.BattleState;
import com.mygdx.global.EndBattleEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BattleHandler {

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
        Connection connection1 = PlayerHandler.getConnectionById(battleState.getPlayer1().getIdString());
        Connection connection2 = PlayerHandler.getConnectionById(battleState.getPlayer2().getIdString());
        connection1.sendTCP(battleState);
        connection2.sendTCP(battleState);
    }

    public static void sendEndBattle(String battleId) {
        // sends end battle event to both players
        BattleState battleState = battleStateList.get(battleId);
        Connection connection1 = PlayerHandler.getConnectionById(battleState.getPlayer1().getIdString());
        Connection connection2 = PlayerHandler.getConnectionById(battleState.getPlayer2().getIdString());
        EndBattleEvent event = new EndBattleEvent();
        connection1.sendTCP(event);
        connection2.sendTCP(event);
    }
}
