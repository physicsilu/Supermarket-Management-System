package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EmployeeController extends MenuController {
	@FXML	
	private void addEmployee(ActionEvent event) {
		new AddEmployee();
	}
	
	@FXML
	private void removeEmployee(ActionEvent event) {
		new DeleteEmployee();
	}
}
