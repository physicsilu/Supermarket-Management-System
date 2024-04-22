package application;

import java.awt.*; // Importing the packages
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class AddVendor extends JFrame {
    JLabel vendorNamelb,vendorIDlb,vendorContactlb;
    JTextField vendorNametf,vendorIDtf,vendorContacttf;
    JButton submit,cancel;
    
    AddVendor(){
        setBounds(100,100,1000,600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        vendorNamelb = new JLabel("NAME");
        vendorNamelb.setBounds(50,50,120,30);
        vendorNamelb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(vendorNamelb);

        vendorNametf = new JTextField();
        vendorNametf.setBounds(200,50,150,30);
        add(vendorNametf);

        vendorIDlb = new JLabel("ID");
        vendorIDlb.setBounds(50,110,120,30);
        vendorIDlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(vendorIDlb);

        vendorIDtf = new JTextField();
        vendorIDtf.setBounds(200,110,150,30);
        add(vendorIDtf);

        vendorContactlb = new JLabel("CONTACT");
        vendorContactlb.setBounds(50,170,120,30);
        vendorContactlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(vendorContactlb);

        vendorContacttf = new JTextField();
        vendorContacttf.setBounds(200,170,150,30);
        add(vendorContacttf);

        submit = new JButton("Submit");
        submit.setBounds(50,350,100,30);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("serif",Font.BOLD, 20));
        submit.setBackground(Color.black);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                String name = vendorNametf.getText();
                String id = vendorIDtf.getText();
                String contact = vendorContacttf.getText();

                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Name should not be empty");
                    return;
                }

                try {
                    ConnectWithSQL conn = new ConnectWithSQL();
                    String query = "insert into Vendors values("+id+",'"+name+"','"+contact+"')";
                    conn.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "New Vendor added successfully");
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
        cancel.setBounds(180,350,100,30);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("serif",Font.BOLD, 20));
        cancel.setBackground(Color.black);
        cancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		dispose();
        	}
        });
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("newVendor.png"));
        Image i2 = i1.getImage().getScaledInstance(550, 450,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400,50,550,450);
        add(image);
        getContentPane().setBackground(Color.white);
        setVisible(true);
    }
}