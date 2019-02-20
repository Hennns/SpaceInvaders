package SpaceWithGraphics;



/**
 * 
 * @author Henning Kjoeita
 * @since 11/28/2016
 * 
 *This class keeps track of the Aliens, and the shot
 *That includes detecting if they collide and moving them each time update is called
 *There is also logic to check if the game is over (all aliens killed or aliens reach the bottom)
 * 
 * 
 */



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.JPanel;


public class Alien extends JPanel  {

	private final int moveDistance =1; //low moveDistance looks better, and easier to play. 1 is a good number
	private final int shotMoveDistance=5; //number of pixels shot moves each cycle. 
	private final int num; //number of aliens per row
	private final int width =35; //width of one alien
	private final int height =25;  //height of one alien
	private final int space =width+10;  // space between each alien (left side to left side)
	private final int shotHeigth =23;
	private final int shotWidth =12;
	private final long sleeptime =10;  //time between each update. around 10 is a good number


	//This let me export as a runnable jar file
	private final Image alienImage = getToolkit().getImage(getClass().getResource("/res2/alien.gif"));
	private final Image shotImage = getToolkit().getImage(getClass().getResource("/res2/shot.gif"));


	private int rows =2; //number of rows with aliens.
	private int x=1; //draw location for alien
	private int y=0;  //draw location for alien
	private int numKills=0; //Used to check for kills in GamePanel
	private int xShot; //draw location for shot
	private int yShot; //draw location for shot


	private boolean goRigth = true;
	private boolean isShotAlive=false;
	private boolean gameOver = false;
	private boolean playerWon = false; 
	private boolean[] alive; //show which aliens are alive (true)



	public Alien(int numAliens){
		this.setBackground(Color.black);

		num=numAliens;
		setAliensAlive();
	}

	public Alien(int numAliens, int numRows){
		this.setBackground(Color.black);

		rows= numRows;
		num=numAliens;
		setAliensAlive();
	}


	//Set every alien to be alive
	private void setAliensAlive(){
		alive= new boolean[rows*num];
		for(int i=0; i<rows*num; i++){
			alive[i]=true;
		}
	}


	//draw aliens
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		//draw aliens
		for(int r=0; r<rows;r++){
			for(int i=0;i<num;i++){
				if(alive[i+num*r]){
					g.drawImage(alienImage, x+space*i, y+r*height, width, height, null);
				}
			}
		}

		//draw shot
		if(isShotAlive){
			g.drawImage(shotImage, xShot, yShot,shotWidth,shotHeigth, null);
		}
	}


	private void moveRigth(){
		x+=moveDistance;
	}

	private void moveLeft(){
		x-=moveDistance;
	}

	//moves the aliens down
	private void moveDown(){
		y+=height;
		//If lowest alien reach the player area the player lose
		if(y+(rows-rowsMissing())*height>this.getHeight()){ 
			gameOver=true;

		}
	}

	//returns number of missing rows with aliens counting from the bottom
	private int rowsMissing(){
		int missing=0;

		//No need to check the last row (r=0).
		for(int r=rows-1; r>0;r--){
			for(int i=num-1;i>=0;i--){
				if(alive[i+num*r]){
					return missing;
				}
			}
			missing++;
		}
		return missing;
	}



	//returns number of aliens missing on a y axis from the right side
	private int rigthSideMissing(){
		int missing =0;
		for(int i=num-1;i>0;i--){
			if(alive[i]||alive[i+num]){
				return missing;
			}
			missing++;
		}
		return missing;
	}

	//returns number of aliens missing on a y axis from the left side
	private int leftSideMissing(){
		int missing =0;
		for(int i=0;i<num;i++){
			if(alive[i]||alive[i+num]){

				return missing;
			}
			missing++;
		}
		return missing;
	}

	public void update(){
		this.repaint();
		moveAliens();
		moveShot();
		gameOverCheck();


		//Delay between each update
		try{
			Thread.sleep(sleeptime); 
		} 
		catch (InterruptedException e){
			e.printStackTrace();
		}

	}

	private void moveAliens(){
		//Checking that the JPanel is valid prevents odd behavior when everything is not yet drawn
		if(this.isValid()){ 

			//Hit right side
			if(x+(space*(num-rigthSideMissing()))>=this.getWidth()){
				goRigth = false;
				moveDown();
			}

			//Hit left side
			else if(x+space*(leftSideMissing())<=0){
				goRigth = true;
				moveDown();
			}
			if(goRigth){
				moveRigth();	
			}
			else{
				moveLeft();
			}
		}
	}


	private void gameOverCheck(){
		for(int i=0; i<alive.length;i++){
			if(alive[i]){
				return;
			}
		}
		playerWon= true;
		gameOver= true;
	}

	//try to fire
	public void fire(int xLocation) {
		if(!isShotAlive){
			new SoundPlayer("/sound-effects/laser-sound.wav",0);
			xShot=xLocation-shotWidth/2;
			yShot=this.getHeight();
			isShotAlive=true;
		}
	}

	//Check if shot hit alien. 
	private boolean hitCheck(){
		//if the shot is not alive there is no need to check
		if(isShotAlive){
			Rectangle shot = new Rectangle(xShot,yShot,shotWidth,shotHeigth);
			for(int r=0; r<rows;r++){
				for(int i=0;i<num;i++){
					if(alive[i+num*r]){
						if(shot.getBounds().intersects(new Rectangle(x+space*i, y+r*height, width, height))){
							alive[i+num*r] =false;
							killShot();
							numKills++;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	//kills the shot so that a new shot may be fired
	private void killShot(){
		isShotAlive=false;
	}

	//updates the location of the shot
	private void moveShot(){
		yShot-=shotMoveDistance;	
		//if out of bounds or hit alien, then killShot
		if(yShot<0 || hitCheck()){
			killShot();
		}
	}

	public boolean isGameOver(){
		return gameOver;
	}

	public boolean getIsShotAlive(){
		return isShotAlive;
	}

	public int getKills(){
		return numKills;
	}

	public boolean playerWon() {
		return playerWon;
	}


}
