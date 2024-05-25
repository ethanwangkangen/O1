package com.mygdx.game.entities;

public class Entity {
    private int xpos = 100;
    private int ypos = 100;

    public void move(int x, int y) {
        this.xpos = x;
        this.ypos = y;
    }
}
