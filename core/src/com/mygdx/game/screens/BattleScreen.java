package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.*;
import com.mygdx.game.handlers.BattleHandler;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.global.AttackEvent;
import com.mygdx.global.BattleState;
import org.w3c.dom.Text;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;

import java.util.UUID;

public class BattleScreen implements Screen {
    private final Game gameObj;
    private SpriteBatch batch;
    private Texture background;

    //private Table table1;
    //private Table table2;
    private Stage stage;
    //private Table pet1Info;
    //private Table pet2Info;
    //private Window skillsWindow;
    //private Table imagesTable;
    private UUID myId = PlayerHandler.getId(); //id of current player
    private Player thisPlayer;
    private Player opponentPlayer;

    private Image pet1Image;
    private Image pet2Image;
    private Label pet1Level;
    private Label pet2Level;
    private Label pet1Name;
    private Label pet2Name;
    private Label health1;
    private Label health2;
    private ProgressBar healthBar1;
    private ProgressBar healthBar2;
    TextButton[] skillButtons;
    Boolean[] skillAvailable = {false, false, false};
    private Label winLabel;
    private Label loseLabel;
    private Table winOrLoseTable;
    Table bgTable = new Table(); //background + pets + usernames
    Table pet1Info = new Table();
    Table pet2Info = new Table();
    Table pet1imageTable = new Table();
    Table pet2imageTable = new Table();
    Window skillsWindow;

    public BattleScreen(Game gameObj) {
        System.out.println("BattleScreen created");
        this.gameObj = gameObj;
        this.batch = new SpriteBatch();
        this.background = new Texture("Pixel_art_grass_image.png");

        final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));

        BattleHandler.loadTextures();

        // set players
        if (myId == BattleHandler.getPlayer1().getId()) {
            thisPlayer = BattleHandler.getPlayer1();
            opponentPlayer = BattleHandler.getPlayer2();
        } else {
            thisPlayer = BattleHandler.getPlayer2();
            opponentPlayer = BattleHandler.getPlayer1();
        }


        Creature thisPet = thisPlayer.getCurrentPet();
        Creature opponentPet = opponentPlayer.getCurrentPet();
        if (thisPet == null) {
            System.out.println("thisPet null");
        }

        this.stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

//        // components to add
//        Label name1 = new Label(thisPlayer.username(), skin);
//        Label name2 = new Label(opponentPlayer.username(), skin);


        //background table
        Drawable background = new TextureRegionDrawable(new Texture(Gdx.files.internal("Pixel_art_grass_image.png")));
        bgTable.setBackground(background);
        bgTable.setFillParent(true);

        //pet Images
        pet1Image = new Image(new Texture("meowmad_ali.png"));
        pet2Image = new Image(new Texture("meowmad_ali.png")); //bug: getTexture() not working. nullpointerexception
        pet1imageTable.add(pet1Image).width(100).height(100).left().padLeft(70);
        pet1imageTable.left();
        pet1imageTable.setFillParent(true);

        pet2imageTable.add(pet2Image).width(100).height(100).right().padRight(70);
        pet2imageTable.right();
        pet2imageTable.setFillParent(true);


        //petInfo
        pet1Name = new Label(thisPet.getName(), skin);
        pet2Name = new Label(opponentPet.getName(), skin);
        pet1Level = new Label(((Integer)thisPet.getLevel()).toString(), skin);
        pet2Level = new Label(((Integer)opponentPet.getLevel()).toString(), skin);
        health1 = new Label(thisPet.getHealth() + " / " + thisPet.getMaxhealth(), skin);
        health2 = new Label(opponentPet.getHealth() + " / " + opponentPet.getMaxhealth(), skin);
        healthBar1 = new ProgressBar(0, thisPet.getMaxhealth(), 1, false, skin);
        healthBar1.setAnimateDuration(.5f);
        healthBar1.setValue(thisPet.getHealth());
        healthBar2 = new ProgressBar(0, thisPet.getMaxhealth(), 1, false, skin);
        healthBar2.setAnimateDuration(0.5f);
        healthBar1.setValue(opponentPet.getHealth());

        pet1Info.add(pet1Name).padLeft(10).padTop(5);
        pet1Info.add(pet1Level).left().padLeft(100);
        pet1Info.row();
        pet1Info.add(health1).center().padLeft(10);
        pet1Info.add(healthBar1).left().padLeft(10);
        //pet1Info.top().left().padLeft(100).padTop(50);
        pet1Info.left();
        pet1Info.setFillParent(true);

        pet2Info.add(pet2Name).padRight(10).padTop(5);
        pet2Info.add(pet2Level).right().padRight(100);
        pet2Info.row();
        pet2Info.add(health2).center().padRight(10);
        pet2Info.add(healthBar2).left().padRight(10);
        //pet2Info.top().right().padRight(100).padTop(50);
        pet2Info.right();
        pet2Info.setFillParent(true);


        //skills window
        final Skill[] skills = {thisPet.skill1, thisPet.skill2, thisPet.skill3};
        TextButton skill1 = createSkillButton(skills[0]);
        TextButton skill2 = createSkillButton(skills[1]);
        TextButton skill3 = createSkillButton(skills[2]);
        skillButtons = new TextButton[]{skill1, skill2, skill3};
        // initialize skillAvailable and skillButtons
        for (int i = 0; i < 3; i ++) {
            if (skillButtons[i].isTouchable()) {
                skillAvailable[i] = true;
            }
            addSkillListener(skillButtons[i], skills[i]);
        }

        skillsWindow = new Window("Skills", skin);
        for (TextButton button: skillButtons) {
            skillsWindow.add(button);
            skillsWindow.row();
        }



