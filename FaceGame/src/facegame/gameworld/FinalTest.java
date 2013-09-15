package facegame.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import facegame.main.InputController;

public class FinalTest implements Screen {

	int testType;
	int totalFaces;
	/**
	 * @param testType indicates which test it is, identify the new face(0), identify the old face(1)
	 * @param totalFaces is the number of face used within this one quest. Maximum of 20 faces as defined in the quest creator
	 */
	String [] testText = {"Select all the faces that you have encountered on your quest.", 
						  "Select all the faces that you have not encountered on your journey"};
	
	private Stage stage;// contains the labels and image buttons in the class
	private Label label;// will contain the text that will be displayed below the image buttons
	private ImageButton [] face;
	private TextureAtlas white_homogeneous;
	
	
	
	public FinalTest(int type, int total){
		this.testType = testType;
	}
	
	@Override
	public void render(float delta) {
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// initialize all the interface variables and other envolved variables
		stage = new Stage();
		
		TextureAtlas textureAtlas = new TextureAtlas("menus/fg_buttons.pack");//////////////////////////////////
		Skin skin = new Skin(Gdx.files.internal("menus/menuSkin.json"), textureAtlas);//////////////////////////
		
		label = new Label(testText[testType], skin);// set the label text depending on the type of test this
													// has to be
		label.setBounds(100, 0, (float) (Gdx.graphics.getWidth() * 0.2) , Gdx.graphics.getHeight()/4);
		stage.addActor(label);
		
		white_homogeneous= new TextureAtlas(Gdx.files.internal("Faces/White/white_male_homogeneous.txt"));
		
		
		Gdx.input.setInputProcessor(new InputController(){
			public boolean keyUp(int keycode){
				
				switch(keycode){
				case Keys.ENTER:
					
					break;
				}
				return true;
			}

		});
		
		// initialize total faces
		face = new ImageButton[totalFaces];
		
		for(int i = 0 ; i < totalFaces; i++){
			Skin faceRegion = new Skin(white_homogeneous);
			face[i] = new ImageButton(faceRegion);
			face[i].setBounds(i * 60, 100, 100, 100);
			stage.addActor(face[i]);
		}
		
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
