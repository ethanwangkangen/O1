package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.DarwinsDuel;

public class SplashScreen implements Screen {

    Texture background = new Texture("mainscreen.png");
    Stage stage = new Stage();
    Table table = new Table();

    public SplashScreen(Game gameobj) {
        this.stage = new Stage();
        this.table = new Table();

        DarwinsDuel.getInstance().loadTextures();

        table.setBackground(new TextureRegionDrawable(background));
        table.setFillParent(true);

        stage.addActor(table);

        System.out.println("SplashScreen created");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the color buffer
        stage.draw();
        stage.act(delta);

//        if (DarwinsDuel.getInstance().getAssetManager().update()) {
//            DarwinsDuel.gameState = DarwinsDuel.GameState.LOGIN;
//        }
        DarwinsDuel.getInstance().getAssetManager().finishLoading(); //this is blocking so EVERYTHING will be loaded first
        DarwinsDuel.gameState = DarwinsDuel.GameState.LOGIN;
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
