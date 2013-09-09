package facegame.gameworld;

import java.util.Vector;

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
	
	Vector2 originalPosition = null;
	Vector2 desiredPosition = null;
	Vector2 direction = null;
	
	public NPC(Vector2 p, int type) {
		super(p);
		// store the original grid position. The NPC only moves from its original position to the others in the list
		originalPosition = new Vector2(p.x, p.y);
		
		// set the NPC movement pattern according to the int passed in
		switch(type){
		case 0: 
			movementType = MovementType.standing; 
			direction = new Vector2(0,0);
			desiredPosition = new Vector2(p.x, p.y);
			break;
		case 1: 
			movementType = MovementType.lineH; 
			direction = new Vector2(1,0); 
			desiredPosition = new Vector2(p.x + 10, p.y);
			break;
		case 2:
			movementType = MovementType.lineV; 
			direction = new Vector2(0,1); 
			desiredPosition = new Vector2(p.x, p.y + 10);
			break;
		case 3: 
			movementType = MovementType.square; 
			direction = new Vector2(0,0); 
			desiredPosition = new Vector2(p.x + 10, p.y);
			break;
		}
		// TODO Auto-generated constructor stub
	}

	Vector<Vector2> lineV = null;
	
	public void populateVectors(){
		
	}
	
	/**
	 * Switch the direction that the NPC is moving in
	 */
	public void switchDirection(){
		direction.x *= -1;
		direction.y *= -1;
		//System.out.println("Switch!!!");
	}
	
	public void Update(){		
		moveInDirection(direction);
		UpdatePosition(); 
	}
	
	
	/**
	 * Moves to the desired target
	 */
	void moveTo(){
		Vector2 delta = new Vector2(desiredPosition.x - originalPosition.x, desiredPosition.y - originalPosition.y);		
		if(delta.x < 2 && delta.x > -2)
		{
			Vector2 temp = new Vector2(desiredPosition);
			
			desiredPosition.x = originalPosition.x;
			desiredPosition.y = originalPosition.y;
			
			originalPosition.x = temp.x;
			originalPosition.y = temp.y;
		}
		
		delta = delta.nor();// normalize the vector
		
		moveInDirection(delta);
			
	}

}
