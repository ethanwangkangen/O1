package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.handlers.UserPlayerHandler;
import com.mygdx.global.TextImageButton;
import com.mygdx.game.handlers.TextureHandler;

import java.util.ArrayList;

import static com.badlogic.gdx.utils.Align.center;

public class PetChangeScreen implements Screen {
    private Stage stage;
    private AssetManager manager;
    private Table mainTable;
    private Stack topBarStack;
    private Stack stack;
    private Table errorTable;

    private Skin skin;
    private Texture emptyBox;
    private TextureRegionDrawable background;
    private TextureRegionDrawable backgroundBrown;

    private int screenWidth;
    private int screenHeight;

    // To be located on the left side of the screen
    // For pets that will be carried into battle by the player
    private ArrayList<Creature> pets1 = UserPlayerHandler.getBattlePets();
    private ScrollPane pane1;
    private Table table1;
    private ArrayList<TextImageButton> buttonList1 = new ArrayList<>();

    // To be located on the right side of the screen
    // For pets that are in player's storage
    private ArrayList<Creature> pets2 = UserPlayerHandler.getReservePets();
    private ScrollPane pane2;
    private Table table2;
    private ArrayList<TextImageButton> buttonList2 = new ArrayList<>();

    private TextImageButton selectedButton1 = null;
    private TextImageButton selectedButton2 = null;
    private TextImageButton lastClickedButton = null;


    public PetChangeScreen(DarwinsDuel gameObj) {
        System.out.println("PetChangeScreen created");

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        stage = new Stage(new FillViewport(screenWidth, screenHeight));
        Gdx.input.setInputProcessor(stage);

        manager = TextureHandler.getInstance().getAssetManager();
        skin = manager.get("buttons/uiskin.json", Skin.class);
        emptyBox = manager.get("crossedbox.png", Texture.class);
        background = new TextureRegionDrawable(manager.get("Pixel_art_grass_image.png", Texture.class));
        backgroundBrown = new TextureRegionDrawable(manager.get("brownBorder.png", Texture.class));
    }

    @Override
    public void show() {

        initialiseScrollPanes();
        createButtonList1();
        createButtonList2();
        refreshTables();
        initialiseTopBar();
        initialiseErrorMessage();

        stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setBackground(background);

        mainTable.add(topBarStack).colspan(2).fillX().center().row();
        mainTable.add(new Label("Battle Pets", skin));
        mainTable.add(new Label("Reserve Pets", skin)).row();
        mainTable.add(pane1).expand();
        mainTable.add(pane2).expand().row();

        stack.add(mainTable);
        stack.add(errorTable);

        System.out.println("Petchangescreen shown");
    }

