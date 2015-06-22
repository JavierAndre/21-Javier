package twentyOne;

/*
 * Card Class
 * 
 * MVC: Model
 * 
 */

public class CardModel
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
	
	public static final int	HEARTS		= 1;
	public static final int	DIAMONDS	= 2;
	public static final int	SPADES		= 3;
	public static final int	CLUBS		= 4;
	
	public static final int JOKER		= 0;
	public static final int	ACE			= 1;
	public static final int JACK		= 11;
	public static final int	QUEEN		= 12;
	public static final int KING		= 13;

	/*
	 * Constructor Methods
	 * 
	 */
	
	public CardModel(int suit, int number)
	{
		cardSuit 	= suit;
		cardNumber 	= number;
		aceAsEleven	= false;
		validCard 	= true;
	}
	
	public CardModel()
	{
		// Allows the creation of a "blank" Card
		
		cardSuit 	= CardModel.JOKER;
		cardNumber 	= CardModel.JOKER;
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
	
	public boolean compareTo(CardModel anotherCard)
	{
		return (getSuit() == anotherCard.getSuit() && getNumber() == anotherCard.getNumber());
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
			case CardModel.ACE:
			{
				cardName = "Ace of ";
				
				break;
			}
			case CardModel.JACK:
			{
				cardName = "Jack of ";
				
				break;
			}
			case CardModel.QUEEN:
			{
				cardName = "Queen of ";
				
				break;
			}
			case CardModel.KING:
			{
				cardName = "King of ";
				
				break;
			}
			case CardModel.JOKER:
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
			case CardModel.DIAMONDS:
			{
				cardName = cardName + "Diamonds";
				
				break;
			}
			case CardModel.SPADES:
			{
				cardName = cardName + "Spades";
				
				break;
			}
			case CardModel.CLUBS:
			{
				cardName = cardName + "Clubs";
				
				break;
			}
			case CardModel.HEARTS:
			{
				cardName = cardName + "Hearts";
				
				break;
			}
			case CardModel.JOKER:
			{
				cardName = "Joker";
				
				break;
			}
		}
		
		// Return the Card name
		
		return cardName;
		
	}	// End of toString method
}
