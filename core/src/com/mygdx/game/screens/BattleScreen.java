package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.*;

public class BattleScreen implements Screen {
    private final Game gameObj;
    private SpriteBatch batch;
    private Texture background;

    public MeowmadAli cat1 = new MeowmadAli();

    //notes for self:
    //spritebatch is object for rendering.
    //process is: call .begin(), render using .draw(texture, x, y), then .end()

    public BattleScreen(Game gameObj) {
        System.out.println("here");
        this.gameObj = gameObj;
        this.batch = new SpriteBatch();
        this.background = new Texture("Pixel_art_grass_image.png");
    }


    /**
     * called when this screen becomes the current screen for the game
     * initialise resources specific to this screen
     */
    @Override
    public void show() {

    }

    /**
     * rendered every frame.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the color buffer
        System.out.println("currently rendering BattleScreen");
        // Begin drawing
        batch.begin();
        // Draw your game elements here
        batch.draw(background, 0, 0, 1000, 1000);
        cat1.render(batch);
        batch.end();




    }

    /**
     *
     * @param width
     * @param height
     */
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
