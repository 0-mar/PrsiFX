package application.gui.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class EndScreenController {
	
	public EndScreenController() {
		
	}
	
	@FXML private AnchorPane root;
	@FXML private Button backBtn;
	@FXML private Label message;
	@FXML private Label turns;
	@FXML private ImageView background;
	
	public void initialize() {
		
	}
	
	@FXML
	private void handleBackBtnClick() {
		//scene.setRoot(menu);
		ControllerManager cm = ControllerManager.getInstance();
		
		FadeTransition ft2 = new FadeTransition(Duration.seconds(2), cm.getMenuPane());
		ft2.setFromValue(0);
		ft2.setToValue(1);
		
		cm.switchPane("menu");
		//ft2.play();
	}
	
	public void setMessage(String msg, int turnsElapsed) {
		message.setText(msg);
		if(msg.equalsIgnoreCase("Vyhrál jsi!")) {
			message.setStyle("-fx-text-fill: linear-gradient(#9cc914, #48a612);");
		}else {
			message.setStyle("-fx-text-fill: linear-gradient(#bf1111, #782408);");
		}
		
		turns.setText(Integer.toString(turnsElapsed));
	}
	
	public void bindBackgroundSize() {
		background.fitWidthProperty().bind(background.getScene().widthProperty());
		background.fitHeightProperty().bind(background.getScene().heightProperty());
	}
}
