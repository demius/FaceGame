package facegame.userinterface;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class FaceWrapper {

	private SpriteDrawable faceSpriteDrawable;
	public SpriteDrawable getSpriteDrawable(){return faceSpriteDrawable;}
	private int uniqueIndex;
	public int getUniqueIndex(){return uniqueIndex;}
	private boolean seen;
	public void setSeen(boolean value){seen = value;}
	public boolean getSeen(){return seen;}
	
	public FaceWrapper(int index, TextureRegion faceTexture){
		faceSpriteDrawable = new SpriteDrawable(new Sprite(faceTexture));
		uniqueIndex = index;
		seen = false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FaceWrapper)
			return uniqueIndex == ((FaceWrapper)obj).getUniqueIndex();
		
		return false;
	}
	
}
