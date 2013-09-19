package facegame.userinterface;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import facegame.gameworld.IngamePlay;
import facegame.main.InputController;
import facegame.quests.QuestManager;

public class FinalTest implements Screen {

	int testType;
	private int totalFaces;
	
	private Stage stage;					// contains the labels and image buttons in the class
	private Label stringLabel, dialogLabel;	// will contain the text that will be displayed above the image buttons
	private String selectionMessage = "Click on the image to make a selection:", dialog; 
	
	private float scrnWidth, scrnHeight;
	
	private IngamePlay gamePlay;
	
	private ArrayList<Sprite> faceList;
	private Vector<ImageButton> buttons;
	
	private QuestManager questManager;
	
	/**
	 * @param testType indicates which test it is, identify the new face(0), identify the old face(1)
	 * @param totalFaces is the number of face used within this one quest. Maximum of 20 faces as defined in the quest creator
	 */
	public FinalTest(IngamePlay game, QuestManager qm, String dialog, ArrayList<Sprite> faces){
		gamePlay = game;
		questManager = qm;
		this.dialog = dialog;
		
		faceList = faces;
		totalFaces = faceList.size();
		
		buttons = new Vector<ImageButton>();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		scrnHeight = Gdx.graphics.getHeight();
		scrnWidth = Gdx.graphics.getWidth();
	}

	@Override
	public void show() {
		
		scrnHeight = Gdx.graphics.getHeight();
		scrnWidth = Gdx.graphics.getWidth();
		
		// initialize all the interface variables and other envolved variables
		stage = new Stage();
		
		TextureAtlas textureAtlas = new TextureAtlas("dialog/dialog.pack");//////////////////////////////////
		Skin skin = new Skin(Gdx.files.internal("dialog/dialogSkin.json"), textureAtlas);//////////////////////////
		
		stringLabel = new Label(selectionMessage, skin, "dialogScreen");
		float stringWidth = stringLabel.getTextBounds().width;
		stringLabel.setBounds(scrnWidth/2 - stringWidth/2, scrnHeight-50, stringLabel.getPrefWidth(), stringLabel.getPrefHeight());
		
		dialogLabel = new Label(dialog, skin, "dialogBox");
		dialogLabel.setBounds(200, 0, scrnWidth-200, scrnHeight/4);
		dialogLabel.setWrap(true);
		
		loadImages();
		
		stage.addActor(stringLabel);
		stage.addActor(dialogLabel);
		
		Gdx.input.setInputProcessor(new InputController(){
			public boolean keyUp(int keycode){
				switch(keycode){
				case Keys.ENTER:
					
					break;
				case Keys.ESCAPE:
					//((Game) Gdx.app.getApplicationListener()).setScreen(gamePlay);
					dispose();
					break;
				case Keys.RIGHT:
					moveImages(1);
					break;
				case Keys.LEFT:
					moveImages(-1);
					break;
				}
				return true;
			}
		});
		Gdx.input.setInputProcessor(stage);
	}
	
	private void loadImages(){
		for(int i = 0; i < totalFaces; i++){
			ImageButton image = new ImageButton(new SpriteDrawable(faceList.get(i)));
			//ImageButton image = new ImageButton(new SpriteDrawable(faceList.get(i)));
			image.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					testImageSelection((ImageButton)event.getListenerActor());
					dispose();
				}
			});
			
			float wdth = (scrnWidth/5)-10;
			float hght = 200;
			
			image.setBounds(10+(wdth*i), scrnHeight-(100+hght), wdth, hght);
			stage.addActor(image);
			buttons.add(image);
		}
	}
	
	private void moveImages(int dir){
		if(buttons.elementAt(0).getX() < (scrnWidth/2 - ((scrnWidth/5)-10)/2) &&
				buttons.elementAt(buttons.size()-1).getX() > (scrnWidth/2 - ((scrnWidth/5)-10)/2)){
			for(int i = 0; i < totalFaces; i++){
				buttons.elementAt(i).setX( (buttons.elementAt(i).getX()+(scrnWidth/5)-10)*dir ); 
			}
		}
	}
	
	private void testImageSelection(ImageButton button){
		System.out.println( ((SpriteDrawable)button.getImage().getDrawable()).getSprite() + " == " + faceList.get(questManager.getTargetIndex()));
		if( ((SpriteDrawable)button.getImage().getDrawable()).getSprite() == faceList.get(questManager.getTargetIndex()) ){
			gamePlay.testSuccess = true;
			System.out.println("correct");
		}
		else{
			gamePlay.testSuccess = false;
			System.out.println("wrong");
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
		((Game) Gdx.app.getApplicationListener()).setScreen(gamePlay);		
		//stage.dispose();
	}

}
