package test;


import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class SetupClass extends BasicGame {
	//declaring some constants to help indexing the animations
	public static final int DEFAULT=0,LEFT=1,RIGHT=2;

	private SpriteSheet bossSheet; 
	private Animation bossAnimation;
	
	private String mouse = "No mouse input!";
	private Rectangle mouseHitBox = null;
	private boolean mouse0Down = false;
	private boolean mouse0Clicked = false;
	
	private float wizardX = 350;
	private float wizardY = 430;
	private Solid wizard;
	private BadGuy ghosts[] = new BadGuy[10];
	private BadGuy boss = null;
	private int timeElapsed = 0; 
	private int ghostsIndex = 0;
	private int SPRITESIZE = 100;
	private float speed = 0.05f;
	private float bossSpeed = 0.001f;
	private float floorBoundary = 450.0f;
	private int score = 1;
	private int firstNum = 0;
	private int secondNum = 0;
	private int life = 3;
	private int bossCount = 0;
	

	
	public SetupClass(String title) {
		
		
		super("GHOST SLAYER XII");
	}

	// GameContainer is the window that holds the game and most settings such as mouse precision. 
	public void init(GameContainer container) throws SlickException {
		//creating the mouse hitbox as a tiny invisible box just for reference
		mouseHitBox = new Rectangle(Mouse.getX(), Mouse.getY(), 20, 20);
		
		//OctoBoss Animation
		bossSheet = new SpriteSheet("Art/Octoboss/octbossSpriteSheet.png", SPRITESIZE, SPRITESIZE);
		bossAnimation = new Animation(bossSheet, 500);
		bossAnimation.setPingPong(true);
		
		//Creating the Wizard
		wizard = new Solid(wizardX,wizardY);
		
		//creating spritesheets for the 3 types of character animation: standing still, walking left and walking right
		wizard.setSprite(new SpriteSheet[3]);
		wizard.getSprite()[DEFAULT] = new SpriteSheet("Art/Wizard/WizardSpreadSheet.png", SPRITESIZE, SPRITESIZE);
		wizard.getSprite()[LEFT] = new SpriteSheet("Art/Wizard/WizardSpreadSheetLeft.png", SPRITESIZE, SPRITESIZE);
		wizard.getSprite()[RIGHT] = new SpriteSheet("Art/Wizard/WizardSpreadSheetRight.png", SPRITESIZE, SPRITESIZE);
		
		wizard.setAnimations(new Animation[3]);
		for(int i=0;i<wizard.getAnimations().length;i++){
			wizard.getAnimations()[i] = new Animation(wizard.getSprite()[i], 400);
			wizard.getAnimations()[i].setPingPong(true);
			
		}
				
		
	}
	
	
	/*
	Update methods updates game logic on screen
	Delta - the amount of time in milliseconds since the last update
	*/
	public void update(GameContainer container, int delta) throws SlickException {
			
		//Grabs the x and y coordinates of the mouse and writes it to screen to aid in implementation.
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		mouse = "Mouse position X: " + xpos + " Y: " + (600-ypos);
		
		//updating the mouseHitBox position
		mouseHitBox.setX(Mouse.getX());
		mouseHitBox.setY(Mouse.getY());
		
		if(Mouse.isButtonDown(0)){ //checking if the mouse button is down
			mouse0Down=true;
		}
		
		if(!Mouse.isButtonDown(0) && mouse0Down){ //checking if the mouse button was down before it has been released
			//here we set a variable to notify that the left mouse button has been clicked
			System.out.println("CLICK DETECTED");
			mouse0Down = false;
			mouse0Clicked = true; //IMPORTANT NOTE: this variable should be reset to false at the end of the update
		}
		
		//Creates 
		timeElapsed += delta;
		if(timeElapsed > 500 & ghostsIndex < 10){
			for(int i = 0; i < 10; i++){
				if(ghosts[i] == null){
					ghosts[i] = new BadGuy((100*ghostsIndex),0,new Animation(new SpriteSheet("Art/Ghost/GhostSpriteSheet.png", SPRITESIZE, SPRITESIZE), 500));
					ghostsIndex++;
				}
			}
			bossCount++;
		}
		
		/*if(timeElapsed > 500) {							
			timeElapsed = 0;
			if(boss != null){
			boss.setY(boss.getX()+bossSpeed);
			}
		}*/
		
		
		
		//making the ghosts fall down
		for(int i = 0; i < 10; i++){
			
			if(ghosts[i] != null){
				ghosts[i].setY(ghosts[i].getY()+(speed+(score/100)));
				//updating animation stuff
				ghosts[i].getAnimations()[DEFAULT].update(delta);
				ghosts[i].getAnimations()[DEFAULT].setPingPong(true);
				//updating hitbox position
				ghosts[i].getHitbox().setX(ghosts[i].getX());
				ghosts[i].getHitbox().setY(ghosts[i].getY());
				
				if(ghosts[i].getY() >= floorBoundary){				//code to make the ghosts disapear when they've reached the bottom. Doesnt work yet.
					ghosts[i] = null;
					ghostsIndex--;
					life--;
				}
				
			}
		}
		
		
		//These next 3 if statements move the wizard animation left if A is pressed or right if D is pressed. If neither is pressed the standing animation is used. 
		wizard.getAnimations()[wizard.getAnimationIndex()].update(delta);

		if(container.getInput().isKeyDown(Input.KEY_A)){
			wizard.setAnimationIndex(LEFT);
			if(wizardX > 1){
				wizardX -= delta * 0.5f;
			}
		}
		
		if(container.getInput().isKeyDown(Input.KEY_D)){
			wizard.setAnimationIndex(RIGHT);
			if(wizardX < 700){
				wizardX += delta * 0.5f;
			}
		}
		
		if(!container.getInput().isKeyDown(Input.KEY_D) & !container.getInput().isKeyDown(Input.KEY_A)){
			wizard.setAnimationIndex(DEFAULT);
		}
		wizard.getAnimations()[wizard.getAnimationIndex()].setPingPong(true);
		
		
		mouse0Clicked = false; //reseting the click variable
	}
	
	
	//renders the graphics to the screen
	public void render(GameContainer container, Graphics g) throws SlickException {
		//g.setFont(uFont);
		
	    g.setBackground(new Color(150,150,150));				//This sets the background color of the screen to white
	    g.drawImage(new Image("Art/Background/sky.png"), 0, 0);
	    
		g.drawString(mouse, 50, 50);
		/*if(bossCount%5 == 0){
			boss = new BadGuy(400, 0, bossAnimation);
			g.drawAnimation(boss.getAm(), boss.getX(), boss.getY());
			bossCount-= 5;
		}*/
	    for(BadGuy bg : ghosts){
	    	if(bg != null){
	    		g.drawAnimation(bg.getAnimations()[DEFAULT], bg.getX(), bg.getY());
	    		g.setColor(new Color(255,255,255));
	    		g.drawString(bg.getNumber(), bg.getX()+40, bg.getY()-5);
	    		
	    		/*//Creating invisible boxes that overlay the ghosts, will be used for collision detection.
	    		Shape hitbox = new Rectangle(bg.getX(), bg.getY(), 100, 100);
	    		g.setColor(new Color(0,0,0,1));
	    		g.fill(hitbox);
	    		g.draw(hitbox);*/
	    		
	    		
	    	}
	    }
	    
		

	    wizard.getAnimations()[wizard.getAnimationIndex()].draw(wizardX, wizardY); //drawing wizard's current animation
		g.drawString("Health: " + life, 100, 530);
		g.drawString("" + firstNum + " x " + secondNum + " = " + firstNum*secondNum, 250, 530);
		
		//bossAnimation.draw(400, 100);

	}
	
	
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SetupClass("Setup Test"));		//creates window(container)
		app.setDisplayMode(800, 600, false);				 //first two fields are for the resolution, the last one is boolean for fullscreen
		app.setAlwaysRender(true); 					//This causes the game to render even if the window is not selected/is not in focus
		
		app.start();								//starts the game
	}
	
}
