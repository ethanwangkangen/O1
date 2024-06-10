package com.mygdx.game.handlers;

import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.Player;
import com.mygdx.global.BattleState;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.concurrent.CompletableFuture;

public class BattleHandler {
    private static BattleState battleState;
    public static boolean changePet = false;
    public static boolean updatePetInfo = false;
    public static boolean battleEnd = false;
    //note: current implementation is such that BattleState is (and is required to be) mutable. should change this in the future


    public static BattleState.Turn getTurn() {
        return battleState.getPlayerTurn();
    }

    public static Boolean hasStarted() {
        return battleState.hasStarted();
    }

    public static void loadTextures(Runnable callback) {
        try{
            battleState.loadTextures(callback);
            System.out.println("battlehandler textures loading");
        } catch (Exception e) {
        }
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
}
