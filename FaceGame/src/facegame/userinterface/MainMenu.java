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
import facegame.quests.QuestManager;

public class MainMenu implements Screen {
	
	private Stage stage;
	private Table table;
	private TextButton buttonExit, buttonPlay, buttonLoadQuests;
	private Label heading;
	private Skin skin;
	private TextureAtlas textureAtlas;

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
	}

	@Override
	public void show() {
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		textureAtlas = new TextureAtlas("menus/fg_buttons.pack");
		skin = new Skin(Gdx.files.internal("menus/menuSkin.json"), textureAtlas);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		buttonExit = new TextButton("Exit", skin);
		buttonExit.pad(1);
		//Sets the action for when the exit button is clicked
		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		buttonPlay = new TextButton("Play", skin);
		buttonPlay.pad(5);
		//Sets the action for when the play button is clicked
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new IngamePlay());
			}
		});
		
		buttonLoadQuests = new TextButton("Load Quests", skin);
		buttonLoadQuests.pad(5);
		buttonLoadQuests.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.addLifecycleListener(new QuestManager());
			}
		});
		
		heading = new Label("Main Menu", skin);
				
		table.add(heading);
		table.row();	//New row of table
		table.add(buttonPlay);
		table.row();
		table.add(buttonLoadQuests);
		table.row();
		table.add(buttonExit);
		table.debug();	//TODO remove later
		stage.addActor(table);
		
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
