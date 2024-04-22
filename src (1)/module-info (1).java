module ExperimentFX_v02 {
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.fxml;
	requires transitive java.desktop;
	requires transitive java.sql;
	requires javafx.base;
		
	opens application to javafx.graphics, javafx.fxml;
	exports application;
}
