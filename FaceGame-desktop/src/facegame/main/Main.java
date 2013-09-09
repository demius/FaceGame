package facegame.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "FaceGame";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 640;
		
		new LwjglApplication(new FaceGame(), cfg);
	}
}
