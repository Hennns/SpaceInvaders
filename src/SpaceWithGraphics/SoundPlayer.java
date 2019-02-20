package SpaceWithGraphics;


/**
 * 
 * @author Henning Kjoeita
 * @since 11/28/2016
 * 
 *This class plays sounds when given the path to a supported file.
 *How many times a file is repeated can be specified, default is continuously. 
 *
 *
 */


import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundPlayer{

	public SoundPlayer(String soundPathName){
		try {
			//Need to load as URL to work when exported as a runnable jar file
			URL url = SoundPlayer.class.getResource(soundPathName);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);

			Clip clip = AudioSystem.getClip(); 
			clip.open(audioIn);
			clip.loop(clip.LOOP_CONTINUOUSLY);

		} 
		catch (UnsupportedAudioFileException e){
			e.printStackTrace();
		} 
		catch (IOException e){
			e.printStackTrace();
		} 
		catch (LineUnavailableException e){
			e.printStackTrace();
		}
	}

	public SoundPlayer(String soundPathName, int numPlaybacks){
				
		try {
			//Need to load as URL to work when exported as a runnable jar file
			URL url = SoundPlayer.class.getResource(soundPathName);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.loop(numPlaybacks);
	
		} 
		catch (UnsupportedAudioFileException e){
			e.printStackTrace();
		} 
		catch (IOException e){
			e.printStackTrace();
		} 
		catch (LineUnavailableException e){
			e.printStackTrace();
		}
	}

	
}
