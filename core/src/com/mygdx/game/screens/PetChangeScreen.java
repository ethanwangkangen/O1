package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.global.TextImageButton;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.badlogic.gdx.utils.Align.center;

public class PetChangeScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Table table;
    Stack stack;
    private Texture emptyBox;
    private AssetManager manager;

    // to be located on the left side of the screen
    // for battlePets that will be carried into battle by the player
    private ArrayList<Creature> pets1 = PlayerHandler.getBattlePets();
    private ScrollPane pane1;
    private Table table1;
    private ArrayList<TextImageButton> buttonList1;

    // to be located on the right side of the screen
    // for battlePets that are in player's storage
    private ArrayList<Creature> pets2 = PlayerHandler.getReservePets();
    private ScrollPane pane2;
    private Table table2;
    private ArrayList<TextImageButton> buttonList2;

    private TextImageButton selectedButton1 = null;
    private TextImageButton selectedButton2 = null;
    private TextImageButton lastClickedButton = null;


    public PetChangeScreen(DarwinsDuel gameObj) {
        System.out.println("PetChangeScreen created");

        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        buttonList1 = new ArrayList<>();
        buttonList2 = new ArrayList<>();

        manager = DarwinsDuel.getInstance().getAssetManager();
        skin = manager.get("buttons/uiskin.json", Skin.class);
        emptyBox = manager.get("crossedbox.png", Texture.class);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

            System.out.println("test1");
            initialiseScrollPanes();
            createButtonList1();
            createButtonList2();
            refreshTables();
            initialiseTopBar();

            table = new Table();
            table.setFillParent(true);

            table.add(stack).colspan(2).fillX().center().row();
            table.add(new Label("Battle Pets", skin)).padTop(10);
            table.add(new Label("Reserve Pets", skin)).row();
            table.add(pane1).expand();
            table.add(pane2).expand().row();

            stage.addActor(table);
        stage.setDebugAll(true);
        System.out.println("Petchangescreen shown");
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

    public void initialiseTopBar() {
        // Stack to overlay the label and the back button
        stack = new Stack();
        // Create the label for "PetNum Change" and add it to the stack
        Label petChangeLabel = new Label("PetNum Change", skin);
        petChangeLabel.setAlignment(center);
        stack.add(petChangeLabel);

        // Create a table for the back button and make it fill parent (right side)
        Table backButtonTable = new Table();
        stack.add(backButtonTable);

        // Create the back button and add it to backButtonTable
        TextButton backButton = new TextButton("Save changes", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                updatePetChanges();
                System.out.println("Changes to battlePets have been saved");
                DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;
                return super.touchDown(event, x, y, pointer, button);
            }
        });        backButtonTable.add(backButton).right().pad(10).expandX();
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
//    private void addButton(Creature petNum) {
//        TextImageButton button = new TextImageButton(petNum.getName(), skin, petNum.getTexturePath());
//
//        // Add click listener to handle button removal
//        button.addListener(new ClickListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                removeButton(button);
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
            TextImageButton button = new TextImageButton(pet, skin);
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
            TextImageButton button = new TextImageButton(pet, skin);
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

        // Calculate how many empty buttons are needed
        System.out.println(buttonList1.size());
        int emptyButtonCount = 3 - buttonList1.size();

        // for empty buttons to make 3 buttons
        for (int i = 0; i < emptyButtonCount; i ++) {
            System.out.println(i);
            TextImageButton emptyButton = createEmptyButton();
            table1.add(emptyButton).pad(5).width(245).height(80).row();
            buttonList1.add(emptyButton);
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
                // same button clicked twice in a row
                // Move to list1 and replace with empty button
                removeButtonFromList1(button);
                selectedButton1 = null;
                selectedButton2 = null;
                lastClickedButton = null;
            } else if (lastClickedButton != null) {
                // 2 different buttons from buttonList1 have been clicked in succession
                // swap these 2 buttons
                swapList1Buttons(button);
                selectedButton1 = null;
                selectedButton2 = null;
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
        System.out.println("removeButtonFromList1 function called");

        if (button.getPet() == null) {
            // button is empty button: do nothing
            System.out.println("Button is empty: cannot be removed");
            return;
        }
        if (index != -1) { // Ensure the button exists in the list
            buttonList1.remove(button);
            buttonList2.add(button);
            updateClickListeners(button, 2);

            // Ensure the index is within the bounds after removal
            if (index <= buttonList1.size()) {
                buttonList1.add(index, createEmptyButton()); // Replace with empty button
            } else {
                buttonList1.add(createEmptyButton()); // Add to the end if index is out of bounds
            }

            refreshTables();
        } else {
            System.err.println("Button not found in buttonList1");
        }
    }

    private void swapList1Buttons(TextImageButton button) {
        int index1 = buttonList1.indexOf(button);
        int index2 = buttonList1.indexOf(lastClickedButton);
        System.out.println("Index1: " + index1);
        System.out.println("Index2: " + index2);

        if (index1 == -1) {
            System.err.println("Error: selectedButton1 not found in buttonList1: " + button.getText());
            return;
        } else if (index2 == -1) {
            System.err.println("Error: lastClickedButton not found in buttonList1: " + lastClickedButton.getText());
            return;
        } else if (index1 == index2) {
            System.err.println("Error lastClickedButton and selectedButton1 have the same index:"
                     + "\nselectedButton1: " + button.getText()
                     + "\nlastClickedButton: " + lastClickedButton.getText());
            return;
        } else if (lastClickedButton.getPet() == null || button.getPet() == null) {
            System.out.println("Cannot swap empty button"); // changed
            return;
        }

        // Swap the buttons
        buttonList1.set(index1, lastClickedButton);
        buttonList1.set(index2, button);

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
            return;
        }

        // Swap buttons between lists
        buttonList1.remove(selectedButton1);
        buttonList2.remove(selectedButton2);

        if (selectedButton1.getPet() != null) {
            // selectedButton1 is not an emptyButton
            buttonList2.add(selectedButton1);
        }
        buttonList1.add(index, selectedButton2);

        // Update the click listeners to correctly set the selected buttons
        updateClickListeners(selectedButton1, 2);
        updateClickListeners(selectedButton2, 1);

        System.out.println("buttonList1 size after addition: " + buttonList1.size());
        System.out.println("buttonList2 size after addition: " + buttonList2.size());

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

    private void updatePetChanges() {
        pets1.clear();
        for (TextImageButton button : buttonList1) {
            if (button.getPet() != null) {
                pets1.add(button.getPet());
            }
        }

        pets2.clear();
        for (TextImageButton button : buttonList2) {
            pets2.add(button.getPet());
        }

        PlayerHandler.updatePets(pets1, pets2);
    }

}
