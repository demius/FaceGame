package facegame.gameworld;

import java.util.Vector;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Dane Mackier
 * The NPC can have 4 types of movement,
 * Standing. Stands still in one position
 * moves vertical or horizontal in a straight line
 * moves in a square to 4 different corners
 */
public class NPC extends Moveable
{	
	// The movement type the currentNPC is
	public enum MovementType {
		
		standing,
		lineV,
		lineH,
		square;
	};
	
	public MovementType movementType = MovementType.standing;
	Vector<Vector2> destinations = null;
	Vector2 originalPosition = null;
	Vector2 desiredPosition = null;
	Vector2 direction = null;
	String name;
	int moveToIndex = 0;
	int movementLength;
	
	
	
	Sprite portrait;
	String defaultDialog;
	
	/**Sets all the variables used to operate an npc
	 * 
	 * @param p			Postion of the NPC
	 * @param type		Type refers to the movement of the NPC
	 * @param n			The name of the NPC
	 * @param l			Distance the NPC moves between its movement points
	 */
	public NPC(Vector2 p, int type, String n, int l, String defaultD) {
		super(p);
		// store the original grid position. The NPC only moves from its original position to the others in the list
		originalPosition = new Vector2((int)(p.x/GridCollision.GRIDBLOCK), (int)(p.y/GridCollision.GRIDBLOCK));
		name = n;
		destinations = new Vector<Vector2>();
		defaultDialog = defaultD;		
		movementLength = l + 1;
		
		// set the NPC movement pattern according to the int passed in
		switch(type){
		case 0: 
			movementType = MovementType.standing; 
			break;
		case 1: 
			movementType = MovementType.lineH; 
			destinations.add(new Vector2(originalPosition.x + movementLength, originalPosition.y));
			destinations.add(new Vector2(originalPosition.x, originalPosition.y));
			break;
		case 2:
			movementType = MovementType.lineV;
			destinations.add(new Vector2(originalPosition.x, originalPosition.y + movementLength));
			destinations.add(new Vector2(originalPosition.x, originalPosition.y));
			break;
		case 3: 
			movementType = MovementType.square;
			destinations.add(new Vector2(originalPosition.x + movementLength, originalPosition.y));
			destinations.add(new Vector2(originalPosition.x + movementLength, originalPosition.y + movementLength));
			destinations.add(new Vector2(originalPosition.x, originalPosition.y + movementLength));
			destinations.add(new Vector2(originalPosition.x, originalPosition.y));
			break;
		}
		// TODO Auto-generated constructor stub
	}
	
	/** Returns the name of the npc specified in the constructor
	 * @return		Returns the name of the NPC 
	 */
	public String getName(){
		return name;
	}
	
	/**Loads the npc's portait from the path specified
	 * @param 		filename of the image that contains the npc portrait
	 */
	public void loadPortrait(String filename){
		portrait = new Sprite(new Texture("NPC/"+filename+".png"));
	}
		
	/**Returns the default dialog for this NPC
	 * @return		defaultDialog for the npc
	 */
	public String getDefaultDialog(){
		return defaultDialog;
	}
	
	/** Returns the NPC Portrait
	 * @return NPC Portrait Sprite
	 */
	public Sprite getNPCPortrait(){
		return portrait;
	}
		
	
	/**Updates the NPC
	 */
	public void Update(){
		//System.out.println("destination("+ moveToIndex +"):"+destinations.elementAt(moveToIndex) + " gp:" + gridPosition);
		//System.out.println("NPC position:" + this.position);
		if(destinations.size() > 0){
			moveTo();
			
			
			
			if(destinations.elementAt(moveToIndex).x == gridPosition.x && destinations.elementAt(moveToIndex).y == gridPosition.y){
			moveToIndex++;
			if(moveToIndex == destinations.size())
				moveToIndex = 0;
			}
			
			UpdatePosition(); 
		}
	}
	
	
	/**
	 * Moves to the desired target
	 */
	void moveTo(){
		//System.out.println("Desired position: "+desiredPosition);
		//System.out.println("Grid position: "+gridPosition);
		
		Vector2 delta = new Vector2(destinations.elementAt(moveToIndex).x - gridPosition.x, destinations.elementAt(moveToIndex).y - gridPosition.y);		
		//System.out.println("dx:"+destinations.elementAt(moveToIndex).x +" gx:"+gridPosition.x);
		//System.out.println("dy:"+destinations.elementAt(moveToIndex).y +" gy:"+gridPosition.y);
		//System.out.println("Delta:" + delta);
		delta.nor();
		moveInDirection(delta);
			
	}

}
