package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.*;
import com.mygdx.game.events.PlayerSkipEvent;
import com.mygdx.game.handlers.UserBattleHandler;
import com.mygdx.game.handlers.UserPlayerHandler;
import com.mygdx.game.events.PlayerAttackEvent;
import com.mygdx.game.events.PlayerChangePetEvent;
import com.mygdx.global.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.handlers.TextureHandler;

import java.util.ArrayList;
import java.util.Objects;


public class BattleScreen implements Screen {

    private Stage stage;
    private AssetManager manager = TextureHandler.getInstance().getAssetManager();
    private Skin skin = manager.get("buttons/uiskin.json", Skin.class);
    private ExtendViewport extendViewport;
    private int screenWidth;
    private int screenHeight;
    private Texture crossedBox;
    private Texture backgroundBrown;

    private String myId = UserPlayerHandler.getUserId(); //id of current player
    private final String battleId = UserBattleHandler.getBattleId(); // id of current battle
    private Player thisPlayer;
    private Player opponentPlayer;
    private Creature thisPet;
    private Creature opponentPet;

    // for pets info
    private ProgressBar healthBar1;
    private ProgressBar healthBar2;
    private Table pet1Status = new Table();
    private Table pet2Status = new Table();

    // for skill buttons and changePet buttons
    private ArrayList<Button> skillButtons = new ArrayList<>();
    private Boolean[] skillAvailable = {false, false, false};
    private ArrayList<TextImageButton> petButtons= new ArrayList<>();
    private Boolean[] petAvailable = {false, false, false};


    private Label turnLabel;
    private Dialog endBattleDialog;

    private TextButton changeSkillButton;
    private TextButton changePetButton;
    private TextButton skipButton;
    private Boolean endBattleTextRendered = false;

    // tables, windows and stacks
    private Table endBattleTable = new Table();
    private Table bgTable = new Table(); //background + battlePets + usernames
    private Table pet1Info = new Table();
    private Table pet2Info = new Table();
    private Table pet1imageTable = new Table();
    private Table pet2imageTable = new Table();
    private Window skillsWindow = new Window("Skills", skin);
    private Window petsWindow = new Window("Pets", skin);
    private Table changeTable = new Table();
    private Stack stack = new Stack();


    private AnimationActor animationActor1;
    private FlippedAnimationActor animationActorFlip;
    private Timer.Task animationCompleteTask;

    public BattleScreen(Game gameObj) {
        System.out.println("BattleScreen created");

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        crossedBox = manager.get("crossedbox.png", Texture.class);
        backgroundBrown = manager.get("brownBorder.png", Texture.class);

        //set stage
        extendViewport = new ExtendViewport(screenWidth, screenHeight);
        this.stage = new Stage(extendViewport);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

        // Set Players, Creatures, etc.
        initialisePlayers();
        initialisePets();

        // Initialise UI elements
        initialiseBgTable();
        initialisePetInfo();
        initialisePetImages();
        initialiseSkillsWindow();
        initialiseChangeButtons();
        initialisePetsWindow();
        initialiseEndBattle();

        // Add all the tables to the stage
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(manager.get("border.png", Texture.class)));
        table.add(petsWindow).bottom().center();

        Table skillTable = new Table();
        skillTable.setFillParent(true);
        skillTable.add(skillsWindow).expand().bottom().padBottom(screenHeight / 10);

        stack.add(bgTable);
        stack.add(table);
        stack.add(skillTable);
        stack.add(endBattleTable);
        stack.setFillParent(true);
        stage.addActor(stack);

        turnLabel = new Label("Testing", skin);
        bgTable.add(turnLabel).center().colspan(3).padTop(screenHeight / 30).top();
        bgTable.row();

        bgTable.add(pet1imageTable).left().padLeft(screenWidth / 17);
        bgTable.add();
        bgTable.add(pet2imageTable).right().padRight(screenWidth / 17);
        bgTable.row();

