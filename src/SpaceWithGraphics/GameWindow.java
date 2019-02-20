package SpaceWithGraphics;


/**
 * 
 * @author Henning Kjoeita
 * @since 11/23/2016
 * 
 *
 *The frame, it contains and creates GamePanel
 *also adds KeyListner to GamePanel and forwards keys pressed
 *The frame does can be resizeable, but that would make it very easy to beat the game
 *
 */


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class GameWindow extends JFrame {


	public static void main(String[] args){


		JFrame frame = new JFrame();
		frame.setSize(900,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Space Invaders");


		GamePanel game = new GamePanel();
		frame.add(game);


		//Add listeners to the GamePanel
		game.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e){
				game.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//left empty
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				//left empty
			}
		});

		game.setFocusable(true);
		frame.setResizable(false);
		frame.setVisible(true); 


	}

}
