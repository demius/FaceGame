package facegame.quests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class QuestProgress{

	//private Label questProgress;
	private int totalquests;
	private static int currentquest=1;
	private final float SPACING=15;
	Image redOrb;
	Image greenOrb;
	Image orangeOrb;
	int positioning;
	
	
	
	public QuestProgress(int totalquests){
		this.totalquests=totalquests;
		
				
		redOrb=new Image(new Texture(Gdx.files.internal("HUD/unfinished_quest.png")));
		redOrb.setWidth(30);
		redOrb.setHeight(30);
		
		greenOrb=new Image(new Texture(Gdx.files.internal("HUD/finished_quest.png")));
		greenOrb.setWidth(30);
		greenOrb.setHeight(30);
		
		orangeOrb=new Image(new Texture(Gdx.files.internal("HUD/current_quest.png")));
		orangeOrb.setWidth(30);
		orangeOrb.setHeight(30);
		
		
	}
	
	public static void updateProgress(int currentquestval){
		currentquest=currentquestval;
		
	}
	
	public void draw(SpriteBatch batch){
	    
		positioning=0;
		for(int i=0;i<currentquest-1;i++)
		{   
			greenOrb.setPosition(20+positioning,20);
			greenOrb.draw(batch, 1);
			positioning+=SPACING;
		}
		if(currentquest<=totalquests){
		orangeOrb.setPosition(20+positioning,20);
		orangeOrb.draw(batch, 1);
		positioning+=SPACING;
		
		for(int i=0;i<totalquests-currentquest;i++){
			redOrb.setPosition(20+positioning,20);
			redOrb.draw(batch, 1);
			positioning+=SPACING;
		}
		}
	}
	
	
	
}
