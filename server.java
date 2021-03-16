/*
 *  © 26ANSH
 * This Code Belongs To Ansh Vidyabhanu
 * 
 * Server side code
 * 
 * Project details : 
 * " ~ Sandesha ~ " this is multiuser chat java GUI application
 * the project uses concepts such as socket, java networking, JFrames, Java AWT, Java I/O and JDBC
 * the project was made on December 1 2020 " the covid-19 year "
 * Github account - 26ANSH
 * Please use the code resposibly
 * © 26ANSH
 */

package chat;

import java.io.*;


import java.sql.*;
import java.net.*;
import java.util.ArrayList;

public class server
{
	static ArrayList<String> activeusers = new ArrayList<String>();
	static ArrayList<PrintWriter> printwriters = new ArrayList<PrintWriter>();
 	static void isdb() throws SQLException
 	{
 		Connection db = DriverManager.getConnection("jdbc:mysql://localhost:8889", "root", "root");
		ResultSet rs = db.getMetaData().getCatalogs();
		 
		while(rs.next())
		{
			String catalogs = rs.getString(1);
			if("sandesha".equals(catalogs))
			{
				return;
			}
		}
		
		Statement st = db.createStatement();
		String sql = "CREATE DATABASE sandesha";
	    st.executeUpdate(sql);
	    st.close();
	    db = DriverManager.getConnection("jdbc:mysql://localhost:8889/sandesha", "root", "root");
	    st = db.createStatement();
		String create_database ="CREATE TABLE details(account_id int NOT NULL AUTO_INCREMENT,username varchar(128), first_name varchar(128), last_name varchar(128), email_id varchar(128), passcode varchar(128), created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, Active boolean, PRIMARY KEY(account_id));\n";
		String cmnd = ("INSERT INTO details (username, first_name, last_name, email_id, passcode, Active) VALUES ('@user', 'user', 'user', 'user@gmail.com', 'user', '0');");
		st.executeUpdate(create_database);
		st.executeUpdate(cmnd);
 	}
	public static void main (String[] args) throws Exception
	{
		isdb(); // this function checks for the database in your local host -> if the database does not exist it creates a database for you
		ServerSocket ss = new ServerSocket(3001);
		
		while(true)
		{
			Socket soc = ss.accept();
			conversation guest = new conversation(soc);
			guest.start();
		}
			
	}
}

class conversation extends Thread
{
	Socket socket;
	Connection db;
	BufferedReader read ;
	PrintWriter send;
	 String guestname;
	String guestpasscode;
	boolean login ;
	
	public conversation(Socket socket) throws Exception
	{
		// TODO Auto-generated constructor stub
		db = DriverManager.getConnection("jdbc:mysql://localhost:8889/sandesha", "root", "root");
		this.socket = socket;
		login = false;
		read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		send = new PrintWriter(socket.getOutputStream(), true);
		server.printwriters.add(send);
	}
	
	
	 public boolean isuseractive() 
	{
		try
    	{
		Statement st = db.createStatement();
		ResultSet r = st.executeQuery("select * from details");
		
		while(r.next())
		{
			if(r.getString("username").equalsIgnoreCase("@"+guestname))
			{
				if (r.getBoolean("Active") == true )
					return true;
				
				return false;
			}

		}
    	} 
	   	 catch( Exception e)
	   	 {
	            System.out.println(e);
	         } 
		return false;
	}
	
	public boolean isuserregistered()
	{
		try
    	{
		Statement st = db.createStatement();
		ResultSet r = st.executeQuery("select * from details");
		
		while(r.next())
		{
			if(r.getString("username").equalsIgnoreCase("@"+guestname))
			{
				return true;
			}
		}
		
	} 
  	 catch( Exception e)
  	 {
           System.out.println(e);
        }
		
		return false;
	}
	
	 public boolean ispasswordcorrect() 
	{
		try
    	{
		Statement st = db.createStatement();
		ResultSet r = st.executeQuery("select * from details");
		
		while(r.next())
		{
			if(r.getString("username").equalsIgnoreCase("@"+guestname))
			{
				if (r.getString("passcode").equals(guestpasscode) )
					return true;
				
				return false;
			}

		}
	} 
  	 catch( Exception e)
  	 {
           System.out.println(e);
        } 
		return false;
	}
	
	public void changeactiveness(boolean i) 
	{
		try
    	{
		Statement st = db.createStatement();
	    String cmnd = ("UPDATE details set Active="+i+" WHERE username='@"+guestname+"'");
		st.executeUpdate(cmnd);

    	} 
	   	 catch( Exception e)
	   	 {
	   		 System.out.println(e);
	     } 
	}
	
	 public void loginprocedure() 
	{
		try
    	{
		  guestname = read.readLine();
		  guestpasscode = read.readLine();

		  
		  if(isuserregistered()== true )
		  {
			  if(isuseractive() == true)
			  {
				  send.println("User found and is active");
				  login = true;
			  }
			  else
			  {  
				  if(ispasswordcorrect()== true ) 
				  {
					  send.println("Welcome to Sandesha");
					  login = true;
					  changeactiveness(true);
				  }
				  else
				  {
					  send.println("wrong username / passcode");
				  }
			  }
		  }
		  else
		  {
			 send.println("User not registered");
		  }
    	} 
   	 catch( Exception e)
   	 {
            System.out.println(e);
         } 
	}
	
	public void run() 
	{
		 try 
		 {      
			 // check for user eligibility
			 
			 loginprocedure();
			 
			 if(login == true)
				 System.out.println("Sucessful login #"+guestname);
			 
			 String message = "\t-------"+guestname+" Welcome to sandesha-------";
			 for(PrintWriter writer : server.printwriters)
			 {
				 writer.println(message);
			 }
			 
			// for messaging
			 while(true)
			 {				 
				 message  = "";
				 message = read.readLine();
				 
				 if(message.equals("logoutrequest#"+guestname))
				 {
					 changeactiveness(false);
					 server.printwriters.remove(send);
					 message = "\t-------"+guestname+" left sandesha-------";
					 for(PrintWriter writer : server.printwriters)
					 {
						 writer.println(message);
					 }
					 break;
				 }
				 
				 if(message != "")
				 for(PrintWriter writer : server.printwriters)
				 {
					 if(writer == send)
					 {
						 writer.println("\t\t\t->"+message);
					 }
					 else
					 {
						 writer.println(guestname + ": " + message);
					 }
				 }
				 
			 }
	
		 } 
		 catch (Exception e) 
		 {
			// TODO: handle exception 
			System.out.println(e);
		}
		 System.out.println("bye user #"+guestname);
	}
	
}
