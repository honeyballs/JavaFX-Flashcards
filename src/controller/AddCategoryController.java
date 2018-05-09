package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import model.Category;

public class AddCategoryController {
	
	private TreeView<Category> tree;
	private Stage stage;
	
	@FXML
	TextField categoryTextField;
	
	@FXML
	void onCancel(ActionEvent event) {
		stage.close();
	}
	
	@FXML
	void onAdd(ActionEvent event) {
		String name = categoryTextField.getText();
		if (name != null && !name.equals("")) {
			Category category = new Category(name);
			Category parent = tree.getRoot().getValue();
			parent.addCategory(category);
			tree.getRoot().getChildren().add(new TreeItem<Category>(category));
			//Close the window
			stage.close();
		}
	}
	
	public void setTreeView(TreeView<Category> tree) {
		this.tree = tree;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
