package test;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Solid {//anything that is actually solid should be instantiated from it (or from a subclass...)
	private float x = 0, y=0;
	private Shape hitbox;
	private SpriteSheet []sprite;
	private Animation []animations = null;//using an animation array to keep things more nicely packed
	private int animationIndex = 0;
	
	public Solid(float x,float y){
		this.x = x;
		this.y = y;
		hitbox = new Rectangle(x, y, 100, 100);
	}
	
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
	/**
	 * @return the hitbox
	 */
	public Shape getHitbox() {
		return hitbox;
	}

	/**
	 * @param hitbox the hitbox to set
	 */
	public void setHitbox(Shape hitbox) {
		this.hitbox = hitbox;
	}

	/**
	 * @return the sprite
	 */
	public SpriteSheet[] getSprite() {
		return sprite;
	}

	/**
	 * @param sprite the sprite to set
	 */
	public void setSprite(SpriteSheet[] sprite) {
		this.sprite = sprite;
	}

	/**
	 * @return the animations
	 */
	public Animation[] getAnimations() {
		return animations;
	}

	/**
	 * @param animations the animations to set
	 */
	public void setAnimations(Animation[] animations) {
		this.animations = animations;
	}

	/**
	 * @return the animationIndex
	 */
	public int getAnimationIndex() {
		return animationIndex;
	}

	/**
	 * @param animationIndex the animationIndex to set
	 */
	public void setAnimationIndex(int animationIndex) {
		this.animationIndex = animationIndex;
	}

}
