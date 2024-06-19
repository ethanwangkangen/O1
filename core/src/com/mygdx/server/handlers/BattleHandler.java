package com.mygdx.server.handlers;

import com.mygdx.game.entities.Player;
import com.mygdx.global.BattleState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerBattleHandler {

    public static Map<String, BattleState> battleStateList = new HashMap<>();
    //map p1UID to the BattleState.


    public static void initialiseBattle(Player p1Player, String p1UID, Player p2Player, String p2UID) {
        BattleState battleState = new BattleState(p1Player, p1UID, p2Player, p2UID);
    }
}
