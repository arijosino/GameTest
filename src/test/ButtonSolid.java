package test;


import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class ButtonSolid {
	private float x = 0, y=0;
	private Shape hitbox;
	private Image picture;
	
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

	public Shape getHitbox() {
		return hitbox;
	}

	public void setHitbox(Shape hitbox) {
		this.hitbox = hitbox;
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(Image picture) {
		this.picture = picture;
	}

	
	
	public ButtonSolid(float x,float y){
		this.x = x;
		this.y = y;
		hitbox = new Rectangle(x, y, 50, 45);
	}
}
