package test;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

public class Solid {//anything that is actually solid should be instantiated from it (or from a subclass...)
	protected float x = 0, y=0;
	protected Shape hitbox;
	protected SpriteSheet sprite;
	protected Animation am = null;
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	public Animation getAm() {
		return am;
	}

	public void setAm(Animation am) {
		this.am = am;
	}

}
