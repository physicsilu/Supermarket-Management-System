package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PromotionController extends MenuController {
	@FXML	
	private void addPromotion(ActionEvent event) {
		new AddPromotion();
	}
	
	@FXML
	private void removePromotion(ActionEvent event) {
		new DeletePromotion();
	}
	
	@FXML
	private void updatePromotion(ActionEvent event) {
		new UpdatePromotion();
	}
}
