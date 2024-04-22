package application;

import java.awt.*; // Importing the packages
import java.awt.event.*;
import javax.swing.*;
import java.sql.SQLException;

public class AddEmployee extends JFrame{
    JLabel employeeNamelb,employeeIDlb,employeeRolelb,employeeUserIDlb,employeePasswordlb;
    JTextField employeeNametf,employeeIDtf,employeeUserIDtf,employeePasswordtf,employeeRoletf;
    JButton submit,cancel;
    
    AddEmployee(){
        setBounds(100,100,1000,600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        employeeNamelb = new JLabel("NAME");
        employeeNamelb.setBounds(50,50,120,30);
        employeeNamelb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(employeeNamelb);

        employeeNametf = new JTextField();
        employeeNametf.setBounds(200,50,150,30);
        add(employeeNametf);

        employeeIDlb = new JLabel("ID");
        employeeIDlb.setBounds(50,110,120,30);
        employeeIDlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(employeeIDlb);

        employeeIDtf = new JTextField();
        employeeIDtf.setBounds(200,110,150,30);
        add(employeeIDtf);

        employeeRolelb = new JLabel("ROLE");
        employeeRolelb.setBounds(50,170,120,30);
        employeeRolelb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(employeeRolelb);

        employeeRoletf = new JTextField();
        employeeRoletf.setBounds(200,170,150,30);
        add(employeeRoletf);

        employeeUserIDlb = new JLabel("USER ID");
        employeeUserIDlb.setBounds(50,230,120,30);
        employeeUserIDlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(employeeUserIDlb);

        employeeUserIDtf = new JTextField();
        employeeUserIDtf.setBounds(200,230,150,30);
        add(employeeUserIDtf);

        employeePasswordlb = new JLabel("PASSWORD");
        employeePasswordlb.setBounds(50,290,120,30);
        employeePasswordlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(employeePasswordlb);

        employeePasswordtf = new JTextField();
        employeePasswordtf.setBounds(200,290,150,30);
        add(employeePasswordtf);

        submit = new JButton("Submit");
        submit.setBounds(50,400,100,30);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("serif",Font.BOLD, 20));
        submit.setBackground(Color.black);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                String name = employeeNametf.getText();
                String id = employeeIDtf.getText();
                String userId = employeeUserIDtf.getText();
                String password = employeePasswordtf.getText();
                String role = employeeRoletf.getText();

                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Name should not be empty");
                    return;
                }

                try {
                    ConnectWithSQL conn = new ConnectWithSQL();
                    String query = "insert into Employees values("+id+",'"+name+"','"+userId+"','"+password+"','"+role+"')";
                    conn.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null,"Employee added successfully");
                    dispose();
                } catch (SQLException e) {
                    // Check if the exception is due to a primary key violation
                    if (e.getErrorCode() == 1062) { // Error code for duplicate primary key
                        JOptionPane.showMessageDialog(null,
                                "Error: Duplicate primary key. Please enter a unique value.", "Primary Key Violation",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Handle other SQL exceptions
                        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }        
                }
            }
        });
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(180,400,100,30);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("serif",Font.BOLD, 20));
        cancel.setBackground(Color.black);
        cancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		dispose();
        	}
        });
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("newEmployee.jpg"));
        Image i2 = i1.getImage().getScaledInstance(550, 450,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400,50,550,450);
        add(image);
        getContentPane().setBackground(Color.white);
        setVisible(true);
    }
}