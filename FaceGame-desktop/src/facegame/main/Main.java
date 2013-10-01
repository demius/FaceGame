package facegame.main;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

import facegame.main.TextureChooser;

public class Main {
	public static void main(String[] args) {
				
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "FaceGame";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 640;
		cfg.x = cfg.y = 0;
		
		Settings settings = new Settings();
        settings.maxWidth = 8192;
        settings.maxHeight = 2048;
		
        //launch face tex chooser gui
		TextureChooser.launchInstance();
		//only proceed when gui selection is complete
		while(!TextureChooser.isComplete()){
			try{
			Thread.sleep(1500);
			}
			catch (InterruptedException e){
				System.err.println(e);
			}
		}
		//rename all images to standard naming convention
		try{
	    TextureChooser.renameImages(TextureChooser.getColouredPath());
	    TextureChooser.renameImages(TextureChooser.getBlackPath());
	    TextureChooser.renameImages(TextureChooser.getWhitePath());
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		//packing textures into texture atlas
		if(TextureChooser.getColouredPath()!=null)
		TexturePacker2.process(settings, TextureChooser.getColouredPath(), "Faces/Coloured", "coloured_male_textures");
		if(TextureChooser.getWhitePath()!=null)
		TexturePacker2.process(settings, TextureChooser.getWhitePath(), "Faces/White", "white_male_textures");
		if(TextureChooser.getBlackPath()!=null)
		TexturePacker2.process(settings, TextureChooser.getBlackPath(), "Faces/Black", "black_male_textures");
	

		new LwjglApplication(new FaceGame(), cfg);
	}
}
