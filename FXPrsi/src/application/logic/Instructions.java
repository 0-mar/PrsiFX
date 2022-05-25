package application.logic;

import application.gui.controller.GameController;

/**
 * 
 * This class represents intructions for AI and Player. Both of these classes contribute here with their instructions for the other (e.g. draw, skip).
 * It contains a <code>Controller</code> reference so that you can make GUI changes based on <code>Player</code>/<code>AI</code> state. Both
 * <code>Player</code> and <code>AI</code> have to share the same instance of this class in order to work.<p>
 * Instructions are: 
 * <ul>nothing - do nothing</ul>
 * <ul>draw - draw cards because of a VII card</ul>
 * <ul>skip - skip turn because of eso card</ul>
 * 
 * @author ZD
 *
 */
public class Instructions {

	/**
	 * <code>Controller</code> reference - for GUI modifying purposes
	 */
	private GameController controller;
	
	/**
	 * Player's instruction
	 */
	private String playerInst;
	
	/**
	 * Player's instruction
	 */
	private String aiInst;
	
	public Instructions() {
		playerInst = "nothing";
		aiInst = "nothing";
	}
	
	public void setPlayerInstruction(String inst) {
		this.playerInst = inst;
		
		if(inst.equals("skip")) {
			controller.displayAce();
		}
	}
	
	public void setAIInstruction(String inst) {
		this.aiInst = inst;
		
		if(inst.equals("skip")) {
			controller.hideAce();
		}
	}
	
	public String getPlayerInstruction() {
		return this.playerInst;
	}
	
	public String getAIInstruction() {
		return this.aiInst;
	}

	public void setController(GameController controller) {
		this.controller = controller;
	}
	
	public void resetInstructions() {
		this.aiInst = "nothing";
		this.playerInst = "nothing";
	}
	
}
