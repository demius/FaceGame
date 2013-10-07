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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import facegame.quests.QuestManager;
import facegame.quests.RewardManager;
import facegame.utils.GameLog;

public class EndGame implements Screen
{
	private Stage stage;
	private Label text;
	private Label score;
	private Label questsCompleted;
	private Label totalFacesUsed;
	private TextButton mainMenu;
	
	private Skin buttonSkin;
	private Skin dialogSkin;
	private TextureAtlas textureAtlas;
	private TextureAtlas dialogAtlas;
	
	private QuestManager questManager;


	public EndGame(QuestManager qm){
		questManager = qm;
	}
	
	public void show() {

		
		textureAtlas = new TextureAtlas("menus/fg_buttons.pack");
		buttonSkin = new Skin(Gdx.files.internal("menus/menuSkin.json"), textureAtlas);
		
		dialogAtlas = new TextureAtlas("dialog/dialog.pack");
		dialogSkin = new Skin(Gdx.files.internal("dialog/dialogSkin.json"), dialogAtlas);
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		mainMenu = new TextButton("Main menu", buttonSkin, "button_white");
	
		mainMenu.setBounds(Gdx.graphics.getWidth() - 130, 0, 130, 50);
		mainMenu.getLabel().setFontScale(0.75f);
		mainMenu.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());	
			}
		});
		
		text = new Label("You have completed the game, congratulations. Thank you for\ncompleting the game.", dialogSkin, "dialogBox");
		text.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4);

		score =  new Label("Total reward points:"+ RewardManager.getCurrentScore() +"/" + RewardManager.getAvailableRewards() , dialogSkin, "loadingLabel");
		score.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4 - 100, Gdx.graphics.getWidth(), 60);
		
		questsCompleted =  new Label("Quests completed:" + questManager.getNumQuests() , dialogSkin, "loadingLabel");
		questsCompleted.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4 - 200, Gdx.graphics.getWidth(), 60);
		
		totalFacesUsed = new Label("Total faces used:" + questManager.getNumQuests() , dialogSkin, "loadingLabel");
		totalFacesUsed.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4 - 300, Gdx.graphics.getWidth(), 60);
				
		stage.addActor(text);
		stage.addActor(score);
		stage.addActor(questsCompleted);
		stage.addActor(totalFacesUsed);
		stage.addActor(mainMenu);
	}
	
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		score.setText("Total reward points:"+ RewardManager.getCurrentScore() +"/" + RewardManager.getAvailableRewards());
		totalFacesUsed.setText("Total faces used:" + questManager.getTotalGameFaces());
		
		stage.act(delta);
		stage.draw();
	}

	public void resize(int width, int height) {

		
	}

	

	public void hide() {

		
	}

	public void pause() {

		
	}

	public void resume() {

		
	}

	public void dispose() {

		
	}

}
