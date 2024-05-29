package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.entities.*;
import com.mygdx.game.listeners.EventListener;
import com.mygdx.game.screens.*;
import com.mygdx.global.BattleState;
import com.mygdx.global.JoinRequestEvent;
import com.mygdx.global.JoinResponseEvent;


import java.io.IOException;


public class DarwinsDuel extends Game {
	SpriteBatch batch;
	Texture img;

	float y = 0;

	public enum GameState {
		FREEROAM,
		BATTLE,
		WIN,
		LOSS
	}

	private GameState gameState = GameState.FREEROAM;


	@Override
	public void create () {
		//this.create();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void resize(int width, int height) {

	}

	//this is the main loop, render() is called every frame.
	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);

		batch.begin();
		batch.draw(img, 0, y);
		batch.end();
		boolean joined = false;
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && !joined){
			joined = true;
			//create client, connect client to server. start battle
			this.gameState = gameState.BATTLE;
			Client client = new Client();
			client.addListener(new EventListener());
			client.getKryo().register(JoinRequestEvent.class);
			client.getKryo().register(JoinResponseEvent.class);
			client.getKryo().register(BattleState.class);
			client.getKryo().register(Player.class);
			client.getKryo().register(Entity.class);
			client.getKryo().register(MeowmadAli.class);
			client.getKryo().register(Creature.class);
			client.getKryo().register(Creature[].class);
			client.getKryo().register(Skill.class);
			client.getKryo().register(BattleState.Turn.class);

			//start the client
			client.start();

			//connect to server
			// Connect to the server in a separate thread
			Thread connectThread = new Thread(() -> {
				String host = "localhost"; // Server's IP address if not running locally
				int tcpPort = 54455;       // Must match the server's TCP port
				int udpPort = 54477;       // Must match the server's UDP port

				try {
					client.connect(5000, host, tcpPort, udpPort);
					System.out.println("Connected to the server.");

					JoinRequestEvent event = new JoinRequestEvent();
					event.player = new Player();
					client.sendTCP(event);
					System.out.println("JoinRequestEvent sent");

				} catch (IOException e) {
					System.err.println("Error connecting to the server: " + e.getMessage());
					e.printStackTrace();
				}
			});
			connectThread.start(); // Start the thread
			//client.sendTCP(new JoinRequestEvent(new Player(5, 5)));

			try {
				connectThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}



		}

		switch (gameState) {
			case FREEROAM:
				if (!(getScreen() instanceof GameScreen)) {
					this.setScreen(new GameScreen(this));
				}
				break;
			case BATTLE:
				if (!(getScreen() instanceof BattleScreen)) {
					System.out.println("BattleScreen started");
					this.setScreen(new BattleScreen(this));
				}
				break;
			case WIN:
				break;
			case LOSS:
				break;
		}

		super.render();
		//note for self: this calls the render() function of Game.
		//this checks current screen, which in turn invokes render() of current screen.
		//so just need to set the correct screen then call this render.

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
