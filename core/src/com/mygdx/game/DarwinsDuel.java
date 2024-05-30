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
