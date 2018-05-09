package controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FlashcardApplication extends Application {

	public static void main(String[] args) {
        launch(args);
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/flashcards_main_window.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 1080, 720);
		scene.getStylesheets().add("./view/styles.css");
		primaryStage.setTitle("THM Flashcards");
		primaryStage.setScene(scene);
		MainController controller = loader.getController();
		controller.setStage(primaryStage);
		
		primaryStage.show();
	}

}
