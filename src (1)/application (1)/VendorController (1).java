package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class VendorController extends MenuController {
	@FXML	
	private void addVendor(ActionEvent event) {
		new AddVendor();
	}
	
	@FXML
	private void removeVendor(ActionEvent event) {
		new DeleteVendor();
	}

	@FXML
	private void placeOrder(ActionEvent event) {
		new MakeOrder(EmployeeID);
	}
	
	@FXML
	private void updateOrder(ActionEvent event) {
		new UpdateOrder();
	}
}