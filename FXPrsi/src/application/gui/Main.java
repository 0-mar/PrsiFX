package application.gui;
	
import application.gui.controller.ControllerManager;
import application.gui.controller.MenuController;
import application.logic.AI;
import application.logic.Deck;
import application.logic.Instructions;
import application.logic.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	private ControllerManager cm;
	private FXMLLoader ldr;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			cm = ControllerManager.getInstance();
			Scene scene = new Scene(cm.getMenuPane(), 1280, 1000);
			//Scene scene = new Scene(cm.getEndScreenPane());
			
			cm.setScene(scene);
			
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// called before start -> construct game objects
	@Override
	public void init() throws Exception {
		/*this.instructions = new Instructions();
		this.deck = new Deck(instructions);
		this.player = new Player(deck, instructions, "Player");
		this.ai = new AI(deck, instructions, "BOT");*/
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
