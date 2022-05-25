package application.logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is parent of Player and AI. It contains their common methods and variables.
 * 
 * @author Wasp
 *
 */
public abstract class AEntity {

	/**
	 * List of all cards you can play this turn (they have same color or id as top card of playedCards).
	 */
	protected ArrayList<Card> playableCards;
	
	/**
	 * List all of cards you have in hand.
	 */
	protected ArrayList<Card> hand;
	protected Random r = new Random();
	
	/**
	 * Reference to this game's deck
	 */
	protected Deck deck;
	
	/**
	 * Determines if you should skip this turn.
	 */
	protected boolean skipTurn;
	
	/**
	 * Name of this Entity
	 */
	protected String name;
	
	/**
	 * Card that will be played this turn
	 */
	protected Card playCard;
	
	protected Instructions inst;
	
	/**
	 * Number of cards this AEntity draws this turn.
	 */
	protected int numOfDrawCards;
	
	/**
	 * Cards which this AEntity has drawn this turn.
	 */
	protected ArrayList<Card> drawnCards;
	
	public AEntity(Deck deck, Instructions instructions, String name) {
		
		this.hand = new ArrayList<Card>();
		this.drawnCards = new ArrayList<Card>();
		this.deck = deck;
		this.inst = instructions;
		this.skipTurn = false;
		this.name = name;
		playCard = null;
		
		//createHand();
	}
	
	/**
	 * Draws random 5 cards that will this entity start game with.
	 */
	public void createHand(){
		
		int index;
		
		System.out.println(name + " Hand: ");
		
		for(int i = 0; i < 5; i++) {
			index = r.nextInt(32 - i);
			//System.out.println(index);
			if(index >= deck.getStack().size())
				index = deck.getStack().size() - 1;
			
			this.hand.add(deck.getStack().get(index));
			System.out.println(deck.getStack().get(index).getColor() + " " + deck.getStack().get(index).getId());
			this.deck.getStack().remove(index);
		}
		
		playableCards = new ArrayList<>();
		
		verifyPlayableCards();
		
	}
	
	/**
	 * This AEntity performs its turn. It contains reacting to it's instruction (drawing cards, skipping turn, etc.), <b>playing <i>playCard</i></b>
	 * (most important part) and also setting opponent's instruction (based on <i>playCard</i>).
	 * 
	 * @param playCard The card to be played this turn. Can also be null, which means that this AEntity has no cards to play this turn and
	 * 		  therefore this turn has to be skipped along with drawing 1 card.
	 */
	public abstract void takeMyTurn(Card playCard);

	@Deprecated
	protected void playCard(String color, String cardID) {
		for(int i = 0; i < hand.size(); i++) {
			
			if(hand.get(i).getId().equals(cardID) && hand.get(i).getColor().equals(color)) {
					this.deck.getPlayedCards().add(hand.get(i));
					this.deck.setTopCard(hand.get(i));
					System.out.println("PLAYED CARD: " + hand.get(i));
					this.hand.remove(i);
					
					
					break;
				}
			
			}
		System.out.println(name +"  HAND: " + this);
	}
	
	/**
	 * Selects the first card with id cardID
	 * @param cardID the ID of card to play
	 */
	public void playCard(String cardID) {
		for(int i = 0; i < hand.size(); i++) {
			
			if(hand.get(i).getId().equals(cardID)) {
					this.playCard = hand.get(i);
					
					this.deck.setTopCard(playCard);
					System.out.println(name + " " + "PLAYED CARD: " + playCard);
					this.hand.remove(playCard);

					break;
				}
			
			}
		//System.out.println(name +"  HAND: " + this);
	}
	
	/**
	 * This entity plays the current playCard.
	 */
	public void playCard() {
			
		this.deck.setTopCard(playCard);
		System.out.println(name + " " + "PLAYED CARD: " + playCard);
		this.hand.remove(playCard);
		
		
		System.out.println(name +"  HAND: " + this);

	}
	
	/**
	 * This entity plays a card.
	 * @param card The card to be played
	 */
	public void playCard(Card card) {
			
		this.deck.setTopCard(card);
		System.out.println(name + " " + "PLAYED CARD: " + card);
		this.hand.remove(card);
		
		
		System.out.println(name +"  HAND: " + this);

	}
	
	/**
	 * Checks playableCards. If there are none cards to be played this turn, draws a card and opponent should play.
	 */
	/*public void checkCards() {
		
		verifyPlayableCards();
		
		if(playableCards.isEmpty()) {
			drawCards(1);
			
			System.out.println(name +"  HAND: " + this);
			skipTurn = true;
		}
		
	}*/
	
