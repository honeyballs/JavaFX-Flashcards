package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Flashcard;

public class AnswerCardController {
	
	@FXML
	BorderPane answerBorderPane;
	
	@FXML
	Label questionLabel;
	
	@FXML
	Label answerLabel;
	
	@FXML
	Label percentageLabel;
	
	@FXML
	Button knewButton;
	
	@FXML
	Button dontKnowButton;
	
	@FXML
	ImageView imageView;
	
	private Flashcard card;
	
	public AnswerCardController(Flashcard card) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/flashcards_answer_card.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.card = card;
		setContent();
	}
	
	public void setContent() {
		this.questionLabel.setText(card.getQuestion());
		this.answerLabel.setText(card.getAnswer());
		this.percentageLabel.setText("Quote: " + card.getPercentage());
		if (card.getImagePath() != null) {
			Image image = new Image(card.getImagePath());
			imageView.setImage(image);
			imageView.setFitHeight(75);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			imageView.setVisible(true);
		} else {
			//If no image path is available we remove the imageView completely
			imageView.setVisible(false);
			imageView.setManaged(false);
		}
	}
	
	public BorderPane getBorderPane() {
		return answerBorderPane;
	}
	
	@FXML
	void answerCard(ActionEvent event) {
		if (((Button) event.getSource()).getText().equals("I knew that!")) {
			card.incrementRight();
		} else {
			card.incrementWrong();
		}
		card.setShowQuestion(true);
	}
	
	@FXML
	void imageClicked(MouseEvent event) {
		//Open the image in a new Window
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/image.fxml"));
			root = loader.load();
			
			Stage stage = new Stage();
			stage.setTitle(card.getImagePath());
			stage.setScene(new Scene(root, 600, 400));			
			ImageController controller = (ImageController) loader.getController();
			controller.setImagePath(card.getImagePath());
			
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
