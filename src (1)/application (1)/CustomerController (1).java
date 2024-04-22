package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CustomerController extends MenuController {
	@FXML
	private Button addButton;
	@FXML
	private Button removeButton;
	
	@FXML	
	private void addCustomer(ActionEvent event) {
		new AddCustomer();
	}
	
	@FXML
	private void removeCustomer(ActionEvent event) {
		new DeleteCustomer();
	}
	
	@FXML
	private void bill(ActionEvent event) {
		new MakeBill(EmployeeID);
	}
}
