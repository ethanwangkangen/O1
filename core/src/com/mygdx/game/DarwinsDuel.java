package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.screens.*;
import com.mygdx.services.AuthService;


public class DarwinsDuel extends Game {
	SpriteBatch batch;
	Texture img;
	private static DarwinsDuel instance;
	private OrthographicCamera camera;
	public static Client client;

	public AuthService authService;
	public AssetManager manager = new AssetManager();

	float y = 0;

	public enum GameState {
		FREEROAM,
		BATTLE,
		LOGIN,
		PETCHANGE,
		WIN,
		LOSS,
		SPLASH
	}

	public static GameState gameState = GameState.SPLASH;

	// should make variables above private
	/*public void changeState(GameState gameState) {
		this.gameState = gameState;
	}
	public static void setClient(Client client) {
		client = client;
	}*/

	public DarwinsDuel(AuthService authService) {
		this.authService = authService;
		//this is an instance of FireBaseAuthServiceAndroid
	}

	public DarwinsDuel() {

	}

	@Override
	public void create () {
		//this.create();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

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

		batch.begin();
		batch.draw(img, 0, y);
		batch.end();


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
			case LOGIN:
				if (!(getScreen() instanceof LoginScreen)) {
					this.setScreen(new LoginScreen(this));
				}
				break;
			case PETCHANGE:
				if (!(getScreen() instanceof PetChangeScreen)) {
					this.setScreen(new PetChangeScreen(this));
				}
			case WIN:
				break;
			case LOSS:
				break;
			case SPLASH:
				if (!(getScreen() instanceof SplashScreen)) {
					this.setScreen(new SplashScreen(this));
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
		img.dispose();
	}

	public static DarwinsDuel getInstance() {
		if (instance == null) {
			instance = (DarwinsDuel) Gdx.app.getApplicationListener();
		}
		return instance;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public static Client getClient() {
		return client;
	}

	public void loadTextures()
	{
		// UI assets
		;
		manager.load("buttons/uiskin.json", Skin.class);

		// background textures
		manager.load("border.png", Texture.class);
		manager.load("crossedbox.png", Texture.class);
		manager.load("mainscreen.png", Texture.class);
		manager.load("Pixel_art_grass_image.png", Texture.class);

		// Creatures textures
		manager.load("badlogic.jpg", Texture.class);
		manager.load("croc lesnar.png", Texture.class);
		manager.load("froggy.png", Texture.class);
		manager.load("meowmad_ali.png", Texture.class);

		manager.load("player1(1).png", Texture.class);
	}

	public AssetManager getAssetManager() {
		return manager;
	}

}
