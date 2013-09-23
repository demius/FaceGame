package facegame.userinterface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import facegame.gameworld.IngamePlay;

public class MainMenu implements Screen {
	
	private Stage stage;
	private TextButton buttonExit, buttonPlay, buttonControls;
	private Label heading;
	private Skin skin;
	private TextureAtlas textureAtlas;
	
	private float scrnWidth, scrnHeight;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Table.drawDebug(stage);
		
		//Update everything in the table
		stage.act(delta);
		
		stage.draw();
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
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		textureAtlas = new TextureAtlas("menus/fg_buttons.pack");
		skin = new Skin(Gdx.files.internal("menus/menuSkin.json"), textureAtlas);
		
		float headingH = 0.75f*scrnHeight;
		
		heading = new Label("Main Menu", skin, "heading_white");
		heading.setFontScale(1);
		heading.setBounds((scrnWidth-heading.getWidth())/2, headingH, heading.getWidth(), heading.getHeight());
		
		float firstBH = headingH - scrnHeight/7;
		float buttonSep = scrnHeight/25;
		
		float buttonW = scrnWidth/6;
		float buttonH = scrnHeight/10;
		buttonPlay = new TextButton("Play", skin, "button_white");
		buttonPlay.setBounds((scrnWidth-buttonW)/2, firstBH, buttonW, buttonH);
		buttonPlay.getLabel().setFontScale(0.75f);
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new LoadScreen());
			}
		});
		
		buttonControls = new TextButton("Controls", skin, "button_white");
		buttonControls.setBounds((scrnWidth-buttonW)/2, firstBH - 1*(buttonH + buttonSep), buttonW, buttonH);
		buttonControls.getLabel().setFontScale(0.75f);
		buttonControls.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		
		buttonExit = new TextButton("Exit", skin, "button_white");
		buttonExit.setBounds((scrnWidth-buttonW)/2, firstBH - 2*(buttonH + buttonSep), buttonW, buttonH);
		buttonExit.getLabel().setFontScale(0.75f);
		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
				
		stage.addActor(heading);
		stage.addActor(buttonPlay);
		stage.addActor(buttonControls);
		stage.addActor(buttonExit);
		
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
	}

}
