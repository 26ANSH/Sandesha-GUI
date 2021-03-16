/*
 * Please Read this   #


 *  © 26ANSH
 * This Code Belongs To Ansh Vidyabhanu
 * 
 * Client side code
 * 
 * Project details : 
 * " ~ Sandesha ~ " this is multiuser chat java GUI application
 * the project uses concepts such as socket, java networking, JFrames, Java AWT, Java I/O and JDBC
 * the project was made on December 1 2020 " the covid-19 year "
 * Github account - 26ANSH
 * Please use the code resposibly
 © 26ANSH
 */

package chat;

import java.io.*;

import java.net.*;
import java.sql.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class client 
{
	// Set up all essential stuff forJfram, Database , Socket
	static String username;
	static String password;
	static JFrame chatwindow = new JFrame("~ Sandesha ~");
	static JTextArea chatarea = new JTextArea(20,40);
	static JTextField textfield = new JTextField(40);
	static JLabel blank = new JLabel("                ");
	static JButton sendbutton = new JButton("Send");
	static JButton instabutton = new JButton("Insta");
	static JButton infobutton = new JButton("Info");
	static JLabel loggedin= new JLabel("          ");
	static BufferedReader read ;
	static PrintWriter send;

	public client() throws Exception
	{
		// TODO Auto-generated constructor stub
		Socket socket = new Socket("localhost",3001);
		read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		send = new PrintWriter(socket.getOutputStream(), true);
	}
	
	void readlogin()
	{
		username = JOptionPane.showInputDialog(chatwindow, "Username", "Login Page - Username", JOptionPane.PLAIN_MESSAGE);
		password = JOptionPane.showInputDialog(chatwindow, ("Password for "+username ), "Login Page - Verify Youself", JOptionPane.PLAIN_MESSAGE);
		send.println(username);
		send.println(password);
	}
	
	boolean loginprocedure() throws Exception
	{
		String msg = "";
	
		while(!(msg.equals("Welcome to Sandesha")))
		{
			readlogin();
			msg = read.readLine();
			
			if(msg.equalsIgnoreCase("User found and is active"))
			{
				Sound.music("src/chat/error.wav");
				JOptionPane.showMessageDialog(chatwindow, username+" is already logged in and happily chatting", "~ User already loged in ~", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			else if(msg.equalsIgnoreCase("wrong username / passcode"))
			{
				Sound.music("src/chat/error.wav");
				JOptionPane.showMessageDialog(chatwindow, username+" Incorrect Login Credentials", "~ wrong details ~", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			else if(msg.equalsIgnoreCase("User not registered") )
			{
				Sound.music("src/chat/error.wav");
				JOptionPane.showMessageDialog(chatwindow, username+" Useer not registered in sandesha", "~ wrong details ~", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
		}
		
		return true;
	}
	
	static void rung(String img, String snd) throws Exception
	{
		Splash run = new Splash(img);
		Sound.music(snd);
		Thread.sleep(2000);
		run.dispose();
	}
	
	// Launch the JFrame and set essential properties
	void launchchatwindow(String user)
	{
		chatwindow.setLayout(new FlowLayout());
		chatarea.setLineWrap(true);
		chatarea.setWrapStyleWord(true);
		chatwindow.add(new JScrollPane(chatarea));
		chatwindow.add(blank);
		chatwindow.add(loggedin);
		chatwindow.add(textfield);
		chatwindow.add(sendbutton);
		chatwindow.getContentPane().setBackground(Color.GREEN);
		chatwindow.add(instabutton);
		chatwindow.add(infobutton);
		
		
		
		chatwindow.addWindowListener(new java.awt.event.WindowAdapter() 
		{
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) 
		    {
		        if (JOptionPane.showConfirmDialog(chatwindow, "Are you sure you want to close this window?", "Close Window?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
		        {
		            send.println("logoutrequest#"+username);
		            JOptionPane.showMessageDialog(chatwindow, " Bye , "+username+" \n We hate to see you go ","Logged out !",JOptionPane.INFORMATION_MESSAGE);
		    	    System.exit(0);
		    	}
		    }
		});
		
		chatwindow.setSize(500, 500);
		chatwindow.setVisible(true);
		
		textfield.setEditable(false);
		chatarea.setEditable(false);
		
		sendbutton.addActionListener(new listen());
		textfield.addActionListener(new listen());
		infobutton.addActionListener(new info());
		instabutton.addActionListener(new calc());
	}
    static boolean alreadyuser()
	{
		if((JOptionPane.showConfirmDialog(chatwindow, "Are You A registered Kabootar ??", " WElcome to Sandesh ", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION))
		{
			return true;
		}
		else
		{
			if((JOptionPane.showConfirmDialog(chatwindow, "Would u like to make a Sandesha Account ??", " WElcome to Sandesh ", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION))
			{
				dblogin pw = new dblogin();
				dblogin.main(null);
				return true;
			}	
			return false;
		}
	}
	void startchat() throws Exception
	{
		loggedin.setText("Username : "+username);
		JOptionPane.showMessageDialog(chatwindow, " Welcome to Sandesha dear, "+username+" \n Happy chatting");
		textfield.setEditable(true);
		
		while(true)
		{
			String message = read.readLine();			
			chatarea.append(message + "\n"); 
		}
		
	}

	public static void main(String[] args) throws Exception
	{
		rung("src/chat/intro.png", "src/chat/start.wav");
		
		if(alreadyuser())
		{
		do
		{
		client guest = new client();

		if(guest.loginprocedure())
		{
			guest.launchchatwindow(guest.username);
			guest.startchat();
		}
		}while(JOptionPane.showConfirmDialog(chatwindow, "Would you like to try again ?", "We hate to see you go !!!", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
		
		}
		else
		{
			
		}
		
		rung("src/chat/bye.png", "src/chat/end.wav" );
		System.exit(0);
	
}
}

// class for sending text messages
class listen implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		client.send.println(client.textfield.getText());
		client.textfield.setText("");
		
	}	
}

class calc implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		calculator cal = new calculator();
		cal.main(null);
	}	
}

class info implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		try
		{
			Connection db = DriverManager.getConnection("jdbc:mysql://localhost:8889/sandesha", "root", "root");
			Statement st = db.createStatement();
			ResultSet r = st.executeQuery("select * from details where Active=true");
			String info = "";
			int i = 1;
			while(r.next())
			{
				info += i+".) "+r.getString("first_name")+"\n";
				i++;
			} 
		    // TODO Auto-generated method stub
			JOptionPane.showMessageDialog(client.chatwindow, info, " ~ Active Users ~ ", JOptionPane.INFORMATION_MESSAGE);
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}	
}



