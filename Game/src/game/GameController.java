package game;

import java.util.Random;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameController {
	static final int DECK_TOTAL = 3;
	private Deck[] collection = new Deck[DECK_TOTAL];
	private int deckChoice;
	private UI view;
	public Participant user;
	public Participant opponent;	
	private float playerPower = 0, opponentPower = 0;
	
	public GameController(UI view) {
		deckChoice = -1;
		this.view = view;
		user = new User();
		opponent = new AutoOpponent();
		buildCollection();
	
		view.addNewGameListener(new NewGameListener());
		view.addDeckListener(new DeckListener());
		view.addCard0Listener(new Card0Listener());
		view.addCard1Listener(new Card1Listener());
		view.addCard2Listener(new Card2Listener());
		view.addPlayCardListener(new PlayCardListener());
		view.addContinueListener(new ContinueListener());
		view.addDifficultyListener(new DifficultyListener());
	}

	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			deckChoice = -1;
			user.reset();
			opponent.reset();
			view.setHealth(100, 100);
			view.resetText();
			view.show("choosePanel");
			view.getTxtpnGame().setFont(new Font("SimSun", Font.PLAIN, 9));
		}
	}
	
	class DeckListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == view.getBtnDeck0()) 
				deckChoice = 0;
			else if(e.getSource() == view.getBtnDeck1())
				deckChoice = 1;
			else if(e.getSource() == view.getBtnDeck2())
			
			deckChoice = 0;
			user.setDeck(collection[deckChoice]);
			user.loadHand();
			updateHand();
			//sets the opponents deck randomly
			int choice = new Random().nextInt(2);
			opponent.setDeck(collection[choice]);
			opponent.loadHand();
			view.show("playPanel");
		}
	}
	
	public void updateHand() {
		for(int i = 0; i < 3; i++)
			view.setImageIcon(i, user.hand[i].getImgIconCard());
	}
	
	class Card0Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			user.setCardPosition(0);
		}
	}
	
	class Card1Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			user.setCardPosition(1);
		}
	}
	
	class Card2Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			user.setCardPosition(2);
		}
	}
	
	class PlayCardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (user.getCardPosition() == -1) {
				view.appendText("Please choose a Card!");
				view.refreshText();
				return;
			}
			else
			{
				//gets a card from the user and the opponent
				Card userCard = ((User) user).playCard();
				Card opCard = ((AutoOpponent) opponent).playCard(((AutoOpponent) opponent).choice());
<<<<<<< HEAD
//				if(opCard == null) System.out.println("OPPONENT INVALID");
				combatCards(userCard, opCard);
=======
				compareCards(userCard, opCard);
>>>>>>> bc71e714fd0ba116d964a6af13c1c52ab70ee408
				
				//check for winner
				if(user.getHealth() == 0)
				{
					view.setEndMessage("YOU LOSE!");
					view.show("endPanel");
				}
				else if (opponent.getHealth() == 0)
				{
					view.setEndMessage("YOU WIN!");
					view.show("endPanel");
				}
				else
				{
					user.setCardPosition(-1); // resets card position
				}

			}
		}
	}
	
	class ContinueListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(deckChoice == -1)
				view.show("choosePanel");
			else
				view.show("playPanel");
		}
	}
	
	class DifficultyListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			((AutoOpponent) opponent).setDifficulty((int) view.getDifficulty());			
		}
	}
	
	public void setDeckChoice(int deckChoice) {
		this.deckChoice = deckChoice;
	}
	
	public void buildCollection() {
		try {
			for(int i = 0; i < DECK_TOTAL; i++)
				collection[i] = new Deck(i+1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getDeckChoice() {
		return deckChoice;
	}
	
	public Deck getDeck() {
		return collection[deckChoice];
	}
	
	//TODO disconnecting compare and logic
	public void combatCards(Card userCard, Card opponentCard) {
		playerPower = 0;
		opponentPower = 0;
		// Print cards played
		view.appendText("You played "+userCard.getName() +
				"\nYour Opponent played "+opponentCard.getName());
		view.refreshText();
		
		int battleResult = compareCards(userCard, opponentCard);

		// Print match results
		view.appendText("Match Results: \nYour "+userCard.getName()+ 
				" power: "+playerPower+"\nYour Opponent's "+opponentCard.getName()+
				" power: "+opponentPower);
		
		// Print outcome
		if (battleResult == 0) {
			user.decreaseHealth();
			view.appendText("\nYou've lost this round!\n");
			view.refreshText();
		}
		else if (battleResult == 1) {
			opponent.decreaseHealth();
			view.appendText("\nYou've won this round!\n");
			view.refreshText();
		}
		else {
			view.appendText("\nIt's a draw!\n");
			view.refreshText();
			return;
		}
		
		// Set next hand for play
		view.setHealth(user.getHealth(), opponent.getHealth());
		user.draw();
		opponent.draw();
		updateHand();
	}
	
	public int compareCards(Card userCard, Card opponentCard) {
		playerPower = userCard.getPower();
		opponentPower = opponentCard.getPower();

		// Make type comparison and modify participants power
		if (userCard.getType().equals(opponentCard.getType())); // do nothing
		else if ((userCard.getType().equals("red") && opponentCard.getType().equals("green"))
				|| (userCard.getType().equals("green") && opponentCard.getType().equals("blue"))
				|| (userCard.getType().equals("blue") && opponentCard.getType().equals("red")))
			playerPower += userCard.getPower();
		else
			opponentPower += opponentCard.getPower();

		// Make archetype comparison and modify participants power
		if (userCard.getArchetype().equals(opponentCard.getArchetype())); // do nothing
		else if ((userCard.getArchetype().equals("dragon") && opponentCard.getArchetype().equals("wizard"))
				|| (userCard.getArchetype().equals("wizard") && opponentCard.getArchetype().equals("knight"))
				|| (userCard.getArchetype().equals("knight") && opponentCard.getArchetype().equals("dragon")))
			playerPower += 1.5 * userCard.getPower();
		else
			opponentPower += 1.5 * userCard.getPower();
		
		if (opponentPower > playerPower) 
			return 0;
		else if (opponentPower < playerPower) 
			return 1;
		else 
			return 2;
	}
}
