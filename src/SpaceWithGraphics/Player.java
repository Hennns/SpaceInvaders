package SpaceWithGraphics;




/**
 * 
 * @author Henning Kjoeita
 * @since 11/28/2016
 * 
 *This is where the player is. 
 *This class simply draws the player on the screen
 *
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


public class Player extends JPanel {

	private final int width =33;
	private final int heigth =23;
	private final Image playerImage = getToolkit().getImage(getClass().getResource("/res2/ship.gif"));
	private int x =450-width;

	Player(){
		this.setPreferredSize(new Dimension(0,25));
		this.setBackground(Color.black);
	}


	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(playerImage, x, 0, width, heigth, null);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(0, 0, this.getWidth(), 0);

	}


	//return the x-center of the player icon
	public int xLocation(){
		return x+width/2;
	}
	


	//Moves the player. Cannot go outside the board
	public void move(int xChange){
		x+=xChange;

		if(x+width>=this.getWidth()){
			x=this.getWidth()-width;
		}

		else if(x<0){
			x=0;
		}
	}
}