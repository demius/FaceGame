package facegame.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class Player extends Moveable {

	public Player(Vector2 p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	
	public void Update(){
		Vector2 dir = Vector2.Zero;// direction the player moves in
		
		if(Gdx.input.isKeyPressed(Keys.A)){// move the player left
			dir = new Vector2(-1*movementSpeed,0);
		}
		
		else if(Gdx.input.isKeyPressed(Keys.D)){// move the player right
			dir = new Vector2(1*movementSpeed,0);
		}
		
		else if(Gdx.input.isKeyPressed(Keys.W)){// move the player up
			dir = new Vector2(0,-1*movementSpeed);
		}
		
		else if(Gdx.input.isKeyPressed(Keys.S)){// move the player down
			dir = new Vector2(0,1*movementSpeed);
		}
		
		if(Gdx.input.isKeyPressed(Keys.P)){// move the player down
			System.out.println(this.gridPosition);
		}
	
		//System.out.println(boundingBox);
		this.moveInDirection(dir);		
		
	}
}
