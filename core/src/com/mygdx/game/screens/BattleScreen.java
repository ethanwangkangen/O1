package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.entities.*;
import com.mygdx.game.handlers.BattleHandler;

public class BattleScreen implements Screen {
    private final Game gameObj;
    private SpriteBatch batch;
    private Texture background;

    public MeowmadAli cat1 = new MeowmadAli();

    private Table table1;
    private Table table2;
    private Stage stage;

    //notes for self:
    //spritebatch is object for rendering.
    //process is: call .begin(), render using .draw(texture, x, y), then .end()

    public BattleScreen(Game gameObj) {
        System.out.println("BattleScreen created");
        this.gameObj = gameObj;
        this.batch = new SpriteBatch();
        this.background = new Texture("Pixel_art_grass_image.png");


        BattleHandler.battleState.loadTextures();

        stage = new Stage();
        final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
        table1 = new Table();
        table1.background("Pixel_art_grass_image.png");
        Label name1 = new Label(BattleHandler.battleState.player1.username(), skin);
        Label name2 = new Label(BattleHandler.battleState.player2.username(), skin);
        table1.add();
        table2 = new Table();



        Stack stack = new Stack(table1, table2);
        stack.setFillParent(true);
        stage.addActor(stack);
        //idea: table1 is background + pets; basically things that don't change
        // table2 is health, rounds, skills, etc.
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
        //System.out.println("currently rendering BattleScreen");
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
