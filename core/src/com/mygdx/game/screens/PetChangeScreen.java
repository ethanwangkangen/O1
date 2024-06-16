package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.global.TextImageButton;

import java.util.ArrayList;

public class PetChangeScreen implements Screen {
    private Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));

    private Texture emptyBox = new Texture("crossedbox.png");

    // to be located on the left side of the screen
    // for pets that will be carried into battle by the player
    private ArrayList<Creature> pets1 = PlayerHandler.getBattlePets();
    private ScrollPane pane1;
    private Table table1;
    private ArrayList<TextImageButton> buttonList1;

    // to be located on the right side of the screen
    // for pets that are in player's storage
    private ArrayList<Creature> pets2 = PlayerHandler.getReservePets();
    private ScrollPane pane2;
    private Table table2;
    private ArrayList<TextImageButton> buttonList2 = new ArrayList<>();

    private TextImageButton selectedButton1 = null;
    private TextImageButton selectedButton2 = null;


    public PetChangeScreen(DarwinsDuel gameObj) {
        System.out.println("PetChangeScreen created");
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        initialiseScrollPanes();
        createButtonList1();
        createButtonList2();
        refreshTables();
    }

    @Override
    public void render(float delta) {
        if (selectedButton1 != null && selectedButton2 != null) {
            moveSelectedButtons();
        }

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

    public void initialiseScrollPanes() {
        table1 = new Table();
        pane1 = new ScrollPane(table1);

        table2 = new Table();
        pane2 = new ScrollPane(pane2);
    }

//    public void removeButton(int button) {
//        buttonList2.remove(button);
//        table2.clearChildren();
//        for (TextImageButton btn : buttonList1) {
//            table2.add(btn).pad(5).row();
//        }
//        table2.invalidateHierarchy(); // Refresh table layout
//    }
//
//    private void addButton(Creature pet) {
//        TextImageButton button = new TextImageButton(pet.getName(), skin, pet.getTexturePath());
//
//        // Add click listener to handle button removal
//        button.addListener(new ClickListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                removeButton(button);
//                //todo move to other list
//                return super.touchDown(event, x, y, pointer, button);
//            }
//        });
//
//        buttonList1.add(button);
//        table1.add(button).expandX().fillX().row();
//        table1.invalidateHierarchy(); // Refresh table layout
//    }

    private void createButtonList1() {
        for (Creature pet: pets1) {
            TextImageButton button = new TextImageButton(pet.getName(), skin, pet.getTexturePath());
            button.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    selectedButton1 = (TextImageButton) event.getListenerActor();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }

    private void createButtonList2() {
        for (Creature pet : pets2) {
            TextImageButton button = new TextImageButton(pet.getName(), skin, pet.getTexturePath());
            button.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    selectedButton2 = (TextImageButton) event.getListenerActor();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            }
    }

    private void refreshTables() {
        table1.clearChildren();
        for (TextImageButton button : buttonList1) {
            table1.add(button).pad(5).row();
        }
        // for empty buttons to make 3 buttons
        for (int i = 0; i < 3 - buttonList1.size(); i ++) {
            table1.add(createEmptyButton()).pad(5).row();
        }
        table1.invalidateHierarchy();

        table2.clearChildren();
        for (TextImageButton button : buttonList2) {
            table2.add(button).pad(5).row();
        }
        table2.invalidateHierarchy();
    }


    private void moveSelectedButtons() {
        // Swap buttons between lists
        buttonList1.add(buttonList1.indexOf(selectedButton1), selectedButton2);
        buttonList2.add(selectedButton1);

        buttonList1.remove(selectedButton1);
        buttonList2.remove(selectedButton2);

        // Update the pet references in the buttons


        // Refresh the tables
        refreshTables();

        selectedButton1 = null;
        selectedButton2 = null;
    }

    public TextImageButton createEmptyButton() {
        return new TextImageButton("No pet", skin, emptyBox);
    }

}