        bgTable.add(pet1Info).left().padLeft(screenHeight / 15).uniform();
        bgTable.add().uniform().expandX();
        bgTable.add(pet2Info).right().padRight(screenHeight / 15).uniform();
        bgTable.row();

        bgTable.add(changeTable).center().expandY();

        skillsWindow.setVisible(true);
        handleTurnLogic();

//        stage.setDebugAll(true);
//        skillTable.setDebug(true);
//        table.setDebug(true);
    }
    public void initialisePlayers() {
        // Set players
        if (Objects.equals(myId, UserBattleHandler.getPlayer1().getUserId())) {
            thisPlayer = UserBattleHandler.getPlayer1();
            opponentPlayer = UserBattleHandler.getPlayer2();
        } else {
            thisPlayer = UserBattleHandler.getPlayer2();
            opponentPlayer = UserBattleHandler.getPlayer1();
        }
    }

    public void initialisePets() {
        thisPet = thisPlayer.getCurrentPet();
        opponentPet = opponentPlayer.getCurrentPet();
    }

    public void initialiseBgTable() {
        System.out.println("initialising BG table");
        Drawable background = new TextureRegionDrawable(manager.get("Pixel_art_grass_image.png", Texture.class));
        bgTable.setBackground(background);
        bgTable.setFillParent(true);
    }

    public void initialisePetImages() {
        pet1imageTable.clear();
        pet2imageTable.clear();

        System.out.println("initialising PetImages table");

        animationActor1 = new AnimationActor(
                TextureHandler.getInstance().getAnimationTextureIdle(thisPet.getType()),
                TextureHandler.getInstance().getAnimationJsonIdle(thisPet.getType()),
                TextureHandler.getInstance().getAnimationTextureAttack(thisPet.getType()),
                TextureHandler.getInstance().getAnimationJsonAttack(thisPet.getType()));

        animationActorFlip = new FlippedAnimationActor(
                TextureHandler.getInstance().getAnimationTextureIdle(opponentPet.getType()),
                TextureHandler.getInstance().getAnimationJsonIdle(opponentPet.getType()),
                TextureHandler.getInstance().getAnimationTextureAttack(opponentPet.getType()),
                TextureHandler.getInstance().getAnimationJsonAttack(opponentPet.getType()));

        pet1imageTable.add(animationActor1).height(screenWidth / 6).width(screenWidth / 6).padTop(screenHeight / 20);
        pet2imageTable.add(animationActorFlip).height(screenWidth / 6).width(screenWidth / 6).padTop(screenHeight / 20);
    }

    public void initialisePetInfo() {
        pet1Info.clear();
        pet2Info.clear();

        // Set backgrounds for both pet1 and pet2 info
        TextureRegionDrawable pet1InfoBackground = new TextureRegionDrawable(new TextureRegion(backgroundBrown));
        pet1InfoBackground.setMinHeight(0);
        pet1InfoBackground.setMinWidth(0);
        pet1Info.setBackground(pet1InfoBackground);

        TextureRegionDrawable pet2InfoBackground = new TextureRegionDrawable(new TextureRegion(backgroundBrown));
        pet2InfoBackground.setMinHeight(0);
        pet2InfoBackground.setMinWidth(0);
        pet2Info.setBackground(pet2InfoBackground);

        System.out.println("initialising PetInfo table");

        // For thisPlayer's pet
        Label pet1Name = new Label(thisPet.getName(), skin);
        Label pet1Level = new Label("(" + (thisPet.getLevelString()) + ")", skin);
        Label pet1Element = createElementLabel(thisPet);
        healthBar1 = new HealthBar(screenWidth / 8, screenHeight / 18, thisPet);
        healthBar1.setValue(thisPet.getHealth());


        // For opponent's pet
        Label pet2Name = new Label(opponentPet.getName(), skin);
        Label pet2Level = new Label("(" + (opponentPet.getLevelString()) + ")", skin);
        Label pet2Element = createElementLabel(opponentPet);
        healthBar2 = new HealthBar(screenWidth / 5, screenHeight / 18, opponentPet);
        healthBar2.setValue(opponentPet.getHealth());

        pet1Info.row().padTop(10);
        pet1Info.add(pet1Name).expandX().left().padLeft(screenHeight / 30);
        pet1Info.add(pet1Level).padLeft(10).padRight(screenHeight / 30);
        pet1Info.row();
        pet1Info.add(pet1Element).left().padLeft(screenHeight / 30);
        pet1Info.add(pet1Status);
        pet1Info.row();
        pet1Info.add(healthBar1).colspan(2).width(screenWidth / 5).pad(screenHeight / 30).padTop(0);

        pet2Info.row().padTop(10);
        pet2Info.add(pet2Name).expandX().left().padLeft(screenHeight / 30);
        pet2Info.add(pet2Level).padLeft(10).padRight(screenHeight / 30);
        pet2Info.row();
        pet2Info.add(pet2Element).left().padLeft(screenHeight / 30);
        pet2Info.add(pet2Status);
        pet2Info.row();
        pet2Info.add(healthBar2).colspan(2).width(screenWidth / 5).pad(screenHeight / 30).padTop(0);

        addStatusImage();
    }

    public void addStatusImage() {
        pet1Status.clear();
        pet2Status.clear();

        if (thisPet.isStunned()) {
            pet1Status.add(new Image(manager.get("stun_effect.png", Texture.class))).size(screenWidth / 30);
        }
        if (thisPet.poisonTurn > 0) {
            pet1Status.add(new Image(manager.get("poison_effect.png", Texture.class))).size(screenWidth / 30);
        }

        if (opponentPet.isStunned()) {
            pet2Status.add(new Image(manager.get("stun_effect.png", Texture.class))).size(screenWidth / 30);
        }
        if (opponentPet.poisonTurn > 0) {
            pet2Status.add(new Image(manager.get("poison_effect.png", Texture.class))).size(screenWidth / 30);
        }
    }

    public Label createElementLabel(Creature pet) {
        if (pet.element == Creature.Element.FIRE) {
            return new Label("FIRE", skin);
        } else if (pet.element == Creature.Element.EARTH) {
            return new Label("EARTH", skin);
        } else {
            return new Label("WATER", skin);
        }
    }

    public void updatePetInfo() {
        for (Creature pet : thisPlayer.battlePets) {
            if (Objects.equals(thisPet.getType(), pet.getType())) {
                healthBar1.setValue(pet.getHealth());
            }
        }
        for (Creature pet : opponentPlayer.battlePets) {
            if (Objects.equals(opponentPet.getType(), pet.getType())) {
                healthBar2.setValue(pet.getHealth());
            }
        }
    }

    public void initialiseChangeButtons() {
        changePetButton = new TextButton("Pets", skin);
        changeSkillButton = new TextButton("Attack", skin);
        skipButton = new TextButton("Skip", skin);

        changeTable.add(changeSkillButton).left().width(screenWidth / 9).height(screenHeight / 11).padLeft(screenHeight / 15);
        changeTable.add(skipButton).width(screenWidth / 9).height(screenHeight / 11).padLeft(screenHeight / 30);
        changeTable.row();
        changeTable.add(changePetButton).left().width(screenWidth / 9).height(screenHeight / 11).padLeft(screenHeight / 15);

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

        skipButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                petsWindow.setVisible(false);
                skillsWindow.setVisible(true);
                PlayerSkipEvent skip = new PlayerSkipEvent();
                skip.id = myId;
                skip.battleId = battleId;
                DarwinsDuel.getClient().sendTCP(skip);
                System.out.println("Sending skip");
                setAllNotTouchable();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }

    public void initialiseSkillsWindow() {
        skillsWindow.clear();
        skillButtons.clear();

        System.out.println("initialising initialiseSkillsWindow");
        skillsWindow.clearChildren();
        final Skill[] skills = {thisPet.skill1, thisPet.skill2, thisPet.skill3};
        Button skill1 = createSkillButton(skills[0]);
        Button skill2 = createSkillButton(skills[1]);
        Button skill3 = createSkillButton(skills[2]);
        skillButtons.add(skill1);
        skillButtons.add(skill2);
        skillButtons.add(skill3);

        // Initialize skillAvailable and skillButtons
        for (int i = 0; i < 3; i ++) {
            if (skillButtons.get(i).isTouchable()) {
                skillAvailable[i] = true;
            } else {
                skillAvailable[i] = false;
            }
            addSkillListener(skillButtons.get(i), skills[i]);
        }

        for (Button button: skillButtons) {
            skillsWindow.add(button).pad(5).width(screenWidth / 4).height(screenHeight / 12);
            skillsWindow.row();
        }
        skillsWindow.padTop(skillButtons.get(0).getHeight() + 15);
    }

    public Button createSkillButton(Skill skill) {
        Button newButton;
        if (skill != null) {
            if (skill.status == Skill.Status.ABSORB) {
                newButton = new TextImageButton(skill.getName(), manager.get("absorb_effect.png", Texture.class), skin, screenWidth);
            } else if (skill.status == Skill.Status.POISON) {
                newButton = new TextImageButton(skill.getName(), manager.get("poison_effect.png", Texture.class), skin, screenWidth);
            } else if (skill.status == Skill.Status.STUN) {
                newButton = new TextImageButton(skill.getName(), manager.get("stun_effect.png", Texture.class), skin, screenWidth);
            } else {
                newButton = new TextButton(skill.getName(), skin);
            }
            newButton.setTouchable(Touchable.enabled);
            newButton.setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
        } else {
            newButton = new TextButton("-", skin);
            newButton.setTouchable(Touchable.disabled);
            newButton.setStyle(skin.get("clicked", TextImageButton.ImageTextButtonStyle.class));
        }
        return newButton;
    }

    public void initialisePetsWindow() {
        petsWindow.clear();
        petButtons.clear();
        for (int i = 0; i < 3; i ++) {
            petAvailable[i] = false;
        }
        System.out.println("initialising initialisePetsWindow");

        // Initialize skillAvailable and skillButtons
        for (int i = 0; i < 3; i ++) {
            createPetButton(i);
        }

        // Add buttons to petsWindow
        for (TextImageButton button: petButtons) {
            petsWindow.add(button).pad(1).width(screenWidth / 3).height(screenHeight / 4);
            petsWindow.row();
        }

        petsWindow.padTop(skillButtons.get(0).getHeight() + 15);
        petsWindow.setHeight((float)(screenHeight / 1.3));
        petsWindow.setWidth(screenWidth / 5);
        petsWindow.setVisible(false);
    }

    public void createPetButton(int index) {
        TextImageButton newButton;
        ArrayList<Creature> battlePets = thisPlayer.getBattlePets();

        if (index < thisPlayer.getBattlePets().size()) {
            // Player has this pet (ie not out of bounds of arraylist)
            Creature pet = battlePets.get(index);
            newButton = new TextImageButton(pet.getName() + "\n(CURRENT)", skin, TextureHandler.getInstance().getTexture(pet.getType()), screenWidth);
            if (Objects.equals(pet.getType(), thisPet.getType())) {
                // Pet is current pet

            } else  {
                newButton = new TextImageButton(pet.getName(), skin,  TextureHandler.getInstance().getTexture(pet.getType()), screenWidth);
            }
            if (Objects.equals(pet, thisPet)) {
                // This pet is current pet
                newButton.setTouchable(Touchable.disabled);
                petAvailable[index] = false;
            } else if (pet.isAlive()) {
                newButton.setTouchable(Touchable.enabled);
                // Records if pet is available
                petAvailable[index] = true;
            } else {
                newButton.setTouchable(Touchable.disabled);
                petAvailable[index] = false;
                newButton.setStyle(skin.get("clicked", TextImageButton.ImageTextButtonStyle.class));
            }
        } else {
            // Pet not owned
            newButton = new TextImageButton("No pet owned", skin, crossedBox, screenWidth);
            newButton.setTouchable(Touchable.disabled);
            petAvailable[index] = false;
            newButton.setStyle(skin.get("clicked", TextImageButton.ImageTextButtonStyle.class));
        }

        // Add new button to petButtons list
        petButtons.add(newButton);

        // Add pet listener
        addPetListener(newButton, index);
    }

    public void initialiseEndBattle() {
        endBattleDialog = new Dialog("Battle over", skin)
        {
            protected void result(Object obj) {
                System.out.println("return to game button has been pressed");
                DarwinsDuel.gameState =  DarwinsDuel.GameState.FREEROAM;
                UserBattleHandler.clearBattleHandler();
            }
        };
        endBattleDialog.button("Return to game");
        endBattleDialog.padTop(skillButtons.get(0).getHeight() + 15);

        endBattleTable.setFillParent(true);
        endBattleTable.add(endBattleDialog).width((float)(screenWidth / 2)).height((float)(screenHeight / 1.2));
        endBattleDialog.setVisible(false);
    }

    public void setSkillNotTouchable() {
        for (Button button: skillButtons) {
            button.setTouchable(Touchable.disabled);
            button.setStyle(skin.get("clicked", TextImageButton.ImageTextButtonStyle.class));
        }
    }

    public void setAllTouchable() {
        // Sets skillButtons to correct touchable state
        for (int i = 0; i < 3; i ++) {
            if (skillAvailable[i]) {
                skillButtons.get(i).setTouchable(Touchable.enabled);
                skillButtons.get(i).setStyle(skin.get("default", TextImageButton.ImageTextButtonStyle.class));
            }
        }

        for (int i = 0; i < petAvailable.length; i++) {
            if (petAvailable[i]) {
                petButtons.get(i).setTouchable(Touchable.enabled);
            }
        }
        skipButton.setTouchable(Touchable.enabled);
    }

    public void setAllNotTouchable() {
        for (Button button: skillButtons) {
            button.setTouchable(Touchable.disabled);
        }

        for (TextImageButton button: petButtons) {
            button.setTouchable(Touchable.disabled);
        }

        skipButton.setTouchable(Touchable.disabled);
    }

    public void setAllNotVisible() {
        petsWindow.setVisible(false);
        skillsWindow.setVisible(false);
        skipButton.setVisible(false);
        changePetButton.setVisible(false);
        changeSkillButton.setVisible(false);
    }

    public void setAllVisible() {
        skillsWindow.setVisible(true);
        skipButton.setVisible(true);
        changePetButton.setVisible(true);
        changeSkillButton.setVisible(true);
    }

    /**
     * Listen for attack
     * @param skillButton
     * @param skill
     */
    public void addSkillListener(Button skillButton, Skill skill) {
        skillButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                PlayerAttackEvent attackEvent = new PlayerAttackEvent();
                attackEvent.id = myId;
                attackEvent.skill = skill;
                attackEvent.battleId = battleId;
                System.out.println("This player is attacking");
                DarwinsDuel.getClient().sendTCP(attackEvent);
                setAllNotTouchable();
                setAllNotVisible();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void addPetListener(TextImageButton petButton, int i) {
        if (i == 0) {
            petButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    PlayerChangePetEvent changePetEvent = new PlayerChangePetEvent();
                    changePetEvent.petNum = Player.PetNum.PET1;
                    changePetEvent.playerId = myId;
                    changePetEvent.battleId = battleId;
                    System.out.println("Changing to pet1");
                    DarwinsDuel.getClient().sendTCP(changePetEvent);
                    setAllNotTouchable();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        } else if (i == 1) {
            petButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    PlayerChangePetEvent changePetEvent = new PlayerChangePetEvent();
                    changePetEvent.petNum = Player.PetNum.PET2;
                    changePetEvent.playerId = myId;
                    changePetEvent.battleId = battleId;
                    System.out.println("Changing to pet2");
                    DarwinsDuel.getClient().sendTCP(changePetEvent);
                    setAllNotTouchable();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        } else if (i == 2) {
            petButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    PlayerChangePetEvent changePetEvent = new PlayerChangePetEvent();
                    changePetEvent.petNum = Player.PetNum.PET3;
                    changePetEvent.playerId = myId;
                    changePetEvent.battleId = battleId;
                    System.out.println("Changing to pet3");
                    DarwinsDuel.getClient().sendTCP(changePetEvent);
                    setAllNotTouchable();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }

    public boolean isMyTurn() {
        return UserBattleHandler.getTurn() == BattleState.Turn.PLAYERONETURN && Objects.equals(UserBattleHandler.getPlayer1().getUserId(), myId)
                || UserBattleHandler.getTurn() == BattleState.Turn.PLAYERTWOTURN && Objects.equals(UserBattleHandler.getPlayer2().getUserId(), myId);
    }

    @Override
    public void render(float delta) {
        handleBattleLogic();
        renderScreen();
    }

    private void animation() {
        if (!isMyTurn()) {
            animationActor1.startAttack();
        } else {
            animationActorFlip.startAttack();
        }
    }

    private void handleBattleLogic() {
        if (UserBattleHandler.updatePetInfo) {
            // New battle state has been received
            initialisePlayers();

            if (UserBattleHandler.petAttacked()) {
                System.out.println("A pet has attacked.");

                animation();
                updatePetInfo();
                initialisePets();
                scheduleHealthBarCheck(); // Schedule the task to check health bar animations
            } else if (UserBattleHandler.petChanged()) {
                updatePetInfo();
                initialisePets();
                System.out.println("A pet change has occurred.");
                schedulePetChangeCheck(); // Schedule the task to handle pet changes
            } else {
                handleTurnLogic();
            }

            UserBattleHandler.updatePetInfo = false;
            addStatusImage();
        }

        if (UserBattleHandler.battleEnd) {
            handleBattleEnd();
        }
    }

    private void scheduleHealthBarCheck() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (!healthBar1.isAnimating() && !healthBar2.isAnimating()) {
                    // Health bar animation completed, update UI elements
                    initialisePetImages();
                    initialiseSkillsWindow();
                    initialisePetsWindow();
                    if (UserBattleHandler.petChanged()) {
                        schedulePetChangeCheck();
                    } else {
                        handleTurnLogic();
                    }
                } else {
                    // Schedule another check if animations are still running
                    scheduleHealthBarCheck();
                }
            }
        }, 0.1f); // Adjust the delay as needed
    }

    private void schedulePetChangeCheck() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (!healthBar1.isAnimating() && !healthBar2.isAnimating()) {
                    // Perform pet change updates only when health bar animations are complete
                    initialisePetInfo();
                    initialisePetImages();
                    initialiseSkillsWindow();
                    initialisePetsWindow();
                    handleTurnLogic();
                } else {
                    // Schedule another check if animations are still running
                    schedulePetChangeCheck();
                }
            }
        }, 0.1f); // Adjust the delay as needed
    }

    private void handleBattleEnd() {
        setAllNotTouchable();

        if (!endBattleTextRendered) {
            if (thisPlayer.isAlive()) {
                // You have won!!
                endBattleDialog.text("You have achieved victory").pad(5);
                UserPlayerHandler.wonBattle();
            } else {
                // You have lost
                endBattleDialog.text("You have lost... noob.\nTry harder next time").pad(5);
                UserPlayerHandler.lostBattle();
            }
            endBattleTextRendered = true;
        }
        endBattleDialog.setVisible(true);
    }

    private void handleTurnLogic() {
        if (isMyTurn()) {
            // This player's turn
            setAllTouchable();
            if (thisPlayer.getCurrentPet().isStunned()) {
                // Current pet is stunned; disable skill buttons
                setSkillNotTouchable();
            }
            setAllVisible();
            turnLabel.setText("Your Turn");

        } else {
            // Opponent's turn
            setAllNotTouchable();
            setAllNotVisible();
            turnLabel.setText("Opponent's Turn");
        }
    }

    private void renderScreen() {
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
        if (animationCompleteTask != null) {
            animationCompleteTask.cancel();
        }
    }


}