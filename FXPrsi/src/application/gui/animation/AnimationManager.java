package application.gui.animation;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import application.gui.controller.GameController;
import application.gui.logic.OnFinishedAction;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationManager {
	private ParallelTransition moveAllCards;
	private ArrayList<ImageView> cards;
	private ArrayList<TranslateTransition> moveCards;
	
	private GameController controller;
	
	public AnimationManager(GameController contr) {
		controller = contr;
		
		cards = new ArrayList<>(8);
		moveCards = new ArrayList<>(cards.size());
		
		for(int i = 0; i < cards.size(); i++) {
			cards.add(new ImageView());
			
			moveCards.get(i).setNode(cards.get(i));
			moveCards.get(i).setDuration(Duration.seconds(1));
		}
	}
	
	/*private void animateAiDrawCards(int numCards) {

		Bounds deckBounds = deckBtn.localToScene(deckBtn.getBoundsInLocal());
		Bounds aiImageView;
		if(ai.getHand().size() <= 5) {
			int index = ai.getHand().size() - 1;
			aiImageView = allCardImages.get(index).localToScene(allCardImages.get(index).getBoundsInLocal());
		}
		else {
			aiImageView = cardImg3.localToScene(cardImg3.getBoundsInLocal());
		}

		double fromX = deckBounds.getMinX();
		double fromY = deckBounds.getMinY();

		double toX = aiImageView.getMinX();
		double toY = aiImageView.getMinY();

		for(int i = 0; i < numCards; i++) {
			cards.get(i).setImage(new Image("/application/image/assets/back.png"));
			cards.get(i).setTranslateX(0);
			cards.get(i).setTranslateY(0);
			cards.get(i).setX(fromX);
			cards.get(i).setY(fromY);

			moveCards.get(i).setAutoReverse(false);

			if(controller.getAi().getHand().size() + i <= 5) {
				int index = ai.getHand().size() - 1 + i;
				aiImageView = allCardImages.get(index).localToScene(allCardImages.get(index).getBoundsInLocal());
				toX = aiImageView.getMinX();
				toY = aiImageView.getMinY();
				
			}else {
				aiImageView = cardImg3.localToScene(cardImg3.getBoundsInLocal());
				double offset = (double) ThreadLocalRandom.current().nextInt(-100, 101);
				toX = aiImageView.getMinX() + offset;
				toY = aiImageView.getMinY();
			}
			
			moveCards.get(i).setToX(toX - fromX);
			moveCards.get(i).setToY(toY - fromY);
		}

		moveAllCards.getChildren().addAll(moveCards);
		
		for(int i = 0; i < numCards; i++) {
			cards.get(i).setVisible(true);
		}
		
		animationPane.setVisible(true);
		moveAllCards.play();

		moveAllCards.setOnFinished(ae -> {
			
			updateAICardCount();
			for(int i = 0; i < numCards; i++) {
				cards.get(i).setVisible(false);
			}
			
			animationPane.setVisible(false);
		});

	}*/

}
