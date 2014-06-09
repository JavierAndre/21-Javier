package twentyOne;

import java.util.ArrayList;

/*
 * Game Class
 * 
 * MVC: Controller
 * 
 */

public class Game
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private CardDeck				cardDeck;
	private String[]				playerNames;
	private ArrayList<Player>		gamePlayers;
	private int						currentPlayerNumber;
	private int						playersCurrentlyPlaying;
	private	ArrayList<PlayerScores>	playerScoresList;
	private boolean					dealingFinished;
	private boolean					gameOver;
	
	/*
	 * Class Constants
	 * 
	 */
	
	public static final String		VERSION					= "Version 0.91";
	public static final int			NUMBER_OF_PLAYERS 		= 4;
	public static final int			MAX_NUMBER_OF_PLAYERS 	= 5;
	private final String			AI_PLAYER_NAME			= "AI Player ";
	private final String			DEALER_NAME				= "Dealer";
	public static final int			MAX_PLAYER_HAND_CARDS 	= 12;
	public static final boolean		INITIAL_DEAL			= true;

	/*
	 * Getters
	 * 
	 */
	
	public int getCurrentPlayer()
	{
		return currentPlayerNumber;
	}
	
	public String getPlayerName(int playerNumber)
	{
		return gamePlayers.get(playerNumber - 1).getPlayerName();
	}

	public int getPlayerType(int playerNumber)
	{
		return gamePlayers.get(playerNumber - 1).getPlayerType();
	}
	
	public int getPlayerPoints(int playerNumber)
	{
		return gamePlayers.get(playerNumber - 1).getPlayerPoints();
	}

	public int getPlayerStatus(int playerNumber)
	{
		return gamePlayers.get(playerNumber - 1).getPlayerStatus();
	}

	public int getPlayerWins(int playerNumber)
	{
		return gamePlayers.get(playerNumber - 1).getPlayerWins();
	}
	
	public int getPlayerLoses(int playerNumber)
	{
		return gamePlayers.get(playerNumber - 1).getPlayerLoses();
	}
	
	public int getPlayerTies(int playerNumber)
	{
		return gamePlayers.get(playerNumber - 1).getPlayerTies();
	}

	public Card getPlayerHand(int playerNumber, int cardNumber)
	{
		return gamePlayers.get(playerNumber - 1).getPlayerHand(cardNumber);
	}
	
	public int getPlayerCardsOnHand(int playerNumber)
	{
		return gamePlayers.get(playerNumber - 1).getPlayerCardsOnHand();
	}
	
	public boolean dealingFinished()
	{
		return dealingFinished;
	}
	
	public boolean isGameOver()
	{
		return gameOver;
	}

	public int getPlayersCurrentlyPlaying()
	{
		return playersCurrentlyPlaying;
	}
	
	/*
	 * Constructor Method
	 * 
	 */
	
	public Game()
	{
		// Create the Player Scores list
		
		playerScoresList = new ArrayList<PlayerScores>();
	}
	
	/*
	 * New Game Method
	 * 
	 */
	
	public void newGame(String[] names)
	{
		playerNames 			= names;
		gamePlayers 			= new ArrayList<Player>(MAX_NUMBER_OF_PLAYERS);
		currentPlayerNumber 	= 1;
		playersCurrentlyPlaying = 0;
		dealingFinished			= false;
		gameOver				= false;
		
		String playerName		= "";

		// Create a new Card Deck
		
		cardDeck = new CardDeck();
		
		// Create the Players (including the Dealer, the last Player).
		// The Dealerwill be an AI Player.
		
		for (int playerNumber = 0; playerNumber < MAX_NUMBER_OF_PLAYERS; playerNumber++)
		{
			if (playerNames[playerNumber] == null)
			{
				if (playerNumber == MAX_NUMBER_OF_PLAYERS - 1)
				{
					playerName = DEALER_NAME;
				}
				else
				{
					playerName = AI_PLAYER_NAME + (playerNumber + 1);
				}
				
				// Create an AI Player

				PlayerScores playerScores = getPlayerScores(playerName);
				
				gamePlayers.add(new AIPlayer(cardDeck, (playerNumber + 1), playerName, Player.AI_PLAYER, playerScores));
			}
			else
			{
				// Create a Human Player
				
				PlayerScores playerScores = getPlayerScores(playerNames[playerNumber]);
				
				gamePlayers.add(new HumanPlayer(cardDeck, (playerNumber + 1), playerNames[playerNumber], Player.HUMAN_PLAYER, playerScores));
			}
			
			// Increment the number of Players currently playing
			
			playersCurrentlyPlaying++;
		}
		
		// Deal each Player (including the Dealer) 2 Cards
		
		for (int cardNumber = 1; cardNumber <= 2; cardNumber++)
		{
			for (int playerNumber = 0; playerNumber < MAX_NUMBER_OF_PLAYERS; playerNumber++)
			{
				gamePlayers.get(playerNumber).initialDeal();
			}
		}
	}
	
	/*
	 * Play Method
	 * 
	 */
	
	public Card play()
	{
		// Tell the current Player to play
		
		Card card = gamePlayers.get(currentPlayerNumber - 1).play();

		/*
		 *  Check if the current Player stood
		 *  
		 */
		
		if (gamePlayers.get(currentPlayerNumber - 1).getPlayerStatus() == Player.STOOD)
		{
			// Decrement the number of Players currently playing
			
			playersCurrentlyPlaying--;
			
			// Give the next Player a turn
			
			nextPlayer();
		}
		
		/*
		 *  Check if the current Player busted
		 *  
		 */
		
		else if (gamePlayers.get(currentPlayerNumber - 1).getPlayerStatus() == Player.BUSTED)
		{
			// Decrement the number of Players currently playing
			
			playersCurrentlyPlaying--;
			
			// Give the next Player a turn
			
			nextPlayer();
		}

		/*
		 * Check if this Game is over
		 * 
		 */
		
		checkGameOver();
		
		return card;
	}
	
	/*
	 * Next Player Method
	 * 
	 */
	
	public void nextPlayer()
	{
		// Give the next Player a turn. Consider only Players with a status of playing.
		
		while (playersCurrentlyPlaying > 0)
		{
			if (currentPlayerNumber < MAX_NUMBER_OF_PLAYERS)
			{
				currentPlayerNumber++;
			}
			else
			{
				currentPlayerNumber = 1;
			}
			
			if (gamePlayers.get(currentPlayerNumber - 1).getPlayerStatus() == Player.PLAYING)
			{
				break;
			}
		}
	}
	
	/*
	 * Stand Method
	 * 
	 */
	
	public void stand()
	{		
		// Tell the current Player to stand
		
		gamePlayers.get(currentPlayerNumber - 1).setPlayerStatus(Player.STOOD);
		
		// Decrement the number of Players currently playing
		
		playersCurrentlyPlaying--;
		
		// Give the next Player a turn
		
		nextPlayer();
		
		// Check if this Game is over
		
		checkGameOver();
	}
	
	/*
	 * Check Game Over Method
	 * 
	 */
	
	private boolean checkGameOver()
	{
		/*
		 *  Check if the Dealer has stood
		 *  
		 */
		
		if (gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).getPlayerStatus() == Player.STOOD)
		{
			// Indicate this Game is over
			
			gameOver = true;

			// Check if the Dealer has Blackjack
			
			if (gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).getPlayerPoints() == 21 && gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).getPlayerCardsOnHand() == 2)
			{
				// All Players lose unless they also have Blackjack, in which case it is a tie
				
				for (int playerNumber = 0; playerNumber < NUMBER_OF_PLAYERS; playerNumber++)
				{
					// Check for BlackJack (a tie)
					
					if (gamePlayers.get(playerNumber).getPlayerPoints() == 21 && gamePlayers.get(playerNumber).getPlayerCardsOnHand() == 2)
					{
						// Increment the Player's number of ties
						
						gamePlayers.get(playerNumber).setPlayerTies();
						
						// Increment the Dealer's number of ties
						
						gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).setPlayerTies();
					}
					else
					{
						// Tell the Player to stand
						
						//gamePlayers.get(playerNumber).setPlayerStatus(Player.STOOD);
						
						// Increment the Player's number of loses
						
						gamePlayers.get(playerNumber).setPlayerLoses();
						
						// Increment the Dealer's number of wins
						
						gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).setPlayerWins();
						
						// Decrement the number of Players currently playing
						
						//playersCurrentlyPlaying--;
					}
				}
			}
			else
			{
				// The Dealer does not have BlackJack. Check all the Players' points
				
				for (int playerNumber = 0; playerNumber < NUMBER_OF_PLAYERS; playerNumber++)
				{
					// Check if the Player has Blackjack
					
					if (gamePlayers.get(playerNumber).getPlayerPoints() == 21 && gamePlayers.get(playerNumber).getPlayerCardsOnHand() == 2)
					{
						// Increment the Player's number of wins
						
						gamePlayers.get(playerNumber).setPlayerWins();
						
						// Increment the Dealer's number of loses
						
						gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).setPlayerLoses();
					}
					
					// Check if the Player has busted
					
					else if (gamePlayers.get(playerNumber).getPlayerStatus() == Player.BUSTED)
					{
						// Increment the Player's number of loses
						
						gamePlayers.get(playerNumber).setPlayerLoses();
						
						// Increment the Dealer's number of wins
						
						gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).setPlayerWins();
					}
					
					// Check if the Player has beaten the Dealer
					
					else if (gamePlayers.get(playerNumber).getPlayerPoints() > gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).getPlayerPoints())
					{
						// Tell the Player to stand
						
						//gamePlayers.get(playerNumber).setPlayerStatus(Player.STOOD);
						
						// Increment the Player's number of wins
						
						gamePlayers.get(playerNumber).setPlayerWins();
						
						// Increment the Dealer's number of loses
						
						gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).setPlayerLoses();
						
						// Decrement the number of Players currently playing
						
						//playersCurrentlyPlaying--;
					}
					
					// Check if the Player has lost to the Dealer
					
					else if (gamePlayers.get(playerNumber).getPlayerPoints() < gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).getPlayerPoints())
					{
						// Tell the Player to stand
						
						//gamePlayers.get(playerNumber).setPlayerStatus(Player.STOOD);
						
						// Increment the Player's number of loses
						
						gamePlayers.get(playerNumber).setPlayerLoses();
						
						// Increment the Dealer's number of wins
						
						gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).setPlayerWins();
						
						// Decrement the number of Players currently playing
						
						//playersCurrentlyPlaying--;
					}

					// Check if the Player has tied the Dealer
						
					else if (gamePlayers.get(playerNumber).getPlayerPoints() == gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).getPlayerPoints())
					{
						// Tell the Player to stand
						
						//gamePlayers.get(playerNumber).setPlayerStatus(Player.STOOD);
						
						// Increment the Player's number of ties
						
						gamePlayers.get(playerNumber).setPlayerTies();
						
						// Increment the Dealer's number of ties
						
						gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).setPlayerTies();
						
						// Decrement the number of Players currently playing
						
						//playersCurrentlyPlaying--;
					}
				}
			}
		}
		
		/*
		 *  Check if the Dealer has busted
		 *  
		 */
		
		else if (gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).getPlayerStatus() == Player.BUSTED)
		{
			// Indicate this Game is over
			
			gameOver = true;

			// Check all the Players' points
			
			for (int playerNumber = 0; playerNumber < NUMBER_OF_PLAYERS; playerNumber++)
			{
				// Check if the Player has busted. A Player automatically loses if it busts, regardless of whether or not the Dealer busts.
				
				if (gamePlayers.get(playerNumber).getPlayerStatus() == Player.BUSTED)
				{
					// Increment the Player's number of loses
					
					gamePlayers.get(playerNumber).setPlayerLoses();

					// Increment the Dealer's number of wins
					
					gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).setPlayerWins();
					
					// Decrement the number of Players currently playing
					
					//playersCurrentlyPlaying--;
				}
					
				// The Player has beaten the Dealer
				
				else
				{					
					// Tell the Player to stand (may have done so already)
					
					//gamePlayers.get(playerNumber).setPlayerStatus(Player.STOOD);
					
					// Increment the Player's number of wins
					
					gamePlayers.get(playerNumber).setPlayerWins();

					// Increment the Dealer's number of loses
					
					gamePlayers.get(MAX_NUMBER_OF_PLAYERS - 1).setPlayerLoses();
					
					// Decrement the number of Players currently playing
					
					//playersCurrentlyPlaying--;
				}
			}
		}
		
		return gameOver;
	}
	
	/*
	 * getPlayerScores Metthod
	 * 
	 */
	
	private PlayerScores getPlayerScores(String playerName)
	{
		// Lookup this Player's Scores List
		
		PlayerScores playerScores = null;
		
		for (int playerNumber = 0; playerNumber < playerScoresList.size(); playerNumber++)
		{
			if (playerScoresList.get(playerNumber).getPlayerName().equals(playerName.toUpperCase()))
			{
				playerScores = playerScoresList.get(playerNumber);
			}
		}
		
		// If not found, return a new Player Scores List
		
		if (playerScores == null)
		{
			playerScores = new PlayerScores(playerName.toUpperCase());
			
			playerScoresList.add(playerScores);
		}
		
		return playerScores;
	}
}
