package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.global.TextImageButton;

import java.util.ArrayList;

public class PetChangeScreen implements Screen {
    private Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));


    // to be located on the left side of the screen
    // for pets that will be carried into battle by the player
    private Table scrollTable1;
    private ArrayList<TextImageButton> buttonList1 = new ArrayList<>();

    // to be located on the right side of the screen
    // for pets that are in player's storage
    private ScrollPane pane;
    private Table scrollTable2;
    private ArrayList<TextImageButton> buttonList2 = new ArrayList<>();



    public PetChangeScreen(DarwinsDuel gameObj) {
        System.out.println("PetChangeScreen created");
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the color buffer
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

    public void initialiseScrollTable() {
        scrollTable1 = new Table();

    }

    public void initialiseTextImageButtons() {
        scrollTable1.clearChildren();
        for (Creature pet : PlayerHandler.getPets()) {
            TextImageButton newButton = new TextImageButton(pet.getName(), skin, pet.getTexturePath());
            newButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    removeButton(button);
                    //todo move to other list
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            scrollTable1.add(newButton).pad(5).row();

        }
    }

    public void removeButton(int button) {
        buttonList1.remove(button);
        scrollTable1.clearChildren();
        for (TextImageButton btn : buttonList1) {
            scrollTable1.add(btn).pad(5).row();
        }
        scrollTable1.invalidateHierarchy(); // Refresh table layout
    }

    private void addButton(Creature pet) {
        TextImageButton button = new TextImageButton(pet.getName(), skin, pet.getTexturePath());

        // Add click listener to handle button removal
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                removeButton(button);
                //todo move to other list
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        buttonList1.add(button);
        scrollTable1.add(button).expandX().fillX().row();
        scrollTable1.invalidateHierarchy(); // Refresh table layout
    }
}
