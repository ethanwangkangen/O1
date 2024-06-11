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
import com.badlogic.gdx.utils.viewport.FillViewport;
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
    private final Table table;
    Drawable background = new TextureRegionDrawable(new Texture(Gdx.files.internal("mainscreen.png")));
    private final TextButton loginButton;
    private final TextField usernameField;
    private Label errorLabel;
    boolean login = false;
    boolean joined = false;
    int height = Gdx.graphics.getHeight();
    int width = Gdx.graphics.getWidth();

    private BitmapFont font;




    public LoginScreen(DarwinsDuel gameObj) {

        /*
        For reference:
        Firebase placeholder logic start (for sk)
         */

        AuthService authService1 = gameObj.authService;
        String email = "user@gmail.com";
        String password = "password123";

        //to sign in:
        authService1.signIn(email, password, new AuthResultCallback() {
            @Override
            public void onSuccess() {
                //change login screen to game screen or wtv
            }

            @Override
            public void onFailure(Exception exception) {
                Gdx.app.log("Auth", "Sign up failed: " + exception.getMessage());
            }
        });

        //to sign up/register:
        authService1.signUp(email, password, new AuthResultCallback() {
            @Override
            public void onSuccess() {
                //change login screen to game screen or wtv
            }

            @Override
            public void onFailure(Exception exception) {
                Gdx.app.log("Auth", "Sign up failed: " + exception.getMessage());
            }
        });

        //to check if use is signed in:
        authService1.isUserSignedIn();

        //to sign out:
        authService1.signOut();

        /*
        Firebase placeholder logic end (for sk)
         */


        System.out.println("LoginScreen created");
        stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);
        table.setBackground(background);

        final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
        skin.getFont("default-font").getData().setScale((int) (2.5 * Gdx.graphics.getDensity()));

        usernameField = new TextField("Username", skin);

        loginButton = new TextButton("Login", skin);

        loginButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //connect
                login = true;
                return super.touchDown(event, x, y, pointer, button);
                //create client, connect client to server. start battle

            }
        });
        stage.addActor(table);
        setTable();
    }

    public void setTable() {
        table.clear();

        table.add(usernameField).center().padTop(100).width(0.3f * Gdx.graphics.getWidth()).height(0.1f * Gdx.graphics.getHeight()).row();
        table.add(loginButton).center().padTop(100).width(0.3f * width).height(0.1f * height).row();
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
            myClient.getKryo().register(StartBattleEvent.class);
            myClient.getKryo().register(java.util.UUID.class);

            myClient.getKryo().register(Player.class);
            myClient.getKryo().register(Entity.class);
            myClient.getKryo().register(MeowmadAli.class);
            myClient.getKryo().register(Creature.class);
            myClient.getKryo().register(Creature[].class);
            myClient.getKryo().register(Skill.class);
            myClient.getKryo().register(Skill[].class);
            myClient.getKryo().register(BattleState.Turn.class);

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

            AddPlayerEvent addPlayerEvent = new AddPlayerEvent();
            addPlayerEvent.username = usernameField.getText();
            myClient.sendTCP(addPlayerEvent);
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

                JoinRequestEvent joinRequestEvent = new JoinRequestEvent();
                client.sendTCP(joinRequestEvent);
                System.out.println("JoinRequestEvent sent");

            } catch (IOException e) {
                System.err.println("Error connecting to the server: " + e.getMessage());
                e.printStackTrace();
                //errorLabel.setText(e.getMessage());
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
}