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
import java.sql.SQLException;

public class ProfileController {
	@FXML
	private Button changePasswordButton;
	@FXML
	private Button cancelButton;
	@FXML
	private AnchorPane scenePane;
	@FXML
	private TextField nameField;
	@FXML
	private TextField IDField;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField RoleField;
	
	int EmployeeID;
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
	private void changePassword(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ChangePassword.fxml"));
	    	Parent root = loader.load();
			Scene scene = new Scene(root);
			String css = this.getClass().getResource("/Stylesheet/simple.css").toExternalForm();
			scene.getStylesheets().add(css);
			
			ChangePasswordController controller = loader.getController();
			controller.setID(EmployeeID);
			
			stage = (Stage) scenePane.getScene().getWindow();
			stage.setTitle("Change Password");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void setID(int EmployeeID) {
		this.EmployeeID = EmployeeID;
	}
	
	public void setFields() {
		try {
        	ConnectWithSQL c = new ConnectWithSQL();
            String query = "SELECT * FROM employees WHERE EmployeeID = " + EmployeeID;
			ResultSet rs_employee = c.s.executeQuery(query);
			rs_employee.next();
			nameField.setText(rs_employee.getString("EmployeeName"));
			IDField.setText(rs_employee.getString("EmployeeID"));
			usernameField.setText(rs_employee.getString("UserID"));
			RoleField.setText(rs_employee.getString("Role"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
