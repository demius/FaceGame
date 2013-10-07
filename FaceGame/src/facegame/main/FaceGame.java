package facegame.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import facegame.userinterface.Splash;

public class FaceGame extends Game {

	
	@Override
	public void create() {
		Gdx.graphics.setDisplayMode(800,480, false);
		
		setScreen(new Splash());

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