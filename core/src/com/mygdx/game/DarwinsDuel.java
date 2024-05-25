package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.screens.*;


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

		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			this.gameState = gameState.BATTLE;
		}

		switch (gameState) {
			case FREEROAM:
				break;
			case BATTLE:
				if (!(getScreen() instanceof BattleScreen)) {
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
