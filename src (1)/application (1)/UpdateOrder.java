package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.*;

public class UpdateOrder {
    private static JFrame frame;
    private static JTable table;
    private static JScrollPane pane;
    private static JLabel statuslabel, label, idlabel, oLabel;
    private static JComboBox<String> orderStatusBox;
    private static JButton updateButton;
    UpdateOrder(){
        frame = new JFrame("Update Vendor");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(50, 50, 600, 600);
        frame.setLayout(null);

        String[][] orders;
        String[] status = new String[5];

        try{
            ConnectWithSQL c = new ConnectWithSQL();

            ResultSet res = c.s.executeQuery("select count(*) from Orders;");
            res.next();
            int x = res.getInt(1);
            res.close();

            orders = new String[x][3];

            ResultSet rs = c.s.executeQuery("select * from Orders;");
            int i = 0;
            while(rs.next()){
                orders[i] = new String[] {rs.getString(1), rs.getString(2), rs.getString(3)};
                i++;
            }
            rs.close();

            String[] columns = {"Order ID", "Status", "Order Date"};
            table = new JTable(orders, columns);
            table.setBounds(25, 25, 300, 900);
            table.setBackground(new Color(0, 128, 128));
            table.setForeground(new Color(0));
            table.setFont(new Font(Font.SERIF, Font.BOLD, 15));
            table.setRowHeight(30);
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(1).setMaxWidth(110);
            table.getColumnModel().getColumn(2).setMaxWidth(140);
            // frame.add(table);
            pane = new JScrollPane(table);
            pane.setBounds(25, 25, 300, 500);
            pane.setForeground(new Color(0,128,128));
            frame.add(pane);

            statuslabel = new JLabel();
            idlabel = new JLabel();

            table.setCellSelectionEnabled(true);
            ListSelectionModel select= table.getSelectionModel();
            select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            select.addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent e) {
                    int row = table.getSelectedRow();
                    String status;
                    status = table.getValueAt(row, 1).toString();
                    // System.out.println("Table element selected is: " + Data);
                    statuslabel.setText(status);
                    String id;
                    id = table.getValueAt(row, 0).toString();
                    idlabel.setText(id);
                }
            });

            statuslabel.setBounds(400, 200, 200, 30);
            statuslabel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 20));
            frame.add(statuslabel);

            idlabel.setBounds(350, 200, 30, 30);
            idlabel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 20));
            frame.add(idlabel);

            label = new JLabel("Choose Order:");
            label.setBounds(350, 170, 300, 20);
            label.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 15));
            frame.add(label);

            oLabel = new JLabel("Choose Status Update:");
            oLabel.setBounds(350, 250, 300, 20);
            oLabel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 15));
            frame.add(oLabel);

            res = c.s.executeQuery("select Status from Orders group by Status;");
            i = 0;
            while(res.next()){
                status[i] = res.getString("Status");
                i++;
            }
            orderStatusBox = new JComboBox<>(status);
            orderStatusBox.setBounds(350, 275, 200, 30);
            frame.add(orderStatusBox);

            updateButton = new JButton("Update");
            updateButton.setBackground(Color.BLACK);
            updateButton.setForeground(Color.CYAN);
            updateButton.setBounds(375, 450, 200, 40);
            updateButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
            frame.add(updateButton);

            updateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    String toUpdate = statuslabel.getText();
                    String newUpdate = orderStatusBox.getSelectedItem().toString();
                    String idUpdate = idlabel.getText();
                    if(toUpdate.equals(newUpdate)){
                        JOptionPane.showMessageDialog(null, "Update Status is same!");
                    } else{
                        if(newUpdate.equals("Delivered")){    
                            try{
                            	c.s.executeUpdate("update Orders set Status = 'Delivered' where OrderID = " + idUpdate + ";");
                                c.s.executeUpdate("update Orders o JOIN OrderDetails od ON o.OrderID = od.OrderID JOIN Products p ON od.ProductID = p.ProductID set p.Quantity = (p.Quantity + od.Quantity) where o.OrderID = " + idUpdate + ";");
                            } catch(SQLException er){
                                System.out.println("SQL Error: " + er);
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Status Updated");
                        frame.setVisible(false);
                    }
                }
            });

        } catch(Exception e){
            System.out.println("Error: " + e);
        }

        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new UpdateOrder();
    }
}