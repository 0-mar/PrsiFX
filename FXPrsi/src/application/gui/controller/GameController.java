package application.gui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import application.gui.Main;
import application.gui.logic.OnFinishedAction;
import application.image.CardPositions;
import application.logic.AI;
import application.logic.Card;
import application.logic.Deck;
import application.logic.Player;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.Duration;

public class GameController{

	private Player player;
	private AI ai;
	private Deck deck;
	private Card hoverCard;

	public static final String PATH = "/application/image/assets/";
	public static final String EXT = ".png";

	private CardPositions cardPosInst = CardPositions.getInstance();
	private HashMap<String, String> cardPos;
	private Timeline timer;

	String debugMsg = "";

	private int turnsElapsed;

	public GameController(Deck d, Player p, AI ai) {
		this.deck = d;
		this.player = p;
		this.ai = ai;

		this.hoverCard = null;
		turnsElapsed = 0;
	}

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-	
	 * 	FXML |
	 *	FXML ¡
	 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
	@FXML private ArrayList<Button> allButtons;
	@FXML private ArrayList<ImageView> allCardImages;
	@FXML private Button deckBtn;
	@FXML private ImageView topCardView;
	@FXML private GridPane aiCards;
	@FXML private AnchorPane acePanel;
	@FXML private AnchorPane animationPane;
	@FXML private GridPane root;
	@FXML private Label numOfPlayerCards;
	@FXML private Label numOfAICards;
	@FXML private ImageView cardImg3;
	@FXML private Button cardBtn3;
	@FXML private Button homeBtn;

	private ContextMenu colorSelection;
	private MenuItem m1;
	private MenuItem m2;
	private MenuItem m3;
	private MenuItem m4;
	
	private Popup popup;

	private TranslateTransition translateImage;
	private ImageView animImage;

	private TranslateTransition translateAcePanel;
	
	private ParallelTransition moveAllCards;
	private ArrayList<ImageView> cards;
	private ArrayList<TranslateTransition> moveCards;

	@FXML
	private void handleDeckClick() {
		if(player.getInst().getPlayerInstruction().equals("skip")) {
			return;
		}

		final int handSize = player.getNumOfCards();
		
		if(player.getInst().getPlayerInstruction().equals("draw")) {
			player.processVII();
		}

		else {
			//deck.drawCards(1, player);
			player.drawCards(1);
		}

		turnsElapsed++;
		animateDrawCards(player.getDrawnCards(), handSize);
		/*ai.takeMyTurn(ai.chooseRandomCard());
		setTopCardImage();
		setCardButtons();
		updateAICardCount();
		updatePlayerCardCount();*/
	}

	@FXML
	private void handleCardButtonClick(MouseEvent me) {
		Button source = (Button) me.getSource();
		//String style = source.getStyle();

		int index = 0;
		for(; index < player.getHand().size(); index++) {
			if(source == allButtons.get(index))
				break;
		}

		Card selectedCard = null;
		if(index >= player.getHand().size())
			return;
		else
			selectedCard = player.getHand().get(index);

		if(selectedCard.getId().equals("svrsek"))
			colorSelection.hide();

		turnsElapsed++;
		player.takeMyTurn(selectedCard);
		if(player.isSkipTurn()) {
			player.setSkipTurn(false);
			return;
		}

		//Card aiCard = ai.chooseRandomCard();

		animatePlayerCard(source, selectedCard);

		source.setStyle(null);
		setCardButtons();
		updatePlayerCardCount();

	}

	@FXML
	private void handleCardButtonHover(MouseEvent me) {
		Button source = (Button) me.getSource();

		int index = 0;
		for(; index < player.getHand().size(); index++) {
			if(source == allButtons.get(index))
				break;
		}

		//Card selectedCard = null;
		if(index >= player.getHand().size())
			return;
		else
			hoverCard = player.getHand().get(index);

		if(!hoverCard.getId().equals("svrsek"))
			return;

		m1.setOnAction(ae -> { hoverCard.setColor(m1.getText()); setCardButtons();});
		m2.setOnAction(ae -> { hoverCard.setColor(m2.getText()); setCardButtons();});
		m3.setOnAction(ae -> { hoverCard.setColor(m3.getText()); setCardButtons();});
		m4.setOnAction(ae -> { hoverCard.setColor(m4.getText()); setCardButtons();});

		if(!colorSelection.isShowing())
			colorSelection.show(source, Side.BOTTOM, 20, 0);
	}


