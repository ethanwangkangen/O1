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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AttributeScreen implements Screen {
    private Stage stage;
    private AssetManager manager;
    private Table table;
    private Stack topBarStack;
    private Stack stack;

    private Skin skin;
    private TextureRegionDrawable background;
    private TextureRegionDrawable backgroundBox;

    private int screenWidth;
    private int screenHeight;

    private ArrayList<Creature> petsList = new ArrayList<>();
    private ScrollPane petsPane;
    private Table petsTable;
    private ArrayList<TextImageButton> buttonList = new ArrayList<>();

    private Label skillDescription;
    TextButton skillButton1;
    TextButton skillButton2;
    TextButton skillButton3;

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
        backgroundBox = new TextureRegionDrawable(manager.get("border.png", Texture.class));
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
        stage.setDebugAll(true);

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
        topBarStack.add(topLabel);

        // Create a table for the back button and make it fill parent (right side)
        Table backButtonTable = new Table();
        topBarStack.add(backButtonTable);

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
        backButtonTable.add(backButton).right().pad(10).expandX();
    }

    public void initialiseScrollPanes() {

        petsTable = new Table();
        for (TextImageButton button : buttonList) {
            petsTable.add(button).pad(5).size((float) (screenWidth / 3.5), (float) screenHeight / 4).padLeft(screenWidth / 20).padRight(screenWidth / 20);
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
        Label level = new Label("Level: " + pet.getLevel(), skin);
        Label element = new Label("Element: " + pet.getElementString(), skin);

        skillDescription = new Label("", skin);

        // create table for pet image and pet description
        Table topTable = new Table();
        Table petDescriptionTable = new Table();

        petDescriptionTable.add(name);
        petDescriptionTable.row();
        petDescriptionTable.add(level);
        petDescriptionTable.row();
        petDescriptionTable.add(element);

        topTable.add(image).width(screenWidth / 5).height(screenWidth / 5).uniform();
        topTable.add(petDescriptionTable).uniform();

        // create table for skills list and skill description
        Table bottomTable = new Table();
        ScrollPane pane = createSkillTable(pet);
        bottomTable.add(pane);
        bottomTable.add(skillDescription).expandX();

        // add everything to infoTable
        infoTable.add(topTable).expandX();
        infoTable.padTop(screenHeight / 10).row();
        infoTable.add(bottomTable).expand().fill();
    }

    private void clearTableSkin() {
        for (TextImageButton button : buttonList) {
            button.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
        }
    }

    public TextButton createSkillButton(Skill skill) {
        TextButton button;
        if (skill != null) {
            button = new TextButton(skill.getName(), skin);
        } else {
            button = new TextButton("No skill", skin);
            button.setTouchable(Touchable.disabled);
        }
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // set skill button clicked red
                clearSkillStyle();
                TextButton thisButton = (TextButton) event.getListenerActor();
                thisButton.setStyle(skin.get("clicked", TextImageButton.ImageTextButtonStyle.class));

                // display description of skill of button pressed
                skillDescription.setText(skill.getName() + "\n\n" + skill.getStatusDescription());
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

        table.add(skillButton1).width(screenWidth / 7).height(screenHeight / 8);
        table.row();
        table.add(skillButton2).width(screenWidth / 7).height(screenHeight / 8);
        table.row();
        table.add(skillButton3).width(screenWidth / 7).height(screenHeight / 8);

        return new ScrollPane(table);
    }

    public void clearSkillStyle() {
        // makes all skill buttons not red
        skillButton1.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
        skillButton2.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
        skillButton3.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
    }
}
