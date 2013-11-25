package test;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SetupClass extends BasicGame {

	private SpriteSheet ghostSheet, wizardSheet, wizardSheetLeft, wizardSheetRight; 
	private Animation ghostAnimation, wizardAnimation, wizardAnimationLeft, wizardAnimationRight, wizardAnimationStanding;
	private String mouse = "No mouse input!";
	private float wizardX = 350;
	private float wizardY = 504;
	private BadGuy ghosts[] = new BadGuy[10];
	private int timeElapsed = 0; 
	private int ghostsIndex = 0;
	private int SPRITESIZE = 100;
	private Random random;
	private float speed = 0.2f;
	
	public SetupClass(String title) {
		
		super(title);
		
	}

	// GameContainer is the window that holds the game and most settings such as mouse precision. 
	public void init(GameContainer container) throws SlickException {
		random = new Random();
		
		ghostSheet = new SpriteSheet("Art/Ghost/GhostSpriteSheet.png", SPRITESIZE, SPRITESIZE);
		ghostAnimation = new Animation(ghostSheet, 400);
		ghostAnimation.setPingPong(true);
		
		
		//Wizard Standing Animation
		wizardSheet = new SpriteSheet("Art/Wizard/WizardSpreadSheet.png", SPRITESIZE, SPRITESIZE);
		wizardAnimationStanding = new Animation(wizardSheet, 400);
		wizardAnimationStanding.setPingPong(true);
		
		//Current Wizard Animation
		wizardAnimation = new Animation(wizardSheet, 400);
		wizardAnimation.setPingPong(true);
		
		//Wizard Moving Left Animation
		wizardSheetLeft = new SpriteSheet("Art/Wizard/WizardSpreadSheetLeft.png", SPRITESIZE, SPRITESIZE);
		wizardAnimationLeft = new Animation(wizardSheetLeft, 400);
		wizardAnimationLeft.setPingPong(true);
		
		//Wizard Moving Right Animation
		wizardSheetRight = new SpriteSheet("Art/Wizard/WizardSpreadSheetRight.png", SPRITESIZE, SPRITESIZE);
		wizardAnimationRight = new Animation(wizardSheetRight, 400);
		wizardAnimationRight.setPingPong(true);
	}
	
	
	/*
	Update methods updates game logic on screen
	Delta - the amount of time in milliseconds since the last update
	*/
	public void update(GameContainer container, int delta) throws SlickException {
				
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position X: " + xpos + " Y: " + ypos;
		
		ghostAnimation.update(delta);							//controls the rate the animation of the ghost updates synced with the games refresh rate(delta).
		
		timeElapsed += delta;
		if(timeElapsed > 500 & ghostsIndex <10){							//***********When score is done being implemented you can use its value to alter this if statement to make the ghosts spawn faster***************
			timeElapsed = 0;
			ghosts[ghostsIndex] = new BadGuy((100*ghostsIndex),0,new Animation(new SpriteSheet("Art/Ghost/GhostSpriteSheet.png", SPRITESIZE, SPRITESIZE), 400));
			
			//ghostX = (100*random.nextInt(8));
			//ghosts.add(new Animation(new SpriteSheet("Art/Ghost/GhostSpriteSheet.png", SPRITESIZE, SPRITESIZE), 400));
			ghostsIndex++;	
		}
		
		//making the ghosts fall down
		for(int i = 0; i < 10; i++){
			
			if(ghosts[i] != null){
				ghosts[i].setY(ghosts[i].getY()+speed);
			}
		}
		
		
		
		
		
		//These next 3 if statements move the wizard animation left if A is pressed or right if D is pressed. If neither is pressed the standing animation is used. 
		wizardAnimation.update(delta);
		if(container.getInput().isKeyDown(Input.KEY_A)){
			wizardAnimation = wizardAnimationLeft;
			wizardX -= delta * 0.5f;
		}
		
		if(container.getInput().isKeyDown(Input.KEY_D)){
			wizardAnimation = wizardAnimationRight;
			wizardX += delta * 0.5f;
		}
		
		if(!container.getInput().isKeyDown(Input.KEY_D) & !container.getInput().isKeyDown(Input.KEY_A)){
			wizardAnimation = wizardAnimationStanding;
		}
		
		
	}
	
	
	//renders the graphics to the screen
	public void render(GameContainer container, Graphics g) throws SlickException {
		
	    g.setBackground(new Color(150,150,150));				//This sets the background color of the screen to white
		g.drawString(mouse, 50, 50);
	    for(BadGuy bg : ghosts){
	    	if(bg != null){
	    		g.drawAnimation(bg.getAm(), bg.getX(), bg.getY());
	    		g.drawString(bg.getNumber(), bg.getX()+40, bg.getY()-5);
	    	}
	    }
		
		
	    //ghostAnimation.draw(ghostX, ghostY);							//this draws the ghost animation at coordinates 200, 200 from top left of screen.
		wizardAnimation.draw(wizardX, wizardY);							//Draws the wizard standing animation
		//wizardAnimationLeft.draw(200, 400);						//Draws the wizard moving left animation
		//wizardAnimationRight.draw(600, 400);					//Draws the wizard moving right animation
	}
	
	/*public void keyPressed(int key, char c) {
		if(key == Input.KEY_A){
			//go left
		}
		if(key == Input.KEY_D){
			//go right
		}
	}*/
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SetupClass("Setup Test"));		//creates window(container)
		app.setDisplayMode(800, 600, false);				 //first two fields are for the resolution, the last one is boolean for fullscreen
		app.setAlwaysRender(true); 					//This causes the game to render even if the window is not selected/is not in focus
		
		app.start();								//starts the game
	}
	
}