//        // table for win/lose
//        // table should cover entire screen, rendering player unable to click anything else
//        // todo change from label to image
//        winLabel = new Label("You have won", skin);
//        loseLabel = new Label("You have lost", skin);
//        winOrLoseTable.setFillParent(true);
//        winOrLoseTable.addListener(new ClickListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;
//                // todo clear BattleHandler
//                return super.touchDown(event, x, y, pointer, button);
//            }
//        });

        //then add all the tables to the stage
        this.stage.addActor(bgTable);
        this.stage.addActor(skillsWindow);
        this.stage.addActor(pet2imageTable);
        this.stage.addActor(pet1imageTable);
        this.stage.addActor(pet1Info);
        this.stage.addActor(pet2Info);



        //Stack stack = new Stack(table1, table2);
        //stack.setFillParent(true);
        // outTable.add(thisTable).colspan(2).expand();
    }


    /**
     * called when this screen becomes the current screen for the game
     * initialise resources specific to this screen
     */
    @Override
    public void show() {

    }

    /**
     * rendered every frame.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        // enable/disable skillButtons
        if (BattleHandler.getTurn() == BattleState.Turn.PLAYERONETURN && BattleHandler.getPlayer1().getId() == myId) {
            // this player's turn
            setTouchable();
        } else {
            // opponent's turn
            setNotTouchable();
        }

        // logic for battle
        if (BattleHandler.changePet) {
            // currently not in use
            changePet();
            BattleHandler.changePet = false;
        } else if (BattleHandler.updatePetInfo) {
            updatePetInfo();
            BattleHandler.updatePetInfo = false;
        } else if (BattleHandler.battleEnd) {
            setNotTouchable();
            // todo load end battle screen
            // if this doesn't work, consider implementing stack
            if (thisPlayer.isAlive()) {
                // this player has won
                winOrLoseTable.add(winLabel);
                stage.addActor(winOrLoseTable);
            } else {
                // this player has lost
                winOrLoseTable.add(loseLabel);
                stage.addActor(winOrLoseTable);
            }
            BattleHandler.battleEnd = false;

        }

        // draw screen
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the color buffer

        this.stage.getViewport().apply();
        this.stage.act();
        this.stage.draw();


    }

    /**
     *
     * @param width
     * @param height
     */
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
        stage.dispose();
    }

    public TextButton createSkillButton(Skill skill) {
        final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
        TextButton newButton;
        if (skill != null) {
            newButton = new TextButton(skill.getName(), skin);
        } else {
            newButton = new TextButton("No Skill Acquired", skin);
            newButton.setTouchable(Touchable.disabled);
        }
        return newButton;
    }

    public void setTouchable() {
        // sets skillButtons to correct touchable state
        for (int i = 0; i < 3; i ++) {
            if (skillAvailable[i]) {
                skillButtons[i].setTouchable(Touchable.enabled);
            }
        }
    }

    public void setNotTouchable() {
        for (int i = 0; i < 3; i ++) {
            skillButtons[i].setTouchable(Touchable.disabled);
        }
    }

    public void addSkillListener(TextButton button, Skill skill) {
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AttackEvent attackEvent = new AttackEvent();
                attackEvent.id = myId;
                attackEvent.skill = skill;
                DarwinsDuel.getClient().sendTCP(attackEvent);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void updatePetImage() {
        BattleHandler.loadTextures();
        pet1Image = new Image(thisPlayer.getCurrentPet().getTexturePath());
        pet2Image = new Image(opponentPlayer.getCurrentPet().getTexturePath());
    }

    public void changePet() {
        Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
        pet1Level.setText(((Integer)thisPlayer.getCurrentPet().getLevel()).toString());
        pet2Level.setText(((Integer)opponentPlayer.getCurrentPet().getLevel()).toString());
        pet1Name.setText(thisPlayer.getCurrentPet().getName());
        pet2Name.setText(opponentPlayer.getCurrentPet().getName());
        health1.setText(thisPlayer.getCurrentPet().getHealth() + "/" + thisPlayer.getCurrentPet().getMaxhealth());
        health2.setText(opponentPlayer.getCurrentPet().getHealth() + "/" + opponentPlayer.getCurrentPet().getMaxhealth());
        healthBar1 = new ProgressBar(0, thisPlayer.getCurrentPet().getMaxhealth(), 1, false, skin);
        healthBar1.setAnimateDuration(.5f);
        healthBar1.setValue(thisPlayer.getCurrentPet().getHealth());
        healthBar2 = new ProgressBar(0, opponentPlayer.getCurrentPet().getMaxhealth(), 1, false, skin);
        healthBar2.setAnimateDuration(.5f);
        healthBar2.setValue(opponentPlayer.getCurrentPet().getHealth());
        updatePetImage();
    }

    public void updatePetInfo() {
        health1.setText(thisPlayer.getCurrentPet().getHealth() + "/" + thisPlayer.getCurrentPet().getMaxhealth());
        health2.setText(opponentPlayer.getCurrentPet().getHealth() + "/" + opponentPlayer.getCurrentPet().getMaxhealth());
        healthBar1.setValue(thisPlayer.getCurrentPet().getHealth());
        healthBar2.setValue(opponentPlayer.getCurrentPet().getHealth());
    }
}