	/**
	 * 
	 */
	@Deprecated
	public void checkTopOfDeck() {
		
		//System.out.println();
		//System.out.println("TOP CARD: " + deck.getTopCard());
		
		if(deck.getTopCard().getId().equals("VII")/* && deck.isTopCardActive()*/) {
			
			if(this.handContainsCard("VII")) {
				playCard("VII");
				System.out.println(name +"  HAND: " + this);
				skipTurn = true;
				
			} else {
			
			int sevenCntr = 1;
		
				for(int j = deck.getPlayedCards().size() - 2; j > -1; j--) {
					if(!(deck.getPlayedCards().get(j).getId().equals("VII")))
						break;
					sevenCntr++;
				}
			
			
			drawCards(2 * sevenCntr);
			
			skipTurn = true;
			/*deck.isTopCardActive = false;*/
			System.out.println(name +"  HAND: " + this);
			}
			
		}
		
		else if(deck.getTopCard().getId().equals("eso")/* && deck.isTopCardActive*/) {
			if(this.handContainsCard("eso")) {
				playCard("eso");
				System.out.println(name +"  HAND: " + this);
				skipTurn = true;
				return;
			} else {
				skipTurn = true;
				/*deck.isTopCardActive = false;*/
				System.out.println(name +"  HAND: " + this);
				return;
			}
		}
		
	}
	
	
	/**
	 * Used to determine if this entity's hand contains card with cardId ID.
	 * 
	 * @param cardId
	 * @return Whether hand contains card with cardId or not
	 */
	public boolean handContainsCard(String cardId) {
	
		for(Card c : hand) {
			
			if(c.getId().equals(cardId))
					return true;
			
			}
		return false;
	}
	
	/**
	 * Returns first found Card from this AEntity's hand based on the cardId.
	 * @param cardId ID of the 
	 * @return Card based on the cardId.
	 */
	public Card getCardById(String cardId) {
		for(Card c : hand) {

			if(c.getId().equals(cardId))
				return c;

		}
		return null;
	}
	
	/**
	 * Used to determine if this entity's hand contains card with cardId ID and color with color.
	 * 
	 * @param color
	 * @param cardId
	 * @return Whether hand contains card with cardId or not
	 */
	public boolean handContainsCard(String color, String cardId) {
		
		for(Card c : hand) {
			
			if(c.getId().equals(cardId))
					return true;
			
			}
		return false;
	}
	
	/**
	 * Used to draw cards
	 * 
	 * @param amount of cards to be drawn
	 */
	public void drawCards(int amount) {
		drawnCards.clear();
		
		for( ; amount > 0; amount--) {
			
			if(deck.getStack().isEmpty())
				deck.reverseStack();
			
			drawnCards.add(deck.getStack().get(deck.getStack().size() - 1));
			hand.add(deck.getStack().get(deck.getStack().size() - 1));
			System.out.println(name + " " + "DRAWN CARD: " + deck.getStack().get(deck.getStack().size() - 1) );
			deck.getStack().remove(deck.getStack().size() - 1);
			
		/*	deck.isTopCardActive = false;*/
		}
		
	}

	@Override
	public String toString() {

		return name + " hand = " + hand;
	}

	public int getNumOfCards() {
		return hand.size();
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public Card getPlayCard() {
		return playCard;
	}

	public void setPlayCard(Card playCard) {
		this.playCard = playCard;
	}
	
	
	public ArrayList<Card> getPlayableCards(){
		return playableCards;
	}
	
	/**
	 * Checks your cards in your hand and stores ones you can play this turn (depends on the deck's top card) into playbleCards.
	 */
	public void verifyPlayableCards() {
		ArrayList<Card> array = new ArrayList<>();
		
		for(Card c : hand) {
			if(c.getColor().equals(this.deck.getTopCard().getColor()) || c.getId().equals(this.deck.getTopCard().getId())) {
				array.add(c);
			}
/*je spravne else if? */		else if(c.getId().equals("svrsek")) {
				array.add(c);
			}
				
		}
		
		playableCards = array;
		
	}

	public void eraseHand() {
		this.hand.clear();
	}

	public boolean isSkipTurn() {
		return skipTurn;
	}

	public void setSkipTurn(boolean skipTurn) {
		this.skipTurn = skipTurn;
	}

	/**
	 * 
	 * @return An <code>Instructions</code> instance associated with this instance
	 */
	public Instructions getInst() {
		return inst;
	}

	public int getNumOfDrawCards() {
		return numOfDrawCards;
	}

	public void setNumOfDrawCards(int numOfDrawCards) {
		this.numOfDrawCards = numOfDrawCards;
	}

	public ArrayList<Card> getDrawnCards() {
		return drawnCards;
	}
}
