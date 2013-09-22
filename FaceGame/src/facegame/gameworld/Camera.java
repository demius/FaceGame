package facegame.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Camera extends OrthographicCamera
{
	Rectangle viewArea;// Rectangle with position and dimensions of Camera, used for frustum culling
	Rectangle shiftArea;// Shift the camera when the player reaches certain boundaries
	
	Vector2 desiredPosition = new Vector2();// Position camera always moves towards
	Vector2 direction = new Vector2();// Direction for camera to move when following the player
	Vector2 velocity = new Vector2();// Velocity for camera to move when following the player
	
	// the position of the shiftarea relative to the camera position
	int xOffset = 50;
	int yOffset = 50;
	
	public Camera(){
	}

	public Camera(float viewportWidth, float viewportHeight) {
		super(viewportWidth, viewportHeight);
		
		viewArea = new Rectangle(this.position.x, this.position.y, this.viewportWidth, this.viewportHeight);
		shiftArea = new Rectangle(xOffset,yOffset, 700, 380);
		// TODO Auto-generated constructor stub
	}

	public Camera(float viewportWidth, float viewportHeight, float diamondAngle) {
		super(viewportWidth, viewportHeight, diamondAngle);
		// initialize rectangle to camera position and dimensions
		
	}
	
	/**
	 * Updates the camera's position to follow the player object
	 */
	public void Update(Player player){
		desiredPosition = new Vector2(player.getPosition().x, player.getPosition().y);
		
		Vector2 delta = desiredPosition.sub(this.position.x, this.position.y);
		
		moveInDirection(delta);
		
		if(Gdx.input.isKeyPressed(Keys.K)){
			System.out.println("ShitArea:"+shiftArea);
			System.out.println("Player Position:" + player.getPosition());
			System.out.println("Camera position:" + this.position);
		}
			
		this.update();
	}
	
	
	
	/**
	 * get shift area
	 */
	public Rectangle getShiftArea(){
		Rectangle flipped = new Rectangle(shiftArea.x, 480 - shiftArea.height - shiftArea.y, shiftArea.width, shiftArea.height);
		return flipped;
	}
	
	/**
	 * Move in direction
	 */
	void moveInDirection(Vector2 delta){
		this.position.x += delta.x;
		this.position.y += delta.y;
	}

}
