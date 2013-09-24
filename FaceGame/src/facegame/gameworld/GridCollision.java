package facegame.gameworld;

import java.util.Vector;

import General.GameObject;
import General.Moveable;
import General.NPC;
import General.Player;
import General.SolidObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GridCollision {

	// global variables indicating the entire world dimensions in number of blocks
	public static int GRID_WIDTH;
	public static int GRID_HEIGHT;
	
	public static int GRIDBLOCK = 64;// size of every movement block
	
	static Vector<GameObject> movementGrid [][] = null;// Grid used for the player to move on
		
	ShapeRenderer shapeRenderer = new ShapeRenderer();// used for drawing the rectangle bounding boxes of the shapes
	public GridCollision(int gridWidth, int gridHeight){
		GRID_WIDTH = gridWidth;
		GRID_HEIGHT = gridHeight;
	}
	
	/**
	 * Initialize the movementGrid to the parameters defined for the grid
	 */
	public void Initialize(){
		movementGrid = new Vector[GRID_HEIGHT][GRID_WIDTH];// initialize movement grid
		
		for(int y = 0; y < GRID_HEIGHT;y++){
			for(int x = 0; x < GRID_WIDTH; x++){
				movementGrid[y][x] = new Vector<GameObject>(); 
			}
		}
	}
	
	/**
	 * Places a game object on the grid.
	 * Checking all four corners and then placing it in the block the corner is in
	 * @param o Game Object that needs to be placed
	 */
	public static void PlaceObject(GameObject o){
				
		for(float y = Math.max(0,o.getBounds().y); y < Math.min(o.getBounds().y + o.getBounds().height, GRID_HEIGHT*GRIDBLOCK); y++)
			for(float x = Math.max(0,o.getBounds().x); x< Math.min(o.getBounds().x + o.getBounds().width, GRID_WIDTH*GRIDBLOCK); x++)
				// place the object in the grid
				if(!movementGrid[(int)Math.floor(y/GRIDBLOCK)][(int)Math.floor(x/GRIDBLOCK)].contains(o))
					movementGrid[(int)Math.floor(y/GRIDBLOCK)][(int)Math.floor(x/GRIDBLOCK)].add(o);
	}
	
	/**
	 * Removes the object from the current position using the identity of the
	 * GameObject passed in
	 * @param o GameObject that needs to be removed from the grid
	 */
	public static void RemoveObject(GameObject o){
		
		for(float y = Math.max(0,o.getBounds().y); y < Math.min(o.getBounds().y + o.getBounds().height, GRID_HEIGHT*GRIDBLOCK); y++)
			for(float x = Math.max(0,o.getBounds().x); x< Math.min(o.getBounds().x + o.getBounds().width, GRID_WIDTH*GRIDBLOCK); x++)
					movementGrid[(int)Math.floor(y/GRIDBLOCK)][(int)Math.floor(x/GRIDBLOCK)].remove(o);
	}
	
	/**
	 * Clears the entire cell, making the size 0
	 * @param x index in 2D array
	 * @param y index in 2D array
	 */
	public void ClearCell(int x, int y){
		x = clampX(x);
		y = clampY(y);
		movementGrid[y][x].clear();
	}
	
	/**
	 * Checks if the desired position is occupied
	 * If it's occupied it returns false, if the player can move
	 * true is returned instead.
	 * @return boolean value indicating if the player can move
	 */
	public boolean CanMove(int x, int y){
		x = clampX(x);
		y = clampY(y);
		
		if(movementGrid[y][x].size() > 0)
			if(movementGrid[y][x].elementAt(0) instanceof SolidObject)
				return false;
		
		return true;
	}
	
	/**
	 * Clamps the x value to the bounds of the grid
	 * @param x value to be clamped
	 * @return integer clamped value
	 */
	int clampX(int x){
		if(x < 0)
			x=0;
		if(x >= GRID_WIDTH)
			x = GRID_WIDTH-1;
		
		return x;
	}
	int clampY(int y){
		if(y < 0)
			y = 0;
		if(y >= GRID_HEIGHT)
			y = GRID_HEIGHT-1;
		
		return y;
	}
	
	/**
	 * Updates the movementGrid as the moveable objects move around
	 */
	public String Update(Moveable o){
		String name = null;
		
		for(float y = o.getBounds().y; y < o.getBounds().y + o.getBounds().height; y++)
		{
			for(float x = o.getBounds().x; x< o.getBounds().x + o.getBounds().width; x++){
				
				
				
				for(int i = 0; i < movementGrid[(int)(y/GRIDBLOCK)][(int)(x/GRIDBLOCK)].size(); i++){
					GameObject g = movementGrid[(int)(y/GRIDBLOCK)][(int)(x/GRIDBLOCK)].elementAt(i);

					if(g instanceof SolidObject && o instanceof Player){
						
						//System.out.println("xGRid:" + (int)(x/GRIDBLOCK) + " yGrid:" + (int)(y/GRIDBLOCK));
						
						if(g.getBounds().overlaps(o.getBounds())){
							o.revertPosition();
						}
					}
					
					if(o instanceof NPC){
						if(g instanceof SolidObject) {
							((NPC)o).revertPosition();
						}
						if(g instanceof Player){
							GameWorld.interactionAvailable = true;
							//return the name of the npc for quest
							 GameWorld.npcName = ((NPC)o).name;
							 ((NPC)o).defaultDelta();
						}
					}
					
					
				}
		}
		}
		
		return name;
	}

	/**
	 * Draw all the objects currently in the collision grid
	 */
	public void Draw(SpriteBatch batch){
		for(int y = 0; y < GRID_HEIGHT; y++){
			for(int x = 0; x < GRID_WIDTH; x++){
				for(int i = 0; i < movementGrid[y][x].size(); i++){
					
					movementGrid[y][x].elementAt(i).Draw(batch);
					
					
				}
			}
		}
		
	}
	
	
	/**
	 * Prints out the entire grid as a string when the object is used as a 
	 * String parameter
	 */
	public String toString(){
		String temp = "";
		for(int y = 0; y < GRID_HEIGHT; y++){
			for(int x = 0; x < GRID_WIDTH; x++){
				temp += movementGrid[y][x].size() + " ";
			}
			temp += "\n";
		}
		
		return temp;
	}
	
}
