package facegame.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteAccessor implements TweenAccessor<Sprite>{

	public static final int ALPHA = 0;
	
	/* (non-Javadoc)
	 * @see aurelienribon.tweenengine.TweenAccessor#getValues(java.lang.Object, int, float[])
	 */
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			//1 because only 1 value (returnValues[0]) was set above.
			return 1;
		default:
			//Something is wrong throw error 
			assert false;
			return -1;
		}		
	}

	/* (non-Javadoc)
	 * @see aurelienribon.tweenengine.TweenAccessor#setValues(java.lang.Object, int, float[])
	 */
	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch(tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		default:
			assert false;
		}
			
	}
}
