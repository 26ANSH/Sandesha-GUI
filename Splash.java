/*
 *  © 26ANSH
 * This Code Belongs To Ansh Vidyabhanu
 * 
 * " Display the flash screen code "
 *  Included with three images = welcome.png, bye.png, intro.png
 * 
 * Project details : 
 * " ~ Sandesha ~ " this is multiuser chat java GUI application
 * the project uses concepts such as socket, java networking, JFrames, Java AWT, Java I/O and JDBC
 * the project was made on December 1 2020 " the covid-19 year "
 * Github account - 26ANSH
 * Please use the code responsibly
 * © 26ANSH
 */

package chat;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JWindow;

public class Splash extends JWindow
{
   Image splashScreen;
   ImageIcon imageIcon;
   
   // Set Image properties
   public Splash(String img) 
   {
	  splashScreen = Toolkit.getDefaultToolkit().getImage(img);
      imageIcon = new ImageIcon(splashScreen);
      setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int x = (screenSize.width-getSize().width)/2;
      int y = (screenSize.height-getSize().height)/2;
      setLocation(x,y);
      setVisible(true);
   }
   
   // Display image onto the Screen
   public void paint(Graphics g)
   {
      super.paint(g);
      g.drawImage(splashScreen, 0, 0, this);
   }
   
   public static void main(String[]args) 
   {
	   // called by other classes as and when required 
   }
   
}