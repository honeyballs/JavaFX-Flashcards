package controller;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Category;
import model.Flashcard;

public class AddFlashcardController {

	private TreeView<Category> tree;
	private Stage stage;
	private ObservableList<Flashcard> carList;
	private Category currentCategory;
	private ObservableList<Category> categories = FXCollections.observableArrayList();
	private ObservableList<Category> subcategories = FXCollections.observableArrayList();
	private int selectedCategoryIndex;

	@FXML
	ChoiceBox<Category> categoryChoiceBox;

	@FXML
	ChoiceBox<Category> subCategoryChoiceBox;

	@FXML
	TextField questionTextField;

	@FXML
	TextField answerTextField;

	@FXML
	Label imagePathLabel;

	@FXML
	void initialize() {
		subCategoryChoiceBox.setItems(subcategories);
		categoryChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				selectedCategoryIndex = newValue.intValue();
				List<Category> loadedSubCategories = tree.getRoot().getChildren().get(selectedCategoryIndex).getValue()
						.getSubcategories();
				subcategories.clear();
				subcategories.addAll(loadedSubCategories);
				subCategoryChoiceBox.getSelectionModel().selectFirst();
			}

		});
	}

	@FXML
	void onCancel(ActionEvent event) {
		stage.close();
	}

	@FXML
	void imageAddButton(ActionEvent event) {
		// Create and configure a FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open image");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
		File f = fileChooser.showOpenDialog(stage);
		if (f != null) {
			String path = null;
			try {
				path = f.toURI().toURL().toExternalForm();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imagePathLabel.setText(path);
		}
	}

	@FXML
	void onAdd(ActionEvent event) {
		String question = questionTextField.getText();
		String answer = answerTextField.getText();
		int subCategoryIndex = subCategoryChoiceBox.getSelectionModel().getSelectedIndex();
		if (question != null && !question.equals("") && answer != null && !answer.equals("")) {
			if (!categoryChoiceBox.getSelectionModel().isEmpty()
					&& !subCategoryChoiceBox.getSelectionModel().isEmpty()) {
				Flashcard card;
				if (imagePathLabel.equals("Path")) {
					card = new Flashcard(question, answer, 0, 0, null);
				} else {
					card = new Flashcard(question, answer, 0, 0, imagePathLabel.getText());
				}
				tree.getRoot().getChildren().get(selectedCategoryIndex).getChildren().get(subCategoryIndex).getValue()
						.addCard(card);
				// Close the window
				stage.close();

			}
		}
	}

	public void setTreeView(TreeView<Category> tree) {
		this.tree = tree;
		for (int i = 0; i < tree.getRoot().getChildren().size(); i++) {
			Category category = tree.getRoot().getChildren().get(i).getValue();
			categories.add(category);
		}
		categoryChoiceBox.setItems(categories);
		categoryChoiceBox.getSelectionModel().selectFirst();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
