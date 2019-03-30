import java.util.Scanner;
import java.io.*;
import javax.swing.*;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPasswordField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.sql.*;
 
public class Verification extends JFrame
{
	JLabel userName;// Taking components
	JLabel forgetPass;
	JLabel passWord;
	JLabel projectTitle;
	JButton loginButton;
	JButton createAccountButton;
	JTextField userNameText;
	JPasswordField passWordText;	
	
	public Verification()
	{
		this.setTitle("LOGIN VERIFICATION");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Jframe close operation
		this.setSize(700,500);//jframe size
		this.setLocationRelativeTo(null);//Set the frame in center of scene
		this.setLayout(null);
		addComponentToFrame();//adding components in jframe
		this.setVisible(true);//set visibility
	}
	
	public void addComponentToFrame()
	{
		Icon logo=new ImageIcon(getClass().getResource("AIUBlogo.png"));//create ICON using icon class
		
		this.projectTitle=new JLabel("AIUB PROJECT MANAGEMENT SYSTEM");
		this.projectTitle.setFont(new Font("Arrus BT", Font.BOLD, 18));//set the font
		this.projectTitle.setForeground(Color.BLUE);//set project colour
		this.projectTitle.setIcon(logo);//set the logo
		this.projectTitle.setHorizontalTextPosition(SwingConstants.CENTER);//set the jlabel in center
		this.projectTitle.setVerticalTextPosition(SwingConstants.BOTTOM);//set the label in bottom of logo
		this.projectTitle.setBounds(170, 000, 450, 300);//set bound for projectTitle
		this.projectTitle.setToolTipText("AMERICAN INTERNATIONAL UNIVERSITY,BANGLADESH");//set tool tip for AIUB
		
		
		this.userName = new JLabel("User Name");//set username
		this.userName.setForeground(Color.BLUE);//set username background
        this.userName.setBounds(25,330, 90, 30);//set bound for username
		
		this.forgetPass = new JLabel("Forget Password? click HERE");
		this.forgetPass.setForeground(Color.BLUE);
        this.forgetPass.setBounds(25, 420, 180, 30);
		
		this.passWord = new JLabel("Password");
		this.passWord.setForeground(Color.BLUE);
		this.passWord.setBounds(25, 380, 90, 30);
		
		this.userNameText= new JTextField(20);//??
		this.userNameText.setBounds(120, 330, 120, 30);
		this.userNameText.setBackground(Color.YELLOW);
		this.userNameText.setForeground(Color.BLUE);
		
		this.passWordText= new JPasswordField();
		this.passWordText.setEchoChar('*');//set echo Charectar
		this.passWordText.setBounds(120, 380, 120, 30);
		this.passWordText.setBackground(Color.YELLOW);
		this.passWordText.setForeground(Color.BLUE);
		
		this.loginButton=new JButton("LOGIN");
		this.loginButton.setBounds(270, 350, 100, 30);
		
		this.createAccountButton=new JButton("Create New Account");
        this.createAccountButton.setBounds(430, 350, 180, 30);	
		
		this.add(projectTitle); //Adding components in jframe
		this.add(userName);
		this.add(forgetPass);
		this.add(userNameText);
		this.add(passWord);
		this.add(passWordText);
		this.add(loginButton);
		this.add(createAccountButton);
		
		
		this.forgetPass.addMouseListener(new MouseAdapter()  
        {  
             public void mouseClicked(MouseEvent e)  
                {  
       
                dispose();// dispose verification frame
				ForgetClass f1 = new ForgetClass();// open ForgetClass frame

                }  
        }); 
		
		
		loginButton.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Connection conn;
				PreparedStatement pst;
				ResultSet rs;
				String pass=String.valueOf(passWordText.getPassword());

				try // Database Connection
				{
					Class.forName("com.mysql.jdbc.Driver");//Load the driver
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagementsystem","root","");// connect with localhost
					String query = "select * from verification WHERE username='" + userNameText.getText() + "' and password='"
						+ pass + "'"; //check data in query
					pst = (PreparedStatement) conn.prepareStatement(query);// prepareStatement
					rs = (ResultSet) pst.executeQuery(query);// execute the pst
					int count = 0;//use counter as a flag
					while (rs.next()) //check data row wise
					{
						count = count + 1;//make flag one
					}
					if (count == 1)//if password match
					{
						dispose();// dispose verification frame
						ProjectFrame frame = new ProjectFrame();// open project frame
					}
					else
					{//if password wrong
						JOptionPane.showMessageDialog(null,"Invalid Username or Password.");// wrong input
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}			
			}
		});
		
		createAccountButton.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				dispose();// dispose verification frame
				Signup newUser = new Signup();// open Signup frame
			}
		});
	}
}