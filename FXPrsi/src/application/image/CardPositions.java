package application.image;

import java.util.HashMap;

/**
 * This class provides <i>CSS</i> -fx-background-image ?properties? of all card images (e. g. zaludyVII, zelenakral, etc.).<br>
 * It stores these paths in the <b><code>HashMap<String, String></code></b> cardPositions.<p>
 * 
 * Do not create a new instance, use the static common instance <b><code>pos</code></b> instead<br>
 * (use getInstance() method for that purpose).
 * 
 * @author ZD
 *
 */
public final class CardPositions {
	
	private static final String[] cardId = {"VII", "VIII", "IX", "X", "spodek", "svrsek", "kral", "eso"};
	private static final String[] color = {"kule", "zelena", "cervena", "zaludy"};
	
	/**
	 *  Common instance of this class which you should use.
	 */
	private static final CardPositions pos = new CardPositions();
	
	/**
	 * Key - <color><cardId>
	 * Value - path to card
	 */
	private HashMap<String, String> cardPositions;
	
	public CardPositions() {
		cardPositions = new HashMap<>();
		
		getCardPositions();
	}

	private void getCardPositions() {
		for(int i = 0; i < color.length; i++) {
			getCardsOf1Color(color[i]);
		}
		
	}
	
	/** Stores css -fx-background-image ?properties? of all card images of the color
	 * 
	 * @param color The color of these cards<br>
	 * 			
	 */
	private void getCardsOf1Color(String color) {
		
		String part1 = "-fx-background-image: url('/application/image/assets/";
		String part2 = "');";
		String ext = ".png";
		
		for(int i = 0; i < cardId.length; i++) {
			cardPositions.put(color + cardId[i], part1 + color + cardId[i] + ext + part2);
			//System.out.println(color + cardId[i] + ", " + part1 + color + cardId[i] + ext + part2);
		}
	}
	
	public HashMap<String, String> getCardPositionsMap(){
		return this.cardPositions;
	}
	
	/**
	 * 
	 * @return Common instance of this class
	 */
	public static final CardPositions getInstance() {
		return pos;
	}
}
