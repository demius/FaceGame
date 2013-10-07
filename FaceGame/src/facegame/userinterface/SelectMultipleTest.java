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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import facegame.gameworld.GameWorld;
import facegame.quests.QuestManager;
import facegame.quests.RewardManager;

public class SelectMultipleTest implements Screen {

	int testType;
	private int totalFaces;
	
	private Stage stage;					
	private Label stringLabel, dialogLabel, dialogNextLabel;	
	private String selectionMessage = "Use the check buttons to select each face:", dialog; 	
	private float scrnWidth, scrnHeight;	
	private GameWorld gamePlay;	
	private ArrayList<FaceWrapper> faceList;
	private Vector<ImageSelection> faceZones;	
	private QuestManager questManager;
	private boolean allSelectionsChecked;
	
	public SelectMultipleTest(GameWorld game, QuestManager qm, String dialog, ArrayList<FaceWrapper> faces){
		allSelectionsChecked = false;
		gamePlay = game;
		questManager = qm;
		this.dialog = dialog;
		
		faceList = faces;
		Collections.shuffle(faceList);
		totalFaces = faceList.size();
		
		faceZones = new Vector<ImageSelection>();
		
		//System.out.println("slectMultiple: facelist size - " + faceList.size());
	}
	
	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		//Table.drawDebug(stage);
		
		/*batch.begin();
		for(ImageSelection is: faceZones) {
			ArrayList<Actor> actors = is.getActors();
			for (Actor actor : actors) {
				actor.act(delta);
				actor.draw(batch, 1);
			}
		}
		batch.end();*/
	}
	
	private void update(){
		//Tests if all the required check boxes are checked
		allSelectionsChecked = true;
		for(int i = 0; i< faceZones.size(); i++){
			if(!faceZones.elementAt(i).isOneChecked())
				allSelectionsChecked = false;
		}
		
		//Show or hide the dialogNextLabel
		if(allSelectionsChecked)
			dialogNextLabel.setVisible(true);
		else
			dialogNextLabel.setVisible(false);
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
		
		stage = new Stage();
		
		TextureAtlas textureAtlas = new TextureAtlas("dialog/dialog.pack");
		Skin skin = new Skin(Gdx.files.internal("dialog/dialogSkin.json"), textureAtlas);
		
		stringLabel = new Label(selectionMessage, skin, "dialogScreenLabel");
		float stringWidth = stringLabel.getTextBounds().width;
		stringLabel.setBounds(scrnWidth/2 - stringWidth/2, scrnHeight-50, 
				stringLabel.getPrefWidth(), stringLabel.getPrefHeight());
		
		dialogLabel = new Label(dialog, skin, "dialogBox");
		dialogLabel.setBounds(200, 0, scrnWidth-200, scrnHeight/4);
		dialogLabel.setAlignment(Align.top | Align.left);
		dialogLabel.setWrap(true);
		
		dialogNextLabel = new Label("", skin, "dialogNext");
		dialogNextLabel.setBounds(scrnWidth-50, 20, 32, 26);
		
		stage.addActor(stringLabel);
		stage.addActor(dialogLabel);
		stage.addActor(dialogNextLabel);
		
		loadImages();
		
		Gdx.input.setInputProcessor(controls());
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
			ImageSelection image = new ImageSelection(faceList.get(i));
			Table table = new Table();
			table = image.setBounds(scrnWidth/2f + xMult*imageX + sepMult*separate, scrnHeight/3f, imageX, imageY, 0.3f*imageY);
			stage.addActor(table);
			faceZones.add(image);
						
			xMult++;
			sepMult++;
		}
	}
	
	private InputMultiplexer controls(){
		return new InputMultiplexer(stage, new InputAdapter() {
			@Override
			public boolean keyUp(int keycode) {
				switch(keycode){
				case Keys.ENTER:
					if(allSelectionsChecked){
						if(testSelections()){
							gamePlay.testSuccess = true;
							RewardManager.awardReward(questManager.getQuest().getReward());
						}
						dispose();
					}
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

	private boolean testSelections() {
		boolean correct  = true;
		for(int i = 0; i < faceZones.size(); i++){
			ImageSelection is = faceZones.elementAt(i);
			if(is.checkSelection()){
				//System.out.println("Face " + i + " correct.");
				
			}
			else{
				//System.out.println("Face " + i + " not correct.");
				correct = false;
			}
		}
		return correct;
	}
	
	private void moveImages(int dir){
		float firstX = faceZones.elementAt(0).getX();
		float lastX = faceZones.elementAt(totalFaces-1).getX();
		float width = faceZones.elementAt(0).getWidth();
		
		if( (firstX < 0 && dir == 1) || (lastX + width > scrnWidth && dir == -1) ){
			for(int i = 0; i < totalFaces; i++){
				faceZones.elementAt(i).setX(faceZones.elementAt(i).getX() + (20 + width )*dir ); 
			}
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
		((Game) Gdx.app.getApplicationListener()).setScreen(gamePlay);
	}
}
