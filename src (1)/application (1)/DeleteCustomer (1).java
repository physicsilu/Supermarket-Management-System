package application;

import java.awt.*; // Importing the packages
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class DeleteCustomer extends JFrame{
    JLabel customerIDlb;
    JTextField customerIDtf;
    JButton submit,cancel;
    
    DeleteCustomer(){
        setBounds(480,300,750,300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.white);

        customerIDlb = new JLabel("CUSTOMER ID");
        customerIDlb.setBounds(50,50,150,30);
        customerIDlb.setFont(new Font("Tahoma", Font.PLAIN , 20));
        add(customerIDlb);

        customerIDtf = new JTextField();
        customerIDtf.setBounds(250,50,150,30);
        add(customerIDtf);

        submit = new JButton("Submit");
        submit.setBounds(50,150,100,30);
        submit.setForeground(Color.white);
        submit.setFont(new Font("serif",Font.BOLD, 20));
        submit.setBackground(Color.black);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                String id = customerIDtf.getText();

                if (id.equals("")) {
                    JOptionPane.showMessageDialog(null, "ID should not be empty");
                    return;
                }

                try {
                    ConnectWithSQL conn = new ConnectWithSQL();
                    String query = "delete from Customers where CustomerID ="+id;
                    conn.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "The Customer has been deleted successfully");
                    dispose();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(200,150,100,30);
        cancel.setForeground(Color.white);
        cancel.setFont(new Font("serif",Font.BOLD, 20));
        cancel.setBackground(Color.black);
        cancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		dispose();
        	}
	    });
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("deleteCustomer.png"));
        Image i2 = i1.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(500,50,150,150);
        add(image);
        setVisible(true);
    }
}