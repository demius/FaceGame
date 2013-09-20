package facegame.gameworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import facegame.quests.QuestManager;
import facegame.userinterface.DialogStage;
import facegame.userinterface.FinalTest;
import facegame.userinterface.MainMenu;

public class IngamePlay implements Screen {

	private Box2DDebugRenderer debugRenderer;
	private Camera camera;
	private QuestManager questManager;
	
	static String npcName; // stores the name of the NPC closest to the player
	
	// global variables indicating the entire world dimensions in number of blocks
	public static int WORLD_WIDTH;
	public static int WORLD_HEIGHT;

	GridCollision collision = null;
	
	Player player = null;
	
	ShapeRenderer shapeRenderer = new ShapeRenderer();
	// TODO take out all the shaperender stuff in final code, it slows down the framerate
	
	// List containing all the npc's in the gameWorld
	Vector<NPC> npcList = new Vector<NPC>();
	
	GameObject grass = null;// Grass tiled under all the objects in the game world
	GameObject interactionPrompt = null;
	
	//Moveable target = null;
	
	public static boolean interactionAvailable = false;// indicates if an interaction with a object is available
	public static boolean interacting = false;
	
	SpriteBatch batch;
	Sprite arrow;
		
	private DialogStage dialogStage;
	
	Vector2 targetPosition = null;// next target the player has to go to
	
	private boolean inDialog, dialogComplete;
	
	private IngamePlay gamePlayScreen;
	
	public IngamePlay(){
		gamePlayScreen = this;
		
		arrow = new Sprite(new Texture("WorldTextures/arrow.png"));
		arrow.setBounds(0, 0, 100, 100);
		
		batch = new SpriteBatch();
		debugRenderer = new Box2DDebugRenderer();
		
		camera = new Camera(1, Gdx.graphics.getHeight()/Gdx.graphics.getWidth());
		camera.setToOrtho(true,800,480);
		
		Initialize();
		LoadContent();
		controlListener();
		
		dialogStage = new DialogStage(questManager);
						
		inDialog = false;
		dialogComplete = true;
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		
		Update();
		
		batch.begin();
		
		Draw(delta);
		
		batch.end();

	}

	/**Gets the location of the npc from the name passed in to the
	 * method.
	 * @params		Name of the npc next in line
	 * @return		Returns a the position of the player as vector2
	 */
	Vector2 getLocationTarget(String npc){
		
		for(int i = 0 ; i < npcList.size(); i++)
			if(npcList.elementAt(i).getName().equals(npc))
				return npcList.elementAt(i).getPosition();
		
		return player.getPosition();
	}
	
	/**Returns the npc that matched with the npc name passed in as paramater
	 * 
	 * @param npc		name of npc
	 * @return			ACtual NPC
	 */
	NPC getNPC(String npc){
			
			for(int i = 0 ; i < npcList.size(); i++)
				if(npcList.elementAt(i).getName().equals(npc))
					return npcList.elementAt(i);
			
			return null;
		}
	
