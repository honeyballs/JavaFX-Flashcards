package view;

import controller.AnswerCardController;
import controller.QuestionCardController;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import model.Flashcard;

public class CardCell extends ListCell<Flashcard> {

	private ListView<Flashcard> parent;

	public CardCell(ListView<Flashcard> parent) {
		this.parent = parent;
	}

	@Override
	protected void updateItem(Flashcard item, boolean empty) {
		super.updateItem(item, empty);

		if (item != null) {
			if (item.isShowQuestion()) {
				QuestionCardController questionCard = new QuestionCardController(item);
				BorderPane pane = questionCard.getBorderPane();
				pane.prefWidthProperty().bind(parent.widthProperty());
				pane.maxWidthProperty().bind(parent.widthProperty());
				setGraphic(pane);
			} else {
				AnswerCardController answerCard = new AnswerCardController(item);
				BorderPane pane = answerCard.getBorderPane();
				pane.prefWidthProperty().bind(parent.widthProperty());
				pane.maxWidthProperty().bind(parent.widthProperty());
				setGraphic(pane);
			}
		} else {
			setGraphic(null);
		}

	}

}
