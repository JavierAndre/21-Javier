package twentyOne;

import java.util.ArrayList;

/*
 * Player Parent (Super) Class
 * 
 * MVC: Model
 * 
 * No Player instances will be created from this class
 * 
 */

public abstract class Player
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private CardDeckModel 			cardDeck;
	
	private int						playerNumber;
	private String					playerName;
	private int						playerType;	
	private int						playerStatus;
	private ArrayList<CardModel>	playerHand;
	private int						playerCardsOnHand;
	private int						playerPoints;
	private PlayerScores			playerScores;
	
	/*
	 * Class Constants
	 * 
	 */

	public static final int	HUMAN_PLAYER	= 1;
	public static final int	AI_PLAYER		= 2;
	
	public static final int	NOT_PLAYING		= 0;
	public static final int	PLAYING 		= 1;
	public static final int	STOOD			= 2;
	public static final int	BUSTED			= 3;
	
	/*
	 * Constructor Method
	 * 
	 */
	
	public Player(CardDeckModel deck, int number, String name, int type, PlayerScores scores)
	{
		cardDeck			= deck;
		
		playerNumber 		= number;
		playerName			= name;
		playerType			= type;
		
		playerStatus		= PLAYING;
		playerHand			= new ArrayList<CardModel>();
		playerCardsOnHand	= 0;
		playerPoints		= 0;
		playerScores		= scores;
	}

	/*
	 * Getters
	 * 
	 */
	
	public CardDeckModel getCardDeck()
	{
		return cardDeck;
	}
	
	public int getPlayerNumber()
	{
		return playerNumber;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}

	public int getPlayerType()
	{
		return playerType;
	}
	
	public CardModel getPlayerHand(int cardNumber)
	{
		return playerHand.get(cardNumber - 1);
	}

	public int getPlayerCardsOnHand()
	{
		return playerCardsOnHand;
	}
	
	public int getPlayerPoints()
	{
		return playerPoints;
	}
	
	public int getPlayerStatus()
	{
		return playerStatus;
	}
	
	public int getPlayerWins()
	{
		return playerScores.getPlayerWins();
	}
	
	public int getPlayerLoses()
	{
		return playerScores.getPlayerLoses();
	}
	
	public int getPlayerTies()
	{
		return playerScores.getPlayerTies();
	}
	
	/*
	 * Setters
	 * 
	 */

	public void setPlayerHand(CardModel card)
	{
		playerHand.add(card);
		
		// Accumulate the Cards on Hand
		
		playerCardsOnHand++;
		
		// Accumulate the Player Points. Update the Card (whether or not this is an Ace and if it is treated as 11 points)
		
		setPlayerPoints();
	}
	
	public void setPlayerPoints()
	{
		// Local variables
		
		int points = 0;

		// Check if this is an Ace
		
		if (playerHand.get(playerCardsOnHand - 1).getNumber() == CardModel.ACE)
		{
			// Check if treating the Ace as 11 points would bust the Player
			
			if ((playerPoints + 11) > 21)
			{
				// Indicate this Ace is not treated as 11 points
				
				playerHand.get(playerCardsOnHand - 1).setAceAsEleven(false);
				
				points = 1;
			}
			else
			{
				// Indicate this Ace is treated as 11 points
				
				playerHand.get(playerCardsOnHand - 1).setAceAsEleven(true);
				
				points = 11;
			}
		}
		
		// Face Cards(Jacks, Queeens, Kings) are worth 10 points
		
		else if (playerHand.get(playerCardsOnHand - 1).getNumber() >= 11)
		{
			points = 10;
		}
		else
		{
			points = playerHand.get(playerCardsOnHand - 1).getNumber();
		}

		// Add new Card points to the Player points
		
		playerPoints = playerPoints + points;
				
		// Check the new Player points total. If the Player has busted,
		// reconsider counting one or more Aces as 1 point each.
		
		if (playerPoints > 21)
		{
			for (int cardNumber = 0; cardNumber < playerHand.size(); cardNumber++)
			{
				if (playerHand.get(cardNumber).getNumber() == CardModel.ACE)
				{
					// Check if this Ace is being treated as 11 points
					
					if (playerHand.get(cardNumber).isAceEleven())
					{
						// Indicate this Ace is now treated as 1 point
						
						playerHand.get(cardNumber).setAceAsEleven(false);
						
						// Reduce the Player points to 1 point for this Ace
						
						playerPoints = playerPoints - 10;
						
						// Check if the Player is no longer busted
					
						if (playerPoints <= 21)
						{
							break;
						}
						
					}	// End of checking if this Ace is being treated as 11 points
					
				}	// End of checking if this Card is an Ace
				
			}	// End of checking the Player's Cards
			
		}	// End of checking if the Player has busted
		
	}	// End of setPlayerHand method
	
	public void setPlayerStatus(int status)
	{
		playerStatus = status;
	}

	public void setPlayerWins()
	{
		playerScores.setPlayerWins();
	}
	
	public void setPlayerLoses()
	{
		playerScores.setPlayerLoses();
	}
	
	public void setPlayerTies()
	{
		playerScores.setPlayerTies();
	}
	
	/*
	 * Initial Deal Method
	 * 
	 */
	
	public CardModel initialDeal()
	{
		// Draw a Card
		
		CardModel card = getCardDeck().deal();
		
		// Add the Card to the Player Hand
		
		setPlayerHand(card);
		
		return card;
	}
	
	/*
	 * Play Method
	 * 
	 * Abstract - must be overriden by the child classes
	 * 
	 */
	
	public abstract CardModel play();
}
