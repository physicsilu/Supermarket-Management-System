package application;

import java.awt.*; // Importing the packages
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class AddPromotion extends JFrame {

    JLabel promotionIDlb, promotionNamelb, promotionDiscountlb, promotionSDlb, promotionEDlb;
    JTextField promotionIDtf, promotionNametf, promotionDiscounttf;
    JSpinner startDate, endDate;
    JButton submit, cancel;

    AddPromotion() {
        setBounds(100, 100, 1000, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.white);

        promotionIDlb = new JLabel("PROMOTION ID");
        promotionIDlb.setBounds(50, 50, 150, 30);
        promotionIDlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(promotionIDlb);

        promotionNamelb = new JLabel("NAME");
        promotionNamelb.setBounds(50, 100, 150, 30);
        promotionNamelb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(promotionNamelb);

        promotionDiscountlb = new JLabel("DISCOUNT");
        promotionDiscountlb.setBounds(50, 150, 150, 30);
        promotionDiscountlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(promotionDiscountlb);

        promotionSDlb = new JLabel("START DATE");
        promotionSDlb.setBounds(50, 200, 150, 30);
        promotionSDlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(promotionSDlb);

        promotionEDlb = new JLabel("END DATE");
        promotionEDlb.setBounds(50, 250, 150, 30);
        promotionEDlb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(promotionEDlb);

        promotionIDtf = new JTextField();
        promotionIDtf.setBounds(250, 50, 150, 30);
        add(promotionIDtf);

        promotionNametf = new JTextField();
        promotionNametf.setBounds(250, 100, 150, 30);
        add(promotionNametf);

        promotionDiscounttf = new JTextField();
        promotionDiscounttf.setBounds(250, 150, 150, 30);
        add(promotionDiscounttf);

        SpinnerDateModel dateModel1 = new SpinnerDateModel();
        dateModel1.setCalendarField(java.util.Calendar.DAY_OF_MONTH);
        startDate = new JSpinner(dateModel1);
        JSpinner.DateEditor dateEditor1 = new JSpinner.DateEditor(startDate, "yyyy/MM/dd");
        startDate.setEditor(dateEditor1);
        startDate.setBounds(250, 200, 150, 30);
        startDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(startDate);

        SpinnerDateModel dateModel2 = new SpinnerDateModel();
        dateModel2.setCalendarField(java.util.Calendar.DAY_OF_MONTH);
        endDate = new JSpinner(dateModel2);
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(endDate, "yyyy/MM/dd");
        endDate.setEditor(dateEditor2);
        endDate.setBounds(250, 250, 150, 30);
        endDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(endDate);

        submit = new JButton("Submit");
        submit.setBounds(50, 350, 100, 30);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("serif", Font.BOLD, 20));
        submit.setBackground(Color.black);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String id = promotionIDtf.getText();
                String name = promotionNametf.getText();
                String discount = promotionDiscounttf.getText();
                String SD, ED;

                Date selectDate1 = (Date) startDate.getValue();
                Date selectDate2 = (Date) endDate.getValue();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SD = dateFormat.format(selectDate1);
                ED = dateFormat.format(selectDate2);

                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Name should not be empty");
                    return;
                }

                try {
                    ConnectWithSQL conn = new ConnectWithSQL();
                    String query = "insert into Promotions values(" + id + ",'" + name + "'," + discount + ",'"
                            + SD + "','" + ED + "')";
                    conn.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "New Promotion added successfully");
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
        cancel.setBounds(180, 350, 100, 30);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("serif", Font.BOLD, 20));
        cancel.setBackground(Color.black);
        cancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		dispose();
        	}
        });
        add(cancel);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("newPromotion.png"));
        Image i2 = i1.getImage().getScaledInstance(450, 400, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(450, 50, 450, 400);
        add(image);
        setVisible(true);
    }
}