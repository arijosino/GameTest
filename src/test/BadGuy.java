package test;

import java.util.Random;

import org.newdawn.slick.Animation;

public class BadGuy {
	private float x = 0, y=0;
	private Animation am = null;
	private String number = "1";
	private Random random = new Random();

	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
	
	public Animation getAm() {
		return am;
	}

	public void setAm(Animation am) {
		this.am = am;
	}

	public BadGuy(float x, float y, Animation am) {
		this.x = x;
		this.y = y;
		this.am = am;
		int firstNum = random.nextInt(12)+1;
		int secondNum = random.nextInt(12)+1;
		int finalNum = firstNum * secondNum;
		number = ""+finalNum;
		
		
		
	}
	

}
