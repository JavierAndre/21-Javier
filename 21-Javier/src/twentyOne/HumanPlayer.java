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
	
	public HumanPlayer(CardDeckModel deck, int number, String name, int type, PlayerScores scores)
	{
		super(deck, number, name, type, scores);
	}
	
	/*
	 * Play Method
	 * 
	 */
	
	@Override
	public CardModel play()
	{
		// Draw a Card
		
		CardModel card = getCardDeck().deal();
		
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
