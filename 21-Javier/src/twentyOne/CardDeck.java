package twentyOne;

import java.util.Random;

import javax.swing.JOptionPane;

/*
 * CardDeck Class
 * 
 * MVC: Model
 * 
 */

public class CardDeck
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private Card[][] 		cardDeck;
	private int		 		cardsInTheDeck;
	
	/*
	 * Class Constants
	 * 
	 */
	
	public static final int	SUITS 				= 4;
	public static final int	CARDS 				= 13;
	public static final int	HEARTS 				= 1;
	public static final int	CLUBS				= 2;
	public static final int	SPADES				= 3;
	public static final int	DIAMONDS 			= 4;
	public static final int JOKER				= 0;
	public static final int ACE					= 1;
	public static final int JACK				= 11;
	public static final int QUEEN				= 12;
	public static final int KING				= 13;
	
	private final int 		CARD_NOT_IN_DECK 	= 0;
	private final int 		CARD_IN_DECK 		= 1;

	/*
	 * getCardDeck Getter
	 * 
	 * Used by Test
	 * 
	 */
	
	public Card[][] getCardDeck()
	{
		return cardDeck;
	}
	
	/*
	 * Constructor Method
	 * 
	 */
	
	public CardDeck()
	{
		// Local Variables
		
		Random random 		  = new Random();
		int	suitNumber 		  = 0;
		int cardNumber 		  = 0;
		boolean cardGenerated = false;

		cardsInTheDeck = 0;
		
		// Create a Card Deck instance
		
		cardDeck = new Card[SUITS][CARDS];

		// Create the Cards Generated parallel array (all array elements will be initialized to zero,
		// meaning "no Card generated".
		
		int[][]	cardsGenerated = new int[SUITS][CARDS];
		
		// Randomly create the Cards and add them to the Card Deck
		
		for (int suit = 0; suit < cardDeck.length; suit++)
		{
			for (int number = 0; number < cardDeck[suit].length; number++)
			{
				// Randomly generate two random #s (suit # and card #).
				// Check the parallel array. If this "Card" (set of #s) has previously been generated
				// loop and ask for another set of two numbers.
				
				cardGenerated = false;
				
				while (!cardGenerated && cardsInTheDeck < SUITS * CARDS)
				{
					// Generated two random numbers. Always request one more number than actually needed.
					
					suitNumber = random.nextInt(SUITS);
					cardNumber = random.nextInt(CARDS);
					
					// Check if this "Card" has already been generated
					
					if (cardsGenerated[suitNumber][cardNumber] == CARD_NOT_IN_DECK)
					{
						cardDeck[suit][number] = new Card(suitNumber + 1, cardNumber + 1);

						cardsInTheDeck++;
						
						// Mark the Card already added to the Card Deck
						
						cardsGenerated[suitNumber][cardNumber] = CARD_IN_DECK;

						cardGenerated = true;
					}					
				}
				
			}	// End of loop to create the Cards
			
		}	// End of loop to create the Card Suits
		
	}	// End of constructor method
	
	/*
	 * Deal Method
	 * 
	 */
	
	public Card deal()
	{
		// Local variables
		
		Random random 		  = new Random();
		int	suitNumber 		  = 0;
		int cardNumber 		  = 0;
		boolean cardDealt 	  = false;
		
		// Temporarily create a "blank" Card
		
		Card card = new Card();

		if (cardsInTheDeck > 0)
		{
			while (!cardDealt)
			{
				// Generate two random numbers. nextInt() takes a limit argument, returning an integer from 0 to limit - 1;
				
				suitNumber = random.nextInt(SUITS);
				cardNumber = random.nextInt(CARDS);
				
				// Check if this Card is in the Card Deck
				
				if (cardDeck[suitNumber][cardNumber] != null)
				{
					card = cardDeck[suitNumber][cardNumber];
					
					cardsInTheDeck--;
					
					// Mark the Card already dealt
					
					cardDeck[suitNumber][cardNumber] = null;
	
					cardDealt = true;
				}					
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "System Error: Card Deck is empty",
												"CardDeck: Deal Method", JOptionPane.ERROR_MESSAGE);	
		}
		
		return card;
	}
}
