package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;


public class DashboardController extends MenuController {
	@FXML
	private Button RefreshButton;
	@FXML
	private VBox scenePane;
	
	@FXML
	private Label WelcomeLabel;
	
	@FXML
	private Label RevenueLabel;
	@FXML
	private Label ATVLabel;
	@FXML
	private Label UPTLabel;
	@FXML
	private Label MonthRevLabel;
	@FXML
	private Label MonthExpLabel;
	@FXML
	private Label GrossProfitLabel;
	@FXML
	private Label MonthProfitLabel;
	
	@FXML
	private ScrollPane inventoryScroll;
	@FXML
	private ScrollPane ordersScroll;
	@FXML
	private ScrollPane transactionScroll;
	
	Stage stage;
	
	@FXML
	private void refresh(ActionEvent event) throws SQLException {
		displayRevenue();
		displayAverageBillValue();
		displayUPT();
		displayGrossProfit();
		displayMonthProfit();
		
		displayUndeliveredOrders();
		displayTransactions();
		displayInventoryAlerts();
	}
	
	public void refresh() throws SQLException {
		displayRevenue();
		displayAverageBillValue();
		displayUPT();
		displayGrossProfit();
		displayMonthProfit();
		
		displayUndeliveredOrders();
		displayTransactions();
		displayInventoryAlerts();
		
	}
	
	private void displayUndeliveredOrders() throws SQLException {
		//Get the required values from SQL
		ConnectWithSQL c = new ConnectWithSQL();
		String query = "SELECT * FROM orders WHERE Status='Pending'";
		ResultSet resultSet = c.s.executeQuery(query);
		
        // Clear any existing content in the scroll pane and Create a VBox to hold all the panes
        ordersScroll.setContent(null);
        VBox stackPane = new VBox();
        ordersScroll.setContent(stackPane);
        stackPane.prefWidthProperty().bind(ordersScroll.widthProperty());
        stackPane.maxWidthProperty().bind(ordersScroll.widthProperty());

        //Create Panes for each entry in the ResultSet
        double verticalPosition = 0;
        while (resultSet.next()) {
        	Label label1 = new Label("#" + resultSet.getString("OrderID"));
            Label label2 = new Label(resultSet.getString("OrderDate")); 
            
            label1.setLayoutX(15);
            label1.setLayoutY(20);
            label1.getStyleClass().add("text_label");
            
            label2.setLayoutX(80);
            label2.setLayoutY(20);
            label2.getStyleClass().add("text_label");

            // Create a pane to hold the HBox
            Pane pane = new Pane();
            pane.getChildren().addAll(label1, label2);

            // Set the width of the pane to be the same as the scroll pane
            pane.prefWidthProperty().bind(ordersScroll.getScene().widthProperty());
            pane.maxWidthProperty().bind(ordersScroll.getScene().widthProperty());
            pane.setPrefHeight(50);
            pane.getStyleClass().add("item");
            pane.setLayoutY(verticalPosition);
            stackPane.getChildren().add(pane);
            verticalPosition += 50;
        }
    }
	
	private void displayTransactions() throws SQLException {
		//Get the required values from SQL
		ConnectWithSQL c = new ConnectWithSQL();
		String query = "SELECT * FROM Transactions NATURAL JOIN Bills ORDER BY bills.BillDate DESC LIMIT 20";
		ResultSet resultSet = c.s.executeQuery(query);
		
        // Clear any existing content in the scroll pane and Create a VBox to hold all the panes
        transactionScroll.setContent(null);
        VBox stackPane = new VBox();
        transactionScroll.setContent(stackPane);
        stackPane.prefWidthProperty().bind(transactionScroll.widthProperty());
        stackPane.maxWidthProperty().bind(transactionScroll.widthProperty());

        //Create Panes for each entry in the ResultSet
        double verticalPosition = 0;
        while (resultSet.next()) {
        	Label label1 = new Label("#" + resultSet.getString("BillID"));
            Label label2 = new Label(resultSet.getString("BillDate")); 
            Label label3 = new Label(resultSet.getString("PaymentMethod")); 
            
            label1.setLayoutX(10);
            label1.setLayoutY(20);
            label1.getStyleClass().add("text_label");
            
            label2.setLayoutX(50);
            label2.setLayoutY(20);
            label2.getStyleClass().add("text_label");
            
            label3.setLayoutX(140);
            label3.setLayoutY(20);
            label3.getStyleClass().add("text_label");

            // Create a pane to hold the HBox
            Pane pane = new Pane();
            pane.getChildren().addAll(label1, label2, label3);

            // Set the width of the pane to be the same as the scroll pane
            pane.prefWidthProperty().bind(transactionScroll.getScene().widthProperty());
            pane.maxWidthProperty().bind(transactionScroll.getScene().widthProperty());
            pane.setPrefHeight(50);
            pane.getStyleClass().add("item");
            pane.setLayoutY(verticalPosition);
            stackPane.getChildren().add(pane);
            verticalPosition += 50;
        }
    }
	
