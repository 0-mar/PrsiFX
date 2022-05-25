package application.logic;

public class Player extends AEntity {

	public boolean played = false;
	public static String whatToDo = "nothing";
	
	public Player(Deck deck, Instructions instructions, String name) {
		super(deck, instructions, name);
		
	}

	public void takeMyTurn(Card card) {

		System.out.println();
		System.out.println(this.name + " current instruction: " + getInst().getPlayerInstruction());
		
		if(getInst().getPlayerInstruction().equals("skip") && !card.getId().equals("eso")) {
			skipTurn = true;
			return;
		}
		else if(getInst().getPlayerInstruction().equals("draw") && !card.getId().equals("VII")) {
			skipTurn = true;
			return;
		}
		
		checkCards(card);
		if(skipTurn) {
			//skipTurn = true;
			return;
		}
		
		for(Card a : this.playableCards) {
			if(a.getColor().equals(card.getColor()) && a.getId().equals(card.getId())) {
				System.out.println();
				System.out.println("TOP CARD: " + deck.getTopCard());
				playCard(card);
				
				
				System.out.println(this);
				
				getInst().setPlayerInstruction("nothing");
				//Player.whatToDo = "nothing";
				//System.out.println("PLAYED CARD: " + playCard);
				
				if(card.getId().equals("VII")) {
					//AI.whatToDo = "draw";
					inst.setAIInstruction("draw");
					System.out.println("*********************************************");
					System.out.println(name + " played VII");
					System.out.println("AI Instruction = " + inst.getAIInstruction());
					System.out.println("*********************************************");
				}

				else if(card.getId().equals("eso")) {
					//AI.whatToDo = "skip";
					inst.setAIInstruction("skip");
					System.out.println("*********************************************");
					System.out.println(name + " played ESO");
					System.out.println("AI Instruction = " + inst.getAIInstruction());
					System.out.println("*********************************************");
				}
			}
		}
	}

	@Override
	public String toString() {

		return "Player hand = " + hand;
	}

	public void processVII() {
		int sevenCntr = 1;
		deck.getPlayedCards().get(deck.getPlayedCards().size() - 1).setWasUsed(true);

		for(int j = deck.getPlayedCards().size() - 2; j > -1; j--) {
			if(!(deck.getPlayedCards().get(j).getId().equals("VII")) || deck.getPlayedCards().get(j).wasUsed())
				break;
			deck.getPlayedCards().get(j).setWasUsed(true);
			sevenCntr++;
		}

		int numCards = 2 * sevenCntr;
		System.out.println(name + " is drawing " + numCards);
		drawCards(2 * sevenCntr);

		//Player.whatToDo = "nothing";
		inst.setPlayerInstruction("nothing");
		System.out.println(name +"  HAND: " + this);
	}

	/**
	 * Checks playableCards. If there are none cards to be played this turn, sets skipTurn to true, causing takeMyTurn to terminate early.
	 * If you didn't call this method before checking the color and id of the card parameter in takeMyTurn(), you would get an Exception (?probably? - wasnt tested)
	 * @see #takeMyTurn()
	 */
	public void checkCards(Card card) {

		verifyPlayableCards();
		
		byte shouldSkip = 1;
		
		//System.out.println(playableCards);
		
		for(Card a : this.playableCards) {
			
			if(a.getColor().equals(card.getColor()) && a.getId().equals(card.getId())) {
				shouldSkip = 1;
				break;
			}
			
			else
				shouldSkip = 0;
		}

		if(playableCards.isEmpty() || shouldSkip == 0) {
			//System.out.println(name +"  HAND: " + this);
			skipTurn = true;
		}

	}
}