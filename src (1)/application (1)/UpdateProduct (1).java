package application;

import java.awt.*; // Importing the packages
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class UpdateProduct extends JFrame {
    JLabel productIDlb,productCPlb,productSPlb,productDiscountlb;
    JTextField productIDtf,productCPtf,productSPtf,productDiscounttf;
    JButton submit,cancel;
    
    UpdateProduct(){
        setBounds(480,300,750,400);
        setLayout(null);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        productIDlb = new JLabel("PRODUCT ID");
        productIDlb.setBounds(50,50,150,30);
        productIDlb.setFont(new Font("Tahoma", Font.PLAIN , 20));
        add(productIDlb);

        productCPlb = new JLabel("COST PRICE");
        productCPlb.setBounds(50,100,150,30);
        productCPlb.setFont(new Font("Tahoma", Font.PLAIN , 20));
        add(productCPlb);

        productSPlb = new JLabel("SELLING PRICE");
        productSPlb.setBounds(50,150,150,30);
        productSPlb.setFont(new Font("Tahoma", Font.PLAIN , 20));
        add(productSPlb);

        productDiscountlb = new JLabel("DISCOUNT");
        productDiscountlb.setBounds(50,200,150,30);
        productDiscountlb.setFont(new Font("Tahoma", Font.PLAIN , 20));
        add(productDiscountlb);

        productIDtf = new JTextField();
        productIDtf.setBounds(250,50,150,30);
        add(productIDtf);

        productCPtf = new JTextField();
        productCPtf.setBounds(250,100,150,30);
        add(productCPtf);

        productSPtf = new JTextField();
        productSPtf.setBounds(250,150,150,30);
        add(productSPtf);

        productDiscounttf = new JTextField();
        productDiscounttf.setBounds(250,200,150,30);
        add(productDiscounttf);

        submit = new JButton("Submit");
        submit.setBounds(50,300,100,30);
        submit.setForeground(Color.white);
        submit.setFont(new Font("serif",Font.BOLD, 20));
        submit.setBackground(Color.black);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                String id = productIDtf.getText();
                String cp = productCPtf.getText();
                String sp = productSPtf.getText();
                String discount = productDiscounttf.getText();

                if (id.equals("")) {
                    JOptionPane.showMessageDialog(null, "ID should not be empty");
                    return;
                }

                try {
                    ConnectWithSQL conn = new ConnectWithSQL();
                    String query = "update Products set CostPrice="+cp+",SellingPrice="+sp+",Discount="+discount+" where ProductID="+id;
                    conn.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "The Item has been updated successfully");
                    dispose();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(200,300,100,30);
        cancel.setForeground(Color.white);
        cancel.setFont(new Font("serif",Font.BOLD, 20));
        cancel.setBackground(Color.black);
        cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
        });
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("updateProduct.png"));
        Image i2 = i1.getImage().getScaledInstance(180, 165,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(500,80,180,165);
        add(image);

        setVisible(true);
    }
}