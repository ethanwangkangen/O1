package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.handlers.TextureHandler;
import com.mygdx.game.handlers.UserPlayerHandler;

public class WelcomeScreen implements Screen {

    private Stage stage;
    private Table table;
    private Table bgTable;

    private AssetManager manager;
    private Skin skin;
    private Texture backgroundBrown;
    private Texture background;

    private int screenWidth;
    private int screenHeight;

    private Button continueButton;
    private Label text;

    @Override
    public void show() {
        createButton();
        createText();

        bgTable.setFillParent(true);
        bgTable.setBackground(new TextureRegionDrawable(background));
        bgTable.add(table).pad(screenWidth / 40).width(screenWidth / 1.5f);

        table.setBackground(new TextureRegionDrawable(backgroundBrown));
        table.add(text).expand().fill().pad(screenHeight / 16).padBottom(0).padTop(screenHeight / 40);
        table.row();
        table.add(continueButton).expandY().top();

        stage.addActor(bgTable);
//        stage.setDebugAll(true);
    }

    public WelcomeScreen(Game gameobj) {
        this.stage = new Stage();
        this.table = new Table();
        this.bgTable = new Table();
        Gdx.input.setInputProcessor(stage);

        this.manager = TextureHandler.getInstance().getAssetManager();
        this.skin = manager.get("buttons/uiskin.json", Skin.class);
        this.background = manager.get("Pixel_art_grass_image.png", Texture.class);
        this.backgroundBrown = manager.get("brownBorder.png", Texture.class);

        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
    }

    private void createButton() {
        continueButton = new TextButton("To Game", skin);
        continueButton.addListener(new ClickListener() {
            @Override
            // continues to map screen
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("To game button pressed");
                DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private void createText() {
        text = new Label(
        "Welcome to Darwin's Duel, " + UserPlayerHandler.getUsername() + "!\n\n" +
            "We, the developers of Darwin's Duel, are thrilled to have you join us on this exciting adventure. " +
            "In Darwin's Duel, you'll engage in thrilling battles with a diverse array of creatures, " +
            "each with their own unique abilities and traits.\n\n" +
            "This game took countless hours to make, and we hope that you will enjoy this game.\n\n" +
            "Happy gaming!\n" +
            "The Development Team of Darwin's Duel",
            skin
        );
        text.setWrap(true);
    }

    @Override
    public void render(float delta) {
        // draw screen
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the color buffer
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
