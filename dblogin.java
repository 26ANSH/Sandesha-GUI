/*
 *  © 26ANSH

 * This Code Belongs To Ansh Vidyabhanu
 * 
 * DataBase Login Frame code
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

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.*; 

// Set up a JFrame for Login Page
class Login extends JFrame 
{
static boolean accntcreated = false;
 static JButton SUBMIT;
 static JPanel panel;
 static JLabel label1,label2,label3,label4,label5;
 static JTextField text1, text2, text3, text4, text5;
  Login()
  {
  label1 = new JLabel();
  label1.setText("\t\tUsername :-");
  text1 = new JTextField(15);

  label2 = new JLabel();
  label2.setText("\t\tPassword :-");
  text2 = new JPasswordField(15);
  
  label3 = new JLabel();
  label3.setText("\t\tFirst Name :-");
  text3 = new JTextField(15);
  
  label4 = new JLabel();
  label4.setText("\t\tLast Name :-");
  text4 = new JTextField(15);
  
  label5 = new JLabel();
  label5.setText("\t\tEmail ID :-");
  text5 =new JTextField(15);
 
  SUBMIT=new JButton("SUBMIT");
  
  
  panel=new JPanel(new GridLayout(6,2));
  panel.add(label1);
  panel.add(text1);
  panel.add(label2);
  panel.add(text2);
  panel.add(label3);
  panel.add(text3);
  panel.add(label4);
  panel.add(text4);
  panel.add(label5);
  panel.add(text5);
  panel.add(SUBMIT);
  text1.setText("");
  text2.setText("");
  text3.setText("");
  text4.setText("");
  text5.setText("");  
  add(panel,BorderLayout.CENTER);
  SUBMIT.addActionListener(new submit());

  }

  // to check if the password is strong enough 
public static boolean pswdeligibility(String password)
{

    // Regex to check valid password. 
	
    String regex = "^(?=.*[0-9])"
                   + "(?=.*[a-z])(?=.*[A-Z])"
                   + "(?=.*[@#$%^&+=])"
                   + "(?=\\S+$).{6,20}$"; 

    Pattern p = Pattern.compile(regex); 
    
    // to check for matching 
    Matcher m = p.matcher(password); 

    return m.matches(); 
}
}
 class dblogin
{	 
  public static void main(String arg[])
  {
  try
  {
  Login frame=new Login();
  frame.setSize(500,300);
  frame.setVisible(true);
  
  while(Login.accntcreated == false)
  {
	  System.out.println("k");
  }
	 PleaseWait ok = new PleaseWait();
	ok.main(null);
	JOptionPane.showMessageDialog(Login.panel, " Your Acconut was Sucessfuly created ", " Walecome To Sandesha ", JOptionPane.INFORMATION_MESSAGE);
	Splash run = new Splash("src/chat/welcome.png");
    try {
        // Make JWindow appear for 10 seconds before disappear
        Sound.music("src/chat/accnt.wav");
        Thread.sleep(2000);
        run.dispose();
     } catch(Exception e) 
     {
        e.printStackTrace();
     }
    frame.setVisible(false);
  }
  catch(Exception e)
  {JOptionPane.showMessageDialog(null, e.getMessage());}
  }
}
 
 class submit implements ActionListener
 {
 	@Override
 	public void actionPerformed(ActionEvent e) 
 	{
 		// TODO Auto-generated method stub
 		try
 		{
 		  String username = "@"+Login.text1.getText();
 		  String password = Login.text2.getText();
 		  String fname = Login.text3.getText();
 		  String lname = Login.text4.getText();
 		  String email = Login.text5.getText();
 		  Connection db = DriverManager.getConnection("jdbc:mysql://localhost:8889/sandesha", "root", "root");
 		  Statement st = db.createStatement();
 		  ResultSet r = st.executeQuery("select * from details");
 		  boolean email_flag = true;
 		 boolean username_flag = true;
 		  while(r.next())
 		  {
 			  if(r.getString("username").equals(username))
 				  username_flag = false;
 			  
 			 if(r.getString("email_id").equals(email))
				  email_flag = false;
 		  }
 		  
 		  if(username.equals("") ||  password.equals("") || fname.equals("") || lname.equals("") || email.equals(""))
 		  {
 			 Sound.music("src/chat/error.wav");
 			 JOptionPane.showMessageDialog(Login.panel, " Fields cannot be empty !! ", " Incomplete Details ", JOptionPane.WARNING_MESSAGE);
 		  } 
 		  else if(email_flag == false || username_flag==false)
 		  {
 			  if(username_flag == false)
 			  {
 				 Sound.music("src/chat/error.wav");
 				  JOptionPane.showMessageDialog(Login.panel, " USername already exists try again!! ", " Username exists ", JOptionPane.WARNING_MESSAGE);
 				  Login.text1.setText("");
 			  }
 			  
 			  if(email_flag == false)
 			  {
 				 Sound.music("src/chat/error.wav");
 				 JOptionPane.showMessageDialog(Login.panel, " Email already exists try again!! ", " Email exists ", JOptionPane.WARNING_MESSAGE);
				  Login.text5.setText("");
 			  }
 		  }
 		  else if (!(Login.pswdeligibility(password)))
 		  {
 			 Sound.music("src/chat/error.wav");
 			 JOptionPane.showMessageDialog(Login.panel, " Please make sure your password passes all parameters \n 1.) atleat 6 charecters long \n 2.) Should contain a uppercase and lowercase alphabet \n 3.) should contain special symbol such as '@' \n 4.) should contan a numeric digit \n 5.) should not contain spaces ", "Password is Weak !!  ", JOptionPane.WARNING_MESSAGE);
 			Login.text2.setText("");
 		  }
 		  else
 		  {
 			  
 			 String cmnd = ("INSERT INTO details (username, first_name, last_name, email_id, passcode, Active) VALUES ('"+username+"', '"+fname+"', '"+lname+"', '"+email+"', '"+password+"', '0');");
 			  System.out.println(cmnd);
 			  st.executeUpdate(cmnd);
 			 Login.accntcreated = true;
 		  }
 		  
 		 System.out.println(username+" : "+fname+":"+lname+":"+email);
 		} 
	   	 catch( Exception er)
	   	 {
	            System.out.println(er);
	         } 
 	}	
 }
