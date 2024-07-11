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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.*;
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
    private Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));;
    private AssetManager manager;
    private ExtendViewport extendViewport;
    private int screenWidth;
    private int screenHeight;
    private Texture crossedBox;

    private String myId = UserPlayerHandler.getUserId(); //id of current player
    private final String battleId = UserBattleHandler.getBattleId(); // id of current battle
    private Player thisPlayer;
    private Player opponentPlayer;
    private Creature thisPet;
    private Creature opponentPet;

    // for pets info
    private FlippedImage pet1Image;
    private Image pet2Image;
    private Label pet1Level;
    private Label pet2Level;
    private Label pet1Name;
    private Label pet2Name;
//    private Label health1;
//    private Label health2;
    private ProgressBar healthBar1;
    private ProgressBar healthBar2;

    // for skill buttons and changePet buttons
    private ArrayList<TextButton> skillButtons = new ArrayList<>();
    private Boolean[] skillAvailable = {false, false, false};
    private ArrayList<TextImageButton> petButtons= new ArrayList<>();
    private Boolean[] petAvailable = {false, false, false};


    private Label turnLabel;
    private Dialog endBattleDialog;

    private TextButton changeSkillButton;
    private TextButton changePetButton;
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
    private AnimationActor animationActor2;
    private FlippedAnimationActor animationActorFlip;
    private Timer.Task animationCompleteTask;

    public BattleScreen(Game gameObj) {
        System.out.println("BattleScreen created");
        skin.getFont("default-font").getData().setScale(3,3);


        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        manager = TextureHandler.getInstance().getAssetManager();
        crossedBox = manager.get("crossedbox.png", Texture.class);

        //set stage
        extendViewport = new ExtendViewport(screenWidth, screenHeight);
        this.stage = new Stage(extendViewport);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        System.out.println("show() run");
        //load textures

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
        initialiseEndBattle();

        //then add all the tables to the stage
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(manager.get("border.png", Texture.class)));
        table.add(petsWindow).bottom().center();

        stack.add(bgTable);
        stack.add(table);
        stack.add(endBattleTable);
        stack.setFillParent(true);
        stage.addActor(stack);

        turnLabel = new Label("Testing", skin);
        bgTable.add(turnLabel).center().colspan(3).padTop(50).top().expandY();
        bgTable.row();

        bgTable.add(pet1Info).left().padLeft(50).uniform();
        bgTable.add().uniform().expandX();
        bgTable.add(pet2Info).right().padRight(50).uniform();
        bgTable.row();

        bgTable.add(pet1imageTable).left().expandY().padLeft(50);
        bgTable.add();
        bgTable.add(pet2imageTable).right().padRight(50);
        bgTable.row();

        bgTable.add(changeTable).center();
        bgTable.add(skillsWindow).bottom().padBottom(50).center();

