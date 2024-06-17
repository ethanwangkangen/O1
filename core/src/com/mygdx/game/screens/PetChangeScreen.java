package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.global.TextImageButton;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class PetChangeScreen implements Screen {
    private Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
    private Table table;
    private Texture emptyBox = new Texture("crossedbox.png");

    // to be located on the left side of the screen
    // for pets that will be carried into battle by the player
    private ArrayList<Creature> pets1 = PlayerHandler.getBattlePets();
    private ScrollPane pane1;
    private Table table1;
    private ArrayList<TextImageButton> buttonList1 = new ArrayList<>();

    // to be located on the right side of the screen
    // for pets that are in player's storage
    private ArrayList<Creature> pets2 = PlayerHandler.getReservePets();
    private ScrollPane pane2;
    private Table table2;
    private ArrayList<TextImageButton> buttonList2 = new ArrayList<>();

    private TextImageButton selectedButton1 = null;
    private TextImageButton selectedButton2 = null;
    private TextImageButton lastClickedButton = null;


    public PetChangeScreen(DarwinsDuel gameObj) {
        System.out.println("PetChangeScreen created");

        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        PlayerHandler.loadTextures(() -> {
            initialiseScrollPanes();
            createButtonList1();
            createButtonList2();
            refreshTables();

            table = new Table();
            table.setFillParent(true);
            table.add(new Label("Pet Change", skin)).colspan(2).row();
            table.add(pane1);
            table.add(pane2).row();

            stage.addActor(table);
        });

    }

    @Override
    public void render(float delta) {
//        if (selectedButton1 != null && selectedButton2 != null) {
//            swapSelectedButtons();
//        }

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
        pane1.setWidth(250);

        table2 = new Table();
        pane2 = new ScrollPane(table2);
        pane2.setWidth(250);
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
                    handleButtonClick((TextImageButton) event.getListenerActor(), 1);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            buttonList1.add(button);
        }
    }

    private void createButtonList2() {
        for (Creature pet : pets2) {
            TextImageButton button = new TextImageButton(pet.getName(), skin, pet.getTexturePath());
            button.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    handleButtonClick((TextImageButton) event.getListenerActor(), 2);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            buttonList2.add(button);
        }
    }

    private void refreshTables() {
        // add buttons from buttonLists to tables
        // table1
        table1.clearChildren();
//        table1.clear();
        for (TextImageButton button : buttonList1) {
            table1.add(button).pad(5).width(245).height(80).row();
        }
        // for empty buttons to make 3 buttons
        for (int i = 0; i < 3 - buttonList1.size(); i ++) {
            table1.add(createEmptyButton()).pad(5).row();
        }
        table1.invalidateHierarchy();

        // table2
        table2.clearChildren();
//        table2.clear();
        for (TextImageButton button : buttonList2) {
            table2.add(button).pad(5).width(245).height(80).row();
        }
        table2.invalidateHierarchy();
    }

    private void handleButtonClick(TextImageButton button, int listNumber) {
        if (listNumber == 1) {
            System.out.println("button1 has been clicked");
            // Handle clicks for buttonList1
            if (button == lastClickedButton && buttonList1.indexOf(button) != 0) {
                // Button clicked twice in a row
                // Move to the other list and replace with empty button
                removeButtonFromList1(button);
                selectedButton1 = null;
                lastClickedButton = null;
            } else {
                // Single click detected, update selected button
                selectedButton1 = button;
                lastClickedButton = button;
            }
        } else if (listNumber == 2){
            System.out.println("button2 has been clicked");
            // Handle clicks for buttonList2
            lastClickedButton = null;
            selectedButton2 = button;
        }

        // If both selected buttons are set, swap them
        if (selectedButton1 != null && selectedButton2 != null) {
            swapSelectedButtons();
            selectedButton1 = null;
            selectedButton2 = null;
            lastClickedButton = null;
        }
    }

    private void removeButtonFromList1(TextImageButton button) {
        int index = buttonList1.indexOf(button);
        buttonList1.remove(button);
        buttonList2.add(button);
        updateClickListeners(button, 2);
        buttonList1.add(index, createEmptyButton()); // Replace with empty button
        refreshTables();
    }


    private void swapSelectedButtons() {

        int index = buttonList1.indexOf(selectedButton1);

        System.out.println("Index of selectedButton1 in buttonList1: " + index);
        System.out.println("buttonList1 size before removal: " + buttonList1.size());
        System.out.println("buttonList2 size before removal: " + buttonList2.size());

        // Check if index is valid
        if (index == -1) {
            System.err.println("Error: selectedButton1 not found in buttonList1: " + selectedButton1.getText());
            selectedButton1 = null;
            selectedButton2 = null;
            return;
        }

        // Swap buttons between lists
        buttonList1.remove(selectedButton1);
        buttonList2.remove(selectedButton2);

        if (!selectedButton1.getText().toString().equals("No pet")) {
            // selectedButton1 is not an emptyButton
            buttonList2.add(selectedButton1);
        }
        buttonList1.add(index, selectedButton2);

        // Update the click listeners to correctly set the selected buttons
        updateClickListeners(selectedButton1, 2);
        updateClickListeners(selectedButton2, 1);

        System.out.println("buttonList1 size after addition: " + buttonList1.size());
        System.out.println("buttonList2 size after addition: " + buttonList2.size());

        //todo Update the pet references in the buttons


        // Refresh the tables
        refreshTables();
    }

    private void updateClickListeners(TextImageButton button, int listNumber) {
        button.clearListeners();
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (listNumber == 1) {
                    handleButtonClick((TextImageButton) event.getListenerActor(), 1);
                } else if (listNumber == 2) {
                    handleButtonClick((TextImageButton) event.getListenerActor(), 2);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public TextImageButton createEmptyButton() {
        TextImageButton button = new TextImageButton("No pet", skin, emptyBox);
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                handleButtonClick((TextImageButton) event.getListenerActor(), 1);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        return button;
    }

}
