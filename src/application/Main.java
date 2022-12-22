package application;

import menu.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Geometry League");
		new MainMenu(this, primaryStage);
		primaryStage.show();
	}

}
