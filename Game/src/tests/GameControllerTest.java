package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Test;
import game.GameController;
import game.Card;
import game.Participant;

public class GameControllerTest {
	
	GameController gc;
	Participant user;
	Participant cpu;
	@Test
	public void compareTest() {
		
		Card c1 = new Card("red", "dragon", "rare", 4.0f);
		Card c2 = new Card("blue", "dragon", "rare", 4.0f);
		Card c3 = new Card("blue", "knight", "rare", 4.0f);
		try {
			//Same type, different archetype
			gc.compareCards(c2, c3);
			assertEquals(gc.user.getHealth(), 100f);
			assertEquals(gc.opponent.getHealth(), 80f);
			
			//Different type, same archetype
			gc.compareCards(c1, c2);
			assertEquals(gc.user.getHealth(), 80f);
			assertEquals(gc.opponent.getHealth(), 80f);
			
			//Same type, same archetype
			gc.compareCards(c1, c1);
			assertEquals(gc.user.getHealth(), 80f);
			assertEquals(gc.opponent.getHealth(), 80f);
		}
		catch(RuntimeException e)
		{
			e.printStackTrace();
		}
		
	}

}
