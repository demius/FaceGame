package facegame.gameworld;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/* 
 * Base class for all on-screen objects used in the game*/

public class GameObject {

	Vector2 position;
	Rectangle boundingBox;// bounding box around this object
	Texture texture;// Texture used to represent this object on the screen
	Sprite sprite;// Sprite used to represent this object on screen
	
	/**
	 * Constructor creating a game object at p
	 * @param p top-left corner of GameObject sprite
	 */
	public GameObject(Vector2 p){
		
		this.position = new Vector2();
		this.position.x = p.x;
		this.position.y = p.y;
		
	}
	
	/**
	 * Load the content for the sprite graphic
	 */
	public void LoadContent(String path){
		// Set texture for this object
		texture = new Texture(Gdx.files.internal(path));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture.getTextureData().prepare();
		Texture.setEnforcePotImages(false);
		
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(texture);
		sprite.flip(false, true);
		
		// Set position of rectangle
		boundingBox = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
		//set the sprite bounds
		sprite.setBounds(position.x, position.y, texture.getWidth(), texture.getHeight());
	}

	
	/**
	 * Release the memory the texture is occupied when the game is closed
	 */
	public void Dispose(){
		this.texture.dispose();
	}

	/**
	 * Returns the most updated boundingBox
	 * @return Rectangle with position.x,position.y,texture.width,texture.height
	 */
	public Rectangle getBounds(){
		boundingBox.x = position.x;
		boundingBox.y = position.y;
		
		return boundingBox;
	}
	
	/**
	 * Returns the position of the object 
	 * @return Vector2 position
	 */
	public Vector2 getPosition(){
		Vector2 center = new Vector2(position.x + getBounds().width/2, position.y + getBounds().height/2);
		return center;
	}
	
	/**
	 * Setting the boundaries for the boundingBox around the GameObject
	 * @param b setting the boundingBox to b.x,b.y,b.width
	 */
	public void setBounds(Rectangle b){
		boundingBox.x = b.x;
		boundingBox.y = b.y;
		
		position.x = boundingBox.x;
		position.y = boundingBox.y;
		
		sprite.setBounds(position.x, position.y, texture.getWidth(), texture.getHeight());
	}
	
	/**Sets the position of the player to the vector passed in then updates the boudingBox
	 * position to the same position.
	 * @param b		bounds that the position is set to
	 */
	public void setPosition(Vector2 b){
		boundingBox.x = b.x;
		boundingBox.y = b.y;
		
		position.x = boundingBox.x;
		position.y = boundingBox.y;
		
		sprite.setBounds(position.x, position.y, texture.getWidth(), texture.getHeight());
	}
	
	/**
	 * Returns the sprite for the gameobject
	 * @return sprite object
	 */
	public Sprite getSprite(){
		return sprite;
	}
	
	/**
	 * Draw the sprite onto the screen
	 * @param batch SpriteBatch used to draw the the sprite on the screen
	 */
	public void Draw(SpriteBatch batch)	{
		sprite.draw(batch);
	}
	
	
	/**
	 * Used for debugging. System.out.println(GameObject) will print the
	 * boundingBox position and dimensions of the boundingBox.
	 */
	public String toString(){
		String temp = "";
		
		temp = temp + boundingBox;
		
		return temp;
	}
}
