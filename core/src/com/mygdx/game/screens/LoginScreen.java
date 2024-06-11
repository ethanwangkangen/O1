package com.mygdx.game.screens;

import static com.badlogic.gdx.utils.Align.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.AuthResultCallback;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.*;
import com.mygdx.game.listeners.EventListener;
import com.mygdx.global.*;
import com.badlogic.gdx.Screen;
import com.mygdx.server.UUIDSerializer;
import com.mygdx.game.AuthService;
import org.w3c.dom.ls.LSOutput;
//import com.mygdx.game.FirebaseAuthServiceAndroid;

import java.io.IOException;
import java.util.UUID;

public class LoginScreen implements Screen {
    private DarwinsDuel gameObj;
    private final Stage stage;
    Drawable background = new TextureRegionDrawable(new Texture(Gdx.files.internal("mainscreen.png")));

    private final Table loginTable = new Table();
    private final Table signupTable = new Table();
    private final Table bgTable = new Table();

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
    private Label errorLabel;

    Skin skin;
    boolean login = false;
    boolean joined = false;
    int height = Gdx.graphics.getHeight();
    int width = Gdx.graphics.getWidth();
    AuthService authService1;

    public enum Situations{
        PASSWORD_TOO_SHORT,
        EMPTY_USERNAME,
        INVALID_EMAIL, // for incorrect email format
        USERNAME_TAKEN,
        INCORRECT_INPUT,
        ALL_GOOD,
    }

    public enum Status {
        LOGIN,
        SIGNUP
    }


    public LoginScreen(DarwinsDuel gameObj) {

        authService1 = gameObj.authService;

//        //to check if use is signed in:
//        authService1.isUserSignedIn();
//        //to sign out:
//        authService1.signOut();

        System.out.println("LoginScreen created");
        stage = new Stage(new FitViewport(width, height));
        skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
        skin.getFont("default-font").getData().setScale((int) (Gdx.graphics.getDensity()));

        //initialise tables
        initialiseBgTable();
        initialiseLoginTable();
        initialiseSignUpTable();

        // preparing stack
        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.add(bgTable);
        stack.add(loginTable);
        stack.add(signupTable);

        stage.addActor(stack);

//        stage.setDebugAll(true);

    }

    public void initialiseBgTable() {
        bgTable.setFillParent(true);
        bgTable.setBackground(background);
    }

    public void initialiseLoginTable() {
        // login table
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
                email = usernameLField.getMessageText();
                password = passwordLField.getMessageText();
                validateInput(email, password, Status.LOGIN);

                //to sign in:
                authService1.signIn(email, password, new AuthResultCallback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("Player has logged in");
                        //change login screen to game screen or wtv
                        //todo retrieve player info -> go to game screen

                        DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        //todo pop-up appear
                        Gdx.app.log("Auth", "Sign up failed: " + exception.getMessage());
                    }
                });

                // todo if empty email or empty password

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
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        loginTable.clear();
        loginTable.add(loginLabel).colspan(3).row();
        loginTable.add(usernameLField).width(500).height(80).padTop(50).colspan(3).row();
        loginTable.add(passwordLField).width(500).height(80).padTop(10).colspan(3).row();
        loginTable.add().uniform();
        loginTable.add(loginButton).padTop(100).size(200, 80).uniform();
        loginTable.add(changeToSignUp).uniform().top();
        loginTable.setVisible(true);
    }

    public void initialiseSignUpTable() {
        // sign up table
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

                String email = usernameSField.getMessageText();
                String password = passwordSField.getMessageText();

                //to sign up/register:
                authService1.signUp(email, password, new AuthResultCallback() {
                    @Override
                    public void onSuccess() {
                        //change login screen to game screen or wtv
                        Player newPlayer = new Player();
                        newPlayer.setUsername(usernameSField.getMessageText());
//                        newPlayer.password(passwordSField.getMessageText());

                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Gdx.app.log("Auth", "Sign up failed: " + exception.getMessage());
                    }
                });

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
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        signupTable.clear();
        signupTable.add(signUpLabel).colspan(3).row();
        signupTable.add(usernameSField).size(500, 80).padTop(50).colspan(3).row();
        signupTable.add(passwordSField).size(500, 80).padTop(10).colspan(3).row();
        signupTable.add().uniform();
        signupTable.add(signUpButton).padTop(100).size(200, 80).uniform();
        signupTable.add(changeToLogin).uniform().top();
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

//        if (login && !joined) {
//            joined = true;
//            DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;
//
//            DarwinsDuel.client = new Client();
//            Client myClient = DarwinsDuel.getClient();
//
//
//            myClient.addListener(new EventListener());
//            //client.addListener(new ConnectionStateListener());
//
//            myClient.getKryo().register(UUID.class,  new UUIDSerializer());
//
//            myClient.getKryo().register(AddPlayerEvent.class);
//            myClient.getKryo().register(AttackEvent.class);
//            myClient.getKryo().register(BattleState.class);
//            myClient.getKryo().register(EndBattleEvent.class);
//            myClient.getKryo().register(JoinRequestEvent.class);
//            myClient.getKryo().register(JoinResponseEvent.class);
//            myClient.getKryo().register(StartBattleEvent.class);
//            myClient.getKryo().register(java.util.UUID.class);
//
//            myClient.getKryo().register(Player.class);
//            myClient.getKryo().register(Entity.class);
//            myClient.getKryo().register(MeowmadAli.class);
//            myClient.getKryo().register(Creature.class);
//            myClient.getKryo().register(Creature[].class);
//            myClient.getKryo().register(Skill.class);
//            myClient.getKryo().register(Skill[].class);
//            myClient.getKryo().register(BattleState.Turn.class);
//
//            //start the client
//            myClient.start();
//
//            // Connect to server
//            // Connect to the server in a separate thread
//            Thread connectThread = getThread(myClient);
//            //client.sendTCP(new JoinRequestEvent(new Player(5, 5)));
//
//            try {
//                connectThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            AddPlayerEvent addPlayerEvent = new AddPlayerEvent();
//            addPlayerEvent.username = usernameLField.getText();
//            myClient.sendTCP(addPlayerEvent);
//        }
    }

//    private Thread getThread(Client client) {
//        Thread connectThread = new Thread(() -> {
//            String host = "localhost"; // Server's IP address if not running locally
//            int tcpPort = 55555;       // Must match the server's TCP port
//            int udpPort = 55577;       // Must match the server's UDP port
//
//            try {
//                client.connect(5000, host, tcpPort, udpPort);
//                System.out.println("Connected to the server.");
//
//                JoinRequestEvent joinRequestEvent = new JoinRequestEvent();
//                client.sendTCP(joinRequestEvent);
//                System.out.println("JoinRequestEvent sent");
//
//            } catch (IOException e) {
//                System.err.println("Error connecting to the server: " + e.getMessage());
//                e.printStackTrace();
//                //errorLabel.setText(e.getMessage());
//            }
//        });
//        connectThread.start(); // Start the thread
//        return connectThread;
//    }

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

    public Situations validateInput(String username, String password, Status status) {

        if (password.length() < 6) {
            return Situations.PASSWORD_TOO_SHORT;
        } else if (username.isEmpty()) {
            return Situations.EMPTY_USERNAME;
        } else {
            return Situations.ALL_GOOD;
        }
        //todo implement conditions for username taken and invalid email
    }
}