	private void displayInventoryAlerts() throws SQLException {
		//Get the required values from SQL
		ConnectWithSQL c = new ConnectWithSQL();
		String query = "SELECT * FROM Products NATURAL JOIN ProductCategories WHERE Quantity < 25";
		ResultSet resultSet = c.s.executeQuery(query);
		
        // Clear any existing content in the scroll pane and Create a VBox to hold all the panes
		inventoryScroll.setContent(null);
        VBox stackPane = new VBox();
        inventoryScroll.setContent(stackPane);
        stackPane.prefWidthProperty().bind(inventoryScroll.widthProperty());
        stackPane.maxWidthProperty().bind(inventoryScroll.widthProperty());

        //Create Panes for each entry in the ResultSet
        double verticalPosition = 0;
        while (resultSet.next()) {
        	Label label1 = new Label("#" + resultSet.getString("ProductID"));
            Label label2 = new Label(resultSet.getString("ProductName")); 
            Label label3 = new Label(resultSet.getString("Quantity") + " / 25"); 
            Label label4 = new Label(resultSet.getString("Category"));
            
            label1.setLayoutX(10);
            label1.setLayoutY(20);
            label1.getStyleClass().add("text_label");
            
            label2.setLayoutX(50);
            label2.setLayoutY(20);
            label2.getStyleClass().add("text_label");
            
            label3.setLayoutX(500);
            label3.setLayoutY(20);
            label3.getStyleClass().add("text_label");
            
            label4.setLayoutX(150);
            label4.setLayoutY(20);
            label4.getStyleClass().add("text_label");

            // Create a pane to hold the HBox
            Pane pane = new Pane();
            pane.getChildren().addAll(label1, label2, label4, label3);

            // Set the width of the pane to be the same as the scroll pane
            pane.prefWidthProperty().bind(inventoryScroll.getScene().widthProperty());
            pane.maxWidthProperty().bind(inventoryScroll.getScene().widthProperty());
            pane.setPrefHeight(50);
            pane.getStyleClass().add("item");
            pane.setLayoutY(verticalPosition);
            stackPane.getChildren().add(pane);
            verticalPosition += 50;
        }
    }
	
