package facegame.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import facegame.main.TextureChooser;
import facegame.quests.QuestManager;

public class NewAssetManager {
	
	private static NewAssetManager singletonAM = new NewAssetManager();
	private static AssetManager assetManager;
	private QuestManager questManager;
	
	private NewAssetManager(){
		assetManager = new AssetManager();
		assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(new AtlasFileResolver()));
				
		//Loading assets
		assetManager.load("NPC/Monica.png", Texture.class);
		assetManager.load("NPC/Sarah.png", Texture.class);
		
		if(TextureChooser.getWhitePath()!=null)
			assetManager.load("Faces/White/white_male_textures.atlas", TextureAtlas.class);
        else
	        assetManager.load("Faces/White/white_male_default.txt",TextureAtlas.class);
		
		if(TextureChooser.getBlackPath()!=null)
			assetManager.load("Faces/Black/black_male_textures.atlas", TextureAtlas.class);
        else
	        assetManager.load("Faces/Black/black_male_default.txt",TextureAtlas.class);
		
		if(TextureChooser.getColouredPath()!=null)
			assetManager.load("Faces/Coloured/coloured_male_textures.atlas", TextureAtlas.class);
        else
	        assetManager.load("Faces/Coloured/coloured_male_default.txt",TextureAtlas.class);
				
		assetManager.load("HUD/arrow2.png", Texture.class);
		assetManager.load("NPC/Alice.png", Texture.class);
		assetManager.load("NPC/Bob.png", Texture.class);
		assetManager.load("NPC/Bruce.png", Texture.class);
		assetManager.load("NPC/Steve.png", Texture.class);
		assetManager.load("NPC/Gerald.png", Texture.class);
		assetManager.load("NPC/John.png", Texture.class);
		assetManager.load("NPC/Lauren.png", Texture.class);
		assetManager.load("NPC/Mike.png", Texture.class);		
		assetManager.load("dialog/dialog.pack", TextureAtlas.class);
		
	}

	public static NewAssetManager getInstance(){
		return singletonAM;
	}
	
	public <T> T get(String fileName){
		return assetManager.get(fileName);
	}
	
	public boolean updateAssetManager(){
		return assetManager.update();
	}
	
	public float getProgress(){
		return assetManager.getProgress();
	}
	
	public void remove(String value){
		assetManager.unload(value);
	}
}
