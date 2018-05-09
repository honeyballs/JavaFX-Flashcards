package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Flashcard;

public class QuestionCardController {

	@FXML
	BorderPane questionPane;
	
	@FXML
	Label questionLabel;
	
	@FXML
	Button turnButton;
	
	private Flashcard card;
	
	public QuestionCardController(Flashcard card) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/flashcards_question_card.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.card = card;
		this.questionLabel.setText(card.getQuestion());
	}
	
	@FXML
	void turnCardAround(ActionEvent event) {
		card.setShowQuestion(false);
	}
	
	public BorderPane getBorderPane() {
		return questionPane;
	}
	
	
	
}
