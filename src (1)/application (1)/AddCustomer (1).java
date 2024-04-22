package application;

import java.awt.*; // Importing the packages
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class AddCustomer extends JFrame {
    JLabel customerNamelb, customerIDlb, customerEmaillb, customerPhoneNolb,customerPasswordlb;
    JTextField customerNametf, customerIDtf, customerEmailtf, customerPhoneNotf,customerPasswordtf;
    JButton submit, cancel;

    AddCustomer() {
        setBounds(100, 100, 900, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        customerNamelb = new JLabel("NAME");
        customerNamelb.setBounds(50, 50, 120, 30);
        customerNamelb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(customerNamelb);

        customerNametf = new JTextField();
        customerNametf.setBounds(200, 50, 150, 30);
        add(customerNametf);

        customerIDlb = new JLabel("ID");
        customerIDlb.setBounds(50, 110, 120, 30);
        customerIDlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(customerIDlb);

        customerIDtf = new JTextField();
        customerIDtf.setBounds(200, 110, 150, 30);
        add(customerIDtf);

        customerEmaillb = new JLabel("EMAIL");
        customerEmaillb.setBounds(50, 170, 120, 30);
        customerEmaillb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(customerEmaillb);

        customerEmailtf = new JTextField();
        customerEmailtf.setBounds(200, 170, 150, 30);
        add(customerEmailtf);

        customerPhoneNolb = new JLabel("PHONE NO");
        customerPhoneNolb.setBounds(50, 230, 120, 30);
        customerPhoneNolb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(customerPhoneNolb);

        customerPhoneNotf = new JTextField();
        customerPhoneNotf.setBounds(200, 230, 150, 30);
        add(customerPhoneNotf);

        customerPasswordlb = new JLabel("PASSWORD");
        customerPasswordlb.setBounds(50,290,120,30);
        customerPasswordlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(customerPasswordlb);

        customerPasswordtf = new JTextField();
        customerPasswordtf.setBounds(200, 290, 150, 30);
        add(customerPasswordtf);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("newCustomer.png"));
        Image i2 = i1.getImage().getScaledInstance(450, 450, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 50, 450, 450);
        add(image);

        submit = new JButton("Submit");
        submit.setBounds(50, 400, 100, 30);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("serif", Font.BOLD, 20));
        submit.setBackground(Color.black);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String name = customerNametf.getText();
                String id = customerIDtf.getText();
                String email = customerEmailtf.getText();
                String phoneno = customerPhoneNotf.getText();
                String password = customerPasswordtf.getText();
                String hash = SHA.getSHA256Hash(password);

                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Name should not be empty");
                    return;
                }

                if (phoneno.length() != 10) {
                    JOptionPane.showMessageDialog(null, "Invalid Phone number");
                    return;
                }

                if (!email.contains("@")) {
                    JOptionPane.showMessageDialog(null, "Invalid Email ID");
                    return;
                }

                try {
                    ConnectWithSQL conn = new ConnectWithSQL();
                    String query = "insert into Customers values(" + id + ",'" + name + "','" + email + "'," + phoneno + ",'"+hash+"')";
                    conn.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Customer added successfully");
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
        cancel.setBounds(180, 400, 100, 30);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("serif", Font.BOLD, 20));
        cancel.setBackground(Color.black);
        cancel.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent ae) {
        		 dispose();
        	 }
        });
        add(cancel);
        getContentPane().setBackground(Color.white);
        setVisible(true);
    }
}