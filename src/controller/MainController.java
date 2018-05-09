package controller;

import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Category;
import model.Flashcard;
import model.NoSelectionModel;
import view.CardCell;

public class MainController {

	private Stage appStage;
	private Category currentSubCategory;

	// Provide a callback extractor to the observable list to watch for changes in
	// the individual items
	private ObservableList<Flashcard> currentCards = FXCollections
			.observableArrayList(new Callback<Flashcard, Observable[]>() {
				@Override
				public Observable[] call(Flashcard param) {
					return new Observable[] { param.question(), param.answer(), param.noRight(), param.noWrong(),
							param.showQuestion() };
				}
			});

	public void setStage(Stage stage) {
		this.appStage = stage;
	}

	@FXML
	private TreeView<Category> categoryTreeView;

	@FXML
	private ListView<Flashcard> cardListView;

	@FXML
	public void initialize() {
		// Init categories
		Category parentCategory = new Category("Categories");
		Category category1 = new Category("JavaFX");
		Category category2 = new Category("React");
		Category subcategory1_1 = new Category("Application");
		Category subcategory1_2 = new Category("FXML");
		Category subcategory1_3 = new Category("Controller");
		Category subcategory2_1 = new Category("State");
		category1.addCategory(subcategory1_1);
		category1.addCategory(subcategory1_2);
		category1.addCategory(subcategory1_3);
		category2.addCategory(subcategory2_1);
		parentCategory.addCategory(category1);
		parentCategory.addCategory(category2);

		// Init flashcards
		Flashcard card1 = new Flashcard("Frage 1", "Antwort 1", 0, 0, null);
		Flashcard card2 = new Flashcard("Dies ist eine längere Frage, oder nicht?",
				"Sie verdient auch eine längere Antwort.", 0, 0, null);
		subcategory1_1.addCard(card1);
		subcategory1_1.addCard(card2);

		// Init TreeView
		TreeItem<Category> rootItem = new TreeItem<Category>(parentCategory);
		rootItem.setExpanded(true);
		for (Category category : parentCategory.getSubcategories()) {
			TreeItem<Category> leaf = new TreeItem<Category>(category);
			for (Category subcategory : category.getSubcategories()) {
				TreeItem<Category> deeperLeaf = new TreeItem<Category>(subcategory);
				leaf.getChildren().add(deeperLeaf);
			}
			rootItem.getChildren().add(leaf);
		}
		categoryTreeView.setRoot(rootItem);
		categoryTreeView.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TreeItem<Category>>() {

					@Override
					public void changed(ObservableValue<? extends TreeItem<Category>> observable,
							TreeItem<Category> oldValue, TreeItem<Category> newValue) {
						TreeItem<Category> selectedItem = newValue;
						// Only do something when a leaf is selected
						if (selectedItem.isLeaf()) {
							// Get the flashcards of this leaf
							currentSubCategory = selectedItem.getValue();
							List<Flashcard> cards = selectedItem.getValue().getCards();
							currentCards.clear();
							currentCards.addAll(cards);
							for (Flashcard card : currentCards) {
								System.out.println(card.getQuestion());
							}
						}
					}

				});

		// Init the context menu for the tree
		ContextMenu menu = new ContextMenu();
		MenuItem deleteItem = new MenuItem();
		deleteItem.setText("Delete category");
		deleteItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Remove the item from the view and remove the actual item from the tree
				TreeItem<Category> selected = categoryTreeView.getSelectionModel().getSelectedItem();
				TreeItem<Category> parent = selected.getParent();
				if (parent != null) {
					int childIndex = parent.getChildren().indexOf(selected);
					parent.getValue().getSubcategories().remove(childIndex);
					parent.getChildren().remove(childIndex);
				} 
			}
		});
		
		menu.getItems().add(deleteItem);
		categoryTreeView.setContextMenu(menu);

		// Init flashcard list
		cardListView.setItems(currentCards);
		// Set a selection model so the cards cannot be clicked but the buttons on the
		// cards still work
		cardListView.setSelectionModel(new NoSelectionModel<Flashcard>());

		// Set a own factory to create list items
		cardListView.setCellFactory(new Callback<ListView<Flashcard>, ListCell<Flashcard>>() {

			@Override
			public ListCell<Flashcard> call(ListView<Flashcard> param) {
				ListCell<Flashcard> cell = new CardCell(param);

				// Create a context menu to delete a selected card
				ContextMenu contextMenu = new ContextMenu();
				MenuItem deleteItem = new MenuItem();
				deleteItem.setText("Delete flashcard");
				deleteItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// Remove the item both from the observable list which is displayed
						// and the "storage" which in this case is the tree item
						cardListView.getItems().remove(cell.getItem());
						categoryTreeView.getSelectionModel().getSelectedItem().getValue().getCards()
								.remove(cell.getIndex());
					}
				});

				contextMenu.getItems().add(deleteItem);

				// Add the context menu only if the list isn't empty
				cell.emptyProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						if (newValue) {
							cell.setContextMenu(null);
						} else {
							cell.setContextMenu(contextMenu);
						}
					}

				});

				return cell;
			}
		});

	}

	@FXML
	void showCategoryDialog(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					FlashcardApplication.class.getResource("/view/add_category_dialog.fxml"));
			VBox page = (VBox) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add category");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(appStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AddCategoryController controller = loader.getController();
			controller.setTreeView(categoryTreeView);
			controller.setStage(dialogStage);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void showSubCategoryDialog(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					FlashcardApplication.class.getResource("/view/add_subcategory_dialog.fxml"));
			VBox page = (VBox) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add subcategory");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(appStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AddSubCategoryController controller = loader.getController();
			controller.setTreeView(categoryTreeView);
			controller.setStage(dialogStage);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void showCardDialog(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					FlashcardApplication.class.getResource("/view/add_flashcard_dialog.fxml"));
			VBox page = (VBox) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add flashcard");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(appStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AddFlashcardController controller = loader.getController();
			controller.setTreeView(categoryTreeView);
			controller.setStage(dialogStage);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void exitApplication(ActionEvent event) {
		Platform.exit();
	}

}
