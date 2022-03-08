package application;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.opencv.core.Core;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * The Main class is used to launch the program as a JavaFX Application.
 */
public class Main extends Application {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load the OpenCV Core library
	}

	/**
	 * Start the program and load FXML and CSS files.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Camera.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root,800,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			CameraController controller = loader.getController();
			primaryStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					System.exit(0);
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launch application.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
