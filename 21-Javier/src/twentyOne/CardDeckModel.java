package twentyOne;

import java.util.Random;

import javax.swing.JOptionPane;

/*
 * CardDeck Class
 * 
 * MVC: Model
 * 
 */

public class CardDeckModel
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private CardModel[][] 	cardDeck;
	private int		 		cardsInTheDeck;
	
	/*
	 * Class Constants
	 * 
	 */
	
	public static final int	SUITS 				= 4;
	public static final int	CARDS 				= 13;
	private final int		NUMBER_DECK_CARDS 	= SUITS * CARDS;
	
	private final CardModel CARD_NOT_IN_DECK 	= null;
	private final CardModel	CARD_IN_DECK 		= null;
	private final int		CARD_NOT_IN			= 0;
	private final int		CARD_IN				= 1;

	/*
	 * getCardDeck Getter
	 * 
	 * Used by Test
	 * 
	 */
	
	public CardModel[][] getCardDeck()
	{
		return cardDeck;
	}
	
	/*
	 * Constructor Method
	 * 
	 */
	
	public CardDeckModel()
	{
		// Local Variables
		
		Random random 		  = new Random();
		int	suitNumber 		  = 0;
		int cardNumber 		  = 0;
		boolean cardGenerated = false;

		cardsInTheDeck = 0;
		
		/*
		 *  Create a Card Deck instance
		 *  
		 *  This Card Deck will be doubly-randomized. The first time here by "shuffling" the generated Cards.
		 *  The second time by the deal() method "dealing" randomly from the Card Deck.
		 *  
		 */
		
		cardDeck = new CardModel[SUITS][CARDS];
		
		// Create the Cards Generated parallel array (all array elements will be initialized to zero, "no Card generated").
		// The Cards Generated array is needed to keep track of the Cards generated and prevent duplicates.
		// The Card Deck array is needed to "shuffle" the Cards generated.
		
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
				
				while (!cardGenerated && cardsInTheDeck < NUMBER_DECK_CARDS)
				{
					// Generated two random numbers. Always request one more number than actually needed.
					
					suitNumber = random.nextInt(SUITS);
					cardNumber = random.nextInt(CARDS);
					
					// Check if this Card has already been generated
					
					if (cardsGenerated[suitNumber][cardNumber] == CARD_NOT_IN)
					{
						cardDeck[suit][number] = new CardModel(suitNumber + 1, cardNumber + 1);

						cardsInTheDeck++;
						
						// Mark the Card already added to the Card Deck
						
						cardsGenerated[suitNumber][cardNumber] = CARD_IN;

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
	
	public CardModel deal()
	{
		// Local variables
		
		Random random 		  = new Random();
		int	suitNumber 		  = 0;
		int cardNumber 		  = 0;
		boolean cardDealt 	  = false;
		
		// Create a "blank" Card. If a Card cannot be dealt this "blank" Card will be returned, instead of null.
		CardModel card = new CardModel();

		if (cardsInTheDeck > 0)
		{
			while (!cardDealt)
			{
				// Generate two random numbers. nextInt() takes a limit argument, returning an integer from 0 to limit - 1;
				
				suitNumber = random.nextInt(SUITS);
				cardNumber = random.nextInt(CARDS);
				
				// Check if this Card is in the Card Deck
				if (cardDeck[suitNumber][cardNumber] != CARD_NOT_IN_DECK)
				{
					card = cardDeck[suitNumber][cardNumber];
					
					cardsInTheDeck--;
					
					// Mark the Card already dealt					
					cardDeck[suitNumber][cardNumber] = CARD_NOT_IN_DECK;
	
					cardDealt = true;
				}					
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "System Error: Card Deck is empty",
												"CardDeck: Deal Method", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		return card;
	}
}
