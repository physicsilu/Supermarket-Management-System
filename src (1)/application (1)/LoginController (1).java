package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.ResultSet;

public class LoginController extends SHA{
	@FXML
	private Button loginButton;
	@FXML
	private Button cancelButton;
	@FXML
	private AnchorPane scenePane;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField usernameField;
	
	Stage stage;
	
	@FXML
    private void exit(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Exit!");
    	alert.setContentText("Are you sure you want to exit?");
    	
    	if(alert.showAndWait().get() == ButtonType.OK) {
    		stage = (Stage) scenePane.getScene().getWindow();
            stage.close();
    	}
    }
	
	@FXML
	private void login(ActionEvent event) {
		String username = usernameField.getText();
		String password = passwordField.getText();
        String sha256hash = getSHA256Hash(password);
        
        if(username.isEmpty() || password.isEmpty()) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Empty Fields!");
        	alert.setContentText("Either the username or password has been left blank. Kindly fill out the details.");
        	alert.showAndWait();
        }
        
        try{
            ConnectWithSQL c = new ConnectWithSQL();           
            String query = "select * from employees where UserID = '" + username + "' and Password = '" + password + "'";
            ResultSet rs_employee = c.s.executeQuery(query);
            
            if(rs_employee.next()) {
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Dashboard.fxml"));
            	Parent root = loader.load();
    			Scene scene = new Scene(root);
    			String css = this.getClass().getResource("/Stylesheet/style.css").toExternalForm();
    			scene.getStylesheets().add(css);
    			
    			stage = (Stage) scenePane.getScene().getWindow();
    			stage.hide();
    			stage.setTitle("Akshay Supermarket");
    			stage.setResizable(false);
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			
    			DashboardController controller = loader.getController();
    			controller.setEmployeeID(Integer.parseInt(rs_employee.getString("EmployeeID")));
    			controller.displayEmployee();
    			controller.setStage(stage);
    			controller.refresh();
    			stage.show();
            } else {
            	query = "SELECT * FROM customers WHERE EmailID = '" + username + "' AND Password = '" + sha256hash + "'";
                ResultSet rs_customer = c.s.executeQuery(query);
                if(rs_customer.next()) {
                	stage = (Stage) scenePane.getScene().getWindow();
                	stage.close();
                	new CustomerDashboard(Integer.parseInt(rs_customer.getString("CustomerID")));
                } else {
	            	Alert alert = new Alert(AlertType.ERROR);
	            	alert.setTitle("Invalid Entry");
	            	alert.setContentText("Either the username or password is incorrect.");
	            	usernameField.setText("");
	            	passwordField.setText("");
	            	alert.showAndWait();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
	}
}
