package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Main extends Application {
		
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
		Scene primaryScene = new Scene(root, Color.BLACK);
		String css = this.getClass().getResource("/Stylesheet/simple.css").toExternalForm();	
		Image icon = new Image("app_icon.png");
	
		primaryScene.getStylesheets().add(css);

		primaryStage.getIcons().add(icon);
		primaryStage.setTitle("Akshay Supermarket");
		primaryStage.setResizable(false);
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}
}