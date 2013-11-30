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
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class SetupClass extends BasicGame {
	//declaring some constants to help indexing the animations
	public static final int DEFAULT=0,LEFT=1,RIGHT=2, ATTACK=3, RESOLUTIONX=800,RESOLUTIONY=600,MAXGHOSTS=8;

	private SpriteSheet bossSheet; 
	private Animation bossAnimation;
	
	private String mouse = "No mouse input!";
	private Rectangle mouseHitBox = null;
	private boolean mouse0Down = false;
	private boolean mouse0Clicked = false;
	
	private float wizardX = 350;
	private float wizardY = 430;
	private Solid wizard;
	private BadGuy ghosts[] = new BadGuy[MAXGHOSTS];
	private BadGuy boss = null;
	private int timeElapsed = 0;
	private int ghostsIndex = 0;
	private int SPRITESIZE = 100;
	private float speed = 0.05f;
	private float bossSpeed = 0.001f;
	private float floorBoundary = 450.0f;
	public int score = 0;
	private int wave = 1;
	private int firstNum = 0;
	private int secondNum = 0;
	private int life = 10;
	private int bossCount = 0;
	private ButtonSolid buttons[] = new ButtonSolid[12];
	private int clickingState = 0,maxClickingState = 2;
	private Rectangle clearButton = new Rectangle(38, 571, 54, 25);

	
	public SetupClass(String title) {
		
		
		super("GHOST SLAYER XII");
	}

	// GameContainer is the window that holds the game and most settings such as mouse precision. 
	public void init(GameContainer container) throws SlickException {
		//creating the mouse hitbox as a tiny invisible box just for reference
		mouseHitBox = new Rectangle(Mouse.getX(), Mouse.getY(), 1, 1);
		
		//OctoBoss Animation
		bossSheet = new SpriteSheet("Art/Octoboss/octbossSpriteSheet.png", SPRITESIZE, SPRITESIZE);
		bossAnimation = new Animation(bossSheet, 500);
		bossAnimation.setPingPong(true);
		
		//Creating the Wizard
		wizard = new Solid(wizardX,wizardY);
		
		//creating spritesheets for the 3 types of character animation: standing still, walking left and walking right
		wizard.setSprite(new SpriteSheet[4]);
		wizard.getSprite()[DEFAULT] = new SpriteSheet("Art/Wizard/WizardSpreadSheet.png", SPRITESIZE, SPRITESIZE);
		wizard.getSprite()[LEFT] = new SpriteSheet("Art/Wizard/WizardSpreadSheetLeft.png", SPRITESIZE, SPRITESIZE);
		wizard.getSprite()[RIGHT] = new SpriteSheet("Art/Wizard/WizardSpreadSheetRight.png", SPRITESIZE, SPRITESIZE);
		wizard.getSprite()[ATTACK] = new SpriteSheet("Art/Wizard/WizardSpriteSheetAttack.png", 133, SPRITESIZE);
		wizard.setAnimations(new Animation[4]);
		for(int i=0;i<wizard.getAnimations().length;i++){
			wizard.getAnimations()[i] = new Animation(wizard.getSprite()[i], 400);
			wizard.getAnimations()[i].setPingPong(true);
			
		}

		/*for(int i=0;i<buttons.length;i++){
			buttons[i] = new ButtonSolid(100+51*i,556);

			buttons[i].setPicture(new Image("Art/GUI/Button"+ (i+1) +".png"));
		}*/
				
		buttons[0] = new ButtonSolid(97,556);
		buttons[0].setPicture(new Image("Art/GUI/Button1.png"));
		
		buttons[1] = new ButtonSolid(148,556);
		buttons[1].setPicture(new Image("Art/GUI/Button2.png"));

		buttons[2] = new ButtonSolid(199,556);
		buttons[2].setPicture(new Image("Art/GUI/Button3.png"));
		
		buttons[3] = new ButtonSolid(250,556);
		buttons[3].setPicture(new Image("Art/GUI/Button4.png"));
		
		buttons[4] = new ButtonSolid(298,556);
		buttons[4].setPicture(new Image("Art/GUI/Button5.png"));
		
		buttons[5] = new ButtonSolid(347,556);
		buttons[5].setPicture(new Image("Art/GUI/Button6.png"));
		
		buttons[6] = new ButtonSolid(395,556);
		buttons[6].setPicture(new Image("Art/GUI/Button7.png"));
		
		buttons[7] = new ButtonSolid(445,556);
		buttons[7].setPicture(new Image("Art/GUI/Button8.png"));
		
		buttons[8] = new ButtonSolid(495,556);
		buttons[8].setPicture(new Image("Art/GUI/Button9.png"));
		
		buttons[9] = new ButtonSolid(543,556);
		buttons[9].setPicture(new Image("Art/GUI/Button10.png"));
		
		buttons[10] = new ButtonSolid(594,558);
		buttons[10].setPicture(new Image("Art/GUI/Button11.png"));
		
		buttons[11] = new ButtonSolid(645,556);
		buttons[11].setPicture(new Image("Art/GUI/Button12.png"));
	}
	
	
	/*
	Update methods updates game logic on screen
	Delta - the amount of time in milliseconds since the last update
	*/
	public void update(GameContainer container, int delta) throws SlickException {
			
		//Grabs the x and y coordinates of the mouse and writes it to screen to aid in implementation.
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		mouse = "Mouse position X: " + xpos + " Y: " + (RESOLUTIONY-ypos);
		
		//updating the mouseHitBox position
		mouseHitBox.setX(Mouse.getX());
		mouseHitBox.setY(RESOLUTIONY - Mouse.getY());
		
		if(Mouse.isButtonDown(0)){ //checking if the mouse button is down
			mouse0Down=true;
		}
		
		if(!Mouse.isButtonDown(0) && mouse0Down){ //checking if the mouse button was down before it has been released
			//here we set a variable to notify that the left mouse button has been clicked
			mouse0Clicked = true; //IMPORTANT NOTE: this variable should be reset to false at the end of the update
			mouse0Down = false;
		}
		
		//Creates the ghosts
		timeElapsed += delta;
		if(timeElapsed > 500 & ghostsIndex < ghosts.length){
			for(int i = 0; i < ghosts.length; i++){
				if(ghosts[i] == null){
					ghosts[i] = new BadGuy((100*ghostsIndex),0,new Animation(new SpriteSheet("Art/Ghost/GhostSpriteSheet.png", SPRITESIZE, SPRITESIZE), 500));
					ghostsIndex++;
				}
			}
			wave++;
			bossCount++;
		}
		
	/*	if(timeElapsed > 500) {							
			timeElapsed = 0;
			if(boss != null){
			boss.setY(boss.getX()+bossSpeed);
			}
		}*/
		
		
		
		//making the ghosts fall down
		for(int i = 0; i < ghosts.length; i++){
			
			if(ghosts[i] != null){
				ghosts[i].setY(ghosts[i].getY()+(speed+(wave/5)));
				//updating animation stuff
				ghosts[i].getAnimations()[DEFAULT].update(delta);
				ghosts[i].getAnimations()[DEFAULT].setPingPong(true);
				//updating hitbox position
				ghosts[i].getHitbox().setX(ghosts[i].getX());
				ghosts[i].getHitbox().setY(ghosts[i].getY());
				
				if(ghosts[i].getHitbox().intersects(mouseHitBox) && mouse0Clicked){ //checking if the player clicked the ghost
					//TODO add the state machine check and the multiplication check here
					
					System.out.println("CLICKED ON GHOST "+i);
					if(clickingState == maxClickingState && ghosts[i].getNumber().equals("" + (firstNum * secondNum))){
						
						ghosts[i] = null;
						clickingState = 0;
						firstNum = 0;
						secondNum = 0;
						score++;
					}
				
				}
				//wizard.setAnimationIndex(DEFAULT);
				
				if((ghosts[i] != null && ghosts[i].getY() >= floorBoundary)){				//code to make the ghosts disapear when they've reached the bottom. Doesnt work yet.
					ghosts[i] = null;
					life--;
				}
				
				if(ghosts[i] != null && (ghosts[i].getHitbox().intersects(wizard.getHitbox()) || ghosts[i].getHitbox().contains(wizard.getHitbox()))){
					ghosts[i] = null;
					life--;
				}
			}
		}
		//this chunk is gonna count how many ghosts are still alive, if none of them are alive, the wave is over
		int ghostCounter = 0;
		for (int i = 0; i < ghosts.length; i++) {
			if (ghosts[i] == null) {
				ghostCounter++;
			}
		}
		if (ghostCounter >= ghosts.length) {//if every ghost is dead, reset the array index so they can respawn
			ghostsIndex = 0;
		}
		
		
		//These next 3 if statements move the wizard animation left if A is pressed or right if D is pressed. If neither is pressed the standing animation is used. 
		wizard.getAnimations()[wizard.getAnimationIndex()].update(delta);

		if(container.getInput().isKeyDown(Input.KEY_A)){
			wizard.setAnimationIndex(LEFT);
			if(wizardX > 1){
				 wizardX -= delta * 0.5f;
				 wizard.getHitbox().setX(wizardX);
			}
		}
		
		if(container.getInput().isKeyDown(Input.KEY_D)){
			wizard.setAnimationIndex(RIGHT);
			if(wizardX < 700){
				wizardX += delta * 0.5f;
				wizard.getHitbox().setX(wizardX);
			}
		}
		
		if(!container.getInput().isKeyDown(Input.KEY_D) & !container.getInput().isKeyDown(Input.KEY_A)){
			wizard.setAnimationIndex(DEFAULT);
		}
		wizard.getAnimations()[wizard.getAnimationIndex()].setPingPong(true);
		
		for (int i = 0; i < buttons.length; i++) {
			
			if(buttons[i].getHitbox().intersects(mouseHitBox) && mouse0Clicked){ //checking if the player clicked the ghost
				//TODO add the state machine check and the multiplication check here
				if(clickingState == 0){
					firstNum = i+1;
					clickingState ++;
				}
				else if(clickingState == 1){
					secondNum = i+1;
					clickingState++;
				}
				System.out.println("CLICKED ON BUTTON "+(i+1));
			
			}
			if(clearButton.intersects(mouseHitBox) && mouse0Clicked){ //checking if the player clicked the clear button
				//reset stuff
				firstNum = 0;
				secondNum = 0;
				clickingState = 0;
			}
		}
		
		
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
//	    		g.draw(bg.getHitbox());	//for debugging
	    		
	    	
	    		
	    		
	    	}
//	    	g.draw(mouseHitBox); //for debugging
	    }
	    
	    /*if(wave % 5 == 0){
	    	
	    }*/
		

	    wizard.getAnimations()[wizard.getAnimationIndex()].draw(wizardX, wizardY); //drawing wizard's current animation
		g.drawString("Health: " + life, 100, 530);
		g.drawString("" + firstNum + " x " + secondNum + " = " + firstNum*secondNum, 250, 530);
		
		//bossAnimation.draw(400, 100);
		//drawing the gui
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].getPicture().draw(buttons[i].getX(), buttons[i].getY());
//			g.draw(buttons[i].getHitbox());//for debugging
		}
		
		g.drawString("CLEAR", 42, 573);
		g.drawString("score", 700, 563);
		g.drawString(""+score, 700, 576);
	}
	
	
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SetupClass("Setup Test"));		//creates window(container)
		app.setDisplayMode(800, 600, false);				 //first two fields are for the resolution, the last one is boolean for fullscreen
		app.setAlwaysRender(true); 					//This causes the game to render even if the window is not selected/is not in focus
		app.setTargetFrameRate(160);
		app.start();								//starts the game
	}
	
}