	public void displayEmployee() {
        try {
        	ConnectWithSQL c = new ConnectWithSQL();
            String query = "select * from employees where EmployeeID = " + EmployeeID;
			ResultSet rs_employee = c.s.executeQuery(query);
			rs_employee.next();
			WelcomeLabel.setText("Welcome back, " + rs_employee.getString("EmployeeName"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void displayRevenue() {
		try {
        	ConnectWithSQL c = new ConnectWithSQL();
        	String query = "SELECT SUM(Checkout.Price * Checkout.Quantity * (1 - COALESCE(Promotions.Discount, 0) / 100)) AS DiscountedTotal "
        			+ "FROM Checkout JOIN Bills ON Checkout.BillID = Bills.BillID "
        			+ "LEFT JOIN Transactions ON Bills.BillID = Transactions.BillID "
        			+ "LEFT JOIN Promotions ON Transactions.PromotionID = Promotions.PromotionID "
        			+ "WHERE Bills.BillDate = CURDATE()";
			ResultSet rs = c.s.executeQuery(query);
			if(rs.next()) {
				double value = rs.getDouble("DiscountedTotal");
				DecimalFormat df = new DecimalFormat("#0.00");
				RevenueLabel.setText("₹ " + df.format(value));
			} else {
				RevenueLabel.setText("₹0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void displayAverageBillValue() {
		try {
        	ConnectWithSQL c = new ConnectWithSQL();
        	String query = "SELECT AVG(total_bill) AS ATV FROM ("
        			+ "	   SELECT Bills.BillID, SUM(Checkout.Price * Checkout.Quantity * (1 - COALESCE(Promotions.Discount, 0) / 100)) AS total_bill "
        			+ "    FROM Checkout JOIN Bills ON Checkout.BillID = Bills.BillID "
        			+ "    LEFT JOIN Transactions ON Bills.BillID = Transactions.BillID "
        			+ "	LEFT JOIN Promotions ON Transactions.PromotionID = Promotions.PromotionID "
        			+ "    WHERE Bills.BillDate = CURDATE() GROUP BY BillID "
        			+ ") AS subquery ";
			ResultSet rs = c.s.executeQuery(query);
			if(rs.next()) {
				double value = rs.getDouble("ATV");
				DecimalFormat df = new DecimalFormat("#0.00");
				ATVLabel.setText("₹ " + df.format(value));
			} else {
				ATVLabel.setText("₹0");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	private void displayUPT() {
		try {
        	ConnectWithSQL c = new ConnectWithSQL();
        	String query = "SELECT AVG(item_count) AS UPT FROM ( "
        			+ "	SELECT Bills.BillID, COUNT(*) AS item_count "
        			+ "	FROM Checkout JOIN Bills ON Checkout.BillID = Bills.BillID "
        			+ "	WHERE Bills.BillDate = CURDATE() GROUP BY BillID "
        			+ ") AS subquery";
			ResultSet rs = c.s.executeQuery(query);
			if(rs.next()) {
				double value = rs.getDouble("UPT");
				DecimalFormat df = new DecimalFormat("#0.00");
				UPTLabel.setText(df.format(value));
			} else {
				UPTLabel.setText("0");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	private void displayGrossProfit() {
		try {
        	ConnectWithSQL c = new ConnectWithSQL();
        	String query = "SELECT (SUM(Checkout.Quantity * Checkout.Price * (1 - COALESCE(Promotions.Discount, 0) / 100)) - SUM(Checkout.Quantity * Products.CostPrice)) AS profit "
        			+ "FROM Checkout JOIN Bills ON Checkout.BillID = Bills.BillID  "
        			+ "JOIN Products ON Checkout.ProductID = Products.ProductID  "
        			+ "LEFT JOIN Transactions ON Bills.BillID = Transactions.BillID "
        			+ "LEFT JOIN Promotions ON Transactions.PromotionID = Promotions.PromotionID "
        			+ "WHERE Bills.BillDate = CURDATE();";
			ResultSet rs = c.s.executeQuery(query);
			if(rs.next()) {
				double value = rs.getDouble("profit");
				DecimalFormat df = new DecimalFormat("#0.00");
				GrossProfitLabel.setText("₹ " + df.format(value));
			} else {
				GrossProfitLabel.setText("0%");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	private void displayMonthRevenue() {
		try {
        	ConnectWithSQL c = new ConnectWithSQL();
        	String query = "SELECT SUM(Checkout.Price * Checkout.Quantity * (1 - COALESCE(Promotions.Discount, 0) / 100)) AS Revenue "
        			+ "FROM Checkout JOIN Bills ON Checkout.BillID = Bills.BillID "
        			+ "LEFT JOIN Transactions ON Bills.BillID = Transactions.BillID "
        			+ "LEFT JOIN Promotions ON Transactions.PromotionID = Promotions.PromotionID "
        			+ "WHERE YEAR(BillDate) = YEAR(CURDATE()) AND MONTH(BillDate) = MONTH(CURDATE());";
			ResultSet rs = c.s.executeQuery(query);
			if(rs.next()) {
				double value = rs.getDouble("Revenue");
				DecimalFormat df = new DecimalFormat("#0.00");
				MonthRevLabel.setText("₹ " + df.format(value));
			} else {
				MonthRevLabel.setText("0%");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void displayMonthExpense() {
		try {
        	ConnectWithSQL c = new ConnectWithSQL();
        	String query = "SELECT SUM(od.Quantity * products.CostPrice) AS TotalExpenditure "
        			+ "FROM Orders JOIN OrderDetails od ON Orders.OrderID = od.OrderID "
        			+ "JOIN Products ON od.ProductID = products.ProductID "
        			+ "WHERE MONTH(orders.OrderDate) = MONTH(CURDATE()) AND YEAR(orders.OrderDate) = YEAR(CURDATE());";
			ResultSet rs = c.s.executeQuery(query);
			if(rs.next()) {
				double value = rs.getDouble("TotalExpenditure");
				DecimalFormat df = new DecimalFormat("#0.00");
				MonthExpLabel.setText("₹ " + df.format(value));
			} else {
				MonthExpLabel.setText("0%");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void displayMonthProfit() {
		displayMonthExpense();
		displayMonthRevenue();
        
		String revenue = MonthRevLabel.getText();
		String expense = MonthExpLabel.getText();
		
		float rev = Float.parseFloat(revenue.replaceAll("[^\\d.]", ""));
		float exp =Float.parseFloat(expense.replaceAll("[^\\d.]", ""));
		float profit = rev - exp;
				
		DecimalFormat df = new DecimalFormat("#0.00");
		MonthProfitLabel.setText(df.format(profit));

	}
}
