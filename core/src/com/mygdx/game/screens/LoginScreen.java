package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.*;
import com.mygdx.game.handlers.PlayerHandler;
import com.mygdx.game.listeners.EventListener;
import com.mygdx.global.*;
import com.badlogic.gdx.Screen;
import com.mygdx.server.UUIDSerializer;


import java.io.IOException;
import java.util.UUID;

import static com.mygdx.game.EmailValidator.isValidEmail;

public class LoginScreen implements Screen {
    private DarwinsDuel gameObj;

    private final Stage stage;
    private final Skin skin;
    private AssetManager manager;

    private Table loginTable;
    private Table signupTable;
    private Table bgTable;

    // UI for login
    private Label loginLabel;
    private TextButton loginButton;
    private TextField usernameLField;
    private TextField passwordLField;
    private TextButton changeToSignUp;

    // UI for sign up
    private Label signUpLabel;
    private TextButton signUpButton;
    private TextField usernameSField;
    private TextField passwordSField;
    private TextButton changeToLogin;

    private Label loginErrorLabel;
    private Label signUpErrorLabel;
    private boolean login = false;
    private boolean joined = false;

    /*
    stuff for firebase login (to be refactored):
     */

    public LoginScreen(DarwinsDuel gameObj) {

        System.out.println("LoginScreen created");
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        manager = DarwinsDuel.getInstance().getAssetManager();
        skin = manager.get("buttons/uiskin.json");

        initialiseErrorLabel();
        initialiseBgTable();
        initialiseLoginTable();
        initialiseSignUpTable();

        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.add(bgTable);
        stack.add(loginTable);
        stack.add(signupTable);


        stage.addActor(stack);
        //stage.addActor(signupTable);
        stage.setDebugAll(true);
    }

    public void initialiseBgTable() {
        bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.setBackground(new TextureRegionDrawable(manager.get("mainscreen.png", Texture.class)));
    }

    public void initialiseErrorLabel() {
        loginErrorLabel = new Label("", skin);
        signUpErrorLabel = new Label("", skin);
    }

