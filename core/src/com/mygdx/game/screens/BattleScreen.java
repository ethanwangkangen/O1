package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.*;
import com.mygdx.game.handlers.BattleHandler;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.global.AttackEvent;
import com.mygdx.global.BattleState;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.Objects;


public class BattleScreen implements Screen {
    private Stage stage;
    private String myId = PlayerHandler.getIdString(); //id of current player
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
    private ArrayList<TextButton> skillButtons = new ArrayList<>();
    private Boolean[] skillAvailable = {false, false, false};
    private final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
    private Label winLabel;
    private Label loseLabel;
    private Label turnLabel;
    private TextButton changeSkillButton;
    private TextButton changePetButton;
    private ArrayList<ImageTextButton> petButtons= new ArrayList<>();
    private Boolean[] petAvailable = {false, false, false};


    private Table winOrLoseTable = new Table(); // todo using addActor, overlay this when win/lose
    private Table bgTable = new Table(); //background + pets + usernames
    private Table pet1Info = new Table();
    private Table pet2Info = new Table();
    private Table pet1imageTable = new Table();
    private Table pet2imageTable = new Table();
    private Window skillsWindow = new Window("Skills", skin);
    private Window petsWindow = new Window("Pets", skin);
    private Table changeTable = new Table();
    private Stack stack = new Stack();

    private Creature thisPet;
    private Creature opponentPet;

    private ExtendViewport extendViewport;
    private int screenWidth = Gdx.graphics.getWidth();
    private int screenHeight = Gdx.graphics.getHeight();

    //Drawable border = new TextureRegionDrawable(new Texture(Gdx.files.internal("border.png")));


    public BattleScreen(Game gameObj) {
        System.out.println("BattleScreen created");

        //set stage
        extendViewport = new ExtendViewport(screenWidth, screenHeight);
        this.stage = new Stage(extendViewport);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        System.out.println("show() run");
        //load textures
        BattleHandler.loadTextures(() -> {
            System.out.println("finished loading all textures");

            //set Players, Creatures, etc.
            initialisePlayers();

            //initialise UI elements
            initialiseBgTable();
            initialisePetInfo();
            initialisePetImages();
            initialiseSkillsWindow();
            initialiseChangeButtons();
            initialisePetsWindow();

            //then add all the tables to the stage
            this.stage.addActor(bgTable);

            /*pet1Info.setBackground(border);
            pet2imageTable.setBackground(border);
            pet2Info.setBackground(border);
            pet1imageTable.setBackground(border);
            skillsWindow.setBackground(border);*/

            petsWindow.setVisible(false);
            stack.add(skillsWindow);
            stack.add(petsWindow);

            turnLabel = new Label("Testing", skin);
            bgTable.add(turnLabel).center().colspan(2).expandY().top();
            bgTable.row();

            bgTable.add(pet1Info).left();
            bgTable.add(pet2Info).right().expandX();
            bgTable.row();

            bgTable.add(pet1imageTable).left().padLeft(10);
            bgTable.add(pet2imageTable).right().padRight(10);
            bgTable.row();

            bgTable.add(changeTable).expandY().bottom();
            bgTable.add(stack).center().bottom();


            /*this.stage.addActor(pet1Info);
            this.stage.addActor(pet2Info);
            this.stage.addActor(pet1imageTable);
            this.stage.addActor(pet2imageTable);
            this.stage.addActor(skillsWindow);
*/
        });
    }
    public void initialisePlayers() {
        // set players
        if (Objects.equals(myId, BattleHandler.getPlayer1().getIdString())) {
            thisPlayer = BattleHandler.getPlayer1();
            opponentPlayer = BattleHandler.getPlayer2();
        } else {
            thisPlayer = BattleHandler.getPlayer2();
            opponentPlayer = BattleHandler.getPlayer1();
        }

        //set pets
        thisPet = thisPlayer.getCurrentPet();
        opponentPet = opponentPlayer.getCurrentPet();

    }
    public void initialiseBgTable() {
        System.out.println("initialising BG table");
        Drawable background = new TextureRegionDrawable(new Texture(Gdx.files.internal("Pixel_art_grass_image.png")));
        bgTable.setBackground(background);
        bgTable.setFillParent(true);
    }

