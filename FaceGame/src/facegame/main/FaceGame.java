package facegame.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import facegame.gameworld.Camera;
import facegame.gameworld.GameWorld;
import facegame.gameworld.IngamePlay;
import facegame.userinterface.FinalTest;
import facegame.userinterface.Splash;

public class FaceGame extends Game {

	
	@Override
	public void create() {
		Gdx.graphics.setDisplayMode(800,480, false);
		
		setScreen(new IngamePlay());

	}
	
	/**
	 * Initialize all the objects used within the game
	 */
	void Initialize(){

	}
	
	/**
	 * Load all the content used in the game
	 */
	void LoadContent(){

	}
	
	

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {	
		super.render();
	}

	
	/**
	 * Updates all the Objects used within the game
	 */
	void Update(){
	}
	
	/**
	 * Draw all the game objects on screen
	 */
	void Draw(){
	}
	
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}