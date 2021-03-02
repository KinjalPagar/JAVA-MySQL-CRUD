import java.awt.EventQueue;
import java.sql.*;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JAVACrud {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtbedition;
	private JTextField txtbprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JAVACrud window = new JAVACrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JAVACrud() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pre;
	ResultSet rs;
	
	public void Connect()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud","root","");
			
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	public void table_load()
	{
		try
		{
			pre = con.prepareStatement("select * from book");
			rs = pre.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1008, 531);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setBounds(346, 11, 221, 57);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(49, 79, 427, 261);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(27, 83, 124, 30);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Book Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(27, 137, 124, 30);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Book Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_2.setBounds(27, 198, 124, 30);
		panel.add(lblNewLabel_1_2);
		
		txtbname = new JTextField();
		txtbname.setBounds(226, 80, 159, 30);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtbedition = new JTextField();
		txtbedition.setColumns(10);
		txtbedition.setBounds(226, 145, 159, 30);
		panel.add(txtbedition);
		
		txtbprice = new JTextField();
		txtbprice.setColumns(10);
		txtbprice.setBounds(226, 206, 159, 30);
		panel.add(txtbprice);
		
		JButton btnsave = new JButton("SAVE");
		btnsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname, bedition, bprice;
				
				bname = txtbname.getText();
				bedition = txtbedition.getText();
				bprice = txtbprice.getText();
				
				try
				{
					pre = con.prepareStatement("insert into book(name, edition, price) values(?, ?, ?)");
					pre.setString(1, bname);
					pre.setString(2, bedition);
					pre.setString(3, bprice);
					
					pre.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added Successfully!!!");
					
					table_load();
					
					txtbname.setText("");
					txtbedition.setText("");
					txtbprice.setText("");
					
					txtbname.requestFocus();
				}
				catch(SQLException e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		btnsave.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnsave.setBounds(49, 351, 124, 49);
		frame.getContentPane().add(btnsave);
		
		JButton btnexit = new JButton("EXIT");
		btnexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnexit.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnexit.setBounds(201, 351, 124, 49);
		frame.getContentPane().add(btnexit);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtbname.setText("");
				txtbedition.setText("");
				txtbprice.setText("");
				txtbname.requestFocus();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnClear.setBounds(352, 351, 124, 49);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(484, 78, 486, 325);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(49, 411, 398, 74);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Book ID : ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_2.setBounds(10, 26, 144, 37);
		panel_1.add(lblNewLabel_2);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try
				{
					String id = txtbid.getText();
					
					pre = con.prepareStatement("select name, edition, price from book where id = ?");
					pre.setString(1, id);
					ResultSet rs = pre.executeQuery();
					
					if(rs.next() == true)
					{
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						txtbname.setText(name);
						txtbedition.setText(edition);
						txtbprice.setText(price);
					}
					else
					{
						txtbname.setText("");
						txtbedition.setText("");
						txtbprice.setText("");
					}
				}
				catch (SQLException ex) {
					// TODO: handle exception
				}
				
			}
		});
		txtbid.setBounds(114, 29, 245, 37);
		panel_1.add(txtbid);
		txtbid.setColumns(10);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname, bedition, bprice, bid;
				
				bname = txtbname.getText();
				bedition = txtbedition.getText();
				bprice = txtbprice.getText();
				bid = txtbid.getText();
				
				try
				{
					pre = con.prepareStatement("update book set name = ?, edition = ?, price = ? where id = ?");
					pre.setString(1, bname);
					pre.setString(2, bedition);
					pre.setString(3, bprice);
					pre.setString(4, bid);
					
					pre.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated Successfully!!!");
					
					table_load();
					
					txtbname.setText("");
					txtbedition.setText("");
					txtbprice.setText("");
					
					txtbname.requestFocus();
				}
				catch(SQLException e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUpdate.setBounds(531, 420, 124, 49);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bid;
				
				bid = txtbid.getText();
				
				try
				{
					pre = con.prepareStatement("delete from book where id = ?");
					pre.setString(1, bid);
					
					pre.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted Successfully!!!");
					
					table_load();
					
					txtbname.setText("");
					txtbedition.setText("");
					txtbprice.setText("");
					
					txtbname.requestFocus();
				}
				catch(SQLException e1)
				{
					e1.printStackTrace();
				}
				

				
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDelete.setBounds(708, 420, 124, 49);
		frame.getContentPane().add(btnDelete);
	}
}
