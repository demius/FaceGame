package General;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Animated extends Moveable{

	int columns, rows;
	public int currentFrame;
	TextureRegion [] region;
	public MovementState currentState = MovementState.right;
	
	int defaultStanding = 0;
	
	int frames = 0; 
	
	public enum MovementState { standing,left,right,up,down }
	
	public Animated(Vector2 p, int c, int r){
		super(p);
		columns = c;
		rows = r;
		currentFrame = 3;
		region = new TextureRegion[columns * rows];// initialize the number of regions
	}
	
	/**Checks the state of the movement and then sets the current frame
	 * to the appropriate index, to animate the sprite
	 */
	public void setCurrentFrame(){
		frames++;
		
		if(frames%3 == 0)
			currentFrame++;
		
		switch(currentState){
		
		case standing:
			currentFrame = defaultStanding;
			break;
		
		case left:
			defaultStanding = 9;
			if(currentFrame >= 12)
				currentFrame = 9;
			break;
		
		case right:
			defaultStanding = 3;
			if(currentFrame >= 6)
				currentFrame = 3;
			break;
		
		case up:
			defaultStanding = 6;
			if(currentFrame >= 9)
				currentFrame = 6;
			break;
		
		case down:
			defaultStanding = 0;
			if(currentFrame >= 3)
				currentFrame = 0;
			break;
		
		}
		
		// reset the frames to 0
		if(frames == 60)
			frames = 0;
	}
	
	/**Set the movementState according to the direction vector d
	 * @param d		Direction that the object moves
	 */
	public void setMovementState(Vector2 d){
		if(d.x > 0 && d.y == 0)
			currentState = MovementState.right;
		
		if(d.x < 0 && d.y == 0)
			currentState = MovementState.left;
		
		if(d.x == 0 && d.y > 0)
			currentState = MovementState.down;
		
		if(d.x == 0 && d.y < 0)
			currentState = MovementState.up;
		
		if(d.x == 0 && d.y == 0)
			currentState = MovementState.standing;
	}
	
	/**
	 * Load a png file into the Texture from the path defined by path
	 * @param path		String path
	 */
	public void LoadContent(String path){
		// Set texture for this object
		texture = new Texture(Gdx.files.internal(path));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture.getTextureData().prepare();
		Texture.setEnforcePotImages(false);

		for(int y = 0; y < rows; y++){
			for(int x = 0; x < columns; x++){
				//System.out.println("Region x:"+x * texture.getWidth()/columns+ "Region y:" + y * texture.getHeight()/rows + "region width:" + texture.getWidth()/columns + " region height:" + texture.getHeight()/rows);
				region[y*rows + x] = new TextureRegion(texture,x * texture.getWidth()/columns, y * texture.getHeight()/rows, texture.getWidth()/columns, texture.getHeight()/rows);
			}
			//System.out.println();
		}
				
		// Set position of rectangle
		boundingBox = new Rectangle(position.x, position.y, region[0].getRegionWidth(), region[0].getRegionHeight());
	}
	
	
	/**
	 * Draw the sprite onto the screen by using the current region frame as the
	 * image that is being drawn
	 * @param batch SpriteBatch used to draw the the sprite on the screen
	 */
	public void Draw(SpriteBatch batch)	{
		sprite = new Sprite(region[currentFrame]);// create sprite from the region at currentFrame
		sprite.setBounds(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
		sprite.flip(false, true);
		//System.out.println("BoundingBox:" + boundingBox);
		sprite.draw(batch);
	}
}
