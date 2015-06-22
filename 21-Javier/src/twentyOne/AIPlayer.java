package twentyOne;

/*
 * AIPlayer Class
 * 
 * MVC: Model
 * 
 */

public class AIPlayer extends Player
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
	
	public AIPlayer(CardDeckModel deck, int number, String name, int type, PlayerScores scores)
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
		// Create a blank Card. If AI Player stands no Card is dealt and AI PLayer returns a blank Card.
		
		CardModel card = new CardModel();

		// If the AI Player has 17 or more points, stand
		
		while (getPlayerPoints() < 17)
		{
			// Draw a Card
		
			card = getCardDeck().deal();
		
			// Add the Card to the Player Hand
		
			setPlayerHand(card);
		}

		// If the AI Player has 17-21 points, stand
		
		if (getPlayerPoints() <= 21)
		{
			setPlayerStatus(Player.STOOD);
		}
		else
		{
			// Player busted
			
			setPlayerStatus(Player.BUSTED);
		}
		
		return card;
	}
}
