package game;

public class GameController {
	private Deck[] collection;
	private int deckChoice;
	
	public GameController() {
		buildCollection();
	}
	
	public void setDeckChoice(int deckChoice) {
		this.deckChoice = deckChoice;
	}
	
	public void buildCollection() {
		// TODO build collection of decks
	}
	
	public int getDeckChoice() {
		return deckChoice;
	}
	
	public Deck getDeck() {
		return collection[deckChoice];
	}
	
	// pops a card from chosen deck
	public Card draw(int cardPosition) {
		return null;
	}
	
	public Deck giveDeck(int deckChoice) {
		return null;
	}
	
	void compareCards(Card userCard, Card opponentCard){
		// TODO game logic
	}
}