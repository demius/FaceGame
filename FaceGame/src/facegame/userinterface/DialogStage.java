package facegame.userinterface;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import facegame.quests.QuestManager;

public class DialogStage extends Stage{
	
	private QuestManager questManager;
	private Vector<Label> faceLabels;
	
	private int scrnWidth, scrnHeight;
	private boolean inDialog, interactionAvailable;
	
	public Label dialogBoxLabel, dialogNextLabel, interactLabel;
	private SpriteBatch batch;
	
	public DialogStage(QuestManager questManager){
		super();
		this.questManager = questManager;
		
		scrnWidth = Gdx.graphics.getWidth(); 
		scrnHeight = Gdx.graphics.getHeight();
		
		batch = new SpriteBatch();
		
		initialize();
		
	}
	
	@Override
	public void draw() {
		float delta = Gdx.graphics.getDeltaTime();
		
		batch.begin();
		if(inDialog){
			dialogBoxLabel.act(delta);
			dialogNextLabel.act(delta);
			dialogBoxLabel.draw(batch, 1);
			dialogNextLabel.draw(batch, 1);
		}
		
		if(interactionAvailable && !inDialog){
			interactLabel.act(delta);
			interactLabel.draw(batch, 1);
		}
		
		batch.end();
	};
	
	public void initialize(){
		TextureAtlas textureAtlas = new TextureAtlas("dialog/dialog.pack");//////////////////////////////////
		Skin skin = new Skin(Gdx.files.internal("dialog/dialogSkin.json"), textureAtlas);//////////////////////////
		
		dialogBoxLabel = new Label("", skin, "dialogBox");////////////////////////////////////////
		dialogBoxLabel.setBounds(200, 0, scrnWidth-200, scrnHeight/4);
		dialogBoxLabel.setAlignment(Align.top | Align.left);
		dialogBoxLabel.setWrap(true);
		
		dialogNextLabel = new Label("", skin, "dialogNext");
		dialogNextLabel.setBounds(scrnWidth-50, 20, 32, 26);				
		
		interactLabel = new Label("Press [Enter] to interact", skin, "dialogBegin");
		float interactLabelWidth = interactLabel.getTextBounds().width;
		float interactLabelHeight = interactLabel.getTextBounds().height;
		interactLabel.setBounds(scrnWidth-(interactLabelWidth+50+10), scrnHeight-(interactLabelHeight*2+10), 
				interactLabelWidth+50, interactLabelHeight*2);
		interactLabel.setAlignment(Align.center);
		interactLabel.setWrap(true);
		
		addActor(dialogBoxLabel);
		addActor(dialogNextLabel);
		addActor(interactLabel);
	}
	
	public void resize(int width, int height){
		scrnWidth = width; 
		scrnHeight = height;
	}
	
	public void update(boolean inDial, boolean interAvail){
		inDialog = inDial;
		interactionAvailable = interAvail;
	}
}
