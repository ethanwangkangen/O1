package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.*;
import com.mygdx.game.handlers.BattleHandler;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.global.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.Objects;


public class BattleScreen implements Screen {
    private String myId = PlayerHandler.getIdString(); //playerId of current player
    private Player thisPlayer;
    private Player opponentPlayer;
    private Creature thisPet;
    private Creature opponentPet;
    private final String battleId = BattleHandler.getBattleId();

    private FlippedImage pet1Image;
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
    private TextButton changeSkillButton;
    private TextButton changePetButton;
    private ArrayList<TextImageButton> petButtons= new ArrayList<>();
    private Boolean[] petAvailable = {false, false, false};

    private Label winLabel;
    private Label loseLabel;
    private Label turnLabel;

    private Skin skin;
    private Stage stage;
    private Texture crossedBox;
    private AssetManager manager = DarwinsDuel.getInstance().getAssetManager();

    private Table winOrLoseTable = new Table(); // todo using addActor, overlay this when win/lose
    private Table bgTable; //background + battlePets + usernames
    private Table pet1Info;
    private Table pet2Info;
    private Table pet1imageTable;
    private Table pet2imageTable;
    private Window skillsWindow;
    private Window petsWindow;
    private Table changeTable;
    private Stack stack;

    private ExtendViewport extendViewport;
    private int screenWidth = Gdx.graphics.getWidth();
    private int screenHeight = Gdx.graphics.getHeight();

    public BattleScreen(Game gameObj) {
        System.out.println("BattleScreen created");

        //set stage
        extendViewport = new ExtendViewport(screenWidth, screenHeight);
        this.stage = new Stage(extendViewport);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        manager = DarwinsDuel.getInstance().getAssetManager();
        skin = manager.get("buttons/uiskin.json", Skin.class);
        crossedBox = manager.get("crossedbox.png", Texture.class);
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
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(manager.get("border.png", Texture.class)));

        table.add(petsWindow).bottom().center();

        stack = new Stack();
        stack.add(bgTable);
        stack.add(table);
        stack.setFillParent(true);
        stage.addActor(stack);

        turnLabel = new Label("Testing", skin);
        bgTable.add(turnLabel).center().colspan(3).top().expandY();
        bgTable.row();

        bgTable.add(pet1Info).left();
        bgTable.add(pet2Info).right().expandX();
        bgTable.row();

        bgTable.add(pet1imageTable).left().expandY().padLeft(10);
        bgTable.add(pet2imageTable).right().padRight(10);
        bgTable.row();

