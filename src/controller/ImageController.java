package controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ImageController {
	
	private String path = null;
	
	@FXML
	ImageView imageView;
	
	@FXML
	AnchorPane anchorPane;
	
	@FXML
	public void initialize() {
		imageView.fitWidthProperty().bind(anchorPane.widthProperty());
		imageView.fitHeightProperty().bind(anchorPane.heightProperty());
	}
	
	public void setImagePath(String path) {
		//this.path = path;
		Image image = new Image(path);
		imageView.setImage(image);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		
	}
	
	

}
