package application.gui.controller;

import java.io.IOException;

import application.gui.Main;
import application.logic.AI;
import application.logic.Deck;
import application.logic.Instructions;
import application.logic.Player;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

public class ControllerManager {

	private static ControllerManager instance = null;
	
	/**
	 * Scene 
	 */
	private Scene scene;
	
	private Player player;
	private AI ai;
	private Deck deck;
	private Instructions instructions;
	
	private MenuController menuController;
	private GameController gameController;
	private EndScreenController esController;
	
	private FXMLLoader ldr;
	
	private Parent menu;
	private Parent game;
	private Parent endScreen;
	
	private ControllerManager() {
		this.instructions = new Instructions();
		this.deck = new Deck(instructions);
		this.player = new Player(deck, instructions, "Player");
		this.ai = new AI(deck, instructions, "BOT");
		
		initManager();
	}
	
	public void initManager() {
		menuController = new MenuController();
		gameController = new GameController(deck, player, ai);
		esController = new EndScreenController();
		
		ldr = new FXMLLoader(Main.class.getResource("/application/gui/fxml/Menu.fxml"));
		ldr.setController(menuController);
		try {
			menu = ldr.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ldr = new FXMLLoader(Main.class.getResource("/application/gui/fxml/FXMLMain.fxml"));
		ldr = new FXMLLoader(Main.class.getResource("/application/gui/fxml/Game.fxml"));
		instructions.setController(gameController);
		ldr.setController(gameController);
		try {
			game = ldr.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ldr = new FXMLLoader(Main.class.getResource("/application/gui/fxml/EndScreen.fxml"));
		ldr = new FXMLLoader(Main.class.getResource("/application/gui/fxml/EndOfGame.fxml"));
		ldr.setController(esController);
		try {
			endScreen = ldr.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void switchPane(String paneName) {
		if(scene == null)
			throw new NullPointerException("Scene can’t be null!");
		
		FadeTransition ft2 = new FadeTransition(Duration.seconds(2));
		ft2.setFromValue(0);
		ft2.setToValue(1);
		
		switch(paneName) {
			case "menu":
				menuController.startPulsing();
				scene.setRoot(menu);
				ft2.setNode(menu);
				ft2.play();
				
				break;
				
			case "game":
				scene.setRoot(game);
				ft2.setNode(game);
				ft2.play();
				break;
				
			case "endScreen":
				scene.setRoot(endScreen);
				
				esController.bindBackgroundSize();
				
				ft2.setNode(endScreen);
				ft2.play();
				break;
		}
	}
	
	/**
	 * Returns instance of this class.<br>
	 * <i><b>Disclaimer:</b></i> when you obtain this class for the first time, you need to use the setScene() method
	 * 							 prior to using the switchPane() method!!! That’s because it has no scene object assigned.
	 * 
	 * @return The only 1 instance of this class that exists
	 */
	public static ControllerManager getInstance() {
		if (instance == null) 
            instance = new ControllerManager(); 
  
        return instance; 
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public Parent getMenuPane() { return menu; }
	public Parent getGamePane() { return game; }
	public Parent getEndScreenPane() { return endScreen; }

	public MenuController getMenuController() {
		return menuController;
	}

	public GameController getGameController() {
		return gameController;
	}

	public EndScreenController getEsController() {
		return esController;
	}

	public Player getPlayer() {
		return player;
	}

	public AI getAi() {
		return ai;
	}

	public Deck getDeck() {
		return deck;
	}
}
