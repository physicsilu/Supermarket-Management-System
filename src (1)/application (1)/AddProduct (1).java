package application;

import java.awt.*; // Importing the packages
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class AddProduct extends JFrame {
    JLabel productNamelb,productIDlb,productCategorylb,productDiscountlb,productCPlb,productSPlb,productQuantitylb;
    JTextField productNametf,productIDtf,productCategorytf,productDiscounttf,productCPtf,productSPtf,productQuantitytf;
    JButton submit,cancel;
    
    public AddProduct(){
        setBounds(100,100,1000,600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        productNamelb = new JLabel("NAME");
        productNamelb.setBounds(50,50,120,30);
        productNamelb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(productNamelb);

        productNametf = new JTextField();
        productNametf.setBounds(200,50,150,30);
        add(productNametf);

        productIDlb = new JLabel("ID");
        productIDlb.setBounds(50,110,120,30);
        productIDlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(productIDlb);

        productIDtf = new JTextField();
        productIDtf.setBounds(200,110,150,30);
        add(productIDtf);

        productCategorylb = new JLabel("CATEGORY");
        productCategorylb.setBounds(50,170,120,30);
        productCategorylb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(productCategorylb);

        productCategorytf = new JTextField();
        productCategorytf.setBounds(200,170,150,30);
        add(productCategorytf);

        productCPlb = new JLabel("COST PRICE");
        productCPlb.setBounds(50,230,120,30);
        productCPlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(productCPlb);

        productCPtf = new JTextField();
        productCPtf.setBounds(200,230,150,30);
        add(productCPtf);

        productSPlb = new JLabel("SELLING PRICE");
        productSPlb.setBounds(50,290,150,30);
        productSPlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(productSPlb);

        productSPtf = new JTextField();
        productSPtf.setBounds(200,290,150,30);
        add(productSPtf);

        productQuantitylb = new JLabel("QUANTITY");
        productQuantitylb.setBounds(50,340,120,30);
        productQuantitylb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(productQuantitylb);

        productQuantitytf = new JTextField();
        productQuantitytf.setBounds(200,340,150,30);
        add(productQuantitytf);

        productDiscountlb = new JLabel("DISCOUNT");
        productDiscountlb.setBounds(50,400,120,30);
        productDiscountlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(productDiscountlb);

        productDiscounttf = new JTextField();
        productDiscounttf.setBounds(200,400,150,30);
        add(productDiscounttf);

        submit = new JButton("Submit");
        submit.setBounds(50,480,100,30);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("serif",Font.BOLD, 20));
        submit.setBackground(Color.black);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                String name = productNametf.getText();
                String id = productIDtf.getText();
                String category = productCategorytf.getText();
                String discount = productDiscounttf.getText();
                String cp = productCPtf.getText();
                String sp = productSPtf.getText();
                String quantity = productQuantitytf.getText();

                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Name should not be empty");
                    return;
                }
                if(Integer.parseInt(discount)>=100){
                    JOptionPane.showMessageDialog(null, "Discount cannot exceed 100%");
                    return;
                }
                if (quantity.equals("")) {
                    JOptionPane.showMessageDialog(null, "Quantity should not be empty");
                    return;
                }

                try {
                    ConnectWithSQL conn = new ConnectWithSQL();
                    String query2 = "select * from ProductCategories where ProductName = '"+name+"'";
                    ResultSet result = conn.s.executeQuery(query2);
                    if (result.next()) {
                        if (category.compareTo(result.getString("category"))==0) {
                        // JOptionPane.showMessageDialog(null, "The Product has been added successfully");
                        }
                        else{
                        JOptionPane.showMessageDialog(null, category+"already exists");
                        }
                    }
                    else{
                        String query3 = "insert into ProductCategories values('"+name+"','"+category+"')";
                        conn.s.executeUpdate(query3);
                    }
                    String query = "insert into Products values("+id+",'"+name+"',"+cp+","+sp+","+quantity+","+discount+")";
                    conn.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "The Product has been added successfully");
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
        cancel.setBounds(180,480,100,30);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("serif",Font.BOLD, 20));
        cancel.setBackground(Color.black);
        cancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		dispose();
        	}
        });
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("newProduct.jpg")); //TODO
        Image i2 = i1.getImage().getScaledInstance(550, 450,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400,50,550,450);
        add(image);
        getContentPane().setBackground(Color.white);
        setVisible(true);
    }

    // public static void main(String[] args) {
    //     new AddItem();
    // }
}
