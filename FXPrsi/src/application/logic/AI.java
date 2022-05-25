package application.logic;

public class AI extends AEntity{

	public static String whatToDo;

	public AI(Deck deck, Instructions instructions, String name) {
		super(deck, instructions, name);
		whatToDo = "nothing";
	}

	@Override
	public void takeMyTurn(Card card) {
		checkMyself();

		if(skipTurn) {
			skipTurn = false;
			return;
		}
		
		if(card == null) {
			// no cards to be played
			this.setNumOfDrawCards(1);
			drawCards(1);
			return;
		}
		
		if(card.getId().equals("VII")) {
			//Player.whatToDo = "draw";
			inst.setPlayerInstruction("draw");

			System.out.println("*********************************************");
			System.out.println(name + " played VII");
			System.out.println("Player instruction = " + inst.getPlayerInstruction());
			System.out.println("*********************************************");
		}
		else if(card.getId().equals("eso")) {
			//Player.whatToDo = "skip";
			inst.setPlayerInstruction("skip");

			System.out.println("*********************************************");
			System.out.println(name + " played ESO");
			System.out.println("Player instruction = " + inst.getPlayerInstruction());
			System.out.println("*********************************************");
		}
		
		else if(card.getId().equals("svrsek")) {
			String newColor = "";
			int cervena = 0, zelena = 0, zaludy = 0, kule = 0;

			for(Card a : this.hand) {

				switch(a.getColor()) {
				case "cervena":
					cervena++;
					break;

				case "zelena":
					zelena++;
					break;

				case "zaludy":
					zaludy++;
					break;

				case "kule":
					kule++;
					break;
				}
			}

			int i1 = Math.max(cervena, zelena);
			int i2 = Math.max(zaludy, kule);

			int i3 = Math.max(i1, i2);

			if(i3 == cervena)
				newColor = "cervena";

			else if(i3 == zelena)
				newColor = "zelena";

			else if(i3 == zaludy)
				newColor = "zaludy";

			else if(i3 == kule)
				newColor = "kule";

			card.setColor(newColor);
			System.out.println("COLOR CHANGED TO: " + newColor);
		}

		System.out.println();
		System.out.println("TOP CARD: " + deck.getTopCard());

		playCard(card);

	}

	/**
	 * Chooses a random card from <i>playableCards</i>.
	 * @return Random card or null, if playableCards is empty.
	 * @see AEntity#verifyPlayableCards()
	 */
	public Card chooseRandomCard() {

		switch(inst.getAIInstruction()) {

			case "nothing":
				break;

			case "skip":
				if(this.handContainsCard("eso")) {
					System.out.println("Vracim eso!");
					return getCardById("eso");
				} else {
					System.out.println("Nemam eso, vracim null");
					return null;
				}
				
			case "draw":
				if(this.handContainsCard("VII")) {
					System.out.println("Vracim VII!");
					return getCardById("VII");
				} else {
					System.out.println("Nemam VII, vracim null");
					return null;
				}
		}

		verifyPlayableCards();
		if(playableCards.isEmpty()) {
			System.out.println("Nejsou zadne karty na hrani, vracim null");
			return null;
		}

		Card card = this.playableCards.get(r.nextInt(this.playableCards.size()));

		System.out.println("Byla nahodne vybrana karta: " + card);
		return card;
	}


	private void checkMyself() {
		switch(inst.getAIInstruction()) {

		case "nothing":

			break;

		case "skip":
			processAce();
			break;

		case "draw":
			processVII();
			break;
		}
	}

	public boolean isSkipTurn() {
		return skipTurn;
	}

	public void setSkipTurn(boolean skipTurn) {
		this.skipTurn = skipTurn;
	}

	public void processVII() {

		if(this.handContainsCard("VII")) {
			playCard("VII");
	
			inst.setAIInstruction("nothing");
			inst.setPlayerInstruction("draw");

			System.out.println(name +"  HAND: " + this);
			skipTurn = true;

		} else {

			int sevenCntr = 1;
			deck.getPlayedCards().get(deck.getPlayedCards().size() - 1).setWasUsed(true);

			for(int j = deck.getPlayedCards().size() - 2; j > -1; j--) {
				if(!(deck.getPlayedCards().get(j).getId().equals("VII")) || deck.getPlayedCards().get(j).wasUsed())
					break;
				deck.getPlayedCards().get(j).setWasUsed(true);
				sevenCntr++;
			}

			int numCards = 2 * sevenCntr;
			this.setNumOfDrawCards(numCards);
			System.out.println(name + " is drawing " + numCards + "and skips turn!");
			drawCards(2 * sevenCntr);
			
			inst.setAIInstruction("nothing");
			skipTurn = true;

			System.out.println(name +"  HAND: " + this);
		}
	}

	public void processAce() {

		if(this.handContainsCard("eso")) {
			playCard("eso");
			System.out.println(name + " played ESO!");
		
			inst.setAIInstruction("nothing");
			inst.setPlayerInstruction("skip");

			System.out.println(name +"  HAND: " + this);
			skipTurn = true;
		} else {
			inst.setAIInstruction("nothing");

			skipTurn = true;
			System.out.println(name +" skips turn!");
		}

	}

}
