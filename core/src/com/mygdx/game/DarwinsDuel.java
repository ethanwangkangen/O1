package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.handlers.TextureHandler;
import com.mygdx.game.interfaces.AuthService;
import com.mygdx.game.interfaces.GameCommunication;
import com.mygdx.game.interfaces.MapInterface;
import com.mygdx.game.MyClient;
import com.mygdx.game.screens.*;



public class DarwinsDuel extends Game implements GameCommunication {
	private SpriteBatch batch;
	private static DarwinsDuel instance;
	private OrthographicCamera camera;
	public static Client client;

	public AuthService authService;

	public enum GameState {
		FREEROAM,
		PETCHANGE,
		BATTLE,
		LOGIN,
		WIN,
		LOSS,
		SPLASH,
		ATTRIBUTE,
		WELCOME,
	}

	// starting screen == SplashScreen
	public static GameState gameState = GameState.SPLASH;

	public DarwinsDuel(AuthService authService) {
		this.authService = authService;
		//this is an instance of FireBaseAuthServiceAndroid
	}

	public DarwinsDuel() {
		//unused. just for DesktopLauncher. unimportant
	}

	/**
	 * Received id of the Player the this user intends to fight.
	 * @param playerUserId id of enemy player
	 */
	@Override
	public void onEnemyInfoReceived(String playerUserId) {
		System.out.println("Sending battle req (in Darwins duel)");
		MyClient.sendBattleRequest(playerUserId);
	}

	@Override
	public void onQuitMapActivity() {
		System.out.println("changing screen to PetChangeScreen");
		gameState = GameState.PETCHANGE;
	}

	@Override
	public void onAttributeActivity() {
		System.out.println("changing screen to AttributeScreen");
		gameState = GameState.ATTRIBUTE;
	}

	@Override
	public void onNPCReqReceived() {
		MyClient.sendNPCReq();
	}

	@Override
	public void create () {
		//this.create();
		batch = new SpriteBatch();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, 800, 600);
		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void resize(int width, int height) {
	}

	//this is the main loop, render() is called every frame.
	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		switch (gameState) {
			case LOGIN:
				if (!(getScreen() instanceof LoginScreen)) {
					System.out.println("Changing to LoginScreen");
					this.setScreen(new LoginScreen(this));
				}
				break;
			case PETCHANGE:
				if (!(getScreen() instanceof PetChangeScreen)) {
					this.setScreen(new PetChangeScreen(this));
					System.out.println("Changing to PetChangeScreen");
				}
				break;
			case FREEROAM:
				if (!(getScreen() instanceof MapScreen)) {
					this.setScreen(new MapScreen(this));
					System.out.println("Changing to MapScreen");
				}
				break;
			case BATTLE:
				if (!(getScreen() instanceof BattleScreen)) {
					System.out.println("Changing to BattleScreen");
					this.setScreen(new BattleScreen(this));
				}
				break;
			case SPLASH:
				if (!(getScreen() instanceof SplashScreen)) {
					System.out.println("Changing to SplashScreen");
					this.setScreen(new SplashScreen(this));
				}
				break;
			case ATTRIBUTE:
				if (!(getScreen() instanceof AttributeScreen)) {
					System.out.println("Changing to AttributeScreen");
					this.setScreen(new AttributeScreen(this));
				}
				break;
			case WELCOME:
				if (!(getScreen() instanceof WelcomeScreen)) {
					System.out.println("Changing to WelcomeScreen");
					this.setScreen(new WelcomeScreen(this));
			}
			case WIN:
				break;
			case LOSS:
				break;
		}

		if (gameState != GameState.FREEROAM) {
			if (Gdx.app.getType() == Application.ApplicationType.Android) {
				MapInterface map = (MapInterface) Gdx.app;
				// Stop showing the map if its being shown
				if (map.mapOn()) {
					map.stopMap();
				}
			}
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
	}

	public static DarwinsDuel getInstance() {
		if (instance == null) {
			instance = (DarwinsDuel) Gdx.app.getApplicationListener();
		}
		return instance;
	}

	public static Client getClient() {
		return client;
	}

	public boolean loadTextures()
	{
		return TextureHandler.getInstance().loadTextures();
	}
}
