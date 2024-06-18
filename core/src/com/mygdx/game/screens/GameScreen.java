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
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.global.StartBattleEvent;

public class GameScreen implements Screen {

    private final Game gameObj;
    private SpriteBatch batch;
    private Texture background;
    private TextButton startBattle;
    private TextButton changePet;
    private Stage stage;
    private Table table;


    public GameScreen(Game gameObj) {
        System.out.println("GameScreen created");
        this.gameObj = gameObj;

        //Texture player = PlayerHandler.getTexture();

        this.stage = new Stage();
//        this.stage.getViewport().setCamera(DarwinsDuel.getInstance().getCamera());

        this.table = new Table();
        this.table.setFillParent(true);
        //this.table.background("Pixel_art_grass_image.png");

        final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));

        this.startBattle = new TextButton("Start Battle", skin);
        this.startBattle.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                DarwinsDuel.getClient().sendTCP(new StartBattleEvent());
                System.out.println("StartBattleEvent sent");
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        changePet = new TextButton("Change battlePets", skin);
        changePet.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                DarwinsDuel.gameState = DarwinsDuel.GameState.PETCHANGE;
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        table.add(changePet).size(250, 50).padTop(100).row();
        this.table.add(startBattle).size(250, 50).padTop(100).row();
        this.stage.addActor(this.table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the color buffer
        //System.out.println("currently rendering GameScreen");
        this.stage.draw();
        this.stage.act(delta);
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
