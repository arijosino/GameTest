package test;

import java.util.Random;

import org.newdawn.slick.Animation;

public class BadGuy extends Solid {
	
	private String number = "1";
	private Random random = new Random();

	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public BadGuy(float x, float y, Animation am,int level) {
		super(x,y);
		this.setAnimations(new Animation[1]);
		this.getAnimations()[0] = am;
		int firstNum = random.nextInt(level)+1;
		int secondNum = random.nextInt(12)+1;
		int finalNum = firstNum * secondNum;
		number = ""+finalNum;
		
		
		
	}
	

}
