package twentyOne;

/*
 * Card Class
 * 
 * MVC: Model
 * 
 */

public class Card
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private int 	cardSuit;
	private int 	cardNumber;
	private boolean aceAsEleven;
	private boolean validCard;
	
	/*
	 * Class Constants
	 * 
	 */

	
	/*
	 * Constructor Methods
	 * 
	 */
	
	public Card(int suit, int number)
	{
		cardSuit 	= suit;
		cardNumber 	= number;
		aceAsEleven	= false;
		validCard 	= true;
	}
	
	public Card()
	{
		// Allows the creation of a "blank" Card
		
		cardSuit 	= CardDeck.JOKER;
		cardNumber 	= CardDeck.JOKER;
		aceAsEleven	= false;
		validCard 	= false;
	}
	
	/*
	 * Getters
	 * 
	 */
	
	public int getSuit()
	{
		return cardSuit;
	}
	
	public int getNumber()
	{
		return cardNumber;
	}

	/*
	 * Setters
	 * 
	 */
	
	public void setAceAsEleven(boolean asEleven)
	{
		aceAsEleven = asEleven;
	}
	
	/*
	 * isAceEleven Method
	 * 
	 */
	
	public boolean isAceEleven()
	{
		return aceAsEleven;
	}
	
	/*
	 * isValid Method
	 * 
	 */
	
	public boolean isValid()
	{
		return validCard;
	}
	
	/*
	 * CompareTo Method
	 * 
	 */
	
	public boolean compareTo(Card anotherCard)
	{
		return (this.getSuit() == anotherCard.getSuit() && this.getNumber() == anotherCard.getNumber());
	}
	
	/*
	 * toString Method
	 * 
	 * Inherited from Object, overridden here
	 * 
	 */
	
	@Override
	public String toString()
	{
		// Local variables
		
		String cardName = ""; 
		
		/*
		 * Card Number
		 * 
		 */
		
		switch (cardNumber)
		{
			case CardDeck.ACE:
			{
				cardName = "Ace of ";
				
				break;
			}
			case CardDeck.JACK:
			{
				cardName = "Jack of ";
				
				break;
			}
			case CardDeck.QUEEN:
			{
				cardName = "Queen of ";
				
				break;
			}
			case CardDeck.KING:
			{
				cardName = "King of ";
				
				break;
			}
			case CardDeck.JOKER:
			{
				break;
			}
			default:
			{
				// String concatenation
				
				cardName = String.valueOf(cardNumber) + " of ";
				
				break;
			}
		}
		
		/*
		 * Suit
		 * 
		 */
		
		switch (cardSuit)
		{
			case CardDeck.DIAMONDS:
			{
				cardName = cardName + "Diamonds";
				
				break;
			}
			case CardDeck.SPADES:
			{
				cardName = cardName + "Spades";
				
				break;
			}
			case CardDeck.CLUBS:
			{
				cardName = cardName + "Clubs";
				
				break;
			}
			case CardDeck.HEARTS:
			{
				cardName = cardName + "Hearts";
				
				break;
			}
			case CardDeck.JOKER:
			{
				cardName = "Joker";
				
				break;
			}
		}
		
		// Return the Card name
		
		return cardName;
		
	}	// End of toString method
}
