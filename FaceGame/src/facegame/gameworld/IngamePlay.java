package facegame.gameworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import facegame.quests.QuestManager;

public class IngamePlay implements Screen {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Camera camera;
	private QuestManager questManager;
	
	static String npcName; 
	
	// global variables indicating the entire world dimensions in number of blocks
	public static int WORLD_WIDTH = 11;
	public static int WORLD_HEIGHT = 15;

	GridCollision collision = null;
	
	Player player = null;
	NPC npc1 = null;
	NPC npc2 = null;
	NPC npc3 = null;
	NPC npc4 = null;
	
	GameObject grass = null;// Grass tiled under all the objects in the game world
		
	SpriteBatch batch;
	
	private Stage stage;//////////////////
	private Table table;//////////////////
	private Label label;//////////////////
	
	private final float pixelToMeter = 32f;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		
		Update();
		
		batch.begin();
		
		Draw(delta);
		
		batch.end();
		
		//debugRenderer.render(world, camera.combined);

	}

	/**
	 * Draw all the objects currently on screen in the game world
	 */
	public void Draw(float delta){
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
		npc1.Draw(batch);
		npc2.Draw(batch);
		npc3.Draw(batch);
		npc4.Draw(batch);
		collision.Draw(batch);	
		
		Table.drawDebug(stage);/////////////////////		
		stage.act(delta);///////////////////
		stage.draw();///////////////////
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		world = new World(new Vector2(0,0), true);
		debugRenderer = new Box2DDebugRenderer();


		camera = new Camera(1, Gdx.graphics.getHeight()/Gdx.graphics.getWidth());
		camera.setToOrtho(true,800,480);
		
		Initialize();
		LoadContent();
		
		stage = new Stage();///////////////////////////////
		TextureAtlas textureAtlas = new TextureAtlas("menus/fg_buttons.pack");//////////////////////////////////
		Skin skin = new Skin(Gdx.files.internal("menus/menuSkin.json"), textureAtlas);//////////////////////////
		
		table = new Table(skin);///////////////////////////////
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4);///////////////////////////////
		
		label = new Label("Main Menu", skin);////////////////////////////////////////
		label.setBounds(100, 0, Gdx.graphics.getWidth()-100, Gdx.graphics.getHeight()/4);
		
		table.add(label);////////////////////////
		table.debug();	//TODO remove later/////////////////////////
		stage.addActor(table);//////////////////////////
	}
	
	/**
	 * Takes in the keys pressed and updates the world
	 */
	public void Update(){
		player.Update();
		npc1.Update();
		npc2.Update();
		npc3.Update();
		npc4.Update();
		
		camera.Update(player);
		
		//String npcName = collision.Update(player);
		//System.out.println(npcName);
		npcName = null;
		
		collision.Update(player);
		collision.Update(npc1);
		collision.Update(npc2);
		collision.Update(npc3);
		collision.Update(npc4);
		
		if(Gdx.input.isKeyPressed(Keys.P)){// move the player right
			System.out.println(collision);
		}
		
		//Collision with npc happening update dialog 
		if(npcName != null){
			//Check the initiation of dialog
			if(Gdx.input.isKeyPressed(Keys.ENTER)){// move the player right
				if(questManager.isInvolved(npcName)){
					System.out.println(questManager.getCurrentDialog());
					questManager.increment();
				}
			}
			
		}
	}
	
	/**
	 * Initializes the movementGrid and set it all to 0
	 * initialize the player object
	 */
	public void Initialize(){

		player = new Player(new Vector2(2 * GridCollision.GRIDBLOCK,2 * GridCollision.GRIDBLOCK));// initialize the players position
		npc1 = new NPC(new Vector2(1 * GridCollision.GRIDBLOCK,2 * GridCollision.GRIDBLOCK), 1, "Barry");
		npc2 = new NPC(new Vector2(7 * GridCollision.GRIDBLOCK,2 * GridCollision.GRIDBLOCK), 1, "Angela");
		npc3 = new NPC(new Vector2(3 * GridCollision.GRIDBLOCK,2 * GridCollision.GRIDBLOCK), 2, "Michael");
		npc4 = new NPC(new Vector2(1 * GridCollision.GRIDBLOCK,5 * GridCollision.GRIDBLOCK), 0, "Bruce Merry");
		
		grass = new GameObject(new Vector2(0,0));
		
		collision = new GridCollision(WORLD_WIDTH, WORLD_HEIGHT);// create collision grid
		collision.Initialize();// Initialize grid
		
		
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
		
		LoadMap("map1.txt");
	}

	@Override
	public void hide() {
		dispose();

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