//        stage.setDebugAll(true);
    }
    public void initialisePlayers() {
        // set players
        if (Objects.equals(myId, UserBattleHandler.getPlayer1().getUserId())) {
            thisPlayer = UserBattleHandler.getPlayer1();
            opponentPlayer = UserBattleHandler.getPlayer2();
        } else {
            thisPlayer = UserBattleHandler.getPlayer2();
            opponentPlayer = UserBattleHandler.getPlayer1();
        }
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

        animationActor2 = new AnimationActor(
                TextureHandler.getInstance().getAnimationTextureIdle(opponentPet.getType()),
                TextureHandler.getInstance().getAnimationJsonIdle(opponentPet.getType()),
                TextureHandler.getInstance().getAnimationTextureAttack(opponentPet.getType()),
                TextureHandler.getInstance().getAnimationJsonAttack(opponentPet.getType()));

        animationActorFlip = new FlippedAnimationActor(
                TextureHandler.getInstance().getAnimationTextureIdle(opponentPet.getType()),
                TextureHandler.getInstance().getAnimationJsonIdle(opponentPet.getType()),
                TextureHandler.getInstance().getAnimationTextureAttack(opponentPet.getType()),
                TextureHandler.getInstance().getAnimationJsonAttack(opponentPet.getType()));

        pet1imageTable.add(animationActor1).height(screenWidth / 6).width(screenWidth / 6);
        pet2imageTable.add(animationActorFlip).height(screenWidth / 6).width(screenWidth / 6);
    }

    public void initialisePetInfo() {
        pet1Info.clear();
        pet2Info.clear();

        System.out.println("initialising PetInfo table");

        // for thisPlayer's pet
//        pet1Name = new Label(thisPet.getName(), skin);
        pet1Name = new Label(thisPet.getName(), skin);
        pet1Level = new Label("(" + (thisPet.getLevel()) + ")", skin);
        Label pet1Element = createElementLabel(thisPet);
        //health1 = new Label(thisPet.getHealth() + " / " + thisPet.getMaxhealth(), skin);
        healthBar1 = new HealthBar(screenWidth / 8, screenHeight / 18, thisPet);
        healthBar1.setValue(thisPet.getHealth());

        // for opponent's pet
        pet2Name = new Label(opponentPet.getName(), skin);
        pet2Level = new Label("(" + (opponentPet.getLevel()) + ")", skin);
        Label pet2Element = createElementLabel(opponentPet);
        //health2 = new Label(opponentPet.getHealth() + " / " + opponentPet.getMaxhealth(), skin);
        healthBar2 = new HealthBar(screenWidth / 5, screenHeight / 18, opponentPet);
        healthBar2.setValue(opponentPet.getHealth());

        pet1Info.add(pet1Name);
        pet1Info.add(pet1Element).padLeft(10);
        pet1Info.row();
        //pet1Info.add(health1).center().padLeft(10);
        pet1Info.add(healthBar1).colspan(2).width(screenWidth / 5).padTop(10);
        pet1Info.add(pet1Level).padLeft(10);


        pet2Info.add(pet2Name);
        pet2Info.add(pet2Element).padLeft(10);
        pet2Info.row();
        //pet2Info.add(health2).center().padRight(10);
        pet2Info.add(healthBar2).colspan(2).width(screenWidth / 5).padTop(10);
        pet2Info.add(pet2Level).padLeft(10);
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
//        health1.setText(thisPet.getHealth() + " / " + thisPet.getMaxhealth());
//        health2.setText(opponentPet.getHealth() + " / " + opponentPet.getMaxhealth());
        healthBar1.setValue(thisPet.getHealth());
        healthBar2.setValue(opponentPet.getHealth());
    }

    public void initialiseChangeButtons() {
        changePetButton = new TextButton("Pets", skin);
        changeSkillButton = new TextButton("Attack", skin);

        changeTable.add(changeSkillButton).left().width(screenWidth / 9).height(screenHeight / 11);
        changeTable.row();
        changeTable.add(changePetButton).left().width(screenWidth / 9).height(screenHeight / 11);

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
            } else {
                skillAvailable[i] = false;
            }
            addSkillListener(skillButtons.get(i), skills[i]);
        }

        for (TextButton button: skillButtons) {
            skillsWindow.add(button).pad(5).width(screenWidth / 4).height(screenHeight / 12);
            skillsWindow.row();
        }
        skillsWindow.padTop(skillButtons.get(0).getHeight() + 15);
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
        petsWindow.clear();
        petButtons.clear();
        for (int i = 0; i < 3; i ++) {
            petAvailable[i] = false;
        }
        System.out.println("initialising initialisePetsWindow");

        // initialize skillAvailable and skillButtons
        for (int i = 0; i < 3; i ++) {
            createPetButton(i);
        }

        // add buttons to petsWindow
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
            // player has this pet (ie not out of bounds of arraylist)
            Creature pet = battlePets.get(index);
            newButton = new TextImageButton(pet.getName(), skin,  TextureHandler.getInstance().getTexture(pet.getType()));
            if (pet.isAlive()) {
                newButton.setTouchable(Touchable.enabled);
                // records if pet is available
                petAvailable[index] = true;
            } else {
                newButton.setTouchable(Touchable.disabled);
                petAvailable[index] = false;
            }
        } else {
            // pet not owned
            newButton = new TextImageButton("No pet owned", skin, crossedBox);
            newButton.setTouchable(Touchable.disabled);
            petAvailable[index] = false;
        }

        // add new button to petButtons list
        petButtons.add(newButton);

        // add pet listener
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

        endBattleTable.setFillParent(true);
        endBattleTable.add(endBattleDialog).width((float)(screenWidth / 2)).height((float)(screenHeight / 1.2));
        endBattleDialog.setVisible(false);
    }

    public void setAllSkillTouchable() {

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

    public void setAllNotTouchable() {
        for (TextButton button: skillButtons) {
            button.setTouchable(Touchable.disabled);
        }

        for (TextImageButton button: petButtons) {
            button.setTouchable(Touchable.disabled);
        }
    }

    /**
     * Listen for attack
     * @param skillButton
     * @param skill
     */
    public void addSkillListener(TextButton skillButton, Skill skill) {
        skillButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                PlayerAttackEvent attackEvent = new PlayerAttackEvent();
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

    public void addPetListener(TextImageButton petButton, int i) {
        if (i == 0) {
            petButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    // todo: swap pet1 (current pet) and pet (in argument)
                    PlayerChangePetEvent changePetEvent = new PlayerChangePetEvent();
                    changePetEvent.petNum = Player.PetNum.PET1;
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
                    PlayerChangePetEvent changePetEvent = new PlayerChangePetEvent();
                    changePetEvent.petNum = Player.PetNum.PET2;
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
                    PlayerChangePetEvent changePetEvent = new PlayerChangePetEvent();
                    changePetEvent.petNum = Player.PetNum.PET3;
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

    public boolean isMyTurn() {
        return UserBattleHandler.getTurn() == BattleState.Turn.PLAYERONETURN && Objects.equals(UserBattleHandler.getPlayer1().getUserId(), myId)
                || UserBattleHandler.getTurn() == BattleState.Turn.PLAYERTWOTURN && Objects.equals(UserBattleHandler.getPlayer2().getUserId(), myId);
    }

    @Override
    public void render(float delta) {

        //logic for battle
        if (UserBattleHandler.updatePetInfo) {
            initialisePlayers();
            // for both attacking and changing pets updates

            // pet has attacked
            if (UserBattleHandler.petAttacked()) {
                System.out.println("A pet has attacked.");

                if (!isMyTurn()) {
                    animationActor1.startAttack();
                } else {
                    animationActorFlip.startAttack();
                }

                animationCompleteTask  = new Timer.Task() {
                    @Override
                    public void run() {
                        updatePetInfo();
                        initialisePetImages();
                        initialiseSkillsWindow();
                        initialisePetsWindow();
                    }
                };
                Timer.schedule(animationCompleteTask, 0.5f); // Delay of 1 second (adjust as needed)
            }

            // pet change has occurred
            if (UserBattleHandler.petChanged()) {
                System.out.println("A pet change has occurred.");

                initialisePetInfo();
                initialisePetImages();
                initialiseSkillsWindow();
                initialisePetsWindow();
            }

            UserBattleHandler.updatePetInfo = false;
        }

        // battle has ended
        if (UserBattleHandler.battleEnd) {
            setAllNotTouchable();
            if (!endBattleTextRendered) {
                if (thisPlayer.isAlive()) {
                    // You have won !!
                    endBattleDialog.text("You have achieved victory").pad(5);
                    UserPlayerHandler.wonBattle();
                } else {
                    // You have lost
                    endBattleDialog.text("You have lost ... noob.\nTry harder next time").pad(5);
                    UserPlayerHandler.lostBattle();
                }
                endBattleTextRendered = true;
            }
            endBattleDialog.setVisible(true);
        }

        // enable/disable skillButtons
        if (isMyTurn()) {
            // this player's turn
            setAllSkillTouchable();
            turnLabel.setText("Your Turn");
        } else {
            // opponent's turn
            setAllNotTouchable();
            turnLabel.setText("Opponent's Turn");
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
        if (animationCompleteTask != null) {
            animationCompleteTask.cancel();
        }
    }


}