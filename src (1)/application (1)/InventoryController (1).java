package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InventoryController extends MenuController {
	@FXML
	private Button addButton;
	@FXML
	private Button removeButton;
	@FXML
	private Button updateButton;
	
	@FXML	
	private void addProduct(ActionEvent event) {
		new AddProduct();
	}
	
	@FXML
	private void removeProduct(ActionEvent event) {
		new DeleteProduct();
	}
	
	@FXML
	private void updateProduct(ActionEvent event) {
		new UpdateProduct();
	}
}