        bgTable.add(changeTable).expandY().bottom();
        bgTable.add(skillsWindow).center().bottom().padBottom(10).colspan(2).left().padLeft(50);
        stage.setDebugAll(true);
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
        thisPet = thisPlayer.getCurrentPet();
        opponentPet = opponentPlayer.getCurrentPet();
    }

    public void initialiseBgTable() {
        System.out.println("initialising BG table");
        bgTable = new Table();
        bgTable.setBackground(new TextureRegionDrawable(manager.get("Pixel_art_grass_image.png", Texture.class)));
        bgTable.setFillParent(true);
    }

    public void initialisePetImages() {
        pet1imageTable = new Table();
        pet2imageTable = new Table();

        pet1imageTable.clear();
        pet2imageTable.clear();

        System.out.println("initialising PetImages table");
        pet1Image = new FlippedImage(thisPet.getTexturePath());
        pet2Image = new Image(opponentPet.getTexturePath());

        pet1imageTable.add(pet1Image).padLeft(10).height(100).width(100);
        pet2imageTable.add(pet2Image).padRight(10).height(100).width(100);
    }

    public void initialisePetInfo() {
        pet1Info = new Table();
        pet2Info = new Table();

        pet1Info.clear();
        pet2Info.clear();

        System.out.println("initialising PetInfo table");
        pet1Name = new Label(thisPet.getName(), skin);
        pet2Name = new Label(opponentPet.getName(), skin);
        pet1Level = new Label("(" + ((Integer)thisPet.getLevel()).toString() + ")", skin);
        pet2Level = new Label("(" + ((Integer)opponentPet.getLevel()).toString() + ")", skin);
        //health1 = new Label(thisPet.getHealth() + " / " + thisPet.getMaxhealth(), skin);
        //health2 = new Label(opponentPet.getHealth() + " / " + opponentPet.getMaxhealth(), skin);
        healthBar1 = new ProgressBar(0, thisPet.getMaxhealth(), 1, false, skin);
        healthBar1.setAnimateDuration(1f);
        healthBar1.setValue(thisPet.getHealth());
        healthBar2 = new ProgressBar(0, thisPet.getMaxhealth(), 1, false, skin);
        healthBar2.setAnimateDuration(1f);
        healthBar2.setValue(opponentPet.getHealth());

        pet1Info.add(pet1Name);
        pet1Info.add(pet1Level).padLeft(2.5f);
        pet1Info.row();
        //pet1Info.add(health1).center().padLeft(10);
        pet1Info.add(healthBar1).colspan(2);
        pet1Info.padLeft(5);

        pet2Info.add(pet2Name);
        pet2Info.add(pet2Level).padLeft(2.5f);
        pet2Info.row();
        //pet2Info.add(health2).center().padRight(10);
        pet2Info.add(healthBar2).colspan(2);
        pet2Info.padRight(5);
    }

    public void updatePetInfo() {
        //health1.setText(thisPet.getHealth() + " / " + thisPet.getMaxhealth());
        //health2.setText(opponentPet.getHealth() + " / " + opponentPet.getMaxhealth());
        healthBar1.setValue(thisPet.getHealth());
        healthBar2.setValue(opponentPet.getHealth());
    }

    public void initialiseChangeButtons() {
        changeTable = new Table();
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
        skillsWindow = new Window("Skills", skin);
        skillsWindow.clear();
        skillButtons.clear();

        System.out.println("initialising initialiseSkillsWindow");
        skillsWindow.clearChildren();
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

        for (TextButton button: skillButtons) {
            skillsWindow.add(button).pad(1).width(245);
            skillsWindow.row();
        }

        //skillsWindow.setPosition(((float)screenWidth - skillsWindow.getWidth()) / 2, 0);
        skillsWindow.setVisible(true);

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
        petsWindow = new Window("Pets", skin);
        petsWindow.clear();
        petButtons.clear();
        for (int i = 0; i < 3; i ++) {
            petAvailable[i] = false;
        }
        System.out.println("initialising initialisePetsWindow");

        TextImageButton pet1 = createPetButton(thisPlayer.getBattlePets().get(0));
        TextImageButton pet2 = createPetButton(thisPlayer.getBattlePets().get(1));
        TextImageButton pet3 = createPetButton(thisPlayer.getBattlePets().get(2));
        petButtons.add(pet1);
        petButtons.add(pet2);
        petButtons.add(pet3);

        // initialize skillAvailable and skillButtons
        for (int i = 0; i < 3; i ++) {
            if (petButtons.get(i).isTouchable()) {
                petAvailable[i] = true;
            }
            addPetListener(petButtons.get(i), thisPlayer.getBattlePets().get(i), i);
        }
        if (pet2.isTouchable()) {
            System.out.println("Pet 2 is touchable");
        }
        if (petAvailable[1].equals(true)) {
            System.out.println("Pet 2 is available");
        }

        for (TextImageButton button: petButtons) {
            petsWindow.add(button).pad(1).width(245).height(80);
            petsWindow.row();
        }

        petsWindow.setHeight(250);
        petsWindow.setWidth(250);
        petsWindow.setVisible(false);
    }

    public TextImageButton createPetButton(Creature pet) {
        TextImageButton newButton;
        if (pet != null) {
            newButton = new TextImageButton(pet.getName(), skin, pet.getTexturePath());
            if (pet.isAlive()) {
                newButton.setTouchable(Touchable.enabled);
            } else {
                newButton.setTouchable(Touchable.disabled);
            }
        } else {
            newButton = new TextImageButton("No pet owned", skin, crossedBox);
            newButton.setTouchable(Touchable.disabled);
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

        for (TextImageButton button: petButtons) {
            button.setTouchable(Touchable.disabled);
        }
    }

    public void addSkillListener(TextButton skillButton, Skill skill) {
        skillButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AttackEvent attackEvent = new AttackEvent();
                attackEvent.id = myId;
                attackEvent.skill = skill;
                attackEvent.battleId = battleId;
                System.out.println("This player is attacking");
                DarwinsDuel.getClient().sendTCP(attackEvent);
                skillButton.setTouchable(Touchable.disabled);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void addPetListener(TextImageButton petButton, Creature pet, int i) {
        if (i == 0) {
            petButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    // todo: swap pet1 (current pet) and pet (in argument)
                    ChangePetEvent changePetEvent = new ChangePetEvent();
                    changePetEvent.pet = Player.Pet.PET1;
                    changePetEvent.playerId = myId;
                    changePetEvent.battleId = battleId;
                    System.out.println("Changing to pet1");
                    DarwinsDuel.getClient().sendTCP(changePetEvent);
                    petButton.setTouchable(Touchable.disabled);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        } else if (i == 1) {
            petButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    // todo: swap pet1 (current pet) and pet (in argument)
                    ChangePetEvent changePetEvent = new ChangePetEvent();
                    changePetEvent.pet = Player.Pet.PET2;
                    changePetEvent.playerId = myId;
                    changePetEvent.battleId = battleId;
                    System.out.println("Changing to pet2");
                    DarwinsDuel.getClient().sendTCP(changePetEvent);
                    petButton.setTouchable(Touchable.disabled);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        } else if (i == 2) {
            petButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    // todo: swap pet1 (current pet) and pet (in argument)
                    ChangePetEvent changePetEvent = new ChangePetEvent();
                    changePetEvent.pet = Player.Pet.PET3;
                    changePetEvent.playerId = myId;
                    changePetEvent.battleId = battleId;
                    System.out.println("Changing to pet3");
                    DarwinsDuel.getClient().sendTCP(changePetEvent);
                    petButton.setTouchable(Touchable.disabled);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }

    @Override
    public void render(float delta) {

        //logic for battle
        if (BattleHandler.updatePetInfo) {
            initialisePlayers();
            // for both attacking and changing battlePets updates

            // pet has attacked
            if (BattleHandler.petAttacked()) {
                System.out.println("A pet has attacked.");
                initialisePetInfo();
                initialisePetImages();
                initialiseSkillsWindow();
                initialisePetsWindow();
                //updatePetInfo();
                //updatePetsWindow();
            }

            // pet change has occurred
            if (BattleHandler.petChanged()) {
                System.out.println("A pet change has occurred.");
                initialisePetInfo();
                initialisePetImages();
                initialiseSkillsWindow();
                initialisePetsWindow();
            }
            BattleHandler.updatePetInfo = false;
        }

        // battle has ended
        if (BattleHandler.battleEnd) {
            setAllsSkillNotTouchable();
            // todo load end battle screen
            DarwinsDuel.gameState =  DarwinsDuel.GameState.FREEROAM;
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
