package SpaceWithGraphics;

/**
 * 
 * @author Henning Kjoeita
 * @since 11/28/2016
 * 
 *Main JPanel, creates and contains the Alien, Player, and ScorePanel Panels. 
 *Responsible for reading inputs (keyPressed) and acting accordingly
 *The game loop is here. 
 *
 *
 */



import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable{


	private final int playerMoveDistance =5;
	private final int scoreMultiplier = 100;
	private final int nextlevelBonus =5*scoreMultiplier;
	private final Player player = new Player();
	private final ScorePanel sp = new ScorePanel(); 
	private final int maxLevel =5;

	private	Alien a;  
	private int level=0;
	private int kills=0;
	private boolean pause = false;



	//adds other Panels to GamePanel and begins the game
	public GamePanel(){
		this.setLayout(new BorderLayout());
		a =  new Alien(5);

		add(sp,BorderLayout.NORTH);
		add(a,BorderLayout.CENTER);
		add(player, BorderLayout.SOUTH);


		new SoundPlayer("/sound-effects/UpbeatFunk.wav");

		Thread thread = new Thread(this);
		thread.start();

	}



	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_LEFT){
			movePlayer(-playerMoveDistance);
		}

		if(key == KeyEvent.VK_RIGHT){
			movePlayer(playerMoveDistance);
		}

		if(key == KeyEvent.VK_SPACE){
			if(a.getIsShotAlive()){
				sp.setMessage("Only 1 shot at a time!");
			}

			else if(!pause && !a.isGameOver()){
				sp.clearMessage();
				a.fire(player.xLocation());
			}
		}

		if(key == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}


		//pauses the game
		if(key == KeyEvent.VK_P){
			pause = !pause;

			if(pause){
				sp.setMessage("press P to resume");	
			}
			else{
				sp.clearMessage();
			}
		}

		//next level
		if(key == KeyEvent.VK_ENTER){
			if(a.isGameOver()&& a.playerWon()&&level!=maxLevel){

				nextLevel();
				sp.clearMessage();
				pause= false;
			}
		}
	}


	//Moves the player sprite
	private void movePlayer(int x){
		if(!pause){
			player.move(x);
			player.repaint();
		}
	}


	public void run(){

		while(!a.isGameOver()){

			if(!pause){
				player.repaint(); //need this line to show the player when creating the game
				a.update();
				if(a.getKills()>kills){
					new SoundPlayer("/sound-effects/AntiAircraftGun.wav",0);
					kills=a.getKills();
					sp.addScore(scoreMultiplier);
				}
			}
			else{
				try {
					Thread.sleep(10);
				} 
				catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		if(a.playerWon()){
			sp.setMessage("Press enter for next level");
		}
		else{
			sp.setMessage("You lost-Press escape to quit");
		}
	}


	private void nextLevel(){
		sp.addScore(nextlevelBonus);
		level++;

		switch(level){

		case 1: 
		{
			a= new Alien(10);
			break;
		}
		case 2:
		{
			a= new Alien(15,3);
			break;
		}
		case 3:
		{
			a= new Alien(15,4);
			break;
		}
		case 4:
		{
			a= new Alien(15,5);
			break;
		}

		default:
			sp.setMessage("You won- Press escape to quit");
			Thread.currentThread().stop();

		}

		this.add(a,BorderLayout.CENTER);
		this.revalidate();
		kills=0;
		sp.addLevel();
		new SoundPlayer("/sound-effects/level-up.wav",0);

		Thread thread = new Thread(this);
		thread.start();
	}



}
