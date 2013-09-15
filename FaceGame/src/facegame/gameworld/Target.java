package facegame.gameworld;

import com.badlogic.gdx.math.Vector2;

public class Target extends Moveable {

	public Target(Vector2 p) {
		super(p);
		this.sprite.setPosition(p.x, p.y);
	}
	
	

}
