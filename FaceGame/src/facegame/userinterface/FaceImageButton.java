package facegame.userinterface;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class FaceImageButton extends ImageButton {

	private int index;
	public int getIndex(){return index;}
	/**
	 * @param imageUp
	 */
	public FaceImageButton(Drawable imageUp, int index) {
		super(imageUp);
	}
}
