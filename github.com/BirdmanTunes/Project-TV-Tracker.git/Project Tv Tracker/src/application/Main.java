package application;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application{
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent mainPane = FXMLLoader.load(Main.class.getResource("view/MainTab.fxml"));
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add("/application/view/LightTheme.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

	public static void main(String[] args) throws InvocationTargetException {
		
		launch(args);

    }
	

}
