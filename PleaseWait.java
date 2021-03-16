/*
 *  © 26ANSH

 * This Code Belongs To Ansh Vidyabhanu
 * 
 * Progress bar code
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

import javax.swing.*;   
// SetUp a default JProgress bar as we require
public class PleaseWait extends JFrame
{    
	JProgressBar jb;    
	int i=0,num=0;     
	PleaseWait ()
	{    
		jb = new JProgressBar(0,1000); 
		jb.setBounds(40,40,160,30);         
		jb.setValue(0);    
		jb.setStringPainted(true);    
		add(jb);    
		setSize(250,150);    
		setLayout(null);    
	}   
	
	
	//for movement of the Jbar
	public boolean iterate()
	{    
		while(i<=1000)
		{    
			jb.setValue(i);    
			i=i+20;    
			try
			{
				Thread.sleep(150);
			}
			catch(Exception e){}  
		} 
		return true;
	}    
	
	
	public void main(String[] args) 
	{    
		PleaseWait m=new PleaseWait ();    
		m.setVisible(true);  
		
		if(m.iterate())
			m.setVisible(false);
}    
}   