    public void initialisePetImages() {
        System.out.println("initialising PetImages table");
        pet1Image = new Image(thisPet.getTexturePath());
        pet2Image = new Image(opponentPet.getTexturePath()); //bug: getTexture() not working. nullpointerexception


        pet1imageTable.clear();

        pet1imageTable.add(pet1Image).padLeft(10).height(100).width(100);

        pet2imageTable.clear();

        pet2imageTable.add(pet2Image).padRight(10).height(100).width(100);
    }

    public void initialisePetInfo() {
        System.out.println("initialising PetInfo table");
        pet1Name = new Label(thisPet.getName(), skin);
        pet2Name = new Label(opponentPet.getName(), skin);
        pet1Level = new Label("(" + ((Integer)thisPet.getLevel()).toString() + ")", skin);
        pet2Level = new Label("(" + ((Integer)opponentPet.getLevel()).toString() + ")", skin);
        //health1 = new Label(thisPet.getHealth() + " / " + thisPet.getMaxhealth(), skin);
        //health2 = new Label(opponentPet.getHealth() + " / " + opponentPet.getMaxhealth(), skin);
        healthBar1 = new ProgressBar(0, thisPet.getMaxhealth(), 1, false, skin);
        healthBar1.setAnimateDuration(.5f);
        healthBar1.setValue(thisPet.getHealth());
        healthBar2 = new ProgressBar(0, thisPet.getMaxhealth(), 1, false, skin);
        healthBar2.setAnimateDuration(0.5f);
        healthBar2.setValue(opponentPet.getHealth());

        pet1Info.clear();

        pet1Info.add(pet1Name);
        pet1Info.add(pet1Level).padLeft(2.5f);
        pet1Info.row();
        //pet1Info.add(health1).center().padLeft(10);
        pet1Info.add(healthBar1).colspan(2);

        pet1Info.padLeft(5);

        pet2Info.clear();

        pet2Info.add(pet2Name);
        pet2Info.add(pet2Level).padLeft(2.5f);
        pet2Info.row();
        //pet2Info.add(health2).center().padRight(10);
        pet2Info.add(healthBar2).colspan(2);

        pet2Info.padRight(5);
    }

    public void initialiseChangeButtons() {
        changePetButton = new TextButton("Pets", skin);
        changeSkillButton = new TextButton("Attack", skin);

        changeTable.add(changeSkillButton).left().width(100);
        changeTable.row();
        changeTable.add(changePetButton).left().width(100);

        changePetButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                petsWindow.setVisible(true);
                skillsWindow.setVisible(false);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        changeSkillButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                petsWindow.setVisible(false);
                skillsWindow.setVisible(true);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }

    public void initialiseSkillsWindow() {
        System.out.println("initialising initialiseSkillsWindow");
        final Skill[] skills = {thisPet.skill1, thisPet.skill2, thisPet.skill3};
        TextButton skill1 = createSkillButton(skills[0]);
        TextButton skill2 = createSkillButton(skills[1]);
        TextButton skill3 = createSkillButton(skills[2]);
        skillButtons.add(skill1);
        skillButtons.add(skill2);
        skillButtons.add(skill3);

        // initialize skillAvailable and skillButtons
        for (int i = 0; i < 3; i ++) {
            if (skillButtons.get(i).isTouchable()) {
                skillAvailable[i] = true;
            }
            addSkillListener(skillButtons.get(i), skills[i]);
        }

        skillsWindow.clear();
        for (TextButton button: skillButtons) {
            skillsWindow.add(button).pad(1).width(245);
            skillsWindow.row();
        }
//        skillsWindow.setHeight(120);
//        skillsWindow.setWidth(250);
        //skillsWindow.setPosition(((float)screenWidth - skillsWindow.getWidth()) / 2, 0);
        skillsWindow.padBottom(1);

    }

    public TextButton createSkillButton(Skill skill) {
        TextButton newButton;
        if (skill != null) {
            newButton = new TextButton(skill.getName(), skin);
            newButton.setTouchable(Touchable.enabled);
        } else {
            newButton = new TextButton("No Skill Acquired", skin);
            newButton.setTouchable(Touchable.disabled);
        }
        return newButton;
    }

