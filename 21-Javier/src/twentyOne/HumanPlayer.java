package twentyOne;

/*
 * HumanPlayer Class
 * 
 * MVC: Model
 * 
 */

public class HumanPlayer extends Player
{
	/*
	 * Class Instance Variables
	 * 
	 */
		
	
	/*
	 * Class Constants
	 * 
	 */
	
	
	/*
	 * Constructor Method
	 * 
	 */
	
	public HumanPlayer(CardDeck deck, int number, String name, int type, PlayerScores scores)
	{
		super(deck, number, name, type, scores);
	}
	
	/*
	 * Play Method
	 * 
	 */
	
	@Override
	public Card play()
	{
		// Draw a Card
		
		Card card = getCardDeck().deal();
		
		// Add the Card to the Player Hand
		
		setPlayerHand(card);

		// If the Player has 21 points, automatically stand
		
		if (getPlayerPoints() == 21)
		{
			setPlayerStatus(Player.STOOD);
		}
		
		// Check if the Player busted
		
		else if (getPlayerPoints() > 21)
		{
			setPlayerStatus(Player.BUSTED);
		}
		
		return card;
	}
}
