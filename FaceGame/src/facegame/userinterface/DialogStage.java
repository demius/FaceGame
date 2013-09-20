package facegame.userinterface;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import facegame.gameworld.NPC;
import facegame.quests.QuestManager;
import facegame.quests.QuestProgress;

public class DialogStage extends Stage{
	
	private QuestManager questManager;
	private QuestProgress progressHUD;
	private int scrnWidth, scrnHeight;
	private boolean inDialog, interactionAvailable;
	
	public Label dialogBoxLabel, dialogNextLabel, interactLabel;
	private SpriteBatch batch;

	private Stage imageStage;
	private Image arrow;
	private Image npcPortrait;
	
	public DialogStage(QuestManager questManager){
		super();
		this.questManager = questManager;
		
		scrnWidth = Gdx.graphics.getWidth(); 
		scrnHeight = Gdx.graphics.getHeight();
		
		batch = new SpriteBatch();
		
		initialize();
		
	}
	
	public void draw(float arrowRotation) {
		float delta = Gdx.graphics.getDeltaTime();
		
		batch.begin();
		if(inDialog){
			if(npcPortrait != null){
				npcPortrait.act(delta);
				npcPortrait.draw(batch, 1);
			}
			
			dialogBoxLabel.act(delta);
			dialogNextLabel.act(delta);
			dialogBoxLabel.draw(batch, 1);
			dialogNextLabel.draw(batch, 1);
			imageStage.act(delta);
			imageStage.draw();
			

		}
		else{
			imageStage.clear();
		}
		
		if(interactionAvailable && !inDialog){
			interactLabel.act(delta);
			interactLabel.draw(batch, 1);
		}
		
		if(!questManager.questsComplete()){
			arrow.setRotation(arrowRotation);
			arrow.act(delta);
			arrow.draw(batch, 1);
		}
		progressHUD.draw(batch);		
		batch.end();
	};
	
	public void initialize(){
		TextureAtlas textureAtlas = new TextureAtlas("dialog/dialog.pack");
		Skin skin = new Skin(Gdx.files.internal("dialog/dialogSkin.json"), textureAtlas);
		
		dialogBoxLabel = new Label("", skin, "dialogBox");
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
		
		imageStage = new Stage();
		
		arrow = new Image(new Sprite(new Texture("WorldTextures/arrow.png")));
		arrow.setBounds(0, scrnHeight - 100, 100, 100);
		arrow.setOrigin(50, 50);
		
		addActor(arrow);
		addActor(dialogBoxLabel);
		addActor(dialogNextLabel);
		addActor(interactLabel);
		progressHUD=new QuestProgress(questManager.getNumQuests());

	}
	
	public void resize(int width, int height){
		scrnWidth = width; 
		scrnHeight = height;
	}
	
	public void update(boolean inDial, boolean interAvail, NPC npc){
		inDialog = inDial;
		interactionAvailable = interAvail;
		if(npc != null){
			npcPortrait = new Image(npc.getNPCPortrait());
			npcPortrait.setBounds(0, 0, 200, 200);
		}
	}
	
	public void addFaces(){
		//Should get the face sprites here.
		ArrayList<FaceWrapper> faces = questManager.getNodeFaces();
		if(faces != null && faces.size() > 0){
			
			int size = faces.size();
			float xMult = -1/(float)(size%2+1);
			float separate = 20;
			float sepMult = -(size-1)*0.5f;
			float imageAspectRatio = faces.get(0).getSpriteDrawable().getSprite().getWidth()/
					faces.get(0).getSpriteDrawable().getSprite().getHeight();
			float imageY = scrnHeight/3;
			float imageX = imageAspectRatio*imageY;
			
			if(size%2 == 1)
				xMult *= size;
			else if(size%2 == 0)
				xMult *= size/2;
			
			for(int i = 0; i < size; i++){
				Image temp = new Image(faces.get(i).getSpriteDrawable());
				
				float tempX = scrnWidth/2 + xMult*imageX + sepMult*separate;
				System.out.println(sepMult);
				
				temp.setBounds(tempX, scrnHeight/2, imageX, imageY);
				imageStage.addActor(temp);
				
				xMult++;
				sepMult++;
			}
		}
	}
}
