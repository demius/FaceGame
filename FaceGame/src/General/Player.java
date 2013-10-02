package General;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class Player extends Animated {

	public Player(Vector2 p, int c, int r) {
		super(p, c, r);
		// TODO Auto-generated constructor stub
	}

	
	public void Update(){

		Vector2 dir = Vector2.Zero;// direction the player moves in
		
		currentState = MovementState.standing;
		
		if(Gdx.input.isKeyPressed(Keys.A)){// move the player left
			dir = new Vector2(-1*movementSpeed,0);
			currentState = MovementState.left;
		}
		
		else if(Gdx.input.isKeyPressed(Keys.D)){// move the player right
			dir = new Vector2(1*movementSpeed,0);
			currentState = MovementState.right;
		}
		
		else if(Gdx.input.isKeyPressed(Keys.W)){// move the player up
			dir = new Vector2(0,-1*movementSpeed);
			currentState = MovementState.up;
		}
		
		if(Gdx.input.isKeyPressed(Keys.S)){// move the player down
			dir = new Vector2(0,1*movementSpeed);
			currentState = MovementState.down;
		}
	
		setCurrentFrame();
		
		if(currentFrame == 12)
			currentFrame = 9;
		
		if(Gdx.input.isKeyPressed(Keys.P)){// print the gridPosition
			System.out.println(this.gridPosition);
		}
	
		//System.out.println(boundingBox);
		this.moveInDirection(dir);
		
		
	}
}
