package application;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import java.util.Arrays;
import java.util.HashSet;

public class MakeBill extends JFrame {

  public MakeBill(int employeeId) {
    initComponents(employeeId);
  }

  @SuppressWarnings("unchecked")
  private void initComponents(int employeeId) {
    makeOrder = new javax.swing.JLabel();
    select = new javax.swing.JLabel();
    itemDrop = new javax.swing.JComboBox<String>();
    quantity = new javax.swing.JLabel();
    quantitySelector = new javax.swing.JSpinner();
    price = new javax.swing.JLabel();
    priceItem = new javax.swing.JLabel();
    addBtn = new javax.swing.JButton();
    cart = new javax.swing.JLabel();
    item1 = new javax.swing.JLabel();
    grandTotal = new javax.swing.JLabel();
    total = new javax.swing.JLabel();
    itemsTable = new javax.swing.JTable();
    jScrollPane1 = new JScrollPane();
    backBtn = new JButton();
    confirmBtn = new JButton();
    customerLabel = new javax.swing.JLabel();
    customerID = new javax.swing.JTextField();
    promotionLabel = new javax.swing.JLabel();
    promotionDrop = new javax.swing.JComboBox<>();
    paymentDrop = new javax.swing.JComboBox<>();
    paymentLabel = new javax.swing.JLabel();
    discountText = new JLabel();
    List<Integer> itemId = new ArrayList<Integer>();
    List<Integer> promotionId = new ArrayList<Integer>();
    List<Integer> itemQuantities = new ArrayList<Integer>();
    List<String> finalQueries = new ArrayList<String>();
    List<String> updateQueries = new ArrayList<String>();
    List<Float> discountList = new ArrayList<Float>();
    Random rand = new Random();
    int billId = rand.nextInt(10000);
    HashSet<String> hs = new HashSet<String>();

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    try {
      ConnectWithSQL c = new ConnectWithSQL();
      String query = "select * from Products;";
      ResultSet rs = c.s.executeQuery(query);
      List<String> itemList = new ArrayList<String>();
      List<String> promotionList = new ArrayList<String>();
      List<Float> itemPriceList = new ArrayList<Float>();
      while (rs.next()) {
        itemList.add(rs.getString("ProductName"));
        itemPriceList.add(rs.getFloat("SellingPrice"));
        itemId.add(rs.getInt("ProductID"));
        itemQuantities.add(rs.getInt("Quantity"));
      }
      Object[] itemObjects = itemList.toArray();
      itemStrings = Arrays.copyOf(itemObjects, itemObjects.length, String[].class);
      itemObjects = itemPriceList.toArray();
      itemPrices = new float[itemObjects.length];
      for (int i = 0; i < itemObjects.length; i++) {
        itemPrices[i] = (float) itemObjects[i];
      }
      rs = c.s.executeQuery("select * from Promotions;");
      while (rs.next()) {
        promotionList.add(rs.getString("PromotionName"));
        promotionId.add(rs.getInt("PromotionID"));
        discountList.add(rs.getFloat("Discount"));
      }
      promotionList.add("None");
      Object[] promotionsObjects = promotionList.toArray();
      promotionString = new String[promotionsObjects.length + 1];
      promotionString = Arrays.copyOf(promotionsObjects, promotionsObjects.length, String[].class);
    } catch (Exception e) {
      e.printStackTrace();
    }

    makeOrder.setFont(new java.awt.Font("Liberation Sans", 1, 24));
    makeOrder.setText("MAKE ORDER");

    select.setFont(new java.awt.Font("Liberation Sans", 0, 17));
    select.setText("Select Item");

    itemDrop.setModel(
        new javax.swing.DefaultComboBoxModel<String>(itemStrings));

    quantity.setFont(new java.awt.Font("Liberation Sans", 0, 17));
    quantity.setText("Quantity");

    price.setFont(new java.awt.Font("Liberation Sans", 0, 17));
    price.setText("Price");

    priceItem.setText("0.00");

    total.setFont(new java.awt.Font("Liberation Sans", 1, 20));
    total.setText("0.00");

    quantitySelector.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent ce) {
        int quantity = (int) quantitySelector.getValue();
        int itemIndex = itemDrop.getSelectedIndex();
        float price = quantity * itemPrices[itemIndex];
        priceItem.setText(String.valueOf(price));
      }
    });

    itemsTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][] {
        },
        new String[] {
            "ItemName", "Quantity", "Price"
        }) {
      Class[] types = new Class[] {
          java.lang.String.class, java.lang.Integer.class, java.lang.Float.class
      };

      public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
      }
    });
    // itemsTable.setSelectionBackground(new java.awt.Color(255, 255, 255));
    itemsTable.setShowGrid(true);
    itemsTable.setColumnSelectionAllowed(false);
    jScrollPane1.setViewportView(itemsTable);

    addBtn.setBackground(new java.awt.Color(0, 0, 0));
    addBtn.setForeground(new java.awt.Color(255, 255, 255));
    addBtn.setText("Add Item");
    addBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        String item = (String) itemDrop.getSelectedItem();
        if (hs.contains(item)) {
          JOptionPane.showMessageDialog(null, "Item Already Added!!");
          return;
        } else {
          hs.add(item);
        }
        int quantity = (int) quantitySelector.getValue();
        float cost = Float.parseFloat(priceItem.getText());
        int itemIDindex = itemDrop.getSelectedIndex();
        int itemStock = itemQuantities.get(itemIDindex);
        if (quantity > itemStock) {
          JOptionPane.showMessageDialog(null, "Stock not available!!");
          return;
        }
        DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();
        model.addRow(new Object[] { item, quantity, cost });
        float totalCost = Float.parseFloat(total.getText());
        totalCost += cost;
        total.setText(String.valueOf(totalCost));
        int itemID = itemId.get(itemIDindex);
        String queryString = "insert into Checkout values(" + billId + ", " + itemID + ", " + cost + ", " + quantity
            + ")";
        finalQueries.add(queryString);
        int promoIndex = promotionDrop.getSelectedIndex();
        float discount = discountList.get(promoIndex);
        discountText.setText(String.valueOf(discount) + "% Discount");
        String updateString = "update Products set Quantity = Quantity - " + quantity + " where ProductID = " + itemID
            + ";";
        updateQueries.add(updateString);
      }
    });

    backBtn.setBackground(new java.awt.Color(0, 0, 0));
    backBtn.setForeground(new java.awt.Color(255, 255, 255));
    backBtn.setText("Back");
    backBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        // do something
      }
    });

    cart.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
    cart.setText("CART");

    item1.setText("Lays");

    grandTotal.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
    grandTotal.setText("GRAND TOTAL");

    paymentDrop.setModel(
        new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Credit Card", "Debit Card", "Online Payment" }));
    paymentDrop.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {

      }
    });

    paymentLabel.setText("Select Payment Method");

    customerLabel.setText("CustomerID");

    customerID.setText("");

    promotionLabel.setText("Promotion");

    discountText.setText("0% Discount");

    promotionDrop
        .setModel(new javax.swing.DefaultComboBoxModel<String>(promotionString));
    paymentDrop.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        if ((String) promotionDrop.getSelectedItem() != "None") {
          int promoIndex = promotionDrop.getSelectedIndex();
          float discount = discountList.get(promoIndex);
          float totalCost = Float.parseFloat(total.getText());
          totalCost = (totalCost * discount) / 100;
          total.setText(String.valueOf(totalCost));
          discountText.setText(String.valueOf(discount) + "% Discount");
        }
      }
    });

    confirmBtn.setBackground(new java.awt.Color(0, 0, 0));
    confirmBtn.setForeground(new java.awt.Color(255, 255, 255));
    confirmBtn.setText("Confirm Order");
    confirmBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        try {
          ConnectWithSQL c = new ConnectWithSQL();
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          LocalDateTime now = LocalDateTime.now();
          c.s.execute("insert into Bills values(" + billId + ", '" + dtf.format(now) + "', '"
              + paymentDrop.getSelectedItem() + "')");
          String customerString = customerID.getText();
          int promotionIndex = promotionDrop.getSelectedIndex();
          boolean trans;
          int promotionID = 0;
          if ((String) promotionDrop.getSelectedItem() == "None") {
            trans = c.s.execute("insert into Transactions values(" + billId + ", NULL, " + customerString + ", "
                + employeeId + ")");
          } else {
            promotionID = promotionId.get(promotionIndex);
            trans = c.s
                .execute("insert into Transactions values(" + billId + ", " + promotionID + ", " + customerString + ", "
                    + employeeId + ")");
          }
          // if (!trans) {
          // JOptionPane.showMessageDialog(null, "CustomerID false");
          // return;
          // }
          for (String query : finalQueries) {
            c.s.execute(query);
          }
          for (String query : updateQueries) {
            c.s.execute(query);
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Order Successfull");
        setVisible(false);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(makeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(select, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 73,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 73,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(itemDrop, 0, 131, Short.MAX_VALUE)
                                    .addComponent(quantitySelector)
                                    .addComponent(priceItem, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(addBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backBtn)
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(paymentLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(paymentDrop, javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 143,
                                            javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(promotionLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(promotionDrop, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(customerLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(customerID, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cart, javax.swing.GroupLayout.PREFERRED_SIZE, 83,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(grandTotal)
                                .addGap(35, 35, 35)
                                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 79,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(discountText)))
                        .addContainerGap(12, Short.MAX_VALUE)))));
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(cart, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                            javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backBtn)
                            .addComponent(makeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(select)
                            .addComponent(itemDrop, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(quantity)
                            .addComponent(quantitySelector, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(price)
                            .addComponent(priceItem))
                        .addGap(18, 18, 18)
                        .addComponent(addBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(customerLabel)
                            .addComponent(customerID, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(promotionLabel)
                            .addComponent(promotionDrop, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(grandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(discountText))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(paymentDrop, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(paymentLabel)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE)));

    pack();
    setVisible(true);
  }

  // Variables declaration - do not modify
  private javax.swing.JButton addBtn;
  private javax.swing.JLabel cart;
  private javax.swing.JLabel grandTotal;
  private javax.swing.JLabel item1;
  private javax.swing.JComboBox<String> itemDrop;
  private javax.swing.JLabel makeOrder;
  private javax.swing.JLabel price;
  private javax.swing.JLabel priceItem;
  private javax.swing.JLabel quantity;
  private javax.swing.JLabel discountText;
  private javax.swing.JSpinner quantitySelector;
  private javax.swing.JLabel select;
  private javax.swing.JLabel total;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTable itemsTable;
  private String[] itemStrings;
  private String[] promotionString;
  private float[] itemPrices;
  private javax.swing.JComboBox<String> promotionDrop;
  private javax.swing.JLabel promotionLabel;
  private javax.swing.JTextField customerID;
  private javax.swing.JLabel customerLabel;
  private javax.swing.JButton backBtn;
  private javax.swing.JButton confirmBtn;
  private javax.swing.JComboBox<String> paymentDrop;
  private javax.swing.JLabel paymentLabel;
  // End of variables declaration
}
