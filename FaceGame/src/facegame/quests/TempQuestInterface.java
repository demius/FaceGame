package facegame.quests;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class TempQuestInterface implements Screen {
	
	private Stage stage;
	private Table table;
	private TextureAtlas textureAtlas;
	private Skin skin;
	
	private QuestManager questManager;
	private Label questNameLabel, questDialogLabel;

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		updateLabels();
		
		stage.act(delta);
		stage.draw();
		
		Table.drawDebug(stage);
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
		table.debug();
		

		questManager = new QuestManager();
		
		questNameLabel = new Label("", skin);
		questDialogLabel = new Label("", skin);
		
		TextButton buttonNext = new TextButton("next", skin);
		buttonNext.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!questManager.increment()) {
					table.removeActor(questNameLabel);
					table.removeActor(questDialogLabel);
				}
			}
		});
		
		table.add(questNameLabel);
		table.row();		
		table.add(questDialogLabel);
		table.row();
		table.add(buttonNext);
		
		stage.addActor(table);
	}
	
	private void updateLabels() {
		if(questNameLabel.isVisible()) {
			questDialogLabel.setText(questManager.getCurrentDialog());
			questNameLabel.setText(questManager.getCurrentQuestName());
		}
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
