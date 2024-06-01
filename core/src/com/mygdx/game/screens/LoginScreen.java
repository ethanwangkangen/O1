package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.entities.*;
import com.mygdx.game.listeners.EventListener;
import com.mygdx.global.*;
import com.badlogic.gdx.Screen;

import java.io.IOException;

public class LoginScreen implements Screen {
    private DarwinsDuel gameObj;
    private final Stage stage;
    private final Table table;
    private Texture background;
    private final TextButton loginButton;
    private final TextField usernameField;
    private Label errorLabel;
    boolean login = false;
    boolean joined = false;


    public LoginScreen(DarwinsDuel gameObj) {

        System.out.println("LoginScreen created");
        this.gameObj = gameObj;
        this.stage = new Stage();
        this.stage.getViewport().setCamera(DarwinsDuel.getInstance().getCamera());

        this.table = new Table();
        this.table.setFillParent(true);
        //this.table.setBackground("mainscreen.png"); //to change

//        Texture background = new Texture("mainscreen.png");
//        stage.act(Gdx.graphics.getDeltaTime());
//        stage.getBatch().begin();
//        stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
//        stage.getBatch().end();

        Drawable background = new TextureRegionDrawable(new TextureRegion(new Texture("mainscreen.png")));
        table.background(background);

        final Skin skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
        this.usernameField = new TextField("Username", skin);

        this.loginButton = new TextButton("Login", skin);
        this.loginButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //connect
                login = true;
                return super.touchDown(event, x, y, pointer, button);
                //create client, connect client to server. start battle

            }
        });
        this.stage.addActor(this.table);
        this.setTable();
    }

    public void setTable() {
        this.table.clear();
        this.table.add(this.usernameField).center().width(250).padTop(50).row();
        this.table.add(this.loginButton).center().size(250, 50).padTop(100).row();
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
        this.stage.draw();
        this.stage.act(delta);

        /* // Begin drawing
        batch.begin();
        // Draw your game elements here
        batch.draw(background, 0, 0, 1000, 1000);
        cat1.render(batch);
        batch.end(); */

        if (login && !joined) {
            joined = true;
            DarwinsDuel.gameState = DarwinsDuel.GameState.FREEROAM;

            DarwinsDuel.client = new Client();
            Client client = DarwinsDuel.getClient();

            client.addListener(new EventListener());
            //client.addListener(new ConnectionStateListener());

            client.getKryo().register(AddPlayerEvent.class);
            client.getKryo().register(AttackEvent.class);
            client.getKryo().register(BattleState.class);
            client.getKryo().register(EndBattleEvent.class);
            client.getKryo().register(JoinRequestEvent.class);
            client.getKryo().register(JoinResponseEvent.class);
            client.getKryo().register(StartBattleEvent.class);

            client.getKryo().register(Player.class);
            client.getKryo().register(Entity.class);
            client.getKryo().register(MeowmadAli.class);
            client.getKryo().register(Creature.class);
            client.getKryo().register(Creature[].class);
            client.getKryo().register(Skill.class);
            client.getKryo().register(BattleState.Turn.class);

            //start the client
            client.start();

            // Connect to server
            // Connect to the server in a separate thread
            Thread connectThread = getThread(client);
            //client.sendTCP(new JoinRequestEvent(new Player(5, 5)));

            try {
                connectThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String username = usernameField.getText();
            AddPlayerEvent AddplayerEvent = new AddPlayerEvent(username);
            client.sendTCP(AddplayerEvent);
        }
    }

    private Thread getThread(Client client) {
        Thread connectThread = new Thread(() -> {
            String host = "localhost"; // Server's IP address if not running locally
            int tcpPort = 54455;       // Must match the server's TCP port
            int udpPort = 54477;       // Must match the server's UDP port

            try {
                client.connect(5000, host, tcpPort, udpPort);
                System.out.println("Connected to the server.");

                JoinRequestEvent JoinRequestEvent = new JoinRequestEvent();
                client.sendTCP(JoinRequestEvent);
                System.out.println("JoinRequestEvent sent");

            } catch (IOException e) {
                System.err.println("Error connecting to the server: " + e.getMessage());
                e.printStackTrace();
                errorLabel.setText(e.getMessage());
            }
        });
        connectThread.start(); // Start the thread
        return connectThread;
    }

    @Override
    public void resize(int width, int height) {

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
