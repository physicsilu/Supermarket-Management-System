package application;

import java.awt.*; // Importing the packages
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class PreviousOrders extends JFrame implements ActionListener {
    // Creating the UI
    JLabel text, prevOrders;
    JButton submit, back;
    JComboBox<String> previousOrders;
    JTable orders;
    JScrollPane sp1;

    PreviousOrders(int id) {
        setBounds(100, 100, 1400, 600); // creating a frame
        setLayout(null); // This helps in customizing our layout
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(Color.WHITE); // This command is used to set the background of the frame to white

        text = new JLabel("CUSTOMER ORDERS");
        text.setBounds(50, 25, 500, 50);
        text.setForeground(Color.BLACK);// choosing the colour of text
        text.setFont(new Font("serif", Font.BOLD, 25)); // choosing the font of text
        getContentPane().add(text);// adding text to the frame

        prevOrders = new JLabel("SELECT");
        prevOrders.setBounds(50, 85, 100, 30);
        prevOrders.setForeground(Color.BLACK);
        prevOrders.setFont(new Font("serif", Font.BOLD, 15));
        getContentPane().add(prevOrders);

        // creating a drop down box
        previousOrders = new JComboBox<>(new String[] { "Past 5 orders", "Past 10 orders", "All time" });
        previousOrders.setBounds(150, 85, 400, 30);
        getContentPane().add(previousOrders);

        // creating a submit button
        submit = new JButton("Submit");
        submit.setBounds(600, 85, 100, 30);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("serif", Font.BOLD, 15));
        submit.setBackground(Color.BLACK);
        getContentPane().add(submit);

        // creating a back button
        back = new JButton("Back");
        back.setBounds(1200, 500, 100, 30);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("serif", Font.BOLD, 15));
        back.setBackground(Color.BLACK);
        back.addActionListener(this);
        getContentPane().add(back);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] column = new String[] { "Bill Date", "Bill ID", "Total Bill Price", "Payment Method" };
                String[][] entries;
                String q = previousOrders.getSelectedItem().toString();
                try {
                    ConnectWithSQL c = new ConnectWithSQL();
                    c.s.executeUpdate(
                            "create table bill_discounts as select b.BillDate, b.BillID, b.PaymentMethod, t.PromotionID, p.Discount from Bills b NATURAL JOIN Transactions t LEFT JOIN Promotions p ON t.PromotionID = p.PromotionID where t.CustomerID = " + id);
                    c.s.executeUpdate("update bill_discounts set Discount = 0.00 where PromotionID IS NULL;");
                    c.s.executeUpdate(
                            "create view bill_sums as select sum(Price * Quantity) as 'Total Bill Price', BillID from Checkout group by BillID;");
                    if (q.equals("Past 5 orders")) {
                        ResultSet res = c.s.executeQuery(
                                "select count(*) from bill_sums NATURAL JOIN bill_discounts;");
                        res.next();
                        int x = res.getInt(1);
                        res.close();
                        if (x != 0 && x < 5) {
                            entries = new String[x][4];
                            ResultSet rs = c.s.executeQuery(
                                    "select bd.BillDate, bd.BillID, Round(bs.`Total Bill Price` * (1 - (bd.Discount / 100)), 2) as `Total Bill Price after Discount` , bd.PaymentMethod from bill_sums bs NATURAL JOIN bill_discounts bd order by bd.BillDate desc;");
                            int i = 0;
                            while (rs.next()) {
                                entries[i] = new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
                                        rs.getString(4) };
                                i++;
                            }
                        } else {
                            if (x >= 5) {
                                entries = new String[5][4];
                                ResultSet rs = c.s.executeQuery(
                                        "select bd.BillDate, bd.BillID, Round(bs.`Total Bill Price` * (1 - (bd.Discount / 100)), 2) as `Total Bill Price after Discount` , bd.PaymentMethod from bill_sums bs NATURAL JOIN bill_discounts bd order by bd.BillDate desc LIMIT 5;");
                                int i = 0;
                                while (rs.next()) {
                                    entries[i] = new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
                                            rs.getString(4) };
                                    i++;
                                }
                            } else {
                                entries = new String[1][5];
                            }
                        }
                    } else{
                        if (q.equals("Past 10 orders")) {
                            ResultSet res = c.s.executeQuery(
                                    "select count(*) from bill_sums NATURAL JOIN bill_discounts;");
                            res.next();
                            int x = res.getInt(1);
                            res.close();
                            if (x != 0 && x < 10) {
                                entries = new String[x][4];
                                ResultSet rs = c.s.executeQuery(
                                        "select bd.BillDate, bd.BillID, Round(bs.`Total Bill Price` * (1 - (bd.Discount / 100)), 2) as `Total Bill Price after Discount`, bd.PaymentMethod from bill_sums bs NATURAL JOIN bill_discounts bd order by bd.BillDate desc;");
                                int i = 0;
                                while (rs.next()) {
                                    entries[i] = new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
                                            rs.getString(4) };
                                    i++;
                                }
                            } else {
                                if (x >= 10) {
                                    entries = new String[10][4];
                                    ResultSet rs = c.s.executeQuery(
                                            "select bd.BillDate, bd.BillID, Round(bs.`Total Bill Price` * (1 - (bd.Discount / 100)), 2) as `Total Bill Price after Discount`, bd.PaymentMethod from bill_sums bs NATURAL JOIN bill_discounts bd order by bd.BillDate desc LIMIT 10;");
                                    int i = 0;
                                    while (rs.next()) {
                                        entries[i] = new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
                                                rs.getString(4) };
                                        i++;
                                    }
                                } else {
                                    entries = new String[1][5];
                                }
                            }
                        } else {
                            ResultSet res = c.s.executeQuery(
                                    "select count(*) from bill_sums NATURAL JOIN bill_discounts;");
                            res.next();
                            int x = res.getInt(1);
                            res.close();
                            if (x != 0) {
                                entries = new String[x][4];
                                ResultSet rs = c.s.executeQuery(
                                        "select bd.BillDate, bd.BillID, Round(bs.`Total Bill Price` * (1 - (bd.Discount / 100)), 2) as `Total Bill Price after Discount`, bd.PaymentMethod from bill_sums bs NATURAL JOIN bill_discounts bd order by bd.BillDate desc;");
                                int i = 0;
                                while (rs.next()) {
                                    entries[i] = new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
                                            rs.getString(4) };
                                    i++;
                                }
                            } else {
                                entries = new String[1][5];
                            }
                            
                        }
                    }
                    orders = new JTable(entries, column);
                    orders.setBounds(150, 120, 1000, 400);
                    sp1 = new JScrollPane(orders);
                    sp1.setBounds(150, 120, 1000, 400);
                    add(sp1);
                    c.s.executeUpdate("drop table bill_discounts;");
                    c.s.executeUpdate("drop view bill_sums;");
                } catch (Exception eR) {
                    eR.printStackTrace();
                }
            }
        });

        setVisible(true); // this makes the frame visible
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}