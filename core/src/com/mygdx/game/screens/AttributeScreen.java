package com.mygdx.game.screens;

import static com.badlogic.gdx.utils.Align.center;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.Skill;
import com.mygdx.game.handlers.TextureHandler;
import com.mygdx.game.handlers.UserPlayerHandler;
import com.mygdx.global.TextImageButton;

import java.util.ArrayList;

public class AttributeScreen implements Screen {
    private Stage stage;
    private AssetManager manager;
    private Table table;
    private Stack topBarStack;

    private Skin skin;
    private TextureRegionDrawable background;
    private Texture backgroundBox;
    private Texture backgroundBrown;

    private int screenWidth;
    private int screenHeight;

    private ArrayList<Creature> petsList = new ArrayList<>();
    private ScrollPane petsPane;
    private Table petsTable;
    private ArrayList<Button> buttonList = new ArrayList<>();

    private Label skillDescription;
    Button skillButton1;
    Button skillButton2;
    Button skillButton3;

    private Table infoTable;

    public AttributeScreen(DarwinsDuel gameObj) {
        System.out.println("PetChangeScreen created");

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        stage = new Stage(new FillViewport(screenWidth, screenHeight));
        Gdx.input.setInputProcessor(stage);

        manager = TextureHandler.getInstance().getAssetManager();
        skin = manager.get("buttons/uiskin.json", Skin.class);
        background = new TextureRegionDrawable(manager.get("Pixel_art_grass_image.png", Texture.class));
        backgroundBox = manager.get("border.png", Texture.class);
        backgroundBrown = manager.get("brownBorder.png", Texture.class);
    }

