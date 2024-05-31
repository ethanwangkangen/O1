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

import java.util.UUID;

public class BattleScreen implements Screen {
    private final Game gameObj;
    private SpriteBatch batch;
    private Texture background;

    private Table table1;
    //private Table table2;
    private Stage stage;
    private Table pet1Info;
    private Table pet2Info;
    private Window skillsWindow;
    private Table imagesTable;
    private UUID id = PlayerHandler.getId(); //id of current player

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

        Player player1;
        Player player2;
        if (id == BattleHandler.getPlayer1().getId()) {
            player1 = BattleHandler.getPlayer1();
            player2 = BattleHandler.getPlayer2();
        } else {
            player1 = BattleHandler.getPlayer2();
            player2 = BattleHandler.getPlayer1();
        }

        Creature pet1 = player1.CurrentPet();
        Creature pet2 = player2.CurrentPet();

        this.stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        // components for table1
        Label name1 = new Label(player1.username(), skin);
        Label name2 = new Label(player2.username(), skin);
        Image pet1Image = new Image(pet1.getTexturePath());
        Image pet2Image = new Image(pet1.getTexturePath());
        Label pet1Name = new Label(pet1.getName(), skin);
        Label pet2Name = new Label(pet2.getName(), skin);

        Label pet1Level = new Label(((Integer)pet1.getLevel()).toString(), skin);
        Label pet2Level = new Label(((Integer)pet1.getLevel()).toString(), skin);

        //table1
        table1 = new Table(); //background + pets + usernames
        table1.background("Pixel_art_grass_image.png");
        table1.setFillParent(true);
        this.stage.addActor(table1);

        // components for table2
        Label health1 = new Label(pet1.getHealth() + " / " + pet1.getMaxhealth(), skin);
        Label health2 = new Label(pet2.getHealth() + " / " + pet2.getMaxhealth(), skin);

        ProgressBar healthBar1 = new ProgressBar(0, pet1.getMaxhealth(), 1, false, skin);
        healthBar1.setAnimateDuration(.5f);
        healthBar1.setValue(pet1.getHealth());
        ProgressBar healthBar2 = new ProgressBar(0, pet2.getMaxhealth(), 1, false, skin);
        healthBar2.setAnimateDuration(0.5f);
        healthBar1.setValue(pet2.getHealth());

        final Skill[] skills = pet1.getSkills();
        TextButton skill1 = createSkillButton(skills[0]);
        TextButton skill2 = createSkillButton(skills[1]);
        TextButton skill3 = createSkillButton(skills[2]);
        TextButton[] skillButtons = {skill1, skill2, skill3};
        Boolean[] skillAvailable = {false, false, false};

        // initialize skillAvailable and skillButtons
        for (int i = 0; i < 3; i ++) {
            if (skillButtons[i].isTouchable()) {
                skillAvailable[i] = true;
            }
            addSkillListener(skillButtons[i], skills[i]);
        }

        // table for pet1Info
        this.pet1Info = new Table();
        this.pet1Info.add(pet1Name).padLeft(10).padTop(5);
        this.pet1Info.add(pet1Level).left().padLeft(100);
        this.pet1Info.row();
        this.pet1Info.add(health1).center().padLeft(10);
        this.pet1Info.add(healthBar1).left().padLeft(10);

        // table for pet2Info
        this.pet2Info = new Table();
        this.pet2Info.add(pet2Name).padLeft(10).padTop(5);
        this.pet2Info.add(pet2Level).left().padLeft(100);
        this.pet2Info.row();
        this.pet2Info.add(health2).center().padLeft(10);
        this.pet2Info.add(healthBar2).left().padLeft(10);


        // table for skills
        this.skillsWindow = new Window("Skills", skin);
        for (TextButton button: skillButtons) {
            this.skillsWindow.add(button);
            this.skillsWindow.row();
        }


        // outTable.add(thisTable).colspan(2).expand();


        //table2
        //table2 = new Table();



        //Stack stack = new Stack(table1, table2);
        //stack.setFillParent(true);
        this.pet1Info.top().left().padLeft(100).padTop(50);
        this.stage.addActor(pet1Info);
        this.pet2Info.top().right().padLeft(100).padTop(50);
        this.stage.addActor(pet2Info);
        this.imagesTable = new Table();
        this.imagesTable.add(pet1Image).left().center().padLeft(100);
        this.imagesTable.add(pet2Image).right().center().padRight(100);
        this.stage.addActor(imagesTable);
        this.stage.addActor(skillsWindow);
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

        Player player1;
        Player player2;
        if (id == BattleHandler.getPlayer1().getId()) {
            player1 = BattleHandler.getPlayer1();
            player2 = BattleHandler.getPlayer2();
        } else {
            player1 = BattleHandler.getPlayer2();
            player2 = BattleHandler.getPlayer1();
        }
        // update petInfos
        health1.setText(pet1.getHealth() + " / " + pet1.getMaxhealth());
        health2.setText(pet2.getHealth() + " / " + pet2.getMaxhealth());
        healthBar1.setValue(pet1.getHealth());
        healthBar2.setValue(pet2.getHealth());

        if (BattleHandler.getTurn() == BattleState.Turn.PLAYERONETURN) {
            Array<Action> skillButtons = skillsWindow.getActions();
        }



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

    public void setTouchable(boolean[] skillAvailable, TextButton[] skillButtons) {
        // sets skillButtons to correct touchable state
        for (int i = 0; i < 3; i ++) {
            if (skillAvailable[i]) {
                skillButtons[i].setTouchable(Touchable.enabled);
            }
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


}
