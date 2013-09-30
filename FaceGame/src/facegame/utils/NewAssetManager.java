package facegame.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class NewAssetManager {
	
	private static NewAssetManager singletonAM = new NewAssetManager();
	private static AssetManager assetManager;
	
	private NewAssetManager(){
		assetManager = new AssetManager();
		
		//Loading assets
		assetManager.load("NPC/Monica.png", Texture.class);
		assetManager.load("NPC/Sarah.png", Texture.class);
		assetManager.load("Faces/White/white_male_homogeneous.txt", TextureAtlas.class);
		assetManager.load("HUD/arrow2.png", Texture.class);
		assetManager.load("NPC/Alice.png", Texture.class);
		assetManager.load("NPC/Bob.png", Texture.class);
		assetManager.load("Faces/White/white_male_heterogeneous.txt", TextureAtlas.class);
		assetManager.load("NPC/Bruce.png", Texture.class);
		assetManager.load("NPC/Steve.png", Texture.class);
		assetManager.load("NPC/Gerald.png", Texture.class);
		assetManager.load("Faces/Black/black_male_heterogeneous.txt", TextureAtlas.class);
		assetManager.load("NPC/John.png", Texture.class);
		assetManager.load("NPC/Lauren.png", Texture.class);
		assetManager.load("NPC/Mike.png", Texture.class);		
		assetManager.load("dialog/dialog.pack", TextureAtlas.class);
		assetManager.load("Faces/Black/black_male_homogeneous.txt", TextureAtlas.class);
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
