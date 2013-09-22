package facegame.gameworld;

import com.badlogic.gdx.math.Vector2;

public class Moveable extends GameObject
{

	Vector2 oldPosition = new Vector2(0,0);
	public Vector2 gridPosition = new Vector2(0,0);
	
	public float movementSpeed = 3.0f;
	
	public Moveable(Vector2 p) {
		super(p);
	}
	
	
	/**
	 * Updates the position to the gridIndexValues
	 * Updates the boundingBox to the position
	 * Updates sprite bounds to the boundingBox values
	 */
	void UpdatePosition(){

		position = clampDesiredPosition(position);
		
		// update the boundingBox position
		boundingBox.x = position.x;
		boundingBox.y = position.y;
		
		// update the current GridPosition
		gridPosition.x = (int)(position.x/GridCollision.GRIDBLOCK);
		gridPosition.y = (int)(position.y/GridCollision.GRIDBLOCK);
		
		// update the sprite bounds
		sprite.setBounds(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
	}
	
	/**
	 * Move the object in the direction passed in
	 */
	public void moveInDirection(Vector2 delta){
		GridCollision.RemoveObject(this);//remove the object from all the blocks it's in
		oldPosition = new Vector2(position.x, position.y);// set old position
		position.x += delta.x;//update to new position
		position.y += delta.y;
		GridCollision.PlaceObject(this);//place object in the new grid blocks
	}
	
	/**
	 * Revert position to previous position
	 */
	public void revertPosition(){
		position.x = oldPosition.x;
		position.y = oldPosition.y;
	}
	
	
	/**
	 * Clamp desired position:
	 * - if its < min make it 0
	 * - if its > max set to max
	 * @param v vector to be clamped
	 */
	Vector2 clampDesiredPosition(Vector2 v){
		Vector2 clampValue = new Vector2(v);
		
		if(v.x >= GridCollision.GRID_WIDTH * GridCollision.GRIDBLOCK)
			clampValue.x = GridCollision.GRID_WIDTH  * GridCollision.GRIDBLOCK;
		if(v.x < 0)
			clampValue.x = 0;
		if(v.y >= GridCollision.GRID_HEIGHT * GridCollision.GRIDBLOCK)
			clampValue.y = GridCollision.GRID_HEIGHT *GridCollision.GRIDBLOCK ;
		if(v.y < 0)
			clampValue.y = 0;
		
		return clampValue;
	}
	
	/**
	 * Clamps the vector passed in to the bounds of the world defined by the
	 * gridwidth and grid height
	 */
	
	/**
	 * Sets the desired position of the object to the value passed in
	 * @param dp desired position
	 * @return true if the desired position is set, false if the space is occupied
	 */
	public boolean setDesiredPosition(Vector2 dp){
		
		Vector2 clamped = clampDesiredPosition(dp);
		
		return true;
	}

}