    @Override
    public void render(float delta) {
        // Draw screen
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
        topBarStack = new Stack();

        // Create the label for "Pet Change" and add it to the stack
        Label petChangeLabel = new Label("Pet Change", skin);
        petChangeLabel.setAlignment(center);
        topBarStack.add(petChangeLabel);

        // Create a table for the back button and make it fill parent (right side)
        Table backButtonTable = new Table();
        topBarStack.add(backButtonTable);

        // Create the back button and add it to backButtonTable
        TextButton backButton = new TextButton("Save changes", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                updatePetChanges();
                System.out.println("Changes to pets have been saved");
                DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        backButtonTable.add(backButton).right().pad(screenWidth / 60).padRight(screenWidth / 30).padBottom(0).expandX();

    }

    public void initialiseErrorMessage() {

        errorTable = new Table();
        errorTable.setFillParent(true);

        // Create contents for errorTable
        Label errorLabel = new Label("Error", skin);
        errorLabel.setColor(1, 0, 0, 1);
        Label errorMessage = new Label("Battle team cannot be empty", skin);
        errorMessage.setColor(1, 0, 0, 1);
        Button button = new TextButton("Close", skin);
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                errorTable.setVisible(false);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        // Create table to store contents
        Table table = new Table();
        TextureRegionDrawable errorBackground = new TextureRegionDrawable(backgroundBrown);
        errorBackground.setMinHeight(0);
        errorBackground.setMinWidth(0);
        table.setBackground(errorBackground);

        table.add(errorLabel).pad(screenHeight / 30);
        table.row();
        table.add(errorMessage).pad(screenHeight / 30);
        table.row();
        table.add(button).pad(screenHeight / 30).size(screenWidth / 10, screenHeight / 12);

        errorTable.add(table).width((float)(screenWidth / 2)).height((float)(screenHeight / 2));
        errorTable.setVisible(false);
    }

    public void initialiseScrollPanes() {

        table1 = new Table();
        pane1 = new ScrollPane(table1);
        pane1.setWidth((float) screenWidth / 4);

        table2 = new Table();
        pane2 = new ScrollPane(table2);
        pane2.setWidth((float) screenWidth / 4);
    }

    private void createButtonList1() {

        for (Creature pet: pets1) {
            TextImageButton button = new TextImageButton(pet, skin, screenWidth);
            button.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    handleButtonClick((TextImageButton) event.getListenerActor(), 1);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            buttonList1.add(button);
            System.out.println(pet.getName());
        }
    }

    private void createButtonList2() {

        for (Creature pet : pets2) {
            TextImageButton button = new TextImageButton(pet, skin, screenWidth);
            button.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    handleButtonClick((TextImageButton) event.getListenerActor(), 2);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            buttonList2.add(button);
            System.out.println(pet.getName());
        }
    }

    private void refreshTables() {

        // Add buttons from buttonLists to tables
        // table1
        table1.clearChildren();
        for (TextImageButton button : buttonList1) {
            button.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
            table1.add(button).pad(5).size((float) (screenWidth / 3.5), (float) screenHeight / 4).row();
        }

        // Calculate how many empty buttons are needed
        int emptyButtonCount = 3 - buttonList1.size();

        // For empty buttons to make 3 buttons
        for (int i = 0; i < emptyButtonCount; i ++) {
            TextImageButton emptyButton = createEmptyButton();
            table1.add(emptyButton).pad(5).size((float) (screenWidth / 3.5), (float) screenHeight / 4).row();
            buttonList1.add(emptyButton);
        }
        table1.invalidateHierarchy();

        // Table2
        table2.clearChildren();
        for (TextImageButton button : buttonList2) {
            button.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
            table2.add(button).pad(5).size((float) (screenWidth / 3.5), (float) screenHeight / 4).row();
        }
        table2.invalidateHierarchy();
    }

    private void clearTableSkin() {
        for (TextImageButton button : buttonList1) {
            button.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
        }
        for (TextImageButton button : buttonList2) {
            button.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
        }
    }

    private void handleButtonClick(TextImageButton button, int listNumber) {
        if (listNumber == 1) {
            int size = 0;
            for (TextImageButton list1Button : buttonList1) {
                if (list1Button.getPet() != null) {
                    size += 1;
                }
            }
            System.out.println("Size: " + size);

            System.out.println("button1 has been clicked");
            // Handle clicks for buttonList1
            if (button == lastClickedButton) {
                // same button clicked twice in a row
                if (size <= 1 && button.getPet() != null) {
                    // list1 only has 1 pet; cannot remove further
                    // button clicked is not empty button
                    System.out.println("setting to visible");
                    errorTable.setVisible(true);
                    refreshTables();
                    selectedButton1 = null;
                    selectedButton2 = null;
                    lastClickedButton = null;
                } else {
                    // Move to list1 and replace with empty button
                    removeButtonFromList1(button);
                    refreshTables();
                    selectedButton1 = null;
                    selectedButton2 = null;
                    lastClickedButton = null;
                }
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
                clearTableSkin();
                button.setStyle(skin.get("clicked", TextImageButton.ImageTextButtonStyle.class));
            }
        } else if (listNumber == 2){
            System.out.println("button2 has been clicked");
            // Handle clicks for buttonList2
            lastClickedButton = null;
            selectedButton2 = button;
            clearTableSkin();
            button.setStyle(skin.get("clicked", TextImageButton.ImageTextButtonStyle.class));
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
            // Button is empty button: do nothing
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
        TextImageButton button = new TextImageButton("No pet", skin, emptyBox, screenWidth);
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

        UserPlayerHandler.updatePets(pets1, pets2);
    }

}
