package application;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MakeOrder extends JFrame implements ActionListener {
    JPanel p1;
    JLabel text, category, availableItems, electronic, food, stationery, hygiene, vendor, quantityLabel;
    JButton b1, electronicButton, foodButton,  hygieneButton, stationeryButton,  submit, cancel;
    JTextField qTextField;
    JComboBox<String> electronicsBox,foodBox, hygieneBox, stationeryBox, vendorBox;
    JScrollPane jBar;
    JTable orderTable;
    int count = 0;
    int EmployeeID;

    MakeOrder(int id){
    	this.EmployeeID = id;
        setBounds(100,100,1400,600); // creating a frame
        setLayout(null); // This helps in customizing our layout

        getContentPane().setBackground(new Color(0, 255, 255));

        String[][] order = new String[100][4];
        
        p1 = new JPanel();
        p1.setBounds(800, 0, 600, 600);
        p1.setBackground(new Color(128, 128, 0));
        p1.setLayout(null);

        getContentPane().add(p1);
        p1.setVisible(false);

        text = new JLabel("PLACE ORDERS");
        text.setBounds(50,25,500,50);
        text.setForeground(Color.BLACK);// choosing the colour of text
        text.setFont(new Font("serif", Font.BOLD, 25)); // choosing the font of text
        getContentPane().add(text);// adding text to the frame

        // Creating categories
        category = new JLabel("Category");
        category.setBounds(50,75,150,30);
        category.setForeground(Color.BLUE);
        category.setFont(new Font("serif", Font.BOLD, 20));
        getContentPane().add(category);

        availableItems = new JLabel("Available Items");
        availableItems.setFont(new Font("serif", Font.BOLD, 20));
        availableItems.setForeground(Color.BLUE);
        availableItems.setBounds(300,75,200,30);
        getContentPane().add(availableItems);

        electronic = new JLabel("Electronic");
        electronic.setForeground(Color.BLACK);
        electronic.setFont(new Font("serif", Font.BOLD, 18));
        electronic.setBounds(50,110,100,30);
        getContentPane().add(electronic);

        food = new JLabel("Food");
        food.setForeground(Color.BLACK);
        food.setFont(new Font("serif", Font.BOLD, 18));
        food.setBounds(50,150,100,30);
        getContentPane().add(food);

        stationery = new JLabel("Hygiene");
        stationery.setForeground(Color.BLACK);
        stationery.setFont(new Font("serif", Font.BOLD, 18));
        stationery.setBounds(50,190,200,30);
        getContentPane().add(stationery);

        hygiene = new JLabel("Stationery");
        hygiene.setForeground(Color.BLACK);
        hygiene.setFont(new Font("serif", Font.BOLD, 18));
        hygiene.setBounds(50,230,200,30);
        getContentPane().add(hygiene);

        vendor = new JLabel("Vendor");
        vendor.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 20));
        vendor.setBounds(650, 75, 200, 30);
        getContentPane().add(vendor);

        quantityLabel = new JLabel("Enter Quantity");
        quantityLabel.setBounds(300, 360, 150, 30);
        quantityLabel.setForeground(Color.BLACK);
        quantityLabel.setFont(new Font("serif", Font.BOLD, 18));
        getContentPane().add(quantityLabel);

        qTextField = new JTextField();
        qTextField.setBounds(500, 360, 30, 30);
        qTextField.setFont(new Font("serif", Font.PLAIN, 18));
        getContentPane().add(qTextField);

        // int count = 0;

        try{
            ConnectWithSQL con = new ConnectWithSQL();
            ResultSet res = con.s.executeQuery("select count(*) from ProductCategories where Category = 'Electronic'");
            res.next();
            int x = res.getInt(1);
            res.close();
            // System.out.println(x);
            ResultSet rs = con.s.executeQuery("select ProductName from ProductCategories where Category = 'Electronic';");
            String[] e = new String[x];
            int i = 0;
            while(rs.next()){
                e[i] = rs.getString("ProductName");
                i++;
            }
            rs.close();
            // for(int j = 0; j < x; j++)
            //     System.out.println(e[j]);

            // Creating dropdown boxes for them
            electronicsBox = new JComboBox<>(e);
            electronicsBox.setBounds(300,110,150,30);
            getContentPane().add(electronicsBox);

            res = con.s.executeQuery("select count(*) from ProductCategories where Category = 'Food'");
            res.next();
            x = res.getInt(1);
            res.close();
            rs = con.s.executeQuery("select ProductName from ProductCategories where Category = 'Food';");
            e = new String[x];
            i = 0;
            while(rs.next()){
                e[i] = rs.getString("ProductName");
                i++;
            }
            rs.close();
            foodBox = new JComboBox<>(e);
            foodBox.setBounds(300,150,150,30);
            getContentPane().add(foodBox);

            res = con.s.executeQuery("select count(*) from ProductCategories where Category = 'Hygiene'");
            res.next();
            x = res.getInt(1);
            res.close();
            rs = con.s.executeQuery("select ProductName from ProductCategories where Category = 'Hygiene';");
            e = new String[x];
            i = 0;
            while(rs.next()){
                e[i] = rs.getString("ProductName");
                i++;
            }
            rs.close();
            hygieneBox = new JComboBox<>(e);
            hygieneBox.setBounds(300,190,150,30);
            getContentPane().add(hygieneBox);

            res = con.s.executeQuery("select count(*) from ProductCategories where Category = 'Stationery'");
            res.next();
            x = res.getInt(1);
            res.close();
            rs = con.s.executeQuery("select ProductName from ProductCategories where Category = 'Stationery';");
            e = new String[x];
            i = 0;
            while(rs.next()){
                e[i] = rs.getString("ProductName");
                i++;
            }
            rs.close();
            stationeryBox = new JComboBox<>(e);
            stationeryBox.setBounds(300,230,150,30);
            getContentPane().add(stationeryBox);

            res = con.s.executeQuery("select count(*) from Vendors");
            res.next();
            x = res.getInt(1);
            res.close();
            rs = con.s.executeQuery("select VendorName from Vendors;");
            e = new String[x];
            i = 0;
            while(rs.next()){
                e[i] = rs.getString("VendorName");
                i++;
            }
            rs.close();
            vendorBox = new JComboBox<>(e);
            vendorBox.setBounds(650,120,150,30);
            getContentPane().add(vendorBox);

            // Creating Add Buttons
            electronicButton = new JButton("Add");
            electronicButton.setBounds(500,110,100,30);
            electronicButton.setForeground(Color.WHITE);
            electronicButton.setFont(new Font("serif",Font.BOLD,18));
            electronicButton.setBackground(Color.BLACK);
            getContentPane().add(electronicButton);

            electronicButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String p = (String) electronicsBox.getSelectedItem();
                    String q = qTextField.getText();
                    String v = (String) vendorBox.getSelectedItem();
                    if(!q.equals("")){
                        order[count] =  new String[] {"Electronic", p, q, v};
                        qTextField.setText("");
                        System.out.println(order[count][0]+ " " + order[count][1]+ " " + order[count][2] + " " + order[count][3]);
                        count++;
                    } else{
                        JOptionPane.showMessageDialog(null, "Put some Quantity!");
                    }
                }
            });

            foodButton = new JButton("Add");
            foodButton.setBounds(500,150,100,30);
            foodButton.setForeground(Color.WHITE);
            foodButton.setFont(new Font("serif",Font.BOLD,18));
            foodButton.setBackground(Color.BLACK);
            getContentPane().add(foodButton);


            foodButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String p = (String) foodBox.getSelectedItem();
                    String q = qTextField.getText();
                    String v = (String) vendorBox.getSelectedItem();
                    if(!q.equals("")){
                        order[count] = new String[] {"Food", p, q, v};
                        qTextField.setText("");
                        System.out.println(order[count][0] + " " + order[count][1] + " " + order[count][2] + " " + order[count][3]);
                        count++;
                    } else{
                        JOptionPane.showMessageDialog(null, "Put some Quantity!");
                    }
                }
            });

            hygieneButton = new JButton("Add");
            hygieneButton.setBounds(500,190,100,30);
            hygieneButton.setForeground(Color.WHITE);
            hygieneButton.setFont(new Font("serif",Font.BOLD,18));
            hygieneButton.setBackground(Color.BLACK);
            getContentPane().add(hygieneButton);


            hygieneButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String p = (String) hygieneBox.getSelectedItem();
                    String q = qTextField.getText();
                    String v = (String) vendorBox.getSelectedItem();
                    if(!q.equals("")){
                        order[count] = new String[] {"Hygiene", p, q, v};
                        qTextField.setText("");
                        System.out.println(order[count][0] + " " + order[count][1] + " " + order[count][2] + " " + order[count][3]);
                        count++;
                    } else{
                        JOptionPane.showMessageDialog(null, "Put some Quantity!");
                    }
                }
            });

            stationeryButton = new JButton("Add");
            stationeryButton.setBounds(500,230,100,30);
            stationeryButton.setForeground(Color.WHITE);
            stationeryButton.setFont(new Font("serif",Font.BOLD,18));
            stationeryButton.setBackground(Color.BLACK);
            getContentPane().add(stationeryButton);


            stationeryButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String p = (String) stationeryBox.getSelectedItem();
                    String q = qTextField.getText();
                    String v = (String) vendorBox.getSelectedItem();
                    if(!q.equals("")){
                        order[count] = new String[] {"Stationery", p, q, v};
                        qTextField.setText("");
                        System.out.println(order[count][0] + " " + order[count][1] + " " + order[count][2] + " " + order[count][3]);
                        count++;
                    } else{
                        JOptionPane.showMessageDialog(null, "Put some Quantity!");
                    }
                }
            });

            String[] order_entry = new String[] {"Category","Product Name", "Quantity", "Vendor"};
            orderTable = new JTable(order, order_entry);
            orderTable.setBounds(0, 100, 600, 200);
            orderTable.setRowHeight(20);
            // orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            orderTable.getColumnModel().getColumn(0).setMaxWidth(125);
            orderTable.getColumnModel().getColumn(1).setMaxWidth(175);
            orderTable.getColumnModel().getColumn(2).setMaxWidth(75);
            orderTable.getColumnModel().getColumn(3).setMaxWidth(225);
            orderTable.setFont(new Font("Calibri", Font.PLAIN, 18));
            orderTable.setBackground(new Color(0, 200, 200));
            p1.add(orderTable);
            jBar = new JScrollPane(orderTable);
            jBar.setForeground(new Color(128,128, 0));
            jBar.setBounds(0, 50, 600, 400);
            p1.add(jBar);

            // Creating Submit and Cancel Buttons
            submit = new JButton("Show Order");
            submit.setBounds(250,500,150,30);
            submit.setForeground(Color.WHITE);
            submit.setFont(new Font("serif",Font.BOLD,20));
            submit.setBackground(Color.BLUE);
            getContentPane().add(submit);

            submit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    p1.setVisible(true);
                }
            });

            cancel = new JButton("Cancel");
            cancel.setBounds(450,500,150,30);
            cancel.setForeground(Color.WHITE);
            cancel.setFont(new Font("serif",Font.BOLD,20));
            cancel.setBackground(Color.BLUE);
            cancel.addActionListener(this);
            getContentPane().add(cancel);
          
            b1 = new JButton("PLACE ORDER?!");
            b1.setBounds(200, 520, 200, 30);

            p1.add(b1);
            b1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    try{
                        ResultSet ors = con.s.executeQuery("select count(*) from Orders;");
                        ors.next();
                        String oid = ors.getString(1);
                        ors.close();
                        con.s.executeUpdate("insert into Orders values (" + oid + ", 'In Progress', curdate());");

                        for(int i = 0; i < count; i++){
                            ResultSet prs = con.s.executeQuery("select ProductID from Products where ProductName = '" + order[i][1] + "';");
                            prs.next();
                            String pid = prs.getString("ProductID");
                            prs.close();
                            ResultSet vrs = con.s.executeQuery("select VendorID from Vendors where VendorName = '" + order[i][3] + "';");
                            vrs.next();
                            String vid = vrs.getString("VendorID");
                            vrs.close();
                            
                            con.s.executeUpdate("insert into OrderDetails values(" + oid + ", " + vid + ", " + pid + ", " + EmployeeID + ", " + order[i][2] + ");");

                        }
                    } catch(SQLException er){
                        System.out.println(er);
                    }
                    JOptionPane.showMessageDialog(null, "Succesfully Order Placed!");
                    dispose();
                }
            });

        } catch(Exception e){
            System.out.println("Error: " + e);
        }
        setVisible(true);
    } 
    
    public void actionPerformed(ActionEvent ae){
        dispose();
    }
}
