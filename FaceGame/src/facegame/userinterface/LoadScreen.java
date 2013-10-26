package facegame.userinterface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import facegame.gameworld.GameWorld;
import facegame.quests.QuestManager;
import facegame.utils.NewAssetManager;

public class LoadScreen implements Screen {

	private NewAssetManager assetManager;
	private Label label;
	private Stage stage;
	
	private Image loadbarInner, loadbarOuter;
	
	private float scrnWidth, scrnHeight;
	private float loadbarWidth, loadbarHeight;
	
	private long startT, endT;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Check the status of the asset manager
		if(assetManager.updateAssetManager()){
			//If all assets loaded, change to the gameplay screen
			//startT = System.currentTimeMillis();
			QuestManager qm = new QuestManager();
			//endT = System.currentTimeMillis() - startT;
			//System.out.println("Loadscreen: load time: " + endT);
			((Game) Gdx.app.getApplicationListener()).setScreen(new GameWorld(qm));
		}
		else{
			//If the assets are still busy loading draw the loading bar and the screen
			loadbarInner.setWidth(loadbarWidth*assetManager.getProgress());
			loadbarInner.invalidate();
			stage.act(delta);
			stage.draw();
		}			
	}

	@Override
	public void resize(int width, int height) {
		scrnWidth = width;
		scrnHeight = height;
	}

	@Override
	public void show() {
		scrnWidth = Gdx.graphics.getWidth();
		scrnHeight = Gdx.graphics.getHeight();
		
		//Instance of the asset manager wrapper
		assetManager = NewAssetManager.getInstance();
		
		stage = new Stage();
		
		//Sets up the stage including the loading bar and the loading label 
		TextureAtlas labelTA = new TextureAtlas("dialog/dialog.pack");
		Skin labelSkin = new Skin(Gdx.files.internal("dialog/dialogSkin.json"), labelTA);
		
		label = new Label("Loading...", labelSkin, "loadingLabel");
		label.setPosition((scrnWidth - label.getTextBounds().width)/2, (scrnHeight + label.getTextBounds().height)/2);
		
		TextureAtlas loadbarTA = new TextureAtlas("menus/loadbar.pack");
		Skin loadbarSkin = new Skin(loadbarTA);

		loadbarInner = new Image(loadbarSkin, "loadbar_inside");
		loadbarOuter = new Image(loadbarSkin, "loadbar_outside");
				
		loadbarWidth = scrnWidth/1.5f;
		loadbarHeight = 15;
		float loadbarX = (scrnWidth - loadbarWidth)/2;
		float loadbarY = scrnHeight/2 - 50;
		
		loadbarOuter.setBounds(loadbarX, loadbarY, loadbarWidth, loadbarHeight);
		loadbarOuter.setZIndex(2);
		
		loadbarInner.setBounds(loadbarX, loadbarY+1, 5, loadbarHeight-1);
		loadbarInner.setZIndex(1);
		
		stage.addActor(label);
		stage.addActor(loadbarOuter);
		stage.addActor(loadbarInner);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
