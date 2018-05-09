package controller;

import org.omg.CORBA.INITIALIZE;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import model.Category;

public class AddSubCategoryController {
	
	private TreeView<Category> tree;
	private Stage stage;
	private ObservableList<Category> categories = FXCollections.observableArrayList();
	
	@FXML
	ChoiceBox<Category> categoryChoiceBox;
	
	@FXML
	TextField subCategoryNameField;
	
	@FXML
	void onCancel(ActionEvent event) {
		stage.close();
	}
	
	@FXML
	public void initialize() {
		
	}
	
	@FXML
	void onAdd(ActionEvent event) {
		String name = subCategoryNameField.getText();
		int categoryIndex = categoryChoiceBox.getSelectionModel().getSelectedIndex();
		if (name != null && !name.equals("") && !categoryChoiceBox.getSelectionModel().isEmpty()) {
			Category subcategory = new Category(name);
			tree.getRoot().getChildren().get(categoryIndex).getValue().addCategory(subcategory);
			tree.getRoot().getChildren().get(categoryIndex).getChildren().add(new TreeItem<Category>(subcategory));
			//Close the window
			stage.close();
		}
	}
	
	public void setTreeView(TreeView<Category> tree) {
		this.tree = tree;
		for (int i = 0; i < tree.getRoot().getChildren().size(); i ++) {
			categories.add(tree.getRoot().getChildren().get(i).getValue());
		}
		categoryChoiceBox.setItems(categories);
		categoryChoiceBox.getSelectionModel().selectFirst();
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}


}
