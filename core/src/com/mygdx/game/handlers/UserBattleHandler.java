package com.mygdx.game.handlers;

import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.Player;
import com.mygdx.global.BattleState;
//import com.sun.org.apache.xpath.internal.operations.Bool;


public class UserBattleHandler {
    private static String battleId;
    private static BattleState battleState;
    public static boolean updatePetInfo = false;
    public static boolean battleEnd = false;

    public static void setBattleId(String id) {
        battleId = id;
    }

    public static String getBattleId() {
        return battleId;
    }

    public static BattleState.Turn getTurn() {
        return battleState.getPlayerTurn();
    }


    public static Player getPlayer1() {
        return battleState.getPlayer1();
    }

    public static Player getPlayer2() {
        return battleState.getPlayer2();
    }

    public static void updateBattleState(BattleState newState) {
        if (battleState == null) {
            battleState = newState;
        } else {
            battleState.update(newState);
        }
    }

    public static void newBattleState(BattleState newState) {
        battleState = newState;
    }

    public static Boolean petChanged () {
        return battleState.petChanged;
    }
    public static Boolean petAttacked() {
        return battleState.petAttacked;
    }

    public static void clearBattleHandler() {
        // To clear UserBattleHandler after battle ends
        battleId = null;
        battleState = null;
        updatePetInfo = false;
        battleEnd = false;
    }
}