    public void initialiseLoginTable() {
        // login table
        loginTable = new Table();
        loginTable.setFillParent(true);

        loginLabel = new Label("Login", skin);

        usernameLField = new TextField("", skin);
        usernameLField.setMessageText("Username");

        passwordLField = new TextField("", skin);
        passwordLField.setMessageText("Password");
        passwordLField.setPasswordCharacter('*');
        passwordLField.setPasswordMode(true);

        loginButton = new TextButton("Log In", skin);
        loginButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // connect to Firebase and get player info
                String email, password;
                email = usernameLField.getText();
                password = passwordLField.getText();
                if (isValidInput(email, password)) {
                    login = true;
                }

                return super.touchDown(event, x, y, pointer, button);
                //create client, connect client to server. start battle
            }
        });

        changeToSignUp = new TextButton("Sign up", skin);
        changeToSignUp.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // change to sign up interface
                loginTable.setVisible(false);
                signupTable.setVisible(true);
                loginErrorLabel.setText("");
                signUpErrorLabel.setText("");
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        loginTable.clear();
        loginTable.add(loginLabel).colspan(3).expandX().row();
        loginTable.add(usernameLField).width(250).padTop(50).colspan(3).row();
        loginTable.add(passwordLField).width(250).padTop(10).colspan(3).row();
        loginTable.add().uniform().padTop(100);
        loginTable.add(loginButton).uniform();
        loginTable.add(changeToSignUp).uniform().top().row();
        loginTable.add(loginErrorLabel).colspan(3).center().row();
        loginTable.setVisible(true);
    }

    public void initialiseSignUpTable() {
        // sign up table
        signupTable = new Table();
        signupTable.setFillParent(true);

        signUpLabel = new Label("Sign Up", skin);

        usernameSField = new TextField("", skin);
        usernameSField.setMessageText("Username");

        passwordSField = new TextField("", skin);
        passwordSField.setMessageText("Password");
        passwordSField.setPasswordCharacter('*');
        passwordSField.setPasswordMode(true);

        signUpButton = new TextButton("Sign Up", skin);
        signUpButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // create new player, connect to Firebase, and upload new player info
                String email, password;
                email = usernameSField.getText();
                password = passwordSField.getText();
                if (isValidInput(email, password)) {
                    login = true;
                }
                return super.touchDown(event, x, y, pointer, button);
                //create client, connect client to server. start battle
            }
        });

        changeToLogin = new TextButton("Login", skin);
        changeToLogin.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // change to login interface
                loginTable.setVisible(true);
                signupTable.setVisible(false);
                loginErrorLabel.setText("");
                signUpErrorLabel.setText("");
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        signupTable.clear();
        signupTable.add(signUpLabel).colspan(3).expandX().row();
        signupTable.add(usernameSField).width(250).padTop(50).colspan(3).row();
        signupTable.add(passwordSField).width(250).padTop(10).colspan(3).row();
        signupTable.add().uniform().padTop(100);
        signupTable.add(signUpButton).uniform();
        signupTable.add(changeToLogin).uniform().top().row();
        signupTable.add(signUpErrorLabel).colspan(3).center().row();
        signupTable.setVisible(false);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the color buffer
        //System.out.println("currently rendering BattleScreen");
        stage.act(delta);
        stage.draw();

        if (login && !joined) {
            joined = true;
            DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;

            DarwinsDuel.client = new Client();
            Client myClient = DarwinsDuel.getClient();


            myClient.addListener(new EventListener());
            //client.addListener(new ConnectionStateListener());

            myClient.getKryo().register(UUID.class,  new UUIDSerializer());

            myClient.getKryo().register(AddPlayerEvent.class);
            myClient.getKryo().register(AttackEvent.class);
            myClient.getKryo().register(BattleState.class);
            myClient.getKryo().register(EndBattleEvent.class);
            myClient.getKryo().register(JoinRequestEvent.class);
            myClient.getKryo().register(JoinResponseEvent.class);
            myClient.getKryo().register(ServerStartBattleEvent.class);
            myClient.getKryo().register(ChangePetEvent.class);
            myClient.getKryo().register(java.util.UUID.class);
            myClient.getKryo().register(java.util.ArrayList.class);
            myClient.getKryo().register(PlayerAcceptBattleEvent.class);
            myClient.getKryo().register(PlayerJoinServerEvent.class);
            myClient.getKryo().register(PlayerRequestBattleEvent.class);
            myClient.getKryo().register(NPCBattleEvent.class);
            myClient.getKryo().register(NPC.class);


            myClient.getKryo().register(Player.class);
            myClient.getKryo().register(Player.PetNum.class);
            myClient.getKryo().register(Entity.class);
            myClient.getKryo().register(MeowmadAli.class);
            myClient.getKryo().register(CrocLesnar.class);
            myClient.getKryo().register(Froggy.class);
            myClient.getKryo().register(BadLogic.class);
            myClient.getKryo().register(Creature.class);
            myClient.getKryo().register(Creature[].class);
            myClient.getKryo().register(Skill.class);
            myClient.getKryo().register(Skill[].class);
            myClient.getKryo().register(BattleState.Turn.class);
            myClient.getKryo().register(TextImageButton.class);

            //start the client
            myClient.start();

            // Connect to server
            // Connect to the server in a separate thread
            Thread connectThread = getThread(myClient);
            //client.sendTCP(new JoinRequestEvent(new Player(5, 5)));

            try {
                connectThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Thread getThread(Client client) {
        Thread connectThread = new Thread(() -> {
            String host = "localhost"; // Server's IP address if not running locally
            int tcpPort = 55555;       // Must match the server's TCP port
            int udpPort = 55577;       // Must match the server's UDP port

            try {
                client.connect(5000, host, tcpPort, udpPort);
                System.out.println("Connected to the server.");

                Player newPlayer = new Player();
                PlayerHandler.updatePlayer(newPlayer);

                PlayerJoinServerEvent playerJoinServerEvent = new PlayerJoinServerEvent();
                playerJoinServerEvent.userId = PlayerHandler.getIdString();
                client.sendTCP(playerJoinServerEvent);
                System.out.println("playerJoinServerEvent sent");

            } catch (IOException e) {
                System.err.println("Error connecting to the server: " + e.getMessage());
                e.printStackTrace();
                //loginErrorLabel.setText(e.getMessage());
            }
        });
        connectThread.start(); // Start the thread
        return connectThread;
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

    public boolean isValidInput(String username, String password) {
        if (password.length() < 6) {
            loginErrorLabel.setText("Password has to be at least 6 characters");
            signUpErrorLabel.setText("Password has to be at least 6 characters");
            return false;
        } else if (username.isEmpty()) {
            loginErrorLabel.setText("Username / email cannot be empty");
            signUpErrorLabel.setText("Username / email cannot be empty");
            return false;
        } else if (!isValidEmail(username)) {
            loginErrorLabel.setText("Email format is invalid");
            signUpErrorLabel.setText("Email format is invalid");
            return false;
        }   else {
            // no errors
            loginErrorLabel.setText("");
            signUpErrorLabel.setText("");
            return true;
        }
    }
}
