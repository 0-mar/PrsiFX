package application.gui.controller;

import application.logic.Card;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MenuController {
	
	private ScaleTransition st;
	private TranslateTransition moveRight;
	private ParallelTransition pt;
	
	private double scale;
	private boolean ejected;
	
	private double creditsWidth;
	private TranslateTransition slide;
	
	public MenuController() {
		
	}
	
	@FXML private Label title;
	@FXML private Button playBtn;
	@FXML private Button creditsBtn;
	@FXML private Button quitBtn;
	@FXML private AnchorPane creditsPane;
	
	public void initialize() {
		creditsWidth = creditsPane.getPrefWidth();
		System.out.println("PREF WIDTH: " + creditsWidth);
		
		ejected = false;
		scale = 1.5;
		
		creditsPane.setTranslateX(creditsWidth);
		slide = new TranslateTransition(Duration.seconds(1), creditsPane);
		slide.setInterpolator(Interpolator.EASE_OUT);
		slide.setToX(0);
		
		moveRight = new TranslateTransition(Duration.seconds(1.5), title);
		moveRight.setToX(35);
		moveRight.setAutoReverse(true);
		moveRight.setCycleCount(Animation.INDEFINITE);
		
		st = new ScaleTransition(Duration.seconds(1.5));
		st.setToX(scale);
		st.setToY(scale);
		
		st.setNode(title);
		st.setAutoReverse(true);
		st.setCycleCount(Animation.INDEFINITE);
		//st.playFromStart();
		
		pt = new ParallelTransition(st, moveRight);
		pt.playFromStart();
		
	}
	
	@FXML
	private void handlePlayBtnClick() {
		
		ControllerManager cm = ControllerManager.getInstance();
		
		cm.getPlayer().eraseHand();
		cm.getAi().eraseHand();
		
		cm.getPlayer().getInst().resetInstructions();
		cm.getGameController().resetTurnCounter();

		cm.getDeck().initDeck();
		cm.getPlayer().createHand();
		cm.getAi().createHand();
		
		/* DEBUG ONLY */
		/*cm.getPlayer().getHand().add(new Card("cervena", "eso"));
		cm.getPlayer().getHand().add(new Card("zelena", "eso"));
		cm.getPlayer().getHand().add(new Card("kule", "eso"));
		*/
		
		/*cm.getPlayer().getHand().add(new Card("cervena", "VII"));
		cm.getPlayer().getHand().add(new Card("zaludy", "VII"));
		cm.getPlayer().getHand().add(new Card("kule", "VII"));
		cm.getPlayer().getHand().add(new Card("zelena", "VII"));
		
		cm.getAi().getHand().add(new Card("cervena", "VII"));*/
		//cm.getAi().getHand().add(new Card("zaludy", "VII"));
		//cm.getAi().getHand().add(new Card("kule", "VII"));

		/*------------------------------*/
		
		cm.getGameController().setTopCardImage();
		cm.getGameController().setCardButtons();
		cm.getGameController().updateAICardCount();
		cm.getGameController().updatePlayerCardCount();
		
		FadeTransition ft2 = new FadeTransition(Duration.seconds(2), cm.getGamePane());
		ft2.setFromValue(0);
		ft2.setToValue(1);
		
		/*FadeTransition ft1 = new FadeTransition(Duration.seconds(2), cm.getGamePane());
		ft1.setFromValue(1);
		ft1.setToValue(0);
		ft1.setOnFinished((ae) -> { ft2.play(); });*/
		
		//scene.setRoot(game);
		pt.pause();
		
		cm.switchPane("game");
		//cm.switchPane("endScreen"); 
		//ft2.play();
	
		
		
	}
	
	@FXML
	private void handleButtonHover(MouseEvent me) {
		/*Button src = (Button) me.getSource();
		
		st.setNode(src);
		st.setByX(scale);
		st.setByY(scale);
		st.playFromStart();*/
	}
	
	@FXML
	private void handleOnMouseExit() {
		/*if(st.getStatus().equals(Status.RUNNING))
			st.stop();
		st.setByX(-scale);
		st.setByY(-scale);
		st.play();*/
		
	}
	
	@FXML
	private void handleQuitBtnClick() {
		Platform.exit();
	}
	
	@FXML
	private void handleCreditsBtnClick() {
		if(slide.getStatus().equals(Status.RUNNING))
			return;
		
		if(!ejected) {
			slide.setToX(0);
			slide.play();
			ejected = true;
		}else {
			//creditsWidth = creditsPane.getWidth();
			slide.setToX(creditsWidth);
			slide.play();
			ejected = false;
		}
	}
	
	public void startPulsing() {
		pt.play();
	}
}
