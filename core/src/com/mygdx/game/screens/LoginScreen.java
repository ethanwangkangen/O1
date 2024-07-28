package com.mygdx.game.screens;

import static com.mygdx.game.EmailValidator.isValidEmail;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.callbacks.AuthResultCallback;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.callbacks.PlayerCallback;
import com.mygdx.game.entities.*;
import com.mygdx.game.events.PlayerJoinServerEvent;
import com.mygdx.game.handlers.UserPlayerHandler;
import com.badlogic.gdx.Screen;
import com.mygdx.game.interfaces.AuthService;
import com.mygdx.game.MyClient;
import com.mygdx.game.handlers.TextureHandler;


public class LoginScreen implements Screen {
    private DarwinsDuel gameObj;
    private Stage stage;
    private AssetManager manager;
    Drawable background;
    Drawable backgroundBrown;

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
    private Label loginErrorLabel;
    private Label signUpErrorLabel;

    Skin skin;
    int height = Gdx.graphics.getHeight();
    int width = Gdx.graphics.getWidth();
    AuthService authService1;

    public LoginScreen(DarwinsDuel gameObj) {

        authService1 = gameObj.authService; //  has error in intellij. taking Authservice from services instead of game

        System.out.println("LoginScreen created");
        manager = TextureHandler.getInstance().getAssetManager();
        background = new TextureRegionDrawable(manager.get("mainscreen.png", Texture.class));
        backgroundBrown = new TextureRegionDrawable(manager.get("brownBorder.png", Texture.class));
        stage = new Stage(new FitViewport(width, height));
        skin = manager.get("buttons/uiskin.json", Skin.class);
        backgroundBrown.setMinHeight(0);
        backgroundBrown.setMinWidth(0);

        // initialise tables
        initialiseErrorLabel();
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

    public void initialiseErrorLabel() {
        loginErrorLabel = new Label("", skin);
        loginErrorLabel.setColor(Color.RED);
        signUpErrorLabel = new Label("", skin);
        signUpErrorLabel.setColor(Color.RED);
    }

    public void initialiseLoginTable() {
        // login table
        loginLabel = new Label("Login", skin);

        usernameLField = new TextField("", skin);
        usernameLField.setMessageText("Email");

        passwordLField = new TextField("", skin);
        passwordLField.setMessageText("Password");
        passwordLField.setPasswordCharacter('*');
        passwordLField.setPasswordMode(true);

        loginButton = new TextButton("Log In", skin);
        System.out.println("here");
        loginButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Connect to Firebase and get player info
                String email, password;
                email = usernameLField.getText();
                password = passwordLField.getText();

                if (isValidInput(email, password)) {
                    // To Login:
                    authService1.signIn(email, password, new AuthResultCallback() {
                        @Override
                        public void onSuccess() { // On success of signIn
                            System.out.println("Player has logged in");
                            authService1.getPlayerFromFirebase(new PlayerCallback() {
                                @Override
                                public void onCallback(Player player) { // On success of getPlayerFromFirebase
                                    UserPlayerHandler.updatePlayer(player);

                                    for (Creature pet : player.battlePets) {
                                        System.out.println(pet.getName());
                                    }
                                    for (Creature pet : player.reservePets) {
                                        System.out.println(pet.getName());
                                    }
                                    System.out.println(authService1.getUserId());

                                    String userId = authService1.getUserId(); // Get unique Id from firebase
                                    UserPlayerHandler.updateIdOfPlayer(userId);

                                    MyClient.sendJoinServerEvent();
                                    System.out.println("playerJoinServerEvent sent");

                                    DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;
                                }
                                @Override
                                public void onFailure() { // On failure of getPlayerFromFirebase
                                    // todo what if getPlayerFromFirebase fails
                                }
                            });
                        }
                        @Override
                        public void onFailure(Exception exception) { // On failure of signIn
                            loginErrorLabel.setText("Login failed: " + exception.getLocalizedMessage());
                            Gdx.app.log("Auth", "Sign up failed: " + exception.getMessage());
                        }
                    });
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        changeToSignUp = new TextButton("Sign up", skin);
        changeToSignUp.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Change to sign up interface
                loginTable.setVisible(false);
                signupTable.setVisible(true);
                loginErrorLabel.setText("");
                signUpErrorLabel.setText("");
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        Table table = new Table();
        table.setBackground(backgroundBrown);
        table.add(loginLabel).colspan(3).expandX().padTop(height / 30);
        table.row().pad(height / 50);
        table.add(usernameLField).width(width / 4).height(height / 12).colspan(3);
        table.row().pad(10);
        table.add(passwordLField).width(width / 4).height(height / 12).colspan(3);
        table.row().pad(height / 20);
        table.add().uniform();
        table.add(loginButton).size(width / 10, height / 12).uniform();
        table.add(changeToSignUp).uniform().size(width / 10, height / 12);

        loginTable.clear();
        loginTable.add(table);
        loginTable.row();
        loginTable.add(loginErrorLabel);
        loginTable.setVisible(true);
    }

    public void initialiseSignUpTable() {
        // Sign up table

        signUpLabel = new Label("Sign Up", skin);

        usernameSField = new TextField("", skin);
        usernameSField.setMessageText("Email");

        passwordSField = new TextField("", skin);
        passwordSField.setMessageText("Password");
        passwordSField.setPasswordCharacter('*');
        passwordSField.setPasswordMode(true);

        signUpButton = new TextButton("Sign Up", skin);
        signUpButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Create new player, connect to Firebase, and upload new player info
                String email, password;
                email = usernameSField.getText();
                password = passwordSField.getText();

                if (isValidInput(email, password)) {
                    // To sign up/register:
                    authService1.signUp(email, password, new AuthResultCallback() {
                        @Override
                        public void onSuccess() {
                            // Change login screen to game screen or wtv
                            Player newPlayer = new Player();
                            String username = generateUsername(email);
                            newPlayer.setUsername(username);
                            System.out.println("Username: " + username);
                            authService1.sendPlayerToFirebase(newPlayer);

                            System.out.println("Registered player successfully");
                            UserPlayerHandler.updatePlayer(newPlayer);

                            String userId = authService1.getUserId(); // Get unique Id from firebase
                            UserPlayerHandler.updateIdOfPlayer(userId);

                            MyClient.sendJoinServerEvent();
                            DarwinsDuel.gameState = DarwinsDuel.GameState.WELCOME;
                        }
                        @Override
                        public void onFailure(Exception exception) {
                            String errorMessage = exception.getLocalizedMessage();
                            signUpErrorLabel.setText("Sign up failed: " + errorMessage);
                            Gdx.app.log("Auth", "Sign up failed: " + exception.getMessage());
                        }
                    });
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        changeToLogin = new TextButton("Login", skin);
        changeToLogin.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Change to login interface
                loginTable.setVisible(true);
                signupTable.setVisible(false);
                loginErrorLabel.setText("");
                signUpErrorLabel.setText("");
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        Table table = new Table();
        table.setBackground(backgroundBrown);

        table.add(signUpLabel).colspan(3).expandX().padTop(height / 30);
        table.row().pad(height / 50);
        table.add(usernameSField).width(width / 4).height(height / 12).colspan(3);
        table.row().pad(10);
        table.add(passwordSField).width(width / 4).height(height / 12).colspan(3);
        table.row().pad(height / 20);
        table.add().uniform();
        table.add(signUpButton).size(width / 10, height / 12).uniform();
        table.add(changeToLogin).uniform().size(width / 10, height / 12);

        signupTable.clear();
        signupTable.add(table);
        signupTable.row();
        signupTable.add(signUpErrorLabel);
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

    public String generateUsername(String email) {
        // Generate username with string in email before @ character
        if (email == null) {
            return "UsernameError";
        } else if (!email.contains("@")) {
            return email;
        }
        return email.split("@")[0];
    }
}
