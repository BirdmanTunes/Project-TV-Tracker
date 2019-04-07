package application.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainController {

//	@FXML
//	Tab2Controller tab2Control;
//	Tab2Controller tab3Control;
	
	@FXML
	private Button light, dark;
	@FXML
	private TabPane tabPane;
	@FXML
	private HBox themeBox;
	@FXML
	private AnchorPane mainNode;
	
	
	
	
	@FXML private void initialize() {
//		tab2Control.injectMainController(this);
//		tab3Control.injectMainController(this);
//		mainNode.getScene().getStylesheets().add("/application/view/LightTheme.css");

	}
	
	public void setLightTheme() {
		mainNode.getScene().getStylesheets().remove("/application/view/DarkTheme.css");
		mainNode.getScene().getStylesheets().add("/application/view/LightTheme.css");
	}
	
	public void setDarkTheme() {
		mainNode.getScene().getStylesheets().remove("/application/view/LightTheme.css");
		mainNode.getScene().getStylesheets().add("/application/view/DarkTheme.css");
	}
	
}



