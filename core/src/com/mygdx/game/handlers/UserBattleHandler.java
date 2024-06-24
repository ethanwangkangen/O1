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
    //note: current implementation is such that BattleState is (and is required to be) mutable. should change this in the future

    public static void setBattleId(String id) {
        battleId = id;
    }

    public static String getBattleId() {
        return battleId;
    }

    public static BattleState.Turn getTurn() {
        return battleState.getPlayerTurn();
    }


/*    public static void loadTextures(Runnable callback) {
        try{
            battleState.loadTextures(callback);
            System.out.println("Battlehandler textures loading");
        } catch (Exception e) {
            System.err.println("BattleHandler texture loading error");

        }
    }*/

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

    public static Boolean petChanged () {
        return battleState.petChanged;
    }
    public static Boolean petAttacked() {
        return battleState.petAttacked;
    }
}
