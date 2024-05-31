package com.mygdx.game.handlers;

import com.mygdx.game.entities.Player;
import com.mygdx.global.BattleState;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class BattleHandler {
    private static BattleState battleState;
    //note: current implementation is such that BattleState is (and is required to be) mutable. should change this in the future

    //logic of
        //checking whose turn isit
        //getting input from player
        //adjust the battleState
        //send back to Server
    public static BattleState.Turn getTurn() {
        return battleState.getPlayerTurn();
    }

    public static Boolean hasStarted() {
        return battleState.hasStarted();
    }

    public static void loadTextures() {
        battleState.loadTextures();
    }

    public static Player getPlayer1() {
        return battleState.getPlayer1();
    }

    public static Player getPlayer2() {
        return battleState.getPlayer2();
    }

    public static void updateBattleState(BattleState newState) {
        battleState = newState;
    }

}