    @Override
    public void show() {
        createPetsButtonList();
        initialiseTopBar();
        initialiseScrollPanes();

        table = new Table();
        table.setFillParent(true);
        table.setBackground(background);

        table.add(topBarStack).fillX().expandX().colspan(2);
        table.row();
        table.add(petsPane);
        table.add(infoTable).expand().fill();

        stage.addActor(table);
//        stage.setDebugAll(true);

        System.out.println("AttributeScreen shown");
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

    public void initialiseTopBar() {
        // Stack to overlay the label and the back button
        topBarStack = new Stack();

        // Create the label for "Pet Change" and add it to the stack
        Label topLabel = new Label("Pet Attributes", skin);
        topLabel.setAlignment(center);


        // Create the back button and add it to backButtonTable
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // todo confirm which screen to return to
                DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        // Create a table for the back button and make it fill parent (right side)
        Table backButtonTable = new Table();
        topBarStack.add(backButtonTable);
        backButtonTable.add(backButton).right().pad(screenWidth / 60).padRight(screenWidth / 30).expandX();
        topBarStack.add(topLabel);
    }

    public void initialiseScrollPanes() {

        petsTable = new Table();
        for (Button button : buttonList) {
            petsTable.add(button).pad(5).padTop(0).size((float) (screenWidth / 3.5), (float) screenHeight / 4).padLeft(screenWidth / 20).padRight(screenWidth / 20);
            petsTable.row();
        }
//        table1.setBackground(backgroundBox);
        petsPane = new ScrollPane(petsTable);
        petsPane.setWidth((float) screenWidth / 4);

        infoTable = new Table();
//        table2.setBackground(backgroundBox);
        // todo to see if can change to expandX()
        infoTable.setWidth((float) (screenWidth / 4 * 3));
    }

    private void createPetsList() {
        petsList.addAll(UserPlayerHandler.getBattlePets());
        petsList.addAll(UserPlayerHandler.getReservePets());
    }

    private void createPetsButtonList() {
        createPetsList();
        for (Creature pet : petsList) {
            TextImageButton button = new TextImageButton(pet, skin, screenWidth);
            button.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    TextImageButton petButton = (TextImageButton) event.getListenerActor();
                    Creature pet = petButton.getPet();

                    // set this button red, and reset the other buttons
                    clearTableSkin();
                    petButton.setStyle(skin.get("clicked", TextImageButton.ImageTextButtonStyle.class));

                    handleButtonClick(pet);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            buttonList.add(button);
            System.out.println("Pet button added: " + pet.getName());
        }
    }

    private void handleButtonClick(Creature pet) {
        // displays the info of the pet of the button clicked
        infoTable.clear();

        Image image = new Image(new TextureRegion(TextureHandler.getInstance().getTexture(pet.getType())));
        Label name = new Label("Name: " + pet.getName(), skin);
        Label level = new Label("Level: " + pet.getLevelString(), skin);
        Label element = new Label("Element: " + pet.getElementString(), skin);
        Label health = new Label("Health: " + pet.getMaxHealth(), skin);

        skillDescription = new Label("", skin);

        // create table for pet image and pet description
        Table topTable = new Table();
        Table petDescriptionTable = new Table();

        petDescriptionTable.padTop(50).padLeft(50).padRight(50);
        petDescriptionTable.add(name);
        petDescriptionTable.row();
        petDescriptionTable.add(level);
        petDescriptionTable.row();
        petDescriptionTable.add(health);
        petDescriptionTable.row();
        petDescriptionTable.add(element);
        petDescriptionTable.padBottom(50);

        // set background for petDescriptionTable
        TextureRegionDrawable petDescriptionTableBackground = new TextureRegionDrawable(new TextureRegion(backgroundBrown));
        petDescriptionTableBackground.setMinHeight(0);
        petDescriptionTableBackground.setMinWidth(0);
        petDescriptionTable.setBackground(petDescriptionTableBackground);

        topTable.add(image).width(screenWidth / 5).height(screenWidth / 5).padRight(screenWidth / 14);
        topTable.add(petDescriptionTable).width((int)(screenWidth / 3.5));//.height((int)(screenHeight / 3.5));

        // create bottom table for skills list and skill description
        Table bottomTable = new Table();
        ScrollPane pane = createSkillTable(pet);
        bottomTable.add(pane);
        bottomTable.add(skillDescription).expand().fill().pad(10);

        // add everything to infoTable
        infoTable.add(topTable).expandX();
        infoTable.row();
        infoTable.add(bottomTable).expand().fill();
    }

    private void clearTableSkin() {
        for (Button button : buttonList) {
            button.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
        }
    }

    public Button createSkillButton(Skill skill) {
        Button button;
        if (skill != null) {
            if (skill.status == Skill.Status.ABSORB) {
                button = new TextImageButton(skill.getName(), manager.get("absorb_effect.png", Texture.class), skin, screenWidth);
            } else if (skill.status == Skill.Status.POISON) {
                button = new TextImageButton(skill.getName(), manager.get("poison_effect.png", Texture.class), skin, screenWidth);
            } else if (skill.status == Skill.Status.STUN) {
                button = new TextImageButton(skill.getName(), manager.get("stun_effect.png", Texture.class), skin, screenWidth);
            } else {
                button = new TextButton(skill.getName(), skin);
            }
        } else {
            button = new TextButton("-", skin);
            button.setTouchable(Touchable.disabled);
        }
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // set skill button clicked red
                clearSkillStyle();
                Button thisButton = (Button) event.getListenerActor();
                thisButton.setStyle(skin.get("clicked", TextImageButton.ImageTextButtonStyle.class));

                // display description of skill of button pressed
                skillDescription.setText(skill.getName() + "\n\n" + skill.getStatusDescription());
                skillDescription.setWrap(true);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        return button;
    }

    public ScrollPane createSkillTable(Creature pet) {
        Table table = new Table();
        skillButton1 = createSkillButton(pet.skill1);
        skillButton2 = createSkillButton(pet.skill2);
        skillButton3 = createSkillButton(pet.skill3);

        table.add(skillButton1).width(screenWidth / 6).height(screenHeight / 8).pad(5).row();
//        table.row().padBottom(5);
        table.add(skillButton2).width(screenWidth / 6).height(screenHeight / 8).pad(5).row();
//        table.row().padBottom(5);
        table.add(skillButton3).width(screenWidth / 6).height(screenHeight / 8).pad(5).row();
//        table.row().padBottom(5);

        return new ScrollPane(table);
    }

    public void clearSkillStyle() {
        // makes all skill buttons not red
        skillButton1.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
        skillButton2.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
        skillButton3.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
    }
}
