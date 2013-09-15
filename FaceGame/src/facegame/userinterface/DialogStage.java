package facegame.userinterface;

import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import facegame.quests.QuestManager;

public class DialogStage extends Stage{
	
	private QuestManager questManager;
	private Vector<Label> faceLabels;
	
	public DialogStage(QuestManager questManager){
		super();
		this.questManager = questManager;
	}
	
	public void update(){
		
	}
}
