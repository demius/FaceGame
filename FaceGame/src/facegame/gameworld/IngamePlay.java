package facegame.gameworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import facegame.quests.QuestManager;
import facegame.userinterface.DialogStage;
import facegame.userinterface.FinalTest;
import facegame.userinterface.MainMenu;

public class IngamePlay implements Screen {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Camera camera;
	private QuestManager questManager;
	
	static String npcName; // stores the name of the NPC closest to the player
	
	// global variables indicating the entire world dimensions in number of blocks
	public static int WORLD_WIDTH;
	public static int WORLD_HEIGHT;

	GridCollision collision = null;
	
	Player player = null;
	NPC npc1 = null;
	NPC npc2 = null;
	NPC npc3 = null;
	NPC npc4 = null;
	
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
	
	private DialogStage dialogStage;
	
	Vector2 targetPosition = null;// next target the player has to go to
	
	private boolean inDialog, dialogComplete;
	
	private IngamePlay gamePlayScreen;
	
	public IngamePlay(){
		gamePlayScreen = this;
		
		batch = new SpriteBatch();
		world = new World(new Vector2(0,0), true);
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
		
		//Call draw and act of the dialog stage
		dialogStage.act(delta);
		dialogStage.draw();
		
		if(!questManager.questsComplete() && !inDialog){
			Vector2 tempTarget = getLocationTarget(questManager.getNPCName());
			
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(1, 1, 0, 1);
			shapeRenderer.line(player.getSprite().getX(),player.getSprite().getY(), tempTarget.x,tempTarget.y);
		    shapeRenderer.end();
		}
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		/*if(inDialog)
			questManager.increment();*/
		
		controlListener();
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
		
		dialogStage.update(inDialog, interactionAvailable);
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
				
				//Check if quest is complete. FinalTest screen displayed.
				if(questManager.endOfQuest()){
					((Game) Gdx.app.getApplicationListener()).setScreen(new FinalTest(gamePlayScreen, 
							questManager.getCorrespondingDialog(npcName), questManager.getQuestFaces()));

					if(questManager.isDialogComplete())
						dialogComplete = true;
					
					questManager.increment();
					dialogStage.dialogBoxLabel.setText(questManager.getCorrespondingDialog(npcName));
					System.out.println(questManager.getCorrespondingDialog(npcName));
					//TODO the final node of the final quest repeats twice.
				}
				else{
					dialogStage.dialogBoxLabel.setText(questManager.getCorrespondingDialog(npcName));
					System.out.println(questManager.getCorrespondingDialog(npcName));
    				
					//Should get the face sprites here.
    				ArrayList<Sprite> faces = questManager.getNodeFaces();
    				if(faces != null){
    					for(int i = 0; i < faces.size(); i++){
    					}
    				}
    				
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
				dialogStage.dialogBoxLabel.setText(npcName + ": default dialog bla bla bla bla bla bla bla bla bla.");
				System.out.println(npcName + ": default dialog bla bla bla.");
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
		npc1 = new NPC(new Vector2(1 * GridCollision.GRIDBLOCK,2 * GridCollision.GRIDBLOCK), 1, "Alice");
		npc2 = new NPC(new Vector2(7 * GridCollision.GRIDBLOCK,2 * GridCollision.GRIDBLOCK), 1, "Bob");
		npc3 = new NPC(new Vector2(3 * GridCollision.GRIDBLOCK,2 * GridCollision.GRIDBLOCK), 2, "Steve");
		npc4 = new NPC(new Vector2(1 * GridCollision.GRIDBLOCK,5 * GridCollision.GRIDBLOCK), 0, "John");
		
		
		npcList.add(npc1);
		npcList.add(npc2);
		npcList.add(npc3);
		npcList.add(npc4);
		
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
		npc1.LoadContent("PlayerTextures/npc.png");
		npc2.LoadContent("PlayerTextures/npc.png");
		npc3.LoadContent("PlayerTextures/npc.png");
		npc4.LoadContent("PlayerTextures/npc.png");
				
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
		world.dispose();
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
						int length = Integer.parseInt(tempSplit[4]);
						
						if(tempSplit[3].equalsIgnoreCase("hl")){// if it's a horizontal line
							for(int x = gridX; x < length; x++){
								
								SolidObject s = new SolidObject(new Vector2(x*GridCollision.GRIDBLOCK,gridY*GridCollision.GRIDBLOCK));
								s.LoadContent("WorldTextures/trees.png");
								collision.PlaceObject(s);// place object on the grid
								
							}
						}
						else if(tempSplit[3].equalsIgnoreCase("vl")){
								for(int y = gridY; y < length; y++){
								
								SolidObject s = new SolidObject(new Vector2(gridX*GridCollision.GRIDBLOCK,y*GridCollision.GRIDBLOCK));
								s.LoadContent("WorldTextures/trees.png");
								collision.PlaceObject(s);// place object on the grid
								
							}
						}
						else if(tempSplit[3].equalsIgnoreCase("H")){
							// place a house on the map
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