    public void initialisePetsWindow() {
        System.out.println("initialising initialisePetsWindow");
        final Skill[] skills = {thisPet.skill1, thisPet.skill2, thisPet.skill3};
        final Creature[] pets = {thisPlayer.pet1, thisPlayer.pet2, thisPlayer.pet3};
        ImageTextButton pet1 = createPetButton(thisPlayer.pet1);
        ImageTextButton pet2 = createPetButton(thisPlayer.pet2);
        ImageTextButton pet3 = createPetButton(thisPlayer.pet3);
        petButtons.add(pet1);
        petButtons.add(pet2);
        petButtons.add(pet3);

        // initialize skillAvailable and skillButtons
        for (int i = 0; i < 3; i ++) {
            if (petButtons.get(i).isTouchable()) {
                petAvailable[i] = true;
            }
            addPetListener(petButtons.get(i), pets[i]);
        }

        petsWindow.clear();
        for (ImageTextButton button: petButtons) {
            petsWindow.add(button).pad(1).width(245);
            petsWindow.row();
        }

        petsWindow.setHeight(120);
        petsWindow.setWidth(250);
        petsWindow.setPosition(((float)screenWidth - petsWindow.getWidth()) / 2, 0);
        petsWindow.padBottom(1);
    }

    public ImageTextButton createPetButton(Creature pet) {
        ImageTextButton newButton;
        if (pet != null) {
            newButton = new ImageTextButton(pet.getName(), skin);
            newButton.setTouchable(Touchable.enabled);
            newButton.getStyle().imageUp = new TextureRegionDrawable(pet.getTexturePath());
        } else {
            newButton = new ImageTextButton("No pet owned", skin);
            newButton.setTouchable(Touchable.disabled);
            newButton.getStyle().imageUp = new TextureRegionDrawable(pet.getTexturePath());
        }

        return newButton;
    }

    public void setAllSkillTouchable() {
        //todo: set all not touchable (ie both skillbuttons, petbuttons, and changebuttons)

        // sets skillButtons to correct touchable state
        for (int i = 0; i < 3; i ++) {
            if (skillAvailable[i]) {
                skillButtons.get(i).setTouchable(Touchable.enabled);
            }
        }

        for (int i = 0; i < petAvailable.length; i++) {
            if (petAvailable[i]) {
                petButtons.get(i).setTouchable(Touchable.enabled);
            }
        }

    }

    public void setAllsSkillNotTouchable() {
        for (TextButton button: skillButtons) {
            button.setTouchable(Touchable.disabled);
        }

        for (ImageTextButton button: petButtons) {
            button.setTouchable(Touchable.disabled);
        }
    }

    public void addSkillListener(TextButton button, Skill skill) {
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AttackEvent attackEvent = new AttackEvent();
                attackEvent.id = myId;
                attackEvent.skill = skill;
                System.out.println("This player is attacking");
                DarwinsDuel.getClient().sendTCP(attackEvent);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void addPetListener(ImageTextButton button, Creature pet) {
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // todo: swap pet1 (current pet) and pet (in argument)
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void render(float delta) {

        //logic for battle
        if (BattleHandler.changePet) {
            // currently not in use
            initialisePlayers();
            initialisePetInfo();
            initialisePetImages();
            BattleHandler.changePet = false;
        } else if (BattleHandler.updatePetInfo) {
            initialisePlayers();
            initialisePetInfo();
            BattleHandler.updatePetInfo = false;
        } else if (BattleHandler.battleEnd) {
            setAllsSkillNotTouchable();
            // todo load end battle screen
            // if this doesn't work, consider implementing stack
            DarwinsDuel.gameState =  DarwinsDuel.gameState.FREEROAM;
            BattleHandler.battleEnd = false;

        }

        // enable/disable skillButtons
        if (BattleHandler.getTurn() == BattleState.Turn.PLAYERONETURN && Objects.equals(BattleHandler.getPlayer1().getIdString(), myId)
                || BattleHandler.getTurn() == BattleState.Turn.PLAYERTWOTURN && Objects.equals(BattleHandler.getPlayer2().getIdString(), myId)) {
            // this player's turn
            setAllSkillTouchable();
        } else {
            // opponent's turn
            setAllsSkillNotTouchable();
        }

        // draw screen
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the color buffer

        // Clear the stage

        this.stage.getViewport().apply();
        this.stage.act();
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height);
        stage.getViewport().update(width, height, true);
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
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


}