	/**
	 * 
	 */
	public void initialize() {
		animationPane.setVisible(false);
		/*if(deck.getTopCard().getId().equals("eso")) {
			System.out.println("eso!");
			//Player.whatToDo = "skip";
			player.getInst().setPlayerInstruction("skip");
		}*/

		cardPos  = cardPosInst.getCardPositionsMap();
		setCardButtons();

		//topCardView.setImage(new Image("/application/image/assets/" + deck.getTopCard().getColor() + deck.getTopCard().getId() + ".png"));

		timer = new Timeline(new KeyFrame(Duration.seconds(0.8), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setTopCardImage();
				updateAICardCount();
			}
		}));

		timer.setCycleCount(1);

		makeContextMenu();

		translateAcePanel = new TranslateTransition(Duration.seconds(.5), acePanel);
		acePanel.setTranslateY(329);
		//System.out.println("AcePanel je na souøadnicích: " + "Y: " + acePanel.getTranslateY());
		
		animImage = new ImageView();
		translateImage = new TranslateTransition(Duration.seconds(1), animImage);

		animationPane.getChildren().add(animImage);
		
		popup = null;
		
		cards = new ArrayList<>(8);
		moveCards = new ArrayList<>(cards.size());
		moveAllCards = new ParallelTransition();
		
		for(int i = 0; i < 8; i++) {
			cards.add(new ImageView());
			moveCards.add(new TranslateTransition());
			
			moveCards.get(i).setNode(cards.get(i));
			moveCards.get(i).setDuration(Duration.seconds(1));
			
			moveAllCards.getChildren().add(moveCards.get(i));
			
			animationPane.getChildren().add(cards.get(i));
		}
	}

	@FXML
	private void acePanelClicked(MouseEvent me) {
		// jde nastavit kurzor - mozna by potom sel MLG click pri kliknuti?
		//acePanel.setCursor(new ImageCursor(new Image("/application/image/assets/deck.png")));

		/*aiCards.setVisible(true);
		acePanel.setVisible(false);*/

		Bounds acePanelBnds = acePanel.localToScene(acePanel.getBoundsInLocal());
		translateAcePanel.setToY(acePanelBnds.getHeight());
		translateAcePanel.setOnFinished(ae -> {
			//System.out.println("AcePanel je po SCHOVÁNÍ na souøadnicích: " + "Y: " + acePanel.getTranslateY());
			acePanel.setVisible(false);

			player.getInst().setPlayerInstruction("nothing");

			Card aiCard = ai.chooseRandomCard();
			turnsElapsed++;
			ai.takeMyTurn(aiCard);

			if(aiCard != null) {
				//animationPane.setVisible(true);
				//aiCardImage.setVisible(true);
				animateAiCard(aiCard);
			}else {
				//animationPane.setVisible(false);
				animateAiDrawCard();
				setTopCardImage();
				updateAICardCount();
			}
			//aiCards.setVisible(true);
		});

		translateAcePanel.play();

	}

	public void displayAce() {
		//Bounds acePanelBnds = acePanel.localToScene(acePanel.getBoundsInLocal());
		translateAcePanel.setToY(0);
		translateAcePanel.setOnFinished(ae -> {
			//System.out.println("AcePanel je po ZOBRAZENÍ na souøadnicích: " + "Y: " + acePanel.getTranslateY());
		});
		//aiCards.setVisible(false);
		acePanel.setVisible(true);
		translateAcePanel.play();
	}

	public void hideAce() {
		Bounds acePanelBnds = acePanel.localToScene(acePanel.getBoundsInLocal());
		translateAcePanel.setToY(acePanelBnds.getHeight());
		translateAcePanel.setOnFinished(ae -> {
			//System.out.println("AcePanel je po SCHOVÁNÍ na souøadnicích: " + "Y: " + acePanel.getTranslateY());
			acePanel.setVisible(false);
			//aiCards.setVisible(true);
		});

		translateAcePanel.play();
	}

	private void makeContextMenu() {
		colorSelection = new ContextMenu();

		m1 = new MenuItem("cervena");
		m2 = new MenuItem("zelena");
		m3 = new MenuItem("kule");
		m4 = new MenuItem("zaludy");

		colorSelection.getItems().addAll(m1, m2, m3, m4);
	}

	private void animatePlayerCard(Button source, Card selectedCard) {
		//Bounds b = source.localToScene(root.getBoundsInLocal());
		Bounds b = source.localToScene(source.getBoundsInLocal());
		Bounds topCardBnds = topCardView.localToScene(topCardView.getBoundsInLocal());
		
		System.out.println();
		System.out.println("[animateAiCard();] souradnice vrchni karty: " + topCardBnds.getMinX() + " " + topCardBnds.getMinY());
		System.out.println();

		Card aiCard = ai.chooseRandomCard();
		debugMsg = selectedCard.getColor() + " " + selectedCard.getId();

		animationPane.setVisible(true);
		animateImageTo(new Image("/application/image/assets/" + selectedCard.getColor() + selectedCard.getId() + ".png"), b.getMinX(), b.getMinY(), topCardBnds.getMinX(), topCardBnds.getMinY(),
				() -> {
					animationPane.setVisible(false);
					setTopCardImage();
					updatePlayerCardCount();
					setCardButtons();

					if(player.getHand().isEmpty()) {
						ControllerManager cm = ControllerManager.getInstance();
						cm.getEsController().setMessage("Vyhrál jsi!", turnsElapsed);

						if(acePanel.isVisible())
							acePanel.setVisible(false);
						
						cm.switchPane("endScreen");
						return;
					}
					
					final String inst = ai.getInst().getAIInstruction();
					final int handSize = ai.getNumOfCards();
					ai.takeMyTurn(aiCard);
					
					if(aiCard != null)
						animateAiCard(aiCard);
					else {
						if(inst.equals("skip")) {
							animationPane.setVisible(false);
							setTopCardImage();
							updateAICardCount();
						}
						// bud lizu kvuli VII nebo nemam zadnou hratelnou kartu
						else {
							animateAiDrawCards(ai.getNumOfDrawCards(), handSize);
						}
					}
					
				});

	}

	private void animateAiCard(Card aiCard) {
		Bounds topCardBnds = topCardView.localToScene(topCardView.getBoundsInLocal());
		
		System.out.println();
		System.out.println("[animateAiCard();] souradnice vrchni karty: " + topCardBnds.getMinX() + " " + topCardBnds.getMinY());
		System.out.println();
		
		//Bounds aiImageView = cardImg3.localToScene(cardImg3.getBoundsInLocal());

		Bounds aiImageView;

		if(ai.getHand().size() < 5) {
			int index = ai.getHand().size();
			aiImageView = allCardImages.get(index).localToScene(allCardImages.get(index).getBoundsInLocal());
		}
		else {
			aiImageView = cardImg3.localToScene(cardImg3.getBoundsInLocal());
		}

		debugMsg = aiCard.getColor() + " " + aiCard.getId();

		animationPane.setVisible(true);
		animateImageTo(new Image("/application/image/assets/" + aiCard.getColor() + aiCard.getId() + ".png"),aiImageView.getMinX(),aiImageView.getMinY(), topCardBnds.getMinX(), topCardBnds.getMinY(),
				() -> {

					//aiCardImage.setTranslateX(0);
					//aiCardImage.setY(aiImageView.getMinY());
					//aiCardImage.setVisible(false);
					//animationPane.setVisible(false);
					setTopCardImage();
					updateAICardCount();
					animationPane.setVisible(false);

					if(ai.getHand().isEmpty()) {
						ControllerManager cm = ControllerManager.getInstance();

						cm.getEsController().setMessage("Prohrál jsi!", turnsElapsed);
						//scene.setRoot(endScreen);
						cm.switchPane("endScreen");
						return;
					}

				});

	}

	private void animateDrawCard() {
		int index = player.getHand().size() - 1;
		Button firstFree = allButtons.get(index);

		Bounds deckBounds = deckBtn.localToScene(deckBtn.getBoundsInLocal());
		Bounds playerHandButtonBounds = firstFree.localToScene(firstFree.getBoundsInLocal());

		debugMsg = player.getHand().get(player.getHand().size() - 1).getColor() + " " + player.getHand().get(player.getHand().size() - 1).getId();

		animationPane.setVisible(true);
		animateImageTo(new Image("/application/image/assets/" + player.getHand().get(player.getHand().size() - 1).getColor() + player.getHand().get(player.getHand().size() - 1).getId() + ".png"), deckBounds.getMinX(),
				deckBounds.getMinY(), playerHandButtonBounds.getMinX(), playerHandButtonBounds.getMinY(), () -> {
					animationPane.setVisible(false);

					Card aiCard = ai.chooseRandomCard();

					ai.takeMyTurn(aiCard);

					if(aiCard != null) {
						animateAiCard(aiCard);
					}else { 
						animateAiDrawCard();
					}

					setCardButtons();
					updatePlayerCardCount();


				});
	}
	
	private void animateDrawCards(ArrayList<Card> drawnCards, int handSize) {
		System.out.println("numCards = " + drawnCards.size());
		System.out.println("handSize = " + handSize);
		System.out.println(drawnCards);
		Bounds deckBounds = deckBtn.localToScene(deckBtn.getBoundsInLocal());
		Bounds playerCardButton;
		if(handSize < 5) {
			int index = handSize;
			playerCardButton = allButtons.get(index).localToScene(allButtons.get(index).getBoundsInLocal());
		}
		else {
			playerCardButton = allButtons.get(2).localToScene(allButtons.get(2).getBoundsInLocal());
		}

		double fromX = deckBounds.getMinX();
		double fromY = deckBounds.getMinY();

		double toX = playerCardButton.getMinX();
		double toY = playerCardButton.getMinY();

		for(int i = 0; i < drawnCards.size(); i++) {
			cards.get(i).setImage(new Image("/application/image/assets/" + drawnCards.get(i).getColor() + drawnCards.get(i).getId() +".png"));
			System.out.println("Nacitam Obrazky: " + "/application/image/assets/" + drawnCards.get(i).getColor() + drawnCards.get(i).getId() +".png");
			cards.get(i).setTranslateX(0);
			cards.get(i).setTranslateY(0);
			cards.get(i).setX(fromX);
			cards.get(i).setY(fromY);

			moveCards.get(i).setAutoReverse(false);

			if(handSize + i < 5) {
				int index = handSize + i;
				playerCardButton = allButtons.get(index).localToScene(allButtons.get(index).getBoundsInLocal());
				toX = playerCardButton.getMinX();
				toY = playerCardButton.getMinY();
				
			}else {
				playerCardButton = allButtons.get(2).localToScene(allButtons.get(2).getBoundsInLocal());
				double offset = (double) ThreadLocalRandom.current().nextInt(-200, 201);
				toX = playerCardButton.getMinX() + offset;
				toY = playerCardButton.getMinY();
			}
			
			moveCards.get(i).setToX(toX - fromX);
			moveCards.get(i).setToY(toY - fromY);
		}

		//moveAllCards.getChildren().addAll(moveCards);
		
		for(int i = 0; i < drawnCards.size(); i++) {
			cards.get(i).setVisible(true);
		}
		
		animationPane.setVisible(true);
		moveAllCards.play();

		moveAllCards.setOnFinished(ae -> {
			
			for(int i = 0; i < drawnCards.size(); i++) {
				cards.get(i).setVisible(false);
			}
			
			animationPane.setVisible(false);

			Card aiCard = ai.chooseRandomCard();

			ai.takeMyTurn(aiCard);

			if(aiCard != null) {
				animateAiCard(aiCard);
			}else { 
				animateAiDrawCard();
			}

			setCardButtons();
			updatePlayerCardCount();


		});

	}

	private void animateAiDrawCard() {
		Bounds deckBounds = deckBtn.localToScene(deckBtn.getBoundsInLocal());
		Bounds aiImageView;

		if(ai.getHand().size() <= 5) {
			int index = ai.getHand().size() - 1;
			aiImageView = allCardImages.get(index).localToScene(allCardImages.get(index).getBoundsInLocal());
		}
		else {
			aiImageView = cardImg3.localToScene(cardImg3.getBoundsInLocal());
		}

		debugMsg = "karta zezadu";

		animationPane.setVisible(true);
		animateImageTo(new Image("/application/image/assets/back.png"), deckBounds.getMinX(), deckBounds.getMinY(), aiImageView.getMinX(), aiImageView.getMinY(),
				() -> {
					//
					updateAICardCount();
					animationPane.setVisible(false);
				});
	}
	
	private void animateAiDrawCards(int numCards, int handSize) {
		System.out.println("numCards = " + numCards);
		System.out.println("handSize = " + handSize);
		Bounds deckBounds = deckBtn.localToScene(deckBtn.getBoundsInLocal());
		Bounds aiImageView;
		if(handSize < 5) {
			int index = handSize;
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

			if(handSize + i < 5) {
				int index = handSize + i;
				aiImageView = allCardImages.get(index).localToScene(allCardImages.get(index).getBoundsInLocal());
				toX = aiImageView.getMinX();
				toY = aiImageView.getMinY();
				
			}else {
				aiImageView = cardImg3.localToScene(cardImg3.getBoundsInLocal());
				double offset = (double) ThreadLocalRandom.current().nextInt(-200, 201);
				toX = aiImageView.getMinX() + offset;
				toY = aiImageView.getMinY();
			}
			
			moveCards.get(i).setToX(toX - fromX);
			moveCards.get(i).setToY(toY - fromY);
		}

		//moveAllCards.getChildren().addAll(moveCards);
		
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

	}

	private void animateImageTo(Image img, double fromX, double fromY, double toX, double toY, OnFinishedAction func) {

		animImage.setImage(img);
		animImage.setTranslateX(0);
		animImage.setTranslateY(0);

		animImage.setX(fromX);
		animImage.setY(fromY);

		translateImage.setAutoReverse(false);

		double moveX = toX - fromX;
		double moveY = toY - fromY;

		translateImage.setToX(toX - fromX);
		translateImage.setToY(toY - fromY);

		//animationPane.setVisible(true);
		translateImage.play();

		/*System.out.println("[DEBUG]: ");
		System.out.println("Je AnimationPane videt: " + animationPane.isVisible());
		System.out.println("Obrazek karty: " + debugMsg);
		System.out.println();*/

		translateImage.setOnFinished(ae -> {
			func.onFinishedAnim();
			//animImage.setTranslateX(0);
			//animImage.setTranslateY(0);
			//animationPane.setVisible(false);
		});

	}

	@FXML
	private void homeBtnClicked() {
		/*Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
        HBox hbox = new HBox(20);
        hbox.getChildren().add(new Button("Testovací tlaèítko"));

        Scene scene = new Scene(hbox);
        popup.setScene(scene);
        popup.show();*/
		if(popup == null) {
			popup = new Popup();
			popup.setX(530);
			popup.setY(430);

			VBox vbox = new VBox(30);
			vbox.getStylesheets().add(Main.class.getResource("css/menu.css").toExternalForm());
			//hbox.getStyleClass().add(".credits");
			vbox.setStyle("-fx-background-color: #1c1306;-fx-background-radius: 5;" + 
						"-fx-border-color: white;" + 
						"-fx-border-width: 5;" + 
						"-fx-border-radius: 6;");
			vbox.setPrefSize(600, 300);
					
			HBox hbox = new HBox(220);
			Button yes = new Button("Ano");
			yes.setOnMouseClicked(me -> {
				popup.hide();
				ControllerManager.getInstance().switchPane("menu");
			});
			Button no = new Button("Ne");
			no.setOnMouseClicked(me -> {
				popup.hide();
			});
			hbox.getChildren().addAll(yes, no);
			HBox.setMargin(yes, new Insets(0, 0, 0, 70));
			
			Label text = new Label("Opravdu si pøeješ skonèit? Veškerý postup bude ztracen.");
			text.setStyle("	-fx-font-size: 15pt;" + 
					"-fx-font-family: \"Sans Serif\";" + 
					"-fx-font-weight: bold;" +
					"-fx-text-fill: #e6d712;");
			vbox.getChildren().addAll(text, hbox);
			VBox.setMargin(text, new Insets(50, 0, 70, 40));
			
			popup.getContent().addAll(vbox);
		}

		if(!popup.isShowing())
			popup.show(homeBtn.getScene().getWindow());
		else popup.hide();
	}
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-	
	 * 	OTHER
	 *	
	 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/	
	public void setCardButtons() {
		ArrayList<Card> playerHand = player.getHand();

		for(int i = 0; i < allButtons.size(); i++) {
			if(i > playerHand.size() - 1) {
				allButtons.get(i).setStyle(null);
				break;
			}
			else
				allButtons.get(i).setStyle(cardPos.get(playerHand.get(i).getColor() + playerHand.get(i).getId()));
		}

	}

	public void setTopCardImage() {
		//System.out.println(deck.getTopCard());
		topCardView.setImage(new Image(PATH + deck.getTopCard().getColor() + deck.getTopCard().getId() + EXT));
	}

	public void updatePlayerCardCount() {
		numOfPlayerCards.setText("" + player.getHand().size());
	}

	public void updateAICardCount() {
		numOfAICards.setText("" + ai.getHand().size());

		for(int i = 0; i < allCardImages.size(); i++) {
			if(i >= ai.getNumOfCards())
				allCardImages.get(i).setVisible(false);
			else
				allCardImages.get(i).setVisible(true);
		}
	}

	public void resetTurnCounter() {
		turnsElapsed = 0;
	}

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-	
	 * 	GETTERS AND SETTERS
	 *	
	 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/	
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;


	}


	public AI getAi() {
		return ai;
	}


	public void setAi(AI ai) {
		this.ai = ai;
	}


	public Deck getDeck() {
		return deck;
	}


	public void setDeck(Deck deck) {
		this.deck = deck;
	}


}
/*
	private void animatePlayerCard(Button source, Card selectedCard) {
		Bounds b = source.localToScene(root.getBoundsInLocal());
		Card aiCard = ai.chooseRandomCard();

		playCardImage = new ImageView();
		animationPane.getChildren().add(playCardImage);
		movePlayerCard.setNode(playCardImage);
		playCardImage.setVisible(true);

		playCardImage.setImage(new Image("/application/image/assets/" + selectedCard.getColor() + selectedCard.getId() + ".png"));
		playCardImage.setX(b.getMinX());
		playCardImage.setY(b.getMinY());

		movePlayerCard.setToX(790 - b.getMinX());
		movePlayerCard.setToY(363 - b.getMinY());

		animationPane.setVisible(true);




int option = 0;
String color = null;
String id = null;
setTopCardImage();
playCardImage.setVisible(false);

if(aiCard != null && !(ai.getInst().getAIInstruction().equals("skip")) && !(ai.getInst().getAIInstruction().equals("draw")))
	option = 0;
else if(ai.getInst().getAIInstruction().equals("skip") && ai.handContainsCard("eso")) {
	option = 1;
	Card c = ai.getCardById("eso");
	color = c.getColor();
	id = c.getId();
}
else if(ai.getInst().getAIInstruction().equals("draw") && ai.handContainsCard("VII")) {
	option = 2;
	Card c = ai.getCardById("VII");
	color = c.getColor();
	id = c.getId();
}
else
	option = 3;

ai.takeMyTurn(aiCard);

switch(option) {
case 0:
	aiCardImage.setVisible(true);
	animateAiCard(aiCard);
	break;

case 1:
	aiCardImage.setVisible(true);
	animateAiCard(new Card(color, id));
	break;

case 2:
	aiCardImage.setVisible(true);
	animateAiCard(new Card(color, id));
	break;

case 3:
	setTopCardImage();
	aiCardImage.setVisible(false);
	updateAICardCount();
	animationPane.setVisible(false);
	break;
}

//aiCardImage.setVisible(true);
//animateAiCard(aiCard);

});*/