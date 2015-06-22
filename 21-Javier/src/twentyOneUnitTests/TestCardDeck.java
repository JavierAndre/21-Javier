package twentyOneUnitTests;

import twentyOne.CardModel;
import twentyOne.CardDeckModel;

public class TestCardDeck
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private CardDeckModel cardDeck;
	
	/*
	 * Constructor
	 * 
	 */
	
	public TestCardDeck()
	{
		cardDeck = new CardDeckModel();
	}
	
	/*
	 * Run Test Method
	 * 
	 */
	
	public void run()
	{
		// Local variables
		
		String displayCard 		= "";
		CardModel card 				= null;
		int	numberOfJokers		= 0;
		
		System.out.println("Test the Card Deck" + "\n");
		
		/*
		 *  Check for Jokers
		 */

		System.out.println("Display the 'shuffled' Card Deck and check for Jokers:" + "\n");
		
		for (int suit = 0; suit < cardDeck.getCardDeck().length; suit++)
		{
			for (int number = 0; number < cardDeck.getCardDeck()[suit].length; number++)
			{
				card = cardDeck.getCardDeck()[suit][number];

				// Check for Joker
				
				if (card.isValid())
				{
					// Display the next Card
					
					System.out.println(cardDeck.getCardDeck()[suit][number].toString());
				}
				else
				{
					System.out.println("\n" + "*** Joker found in Card Deck array at Suit # " + suit + " Card Number " + number + "***" + "\n");
					
					numberOfJokers++;
				}
			}
		}

		System.out.println("\n" + "Number of Jokers found: " + numberOfJokers + "\n");
		
		/*
		 * Check for duplicates
		 * 
		 */
		
		System.out.println("Check for duplicates:" + "\n");
		
		if (testCardDeckDuplicates())
		{
			System.out.println("*** At least one duplicate Card was found ***" + "\n");
		}
		else
		{
			System.out.println("No duplicate Cards found" + "\n");
		}
		
		/*
		 * Check each Suit is complete
		 * 
		 */
		
		System.out.println("Check all Suits are complete:" + "\n");
		
		if (testCardDeckSuits())
		{
			System.out.println("*** At least one Suit is missing a Card ***" + "\n");
		}
		else
		{
			System.out.println("All Suits are complete" + "\n");
		}
	}
	
	private boolean testCardDeckDuplicates()
	{
		// Local variables
		
		boolean duplicateFound = false;
		
		for (int suit = 0; suit < cardDeck.getCardDeck().length; suit++)
		{
			for (int number = 0; number < cardDeck.getCardDeck()[suit].length; number++)
			{
				if (testCardDeckDuplicates(cardDeck.getCardDeck()[suit][number]) > 1)
				{
					System.out.println("*** The Card Suit[" + (suit + 1) + "]" + " Card[" + (number + 1) + "] has a duplicate ***");
					
					duplicateFound = true;
				}
			}
		}
		
		return duplicateFound;
	}
	
	private int testCardDeckDuplicates(CardModel card)
	{
		// Local variables
		
		int count = 0;
		
		for (int suit = 0; suit < cardDeck.getCardDeck().length; suit++)
		{
			for (int number = 0; number < cardDeck.getCardDeck()[suit].length; number++)
			{
				if (card.compareTo(cardDeck.getCardDeck()[suit][number]))
				{
					count++;
				}
			}
		}
		
		return count;
	}
	
	private boolean testCardDeckSuits()
	{
		// Local Variables
		
		boolean incompleteSuit = false;
		
		for (int suit = 0; suit < CardDeckModel.SUITS; suit++)
		{
			for (int number = 0; number < CardDeckModel.CARDS; number++)
			{
				CardModel card = new CardModel(suit + 1, number + 1);
				
				if (testCardDeckSuit(card))
				{
					// Replace with Card.toString() method
					
					System.out.println("*** Suit # " + (suit + 1) + " Card # " + (number + 1) + " is missing ***");
				}
			}
		}
		
		return incompleteSuit;
	}
	
	private boolean testCardDeckSuit(CardModel card)
	{
		// Local Variables
		
		boolean incompleteSuit = true;
				
		for (int suit = 0; suit < cardDeck.getCardDeck().length; suit++)
		{
			for (int number = 0; number < cardDeck.getCardDeck()[suit].length; number++)
			{
				if (card.compareTo(cardDeck.getCardDeck()[suit][number]))
				{
					incompleteSuit = false;
					
					break;
				}
			}
		}
		
		return incompleteSuit;
	}
}
