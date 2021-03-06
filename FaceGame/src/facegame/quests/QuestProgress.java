package facegame.quests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Class to monitor progression through quests and display orb HUD elements
 * @author Grant
 *
 */
public class QuestProgress{

	//private Label questProgress;
	private int totalquests;
	private static int currentquest=1;
	private final float SPACING=20;
	Image redOrb;
	Image greenOrb;
	Image orangeOrb;
	int positioning;
	
	
	
	public QuestProgress(int totalquests){
		this.totalquests=totalquests;
		
				
		redOrb=new Image(new Texture(Gdx.files.internal("HUD/unfinished_quest_orb.png")));
		redOrb.setWidth(30);
		redOrb.setHeight(30);
		
		greenOrb=new Image(new Texture(Gdx.files.internal("HUD/finished_quest_orb.png")));
		greenOrb.setWidth(30);
		greenOrb.setHeight(30);
		
		orangeOrb=new Image(new Texture(Gdx.files.internal("HUD/current_quest_orb.png")));
		orangeOrb.setWidth(30);
		orangeOrb.setHeight(30);
		
		
	}
	/**
	 * Set the current quest to the first one
	 */
	public static void initialize(){
		currentquest=1;
	}
	
	/**
	 * Set the current quest value to the parameter supplied
	 * @param currentquestval
	 */
	public static void updateProgress(int currentquestval){
		currentquest=currentquestval;
		
	}
	
	public void draw(SpriteBatch batch){
	    
		positioning=0;
		for(int i=0;i<currentquest-1;i++)
		{   
			greenOrb.setPosition(20+positioning,17);
			greenOrb.draw(batch, 1);
			positioning+=SPACING;
		}
		if(currentquest<=totalquests){
		orangeOrb.setPosition(20+positioning,17);
		orangeOrb.draw(batch, 1);
		positioning+=SPACING;
		
		for(int i=0;i<totalquests-currentquest;i++){
			redOrb.setPosition(20+positioning,17);
			redOrb.draw(batch, 1);
			positioning+=SPACING;
		}
		
		}
	}
	
	
	
}
