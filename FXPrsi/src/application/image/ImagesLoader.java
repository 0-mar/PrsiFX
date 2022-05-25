package application.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

/********************************************
 * 
 * This class is used to <i><b>load</b></i> images.
 * 
 * @author Wasp
 *
 */

public class ImagesLoader {
	
	public static final String IMG_DIR = "assets/";
	
	/**
	 *  Other images (not cards) are stored here.
	 */
	
	private ArrayList<BufferedImage> extras;
	
	/**
	 Key structure of this class.<br>
	 Images of all cards are stored here.<br>
	 <br>
	 They are divided to 4 groups ( <code>ArrayList</code>s ):
	 <ul>
	 	<li>"kule"</li>
	 	<li>"zelena"</li>
	 	<li>"cervena"</li>
	 	<li>"zaludy"</li>
	 </ul>
	 */
	
	private HashMap<String, ArrayList<BufferedImage>> cards = new HashMap<String, ArrayList<BufferedImage>>();
	
	/**
	 * Images of cards with color "kule".
	 */
	private ArrayList<BufferedImage> kule = new ArrayList<>();
	/**
	 * Images of cards with color "zelena".
	 */
	private ArrayList<BufferedImage> zelena = new ArrayList<>();
	/**
	 * Images of cards with color "cervena".
	 */
	private ArrayList<BufferedImage> cervena = new ArrayList<>();
	/**
	 * Images of cards with color "zaludy".
	 */
	private ArrayList<BufferedImage> zaludy = new ArrayList<>();
	
	/**
	 * Common instance of <code>ImagesLoader</code> for all classes.<br>
	 * If you need to load images, use this instance only.
	 */
	private static final ImagesLoader ldr = new ImagesLoader();
	
	private ImagesLoader() {
		cards = new HashMap<>();
		extras = new ArrayList<>();
		
		//cardsC = new HashMap<String, BufferedImage>();
		
		prepare();
	}
	
	/**
	 * Initializes the <code>ImagesLoader</code>.
	 */
	private void prepare() {
		
		getCards();
		getExtras();
		
	}
	
	/**
	 *  Prepares all {@code BufferedImage}s to be getable from extras ArrayList
	 */
	private void getExtras() {
		extras.add(loadImage("back.png"));
		extras.add(loadImage("deck.png"));
		extras.add(loadImage("blank.png"));
	}
	
	/**
	 *  Prepares all {@code BufferedImage}s to be getable from cards ArrayList
	 */
	private void getCards() {
		
		BufferedImage cardSheet = loadImage("karty.png");
		
		for(int j = 0; j < 4; j++) {			
			
			for(int i = 0; i < 8; i++) {
				if(j == 0)
					kule.add(cardSheet.getSubimage(185 * i, 290 * j, 185, 290));
				else if(j == 1)
					zelena.add(cardSheet.getSubimage(185 * i, 290 * j, 185, 290));
				else if(j == 2)
					cervena.add(cardSheet.getSubimage(185 * i, 290 * j, 185, 290));
				else if(j == 3)
					zaludy.add(cardSheet.getSubimage(185 * i, 290 * j, 185, 290));
			
			}
			
			switch(j) {
				case 0:
					this.cards.put("kule", kule);
					break;
				case 1:
					this.cards.put("zelena", zelena);
					break;
				case 2:
					this.cards.put("cervena", cervena);
					break;
				case 3:
					this.cards.put("zaludy", zaludy);
					break;
			}
			
		}
		
	}

	private BufferedImage loadImage(String fnm){
		
		System.out.println(IMG_DIR + fnm);
		
		try{
			BufferedImage bi = ImageIO.read(getClass().getResource(IMG_DIR + fnm));
			
			return bi;
		}catch(IOException e){
			
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns a <code>BufferedImage</code> representing card specified by it's ID and color
	 * 
	 * @param color The card's color
	 * @param cardId The card's ID
	 * @return The <code>BufferedImage</code> u want
	 */
	
	public BufferedImage getCardImage(String color, String cardId){
		
		//System.out.println("barva je " + color + " a hodnota je " + cardId);
		//System.out.println(cards.get(color));
		
		int index = 0;
		
		switch(cardId) {
			case "VII":
				index = 0;
				break;
			case "VIII":
				index = 1;
				break;
			case "IX":
				index = 2;
				break;
			case "X":
				index = 3;
				break;
			case "spodek":
				index = 4;
				break;
			case "svrsek":
				index = 5;
				break;
			case "kral":
				index = 6;
				break;
			case "eso":
				index = 7;
				break;
		}
		
		return cards.get(color).get(index);
		
		
	}
	
	/**
	 * <br>
	 * @return The common instance you should use when you need to load images
	 */
	public static ImagesLoader getInstance() {
		return ldr;
	}
	
	/**
	 * 
	 * @return Back image of a card
	 */
	public BufferedImage getBackImage() {
		return extras.get(0);
	}
	/**
	 * 
	 * @return Deck image
	 */
	public BufferedImage getDeckImage() {
		return extras.get(1);
	}
	/**
	 * 
	 * @return Blank image of card
	 */
	public BufferedImage getBlankImage() {
		return extras.get(2);
	}
	
	//public BufferedImage getCard(String color, int index) {
		//return cardz.get(color).get(index);
	//}
}
