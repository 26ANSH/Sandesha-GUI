package chat;

import java.io.File;


import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;

public class Sound 
{
	static void music(String music) throws Exception
	{
		  Scanner scanner = new Scanner(System.in);
		  
		  File file = new File(music);
		  AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		  Clip clip = AudioSystem.getClip();
		  
		  clip.open(audioStream);

		  clip.start();
		  
		  //Thread.sleep(4000);
		return;
	}
 public static void main(String[] args) throws Exception
 {
	 music(args[0]);
  }
 
}