import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import javax.swing.table.*;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class ProjectManagement extends JPanel
{
	
	JButton eSaveButton,eGoButton;//Taking Components
	JTextField groupNumberField;
	JTextField groupNameField;
	JTextField projectTitleField,totalMarksField;
	JLabel groupNumberLabel,totalMarks;
	JLabel groupNameLabel;
	JLabel projectTitleLabel;
	JLabel numberOfMember;
	JTextField numberOfMemberField;
	Checkbox doneBox;
	CreateGroupClass g1;
	EditGroupClass e1;
	ProjectManagement s1;
	public int serialx=1;
	DefaultTableModel model;
	JTable jtbl;
	JTable cTable;
	JTable tb;
	JScrollPane sp;
	static Object[][] databaseInfo;
	static DefaultTableModel dTableModel;
	
	ProjectFrame frame;
	JComboBox<String> cSemesterChoose;
	private String semesterNames[]={"2017-18,SPRING","2017-18,SUMMER","2018-19,FALL","2018-19,SPRING","2018-19,SUMMER"};
		 
	String data[][]={ {"","","","",""},    
                          {"","","","",""},    
                          {"","","","",""},{"","","","",""},{"","","","",""}};    
    String column[]={"NAME","ID","SECTION","PART","MARK"};   
	
	public ProjectManagement(){
	
	}
	public ProjectManagement(ProjectFrame frame,CreateGroupClass g1,EditGroupClass e1,ProjectManagement s1){
		this.g1=g1;
		this.s1=s1;
		this.e1=e1;
		this.frame = frame;
		this.setLayout(null);
		this.setBackground(new Color( 82, 144, 78 ));
		addComponentToPanel();
	}
	public void addTable(JTable s)
	{
		this.tb=s;
	}
	
	public JTable getTable()
	{
		return tb;
	}
	
	public void addComponentToPanel(){
		
		
		Icon image1=new ImageIcon(getClass().getResource("GO.png"));//Add Go button
		this.eGoButton = new JButton();
		eGoButton.setIcon(image1);
		this.eGoButton.setBounds(610, 25, 80, 30);
		this.add(eGoButton);
		
		this.groupNumberLabel=new JLabel("Group Number");
		this.groupNumberLabel.setFont(new Font("Arrus BT",Font.BOLD,14));
	    
		this.groupNameLabel=new JLabel("Group Name");
		this.groupNameLabel.setFont(new Font("Arrus BT",Font.BOLD,14));
	    
		this.projectTitleLabel=new JLabel("Project Title");
		this.projectTitleLabel.setFont(new Font("Arrus BT",Font.BOLD,14));
		
		this.numberOfMember=new JLabel("No. of Member");
		this.numberOfMember.setFont(new Font("Arrus BT",Font.BOLD,14));
		
		this.groupNumberField=new JTextField(30);
	    this.groupNameField=new JTextField(50);
	    
		this.projectTitleField=new JTextField(50);
		this.numberOfMemberField=new JTextField(30);
		
		this.groupNumberLabel.setBounds(50, 25, 120, 30);
	    this.groupNameLabel.setBounds(50, 120, 120, 30);
	    this.projectTitleLabel.setBounds(50, 170, 120, 30);
		this.numberOfMember.setBounds(50, 220, 120, 30);
		this.groupNumberField.setBounds(250, 25, 60, 30);
	    this.groupNameField.setBounds(250, 120, 300, 30);
	    this.projectTitleField.setBounds(250, 170, 300, 30);
		this.numberOfMemberField.setBounds(250, 220, 60, 30);
		
		this.add(groupNumberLabel);//Adding components to frame
		this.add(groupNameLabel);
		this.add(projectTitleLabel);
		this.add(groupNumberField);
		this.add(groupNameField);
		this.add(projectTitleField);
		this.add(numberOfMember);
		this.add(numberOfMemberField);
		
		this.groupNameField.setEditable(false);
		this.projectTitleField.setEditable(false);
		this.numberOfMemberField.setEditable(false);
		
		this.cSemesterChoose=new JComboBox<>(semesterNames);
		this.cSemesterChoose.setMaximumRowCount(3);
		this.cSemesterChoose.setBounds(390, 25, 150, 30);
		this.add(cSemesterChoose);
		
		
		
		 
		this.eGoButton.addActionListener(new ActionListener()
		{//Action Listener for eGoButton
			public void actionPerformed(ActionEvent e)
			{
				groupNameField.setEditable(false);
				projectTitleField.setEditable(false);
				numberOfMemberField.setEditable(false);
				String groupNumber=groupNumberField.getText();
				g1.getGroupNumber(groupNumber);//set value in createGroupClass method
				String semesterChoose = cSemesterChoose.getSelectedItem().toString();
				try 
				{
					Class.forName("com.mysql.jdbc.Driver");//load driver
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagementsystem","root","");
					Statement stmt = con.createStatement();
					String sql1= "SELECT `groupname`,`projecttitle`,`totalmember` FROM `create_group_info` WHERE groupnumber='"+groupNumber+"' and semester='"+semesterChoose+"'";
					ResultSet rs1   =  stmt.executeQuery(sql1);
					while(rs1.next())
					{
						groupNameField.setText(rs1.getString("groupname"));//Show Textfield output
						projectTitleField.setText(rs1.getString("projecttitle"));
						numberOfMemberField.setText(rs1.getString("totalmember"));
					}
					con.close();
				}
				catch(Exception e3)
				{
					System.out.println(e3);
				}
				
				model = new DefaultTableModel();
				jtbl = new JTable(model);
				model.addColumn("NAME"); //Column for DefaultTableModel
				model.addColumn("ID");
				model.addColumn("SECTION");
				model.addColumn("PART");
				model.addColumn("MARK");
				
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					Connection cont = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagementsystem","root","");
					PreparedStatement pstm = cont.prepareStatement("SELECT `sname`,`sid`,`ssec`,`spart`,`mark` FROM `create_group` WHERE sgroupno='"+groupNumber+"' and semester='"+semesterChoose+"'");
					ResultSet Rs = pstm.executeQuery();	
					while(Rs.next())
					{
						model.addRow(new Object[]{Rs.getString(1), Rs.getString(2),Rs.getString(3),Rs.getString(4),Rs.getString(5)});
					//show values in Table
					}
				}
				catch (Exception em)
				{
					System.out.println(em.getMessage());
				}
				JScrollPane pg = new JScrollPane(jtbl);
				pg.setBounds(60,330,650,100);
				add(pg);
				s1.addTable(jtbl);//add table
			}
		});	
	}
}