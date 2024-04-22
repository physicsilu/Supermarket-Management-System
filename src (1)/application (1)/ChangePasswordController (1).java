package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.ResultSet;

public class ChangePasswordController extends SHA{
	@FXML
	private Button changeButton;
	@FXML
	private Button cancelButton;
	@FXML
	private AnchorPane scenePane;
	@FXML
	private PasswordField oldPasswordField;
	@FXML
	private PasswordField reOldPasswordField;
	@FXML
	private PasswordField newPasswordField;
	
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
	private void change(ActionEvent event) {
		String old_password = oldPasswordField.getText();
		String re_old_password = reOldPasswordField.getText();
		String new_password = newPasswordField.getText();
		
		oldPasswordField.setText("");
		reOldPasswordField.setText("");
		newPasswordField.setText("");
		
        String hash_old_password = getSHA256Hash(old_password);
        String hash_new_password = getSHA256Hash(new_password);
        
        if(old_password.isBlank() || re_old_password.isBlank() || new_password.isBlank()) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Empty Fields!");
        	alert.setContentText("Kindly fill out all the fields.");
        	alert.showAndWait();
        } else if(old_password.compareTo(re_old_password) != 0) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Password doesn't match!");
        	alert.setContentText("The old password entry and re-entry doesn't match. Kindly verify!");
        	alert.showAndWait();
        } else {
        	try{
                ConnectWithSQL c = new ConnectWithSQL();
                String query = "SELECT * FROM employees where EmployeeID = " + EmployeeID + " and Password = '" + hash_old_password + "'";
                ResultSet rs_employee = c.s.executeQuery(query);
                
                if(rs_employee.next()) {
                	query = "UPDATE Employees SET Password = '" + hash_new_password + "' WHERE EmployeeID = " + EmployeeID;
                	c.s.execute(query);
                	stage = (Stage) scenePane.getScene().getWindow();
                    stage.close();
                } else {
                	Alert alert = new Alert(AlertType.ERROR);
                	alert.setTitle("Incorrect Password!");
                	alert.setContentText("The old password entered is incorrect. Kindly verify!");
                	alert.showAndWait();
                }
        	} catch (Exception e){
                e.printStackTrace();
            }
        }
	}
        
	public void setID(int EmployeeID) {
		this.EmployeeID = EmployeeID;
	}
}
