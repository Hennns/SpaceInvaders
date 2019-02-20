package SpaceWithGraphics;


/**
 * 
 * @author Henning Kjoeita
 * @since 11/28/2016
 * 
 *This is the score Panel on top of the game, it keeps track of the score. 
 *High score is stored in a file so that it is saved even if the game is closed
 *The score Panel can also display a custom message
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JPanel;


public class ScorePanel extends JPanel{

	private final String filename  = "data.dat";

	private int highScore=0;
	private int score=0;
	private int level=1;
	private String message ="Left and Rigth to move. Space to Fire. P to Pause. ESC to Exit.";

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.white);
		g.drawString("Level: "+level, 10, this.getHeight()/2);
		g.drawString("Score: "+score, 70, this.getHeight()/2);
		g.drawString("High: "+highScore, 160, this.getHeight()/2);
		g.drawString(message, 240, this.getHeight()/2);
	
	}

	ScorePanel(){
		this.setPreferredSize(new Dimension(0,30));
		this.setBackground(Color.black);


		//If file does not exist then create it
		if(!(new File("data.dat").isFile())){
			writeData();
		}
		else{
			readData();
		}
	}

	public void setMessage(String s){
		message = s;
		this.repaint();
		
	}

	public void clearMessage(){
		message ="";
		this.repaint();
	}

	public void update(){
		readData();
		updateHighScore();
		clearMessage();
	}



	private void readData(){
		try{
			DataInputStream input = new DataInputStream(new FileInputStream(filename));
			highScore=input.readInt();
			input.close();
		}
		catch (IOException e) {
			System.out.println("readData fail");
			e.printStackTrace();
		}
	}


	private void writeData(){
		try {
			DataOutputStream output = new DataOutputStream(new FileOutputStream(filename));
			output.writeInt(highScore);
			output.close();
		}
		catch (IOException e) {
			System.out.println("writeData fail");
			e.printStackTrace();
		}
	}

	private void updateHighScore(){
		if(score>highScore){
			highScore=score;
			writeData();
		}
	}


	public void addLevel(){
		level++;
		this.repaint();
	}

	public void addScore(int s){
		score+=s;
		writeData();
		update();
	}

	public int getScore(){
		return score;
	}
}





