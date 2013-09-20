package facegame.userinterface;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class FaceWrapper {

	private SpriteDrawable faceSpriteDrawable;
	public SpriteDrawable getSpriteDrawable(){return faceSpriteDrawable;}
	private int uniqueIndex;
	public int getUniqueIndex(){return uniqueIndex;}
	private boolean isTarget;
	
	public FaceWrapper(int index, TextureRegion faceTexture, boolean target){
		faceSpriteDrawable = new SpriteDrawable(new Sprite(faceTexture));
		uniqueIndex = index;
		isTarget = target;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FaceWrapper)
			return uniqueIndex == ((FaceWrapper)obj).getUniqueIndex();
		
		return false;
	}
}
