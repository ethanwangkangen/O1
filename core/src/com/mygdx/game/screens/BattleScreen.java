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
    private UUID id = PlayerHandler.getId(); //id of current player
    private Player thisPlayer;
    private Player opponent;

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

    //notes for self:
    //spritebatch is object for rendering.
    //process is: call .begin(), render using .draw(texture, x, y), then .end()

    public BattleScreen(Game gameObj) {
        System.out.println("BattleScreen created");
        this.gameObj = gameObj;
        this.batch = new SpriteBatch();
        this.background = new Texture("Pixel_art_grass_image.png");

        final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));

        BattleHandler.loadTextures();

        // set players
        if (id == BattleHandler.getPlayer1().getId()) {
            thisPlayer = BattleHandler.getPlayer1();
            opponent = BattleHandler.getPlayer2();
        } else {
            thisPlayer = BattleHandler.getPlayer2();
            opponent = BattleHandler.getPlayer1();
        }

        Creature pet1 = thisPlayer.CurrentPet();
        Creature pet2 = opponent.CurrentPet();

        this.stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        // components to add
        Label name1 = new Label(thisPlayer.username(), skin);
        Label name2 = new Label(opponent.username(), skin);

        pet1Image = new Image(pet1.getTexturePath());
        pet2Image = new Image(pet1.getTexturePath());

        pet1Name = new Label(pet1.getName(), skin);
        pet2Name = new Label(pet2.getName(), skin);

        pet1Level = new Label(((Integer)pet1.getLevel()).toString(), skin);
        pet2Level = new Label(((Integer)pet1.getLevel()).toString(), skin);

        health1 = new Label(pet1.getHealth() + " / " + pet1.getMaxhealth(), skin);
        health2 = new Label(pet2.getHealth() + " / " + pet2.getMaxhealth(), skin);

        healthBar1 = new ProgressBar(0, pet1.getMaxhealth(), 1, false, skin);
        healthBar1.setAnimateDuration(.5f);
        healthBar1.setValue(pet1.getHealth());
        healthBar2 = new ProgressBar(0, pet2.getMaxhealth(), 1, false, skin);
        healthBar2.setAnimateDuration(0.5f);
        healthBar1.setValue(pet2.getHealth());

        final Skill[] skills = pet1.getSkills();
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

        // table1: background
        Table table1 = new Table(); //background + pets + usernames
        table1.background("Pixel_art_grass_image.png");
        table1.setFillParent(true);
        this.stage.addActor(table1);

        // table for pet1Info
        Table pet1Info = new Table();
        pet1Info.add(pet1Name).padLeft(10).padTop(5);
        pet1Info.add(pet1Level).left().padLeft(100);
        pet1Info.row();
        pet1Info.add(health1).center().padLeft(10);
        pet1Info.add(healthBar1).left().padLeft(10);

        // table for pet2Info
        Table pet2Info = new Table();
        pet2Info.add(pet2Name).padLeft(10).padTop(5);
        pet2Info.add(pet2Level).left().padLeft(100);
        pet2Info.row();
        pet2Info.add(health2).center().padLeft(10);
        pet2Info.add(healthBar2).left().padLeft(10);

        // table for skills
        Window skillsWindow = new Window("Skills", skin);
        for (TextButton button: skillButtons) {
            skillsWindow.add(button);
            skillsWindow.row();
        }

        // table for win/lose
        // table should cover entire screen, rendering player unable to click anything else
        // todo change from label to image
        winLabel = new Label("You have won", skin);
        loseLabel = new Label("You have lost", skin);
        winOrLoseTable.setFillParent(true);
        winOrLoseTable.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;
                // todo clear BattleHandler
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        pet1Info.top().left().padLeft(100).padTop(50);
        stage.addActor(pet1Info);
        pet2Info.top().right().padLeft(100).padTop(50);
        stage.addActor(pet2Info);
        Table imagesTable = new Table();
        imagesTable.add(pet1Image).left().center().padLeft(100);
        imagesTable.add(pet2Image).right().center().padRight(100);
        stage.addActor(imagesTable);
        stage.addActor(skillsWindow);


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
        if (BattleHandler.getTurn() == BattleState.Turn.PLAYERONETURN && BattleHandler.getPlayer1().getId() == id) {
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
        this.stage.act(delta);

        //System.out.println("currently rendering BattleScreen");
//        // Begin drawing
//        batch.begin();
//        // Draw your game elements here
//        batch.draw(background, 0, 0, 1000, 1000);
//        cat1.render(batch);
//        batch.end();

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
                AttackEvent attackEvent = new AttackEvent(id, skill);
                DarwinsDuel.getClient().sendTCP(attackEvent);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void updatePetImage() {
        BattleHandler.loadTextures();
        pet1Image = new Image(thisPlayer.CurrentPet().getTexturePath());
        pet2Image = new Image(opponent.CurrentPet().getTexturePath());
    }

    public void changePet() {
        Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
        pet1Level.setText(((Integer)thisPlayer.CurrentPet().getLevel()).toString());
        pet2Level.setText(((Integer)opponent.CurrentPet().getLevel()).toString());
        pet1Name.setText(thisPlayer.CurrentPet().getName());
        pet2Name.setText(opponent.CurrentPet().getName());
        health1.setText(thisPlayer.CurrentPet().getHealth() + "/" + thisPlayer.CurrentPet().getMaxhealth());
        health2.setText(opponent.CurrentPet().getHealth() + "/" + opponent.CurrentPet().getMaxhealth());
        healthBar1 = new ProgressBar(0, thisPlayer.CurrentPet().getMaxhealth(), 1, false, skin);
        healthBar1.setAnimateDuration(.5f);
        healthBar1.setValue(thisPlayer.CurrentPet().getHealth());
        healthBar2 = new ProgressBar(0, opponent.CurrentPet().getMaxhealth(), 1, false, skin);
        healthBar2.setAnimateDuration(.5f);
        healthBar2.setValue(opponent.CurrentPet().getHealth());
        updatePetImage();
    }

    public void updatePetInfo() {
        health1.setText(thisPlayer.CurrentPet().getHealth() + "/" + thisPlayer.CurrentPet().getMaxhealth());
        health2.setText(opponent.CurrentPet().getHealth() + "/" + opponent.CurrentPet().getMaxhealth());
        healthBar1.setValue(thisPlayer.CurrentPet().getHealth());
        healthBar2.setValue(opponent.CurrentPet().getHealth());
    }
}
