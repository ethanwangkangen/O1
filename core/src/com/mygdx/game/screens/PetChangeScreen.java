package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.Skill;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.global.AttackEvent;
import com.mygdx.global.TextImageButton;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class PetChangeScreen implements Screen {
    private Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
    private ScrollPane pane;

    private Table scrollTable;
    private ArrayList<TextImageButton> textImageButtons = new ArrayList<>();



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
        scrollTable = new Table();

    }

    public void initialiseTextImageButtons() {
        scrollTable.clearChildren();
        for (Creature pet : PlayerHandler.getPets()) {
            TextImageButton newButton = new TextImageButton(pet.getName(), skin, pet.getTexturePath());
            newButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    removeButton(button);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            scrollTable.add(newButton).pad(5).row();

        }
    }

    public void removeButton(int button) {
        textImageButtons.remove(button);
        scrollTable.clearChildren();
        for (TextImageButton btn : textImageButtons) {
            scrollTable.add(btn).pad(5).row();
        }
        scrollTable.invalidateHierarchy(); // Refresh table layout
    }

}
