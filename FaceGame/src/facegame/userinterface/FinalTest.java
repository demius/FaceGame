package facegame.userinterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import facegame.gameworld.GameWorld;
import facegame.quests.QuestManager;
import facegame.quests.RewardManager;

public class FinalTest implements Screen {

	int testType;
	private int totalFaces;
	
	private Stage stage;					// contains the labels and image buttons in the class
	private Label stringLabel, dialogLabel;	// will contain the text that will be displayed above the image buttons
	private String selectionMessage = "Click on the image to make a selection:", dialog; 
	
	private float scrnWidth, scrnHeight;
	
	private GameWorld gamePlay;
	
	private ArrayList<FaceWrapper> faceList;
	private Vector<ImageButton> buttons;
	
	private QuestManager questManager;
	
	/**
	 * @param testType indicates which test it is, identify the new face(0), identify the old face(1)
	 * @param totalFaces is the number of face used within this one quest. Maximum of 20 faces as defined in the quest creator
	 */
	public FinalTest(GameWorld game, QuestManager qm, String dialog, ArrayList<FaceWrapper> faces){
		gamePlay = game;
		questManager = qm;
		this.dialog = dialog;
		
		faceList = faces;
		Collections.shuffle(faceList);
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
		
		TextureAtlas textureAtlas = new TextureAtlas("dialog/dialog.pack");
		Skin skin = new Skin(Gdx.files.internal("dialog/dialogSkin.json"), textureAtlas);
		
		stringLabel = new Label(selectionMessage, skin, "dialogScreenLabel");
		float stringWidth = stringLabel.getTextBounds().width;
		stringLabel.setBounds(scrnWidth/2 - stringWidth/2, scrnHeight-50, stringLabel.getPrefWidth(), stringLabel.getPrefHeight());
		
		dialogLabel = new Label(dialog, skin, "dialogBox");
		dialogLabel.setBounds(200, 0, scrnWidth-200, scrnHeight/4);
		dialogLabel.setAlignment(Align.top | Align.left);
		dialogLabel.setWrap(true);
		
		loadImages();
		
		stage.addActor(stringLabel);
		stage.addActor(dialogLabel);
		
		Gdx.input.setInputProcessor(controls());
	}
	
	public InputMultiplexer controls(){
		return new InputMultiplexer(stage, new InputAdapter() {
			@Override
			public boolean keyUp(int keycode) {
				switch(keycode){
				case Keys.ENTER:
					
					break;
				case Keys.ESCAPE:
					dispose();
					break;
				case Keys.RIGHT:
					moveImages(-1);
					break;
				case Keys.LEFT:
					moveImages(1);
					break;
				}
				return true;
			}
		});
	}
	
	private void loadImages(){
		int size = faceList.size();
		float xMult = -1/(float)(size%2+1);
		float separate = 20;
		float sepMult = -(size-1)*0.5f;
		float imageAspectRatio = faceList.get(0).getSpriteDrawable().getSprite().getWidth()/
				faceList.get(0).getSpriteDrawable().getSprite().getHeight();
		float imageY = scrnHeight/2.5f;
		float imageX = imageAspectRatio*imageY;
		
		if(size%2 == 1)
			xMult *= size;
		else if(size%2 == 0)
			xMult *= size/2;

		for(int i = 0; i < totalFaces; i++){
			FaceImageButton image = new FaceImageButton(faceList.get(i).getSpriteDrawable(), faceList.get(i).getUniqueIndex());
			image.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					testImageSelection( ((FaceImageButton)event.getListenerActor()).getIndex() );
					dispose();
				}
			});

			image.setBounds(scrnWidth/2 + xMult*imageX + sepMult*separate, scrnHeight/3, imageX, imageY);
			stage.addActor(image);
			buttons.add(image);
			
			xMult++;
			sepMult++;
		}
	}
	
	private void moveImages(int dir){
		float firstX = buttons.elementAt(0).getX();
		float lastX = buttons.elementAt(totalFaces-1).getX();
		float width = buttons.elementAt(0).getWidth();
		
		if( (firstX < 0 && dir == 1) || (lastX + width > scrnWidth && dir == -1) ){
			for(int i = 0; i < totalFaces; i++){
				buttons.elementAt(i).setX(buttons.elementAt(i).getX() + (20 + width )*dir ); 
			}
		}
	}
	
	private void testImageSelection(int faceIndex){
		System.out.println(faceIndex + " == " + questManager.getTargetFace().getUniqueIndex());
		if(faceIndex == questManager.getTargetFace().getUniqueIndex()){
			gamePlay.testSuccess = true;
			RewardManager.awardReward(questManager.getQuest().getReward());
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
