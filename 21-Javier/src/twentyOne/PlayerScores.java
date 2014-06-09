package twentyOne;

/*
 * PlayerScores Class
 * 
 * MVC: Model
 * 
 */

public class PlayerScores
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private String 	playerName;
	private int		playerWins;
	private int		playerLoses;
	private int		playerTies;
	
	/*
	 * Class Constants
	 * 
	 */
	
	
	
	/*
	 * Getters
	 * 
	 */
	
	public String getPlayerName()
	{
		return playerName;
	}

	public int getPlayerWins()
	{
		return playerWins;
	}
	
	public int getPlayerLoses()
	{
		return playerLoses;
	}
	
	public int getPlayerTies()
	{
		return playerTies;
	}
	
	/*
	 * Setters
	 * 
	 */
	
	public void setPlayerWins()
	{
		playerWins++;
	}
	
	public void setPlayerLoses()
	{
		playerLoses++;
	}
	
	public void setPlayerTies()
	{
		playerTies++;
	}
	
	/*
	 * Constructor Method
	 * 
	 */
	
	public PlayerScores(String name)
	{
		playerName	= name.toUpperCase();
		playerWins 	= 0;
		playerLoses = 0;
		playerTies 	= 0;
	}
}
