package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuController {
	protected int EmployeeID;
	protected Stage stage;
	final String css = this.getClass().getResource("/Stylesheet/style.css").toExternalForm(); 
	
	@FXML
	protected void exit(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Exit!");
    	alert.setContentText("Are you sure you want to exit?");
    	
    	if(alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
    	}
    }
	
	@FXML
	protected void profile(ActionEvent event) {
		try {
			FXMLLoader profile_loader = new FXMLLoader(getClass().getResource("/FXML/Profile.fxml"));
			Parent root = profile_loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(css);
			
			ProfileController controller = profile_loader.getController();
			controller.setID(EmployeeID);
			controller.setFields();
			
			Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Profile");
			stage.setResizable(false);
			stage.setScene(scene);
            stage.showAndWait();
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	@FXML
	protected void switchToDashboard(ActionEvent event) {
		try {
			FXMLLoader profile_loader = new FXMLLoader(getClass().getResource("/FXML/Dashboard.fxml"));
			Parent root = profile_loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(css);
			
			DashboardController controller = profile_loader.getController();
			controller.setEmployeeID(EmployeeID);
			controller.setStage(stage);
			controller.refresh();
			
			stage.setResizable(false);
			stage.hide();
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	@FXML
	protected void switchToInventory(ActionEvent event) {
		try {
			FXMLLoader profile_loader = new FXMLLoader(getClass().getResource("/FXML/Inventory.fxml"));
			Parent root = profile_loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(css);
			
			InventoryController controller = profile_loader.getController();
			controller.setEmployeeID(EmployeeID);
			controller.setStage(stage);
			
			stage.setResizable(false);
			stage.hide();
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	@FXML
	protected void switchToEmployees(ActionEvent event) {
		try {
			FXMLLoader profile_loader = new FXMLLoader(getClass().getResource("/FXML/Employees.fxml"));
			Parent root = profile_loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(css);
			
			EmployeeController controller = profile_loader.getController();
			controller.setEmployeeID(EmployeeID);
			controller.setStage(stage);
			
			stage.setResizable(false);
			stage.hide();
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	@FXML
	protected void switchToCustomer(ActionEvent event) {
		try {
			FXMLLoader profile_loader = new FXMLLoader(getClass().getResource("/FXML/Customer.fxml"));
			Parent root = profile_loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(css);
			
			CustomerController controller = profile_loader.getController();
			controller.setEmployeeID(EmployeeID);
			controller.setStage(stage);
			
			stage.setResizable(false);
			stage.hide();
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	@FXML
	protected void switchToVendor(ActionEvent event) {
		try {
			FXMLLoader profile_loader = new FXMLLoader(getClass().getResource("/FXML/Vendor.fxml"));
			Parent root = profile_loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(css);
			
			VendorController controller = profile_loader.getController();
			controller.setEmployeeID(EmployeeID);
			controller.setStage(stage);
			
			stage.setResizable(false);
			stage.hide();
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	@FXML
	protected void switchToPromotion(ActionEvent event) {
		try {
			FXMLLoader profile_loader = new FXMLLoader(getClass().getResource("/FXML/Promotions.fxml"));
			Parent root = profile_loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(css);
			
			PromotionController controller = profile_loader.getController();
			controller.setEmployeeID(EmployeeID);
			controller.setStage(stage);
			
			stage.setResizable(false);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	public void setEmployeeID(int ID) {
		this.EmployeeID = ID;
	}
	
	public void setStage(Stage s) {
		this.stage = s;
	}
	

}
