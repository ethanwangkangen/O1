package com.mygdx.game.interfaces;


/**
 * Used by libgdx to tell Android module to do things, like start the map,
 * or show accept/reject screen.
 */
public interface MapInterface {
    public void showMap();

    public void acceptOrReject(String enemyUsername);
}
