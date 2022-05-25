package application.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*******************************
 * 
 * This class represents a deck.<br>
 * It contains 2 main <code>ArrayList</code>s:
 * <ol>
 * 		<li><b>stack</b> - pile of cards which can be drawn</li>
 * 		<li><b>playedCards</b> - pile of cards which have been played<br> (this is important for determining whether there was a VII or not and so on).
 * </ol>
 * 
 * @author Wasp
 *
 */
public class Deck {
	
	private Instructions inst;
	
	/**
	 * Enumeration of all cards in game.
	 */
	public static final Card[] CARDS = {
		new Card("cervena", "VII"),
		new Card("cervena", "VIII"),
		new Card("cervena", "IX"),
		new Card("cervena", "X"),
		new Card("cervena", "spodek"),
		new Card("cervena", "svrsek"),
		new Card("cervena", "kral"),
		new Card("cervena", "eso"),
	
		new Card("zelena", "VII"),
		new Card("zelena", "VIII"),
		new Card("zelena", "IX"),
		new Card("zelena", "X"),
		new Card("zelena", "spodek"),
		new Card("zelena", "svrsek"),
		new Card("zelena", "kral"),
		new Card("zelena", "eso"),
	
		new Card("kule", "VII"),
		new Card("kule", "VIII"),
		new Card("kule", "IX"),
		new Card("kule", "X"),
		new Card("kule", "spodek"),
		new Card("kule", "svrsek"),
		new Card("kule", "kral"),
		new Card("kule", "eso"),
	
		new Card("zaludy", "VII"),
		new Card("zaludy", "VIII"),
		new Card("zaludy", "IX"),
		new Card("zaludy", "X"),
		new Card("zaludy", "spodek"),
		new Card("zaludy", "svrsek"),
		new Card("zaludy", "kral"),
		new Card("zaludy", "eso")};
	
	/**
	 * Used to randomize the stack.
	 */
	private ArrayList<Card> cardArray;
	
	/**
	 *  List of all cards which were played.
	 */
	private ArrayList<Card> playedCards;
	/**
	 *  List of all cards which can be drawn.
	 */
	private ArrayList<Card> stack;
	/**
	 * The card at top of the deck
	 */
	private Card topCard;
	
	/**
	 * Determines if the top card is active (someone has already reacted to it).<br>
	 * This is used for aces and VII to find out if someone has already drawn cards or skipped turn because of it.
	 */
	//protected boolean isTopCardActive;
	
	private Random r = new Random();
	
	public Deck(Instructions inst) {
		this.inst = inst;
		//initDeck();
	}
	
	/**
	 * Initializes the deck
	 */
	public void initDeck() {
		cardArray = new ArrayList<Card>(32);
		
		stack = new ArrayList<Card>(32); 
		playedCards = new ArrayList<Card>();
		
		for(int i = 0; i < 32; i++) {
			cardArray.add(CARDS[i]);
		}
		
		for(int i = 0; i < 32; i++) {
			int index = r.nextInt(cardArray.size());
			
			stack.add(cardArray.get(index));
			cardArray.remove(index);
		}
	
		//isTopCardActive = true;
	
		topCard = stack.get(stack.size() - 1);
		
		System.out.println("ID vrchní karty je: " + topCard.getId());
		
		if(topCard.getId().equals("VII")) {
			System.out.println("sedmicka!");
			//Player.whatToDo = "draw";
			inst.setPlayerInstruction("draw");
		}else if(topCard.getId().equals("eso")) {
			System.out.println("eso!");
			//Player.whatToDo = "skip";
			inst.setPlayerInstruction("skip");
		}
		
		playedCards.add(topCard);
		stack.remove(stack.size() - 1);
	}
	
	/**
	 * Used to draw cards
	 * 
	 * @param amount Amount of cards to be drawn
	 * @param ae <code>AEntity</code>(<code>Player</code> or <code>AI</code>) who draws these cards
	 */
	public void drawCards(int amount, AEntity ae) {
		
		for( ; amount > 0; amount--) {
			
			if(getStack().isEmpty())
				reverseStack();
			
			ae.getHand().add(getStack().get(getStack().size() - 1));
			System.out.println(ae.name + " " + "DRAWN CARD: " + getStack().get(getStack().size() - 1) );
			getStack().remove(getStack().size() - 1);
			
		}
		/*	deck.isTopCardActive = false;*/
	}

	/**
	 * 
	 * @return The stack associted with this deck
	 */
	public ArrayList<Card> getStack() {
		return stack;
	}
	
	/**
	 * 
	 * @return The list of all played cards.
	 */
	public ArrayList<Card> getPlayedCards() {
		return playedCards;
	}
	
	/**
 	* 
 	* @return The card at the top of all played cards.
 	*/
	public Card getTopCard() {
		return topCard;
	}
	/**
	 * Adds the @param topCard to playedCards and also sets it as new topCard.<br>
	 * 
	 * @param topCard - A card that will be the new topCard. 
	 */
	public void setTopCard(Card topCard) {
		playedCards.add(topCard);
		
		this.topCard = topCard;
	}
	
	/**
	 * Reverses the stack
	 */
	public void reverseStack() {
		
	//	System.out.println("velikost stacku " + stack.size());
	//	System.out.println("velikost playedCards " + playedCards.size());
		
	//	this.stack = playedCards;
		
	//	Collections.copy(stack, playedCards);
		stack = new ArrayList<>(playedCards);
	//	System.out.println("velikost stacku " + stack.size());
		
		playedCards.clear();
		
		System.out.println("velikost stacku " + stack.size());
		
		topCard = stack.get(stack.size() - 1);
		
		playedCards.add(topCard);
		stack.remove(stack.size() - 1);
		
		Collections.reverse(stack);
	}

	/*public boolean isTopCardActive() {
		return isTopCardActive;
	}

	public void setTopCardActive(boolean isTopCardActive) {
		this.isTopCardActive = isTopCardActive;
	}*/
	
}
