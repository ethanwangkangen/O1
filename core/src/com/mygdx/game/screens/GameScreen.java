package com.mygdx.game.screens;

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

public class GameScreen implements Screen {

    private final Game gameObj;
    private SpriteBatch batch;
    private Texture background;


    public GameScreen(Game gameObj) {
        System.out.println("GameScreen created");
        this.gameObj = gameObj;
        this.batch = new SpriteBatch();
        this.background = new Texture("mainscreen.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the color buffer
        //System.out.println("currently rendering GameScreen");
        // Begin drawing
        batch.begin();
        // Draw your game elements here
        batch.draw(background, 0, 0, 400, 100);
        batch.end();
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
