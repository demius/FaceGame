package facegame.gameworld;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class GameWorld 
{
	// global variables indicating the entire world dimensions in number of blocks
	public static int WORLD_WIDTH = 10;
	public static int WORLD_HEIGHT = 15;

	GridCollision collision = null;
	
	Player player = null;
	NPC npc = null;
	
	GameObject grass = null;// Grass tiled under all the objects in the game world
	
	public GameWorld(){
		Initialize();
	}
	
	/**
	 * Initializes the movementGrid and set it all to 0
	 * initialize the player object
	 */
	public void Initialize(){

		player = new Player(new Vector2(2 * GridCollision.GRIDBLOCK,2 * GridCollision.GRIDBLOCK));// initialize the players position
		npc = new NPC(new Vector2(1 * GridCollision.GRIDBLOCK,2 * GridCollision.GRIDBLOCK), 2);
		
		grass = new GameObject(new Vector2(0,0));
		
		collision = new GridCollision(WORLD_WIDTH, WORLD_HEIGHT);// create collision grid
		collision.Initialize();// Initialize grid
		
	}
	
	/**
	 * Load the content of all the objects used in the game world
	 * into memory.
	 */
	public void LoadContent(){
		player.LoadContent("PlayerTextures/player.png");
		npc.LoadContent("PlayerTextures/npc.png");
		
		grass.LoadContent("WorldTextures/grass.jpg");
		
		LoadMap("map1.txt");
	}
	
	/**
	 * Takes in the keys pressed and updates the world
	 */
	public void Update(Camera cam){
		player.Update();
		
		cam.Update(player);
		
		collision.Update(player);
		
		if(Gdx.input.isKeyPressed(Keys.P)){// move the player right
			System.out.println(collision);
		}
	}
	

	
	/**
	 * Draw all the objects currently on screen in the game world
	 */
	public void Draw(SpriteBatch batch){
		// Get the grass sprite and draw it over and over and over
		Sprite grassSprite;		
		for(int y = -5; y < 5; y++){
			for(int x = -5;x < 5; x++){
				grassSprite = grass.getSprite();
				grassSprite.setBounds(x * 256, y * 256, 256, 256);
				grassSprite.draw(batch);
			}
		}
		
		player.Draw(batch);
		npc.Draw(batch);
		collision.Draw(batch);	
	}
	
	
	/**
	 * Dispose of all the objects in the game world when its done being used
	 */
	public void Dispose(){
		player.Dispose();
	}
	
	
	//*****************************************************************************
	//******************************MAPLOADER**************************************
	/**
	 * Reads in a text file and loads all the indicated objects into the collision grid
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	void LoadMap(String mapName){
		BufferedReader inputStream = null;
		
		inputStream = new BufferedReader (new InputStreamReader(Gdx.files.internal("data/"+mapName).read()));
		//inputStream = new Scanner(new FileInputStream(mapName));
		
		String tempLine = "";
		int y = 0;
		// when not at end of text file
		try {
			while(inputStream.ready()){
				try {
					tempLine = inputStream.readLine();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// get next line
				
				String [] tempSplit = tempLine.split(" ");// split the line at space
				
				for(int x = 0;x < tempSplit.length; x++){
					
					switch(Integer.parseInt(tempSplit[x]))
					{
					case 1:
						SolidObject s = new SolidObject(new Vector2(x*GridCollision.GRIDBLOCK,y*GridCollision.GRIDBLOCK));
						s.LoadContent("WorldTextures/trees.png");
						collision.PlaceObject(s);// place object on the grid
						break;
					}
					
				}
				
				y++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