	/**
	 * Draw all the objects currently on screen in the game world
	 */
	public void Draw(float delta){
		// Get the grass sprite and draw it over and over and over
		Sprite grassSprite;		
		for(int y = -5; y < WORLD_HEIGHT/4; y++){
			for(int x = -5;x < WORLD_WIDTH/4; x++){
				grassSprite = grass.getSprite();
				grassSprite.setBounds(x * 256, y * 256, 256, 256);
				grassSprite.draw(batch);
			}
		}
		
		player.Draw(batch);

		for(int i = 0; i < npcList.size(); i++)
			npcList.elementAt(i).Draw(batch);
		
		collision.Draw(batch);	
		
		float angle = 0;
		if(!questManager.questsComplete() && !inDialog){
			Vector2 tempTarget = getLocationTarget(questManager.getNPCName());
			
			// Comment this out, and uncomment below to see the line drawn from the player to its target
			Vector2 diff = tempTarget.sub(new Vector2(player.getSprite().getX(), player.getSprite().getY()));
			angle = diff.angle();
			angle *= -1;
			
			/*Uncomment to see the line drawn to the target
			 * shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(1, 1, 0, 1);
			shapeRenderer.line(player.getSprite().getX(),player.getSprite().getY(), tempTarget.x,tempTarget.y);
		    shapeRenderer.end();*/
		}
		
		dialogStage.draw(angle);
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean testSuccess = false;
	
	@Override
	public void show() {		
		controlListener();	
		
		
		dialogStage.dialogBoxLabel.setText(questManager.getResponseDialog(testSuccess));
		System.out.println(questManager.getResponseDialog(testSuccess));	
		testSuccess = false;
		
		if(inDialog){
			questManager.increment();
			dialogComplete = true;
		}		
	}
	
	/**
	 * Takes in the keys pressed and updates the world
	 */

	public void Update(){
		// reset the interaction variables every loop
		interactionAvailable = false;
		npcName = null;
		
		for(int i = 0; i < npcList.size(); i++)
			npcList.elementAt(i).Update();

		collision.Update(player);
		
		for(int i = 0; i < npcList.size(); i++)
			collision.Update(npcList.elementAt(i));
		
		if(!inDialog)
			player.Update();
		
		camera.Update(player);
		
		if(Gdx.input.isKeyPressed(Keys.P)){// move the player right
			System.out.println(collision);
		}
		
		NPC n = getNPC(npcName);
		dialogStage.update(inDialog, interactionAvailable, n);
	}
	
	private void controlListener(){
		Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter(){
			@Override
            public boolean keyDown(int keycode) {
                    switch(keycode) {
                    case Keys.ESCAPE:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                        break;
                    }
                    return false;
            }
			
			@Override
            public boolean keyUp(int keycode) {
                    switch(keycode) {
                    case Keys.ENTER:
                    	//Collision with npc happening update dialog 
                		if(npcName != null){

                			startDialog();                			                			

                		}
                		break;
                		
                    case Keys.UP:
                    	if(camera.zoom > 1)
                    		camera.zoom -= 1;
                    	break;
                    case Keys.DOWN:
                    	if(camera.zoom < 10)
                    		camera.zoom += 1;
                    	break;
                    }
                    return false;
            }
		}));
	}
	
	private void startDialog(){
		if(!inDialog){
			inDialog = true;
			dialogComplete = false;
		}
		
		if(dialogComplete){
			inDialog = false;
		}else{
			if(questManager.isCurrentNPC(npcName)){
				//Check if current element is a test element. FinalTest screen displayed.
				if(questManager.isTestNode()){
					((Game) Gdx.app.getApplicationListener()).setScreen(new FinalTest(gamePlayScreen, questManager,
							questManager.getCorrespondingDialog(npcName), questManager.getQuestFaces()));

					if(questManager.isDialogComplete())
						dialogComplete = true;
					
					//questManager.increment();
					
					//TODO the final node of the final quest repeats twice.
				}
				else{
					dialogStage.dialogBoxLabel.setText(questManager.getCorrespondingDialog(npcName));
					System.out.println(questManager.getCorrespondingDialog(npcName));

					dialogStage.addFaces();
					
    				if(questManager.isDialogComplete())
    					dialogComplete = true;
    				questManager.increment();
				}
			}
			else if(questManager.isPrevNPC(npcName)){
				dialogStage.dialogBoxLabel.setText(questManager.getCorrespondingDialog(npcName));
				System.out.println(questManager.getCorrespondingDialog(npcName));
				dialogComplete = true;
			}
			else{
				dialogStage.dialogBoxLabel.setText(npcName + ":" + getNPC(npcName).getDefaultDialog());
				dialogComplete = true;
			}
		}
	}
	
	/**
	 * Initializes the movementGrid and set it all to 0
	 * initialize the player object
	 */
	public void Initialize(){

		player = new Player(new Vector2(2 * GridCollision.GRIDBLOCK,2 * GridCollision.GRIDBLOCK));// initialize the players position		
		
		inDialog = false;
		
	
		
		grass = new GameObject(new Vector2(0,0));
		interactionPrompt = new GameObject(new Vector2(0,0));
				
		questManager = new QuestManager();
	}
	
	/**
	 * Load the content of all the objects used in the game world
	 * into memory.
	 */
	public void LoadContent(){
		player.LoadContent("PlayerTextures/player.png");
		grass.LoadContent("WorldTextures/grass.jpg");
		interactionPrompt.LoadContent("WorldTextures/grass.jpg");
		
		LoadMap("map2.txt");
	}

	@Override
	public void hide() {
		//dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		System.out.println("World dispose");
		questManager.dispose();
		debugRenderer.dispose();
	}
	
	//*****************************************************************************
	//******************************MAPLOADER**************************************
	
	/**
	 * Reads in a text file and loads all the indicated objects into the collision grid
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public void LoadMap(String filename){
		BufferedReader inputStream = null;
		
		inputStream = new BufferedReader (new InputStreamReader(Gdx.files.internal("data/"+filename).read()));
		//inputStream = new Scanner(new FileInputStream(mapName));
		
		String tempLine = "";

		// when not at end of text file
		try {
			while(inputStream.ready()){
				try {
					tempLine = inputStream.readLine();
				} catch (Exception e) {
					e.printStackTrace();
				}// get next line
				
				String [] tempSplit = tempLine.split(" ");// split the line at all spaces
				
				if(tempSplit.length > 0){
					// check for the map size
					if(tempSplit[0].equalsIgnoreCase("ms")){
						WORLD_WIDTH = Integer.parseInt(tempSplit[1]);
						WORLD_HEIGHT = Integer.parseInt(tempSplit[2]);
						
						collision = new GridCollision(WORLD_WIDTH, WORLD_HEIGHT);// create collision grid
						collision.Initialize();// Initialize grid
						
						collision.PlaceObject(player);
						
						for(int i = 0 ; i < npcList.size(); i++)
							collision.PlaceObject(npcList.elementAt(i));
						
					}
					// Hash tag indicates a comment
					else if(tempSplit[0].equalsIgnoreCase("#")){
						
					}
					else if(tempSplit[0].equalsIgnoreCase("o")){
						int gridX = Integer.parseInt(tempSplit[1]);
						int gridY = Integer.parseInt(tempSplit[2]);
					
						
						if(tempSplit[3].equalsIgnoreCase("hl")){// horizontal line
							int length = Integer.parseInt(tempSplit[4]);
							for(int x = gridX; x < length; x++){
								SolidObject s = new SolidObject(new Vector2(x*GridCollision.GRIDBLOCK,gridY*GridCollision.GRIDBLOCK));
								s.LoadContent("WorldTextures/trees.png");
								collision.PlaceObject(s);// place object on the grid
								
							}
						}
						else if(tempSplit[3].equalsIgnoreCase("vl")){// vertical line
							int length = Integer.parseInt(tempSplit[4]);
								for(int y = gridY; y < length; y++){
								SolidObject s = new SolidObject(new Vector2(gridX*GridCollision.GRIDBLOCK,y*GridCollision.GRIDBLOCK));
								s.LoadContent("WorldTextures/trees.png");
								collision.PlaceObject(s);// place object on the grid
								
							}
						}
						else if(tempSplit[3].equalsIgnoreCase("h")){// house
							// place a house on the map
							int houseType = Integer.parseInt(tempSplit[4]);
							SolidObject s = new SolidObject(new Vector2(gridX *GridCollision.GRIDBLOCK, gridY*GridCollision.GRIDBLOCK ));
							s.LoadContent("WorldTextures/house"+ houseType +".png");
							collision.PlaceObject(s);// place object on the grid
						
						}
						else if(tempSplit[3].equalsIgnoreCase("npc")){// npc
							int npcMovementType = Integer.parseInt(tempSplit[4]);
							String npcName = tempSplit[5];
							int movementLength = Integer.parseInt(tempSplit[6]);
							String defaultDialog = "";
							for(int i = 7 ; i < tempSplit.length; i++)
								defaultDialog += tempSplit[i]+" ";
							NPC n = new NPC(new Vector2(gridX*GridCollision.GRIDBLOCK, gridY*GridCollision.GRIDBLOCK), npcMovementType, npcName, movementLength, defaultDialog);
							n.LoadContent("PlayerTextures/npc.png");
							n.loadPortrait(npcName);
							npcList.add(n);
							
						}
						
						
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
