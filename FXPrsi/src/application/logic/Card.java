package application.logic;
/**
 * This class represents a playing card.<br>
 * Each card has color and ID.<br>
 * <br>
 * Colors are:<br>
 * <ul>
 * 		<li> cervena</li>
 * 		<li> zelena</li>
 * 		<li> kule</li>
 * 		<li> zaludy</li>
 * </ul>
 * 
 * IDs are:<br>
 * <ol>
 * 		<li> VII</li>
 * 		<li> VIII</li>
 * 		<li> IX</li>
 * 		<li> X</li>
 * 		<li> spodek</li>
 * 		<li> svrsek</li>
 * 		<li> kral</li>
 * 		<li> eso</li>
 * </ol>
 * 
 * @author Wasp
 *
 */
public class Card {

	private String color;
	private String id;
	private boolean wasUsed;
	
	public Card(String c, String id) {
		this.color = c;
		this.id = id;
		this.wasUsed = false;
	}

	public String getColor() {
		return color;
	}

	public String getId() {
		return id;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return color + " " + id;
	}

	public boolean wasUsed() {
		return wasUsed;
	}

	public void setWasUsed(boolean wasUsed) {
		this.wasUsed = wasUsed;
	}
}
