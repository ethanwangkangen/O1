package com.mygdx.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.MapInterface;
import com.mygdx.game.MapInterface;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.global.StartBattleEvent;


public class MapScreen implements Screen{

    private DarwinsDuel gameObj;


    public MapScreen(DarwinsDuel gameObj) {
        this.gameObj = gameObj;
    }
    @Override
    public void show() {
//        if (Gdx.app.getType() == Application.ApplicationType.Android) {
//            MapInterface map = (MapInterface) Gdx.app;
//            System.out.println("Attempting to show map");
//            map.showMap();
//        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        System.out.println("rendering MapScreen");
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
