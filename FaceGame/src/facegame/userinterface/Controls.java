package facegame.userinterface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Controls implements Screen{

	private Stage buttonStage, tut1, tut2, tut3, tut4;
	private Image movementI, interactionsI, selectionsI, directionsI;
	private TextButton back, next;
	private Skin buttonSkin;
	private Skin dialogSkin;
	private TextureAtlas textureAtlas;
	private TextureAtlas dialogAtlas;
	
	private Label movement;
	private Label interactions;
	private Label selection;
	private Label directions;
	
	private int currentStep = 1;
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		switch(currentStep){
		case 1:
			tut1.act(delta);
			tut1.draw();
			break;
		case 2:
			tut2.act(delta);
			tut2.draw();
			break;
		case 3:
			tut3.act(delta);
			tut3.draw();
			break;
		case 4:
			tut4.act(delta);
			tut4.draw();
			break;
		
		}
		
		buttonStage.act(delta);
		buttonStage.draw();
	}

	public void resize(int width, int height) {

		
	}

	public void show() {		
		textureAtlas = new TextureAtlas("menus/fg_buttons.pack");
		buttonSkin = new Skin(Gdx.files.internal("menus/menuSkin.json"), textureAtlas);
		
		dialogAtlas = new TextureAtlas("dialog/dialog.pack");
		dialogSkin = new Skin(Gdx.files.internal("dialog/dialogSkin.json"), dialogAtlas);
		
		buttonStage = new Stage();
		tut1 = new Stage();
		tut2 = new Stage();
		tut3 = new Stage();
		tut4 = new Stage();
		Gdx.input.setInputProcessor(buttonStage);
		
		back = new TextButton("Back", buttonSkin, "button_white");
		next = new TextButton("Next", buttonSkin, "button_white");
		// Labels used in the tutorial screens
		// MOVEMENT Stage
		movement = new Label("Use A,S,D,W keys to navigate around the world", dialogSkin, "dialogBox");
		movement.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4);
		tut1.addActor(movement);
		//movementI = new Image(new Texture(""));
		
		// INTERACTIONS Stage
		interactions = new Label("When the interaction prompt shows in the top right corner\n" +
								 "press [ENTER].To go to the next part of the dialog press [ENTER]" , dialogSkin, "dialogBox");
		interactions.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4,  Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4);
		tut2.addActor(interactions);
		//interactionsI = new Image(new Texture(""));
		
		//DIRECTIONS Stage
		directions = new Label("Walk in the direction of the arrow to get to your next target.\n" + 
								"Look at the Distance meter below the arrow to see how far\n"+
								"away you are from your next target.", dialogSkin, "dialogBox");
		directions.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4,  Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4);
		tut3.addActor(directions);
		//directionsI = new Image(new Texture(""));
		
		//SELECTION Stage
		selection = new Label("When you reach the final node click on the appropriate face using the mouse pointer", dialogSkin, "dialogBox");
		selection.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4,  Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4);
		tut4.addActor(selection);
		//selectionsI = new Image(new Texture(""));
		
		
		back.setBounds(0, 0, 100, 50);
		back.getLabel().setFontScale(0.75f);
		back.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				
				currentStep--;
				
				if(currentStep == 0)
					((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		
		next.setBounds(Gdx.graphics.getWidth() - 100, 0, 100, 50);
		next.getLabel().setFontScale(0.75f);
		next.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				
				currentStep++;
				
				if(currentStep == 5)
					((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				
			}
		});
		
		buttonStage.addActor(back);
		buttonStage.addActor(next);
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
