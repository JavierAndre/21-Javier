package twentyOneUnitTests;

import java.util.Scanner;

import twentyOne.Controller;

public class TestPlay
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private Controller 		game;
	private String[]	playerNames;
	
	/*
	 * Class Constants
	 * 
	 */
	
	
	
	
	/*
	 * Constructor Method
	 * 
	 */
	
	public TestPlay()
	{
		game = new Controller();
	}
	
	/*
	 * Run All Tests Method
	 * 
	 */
	
	public void run()
	{
		testAIPlayerPlay();
	}
	
	/*
	 * Test AIPlayer Method
	 * 
	 */
	
	public void testAIPlayerPlay()
	{
		playerNames = new String[5];	// Null Player Names are AI Players
		
		// Tell the Controller to begin a New Game
		
		game.newGame(playerNames);

		System.out.println("Test AIPlayer Play" + "\n");
		
		System.out.println("Test Initial Deal" + "\n");
		
		// Display Game Status
		
		System.out.println("Game Status: " + (game.isGameOver() ? "Game Over" : "Game Not Over") + "\n");
		
		// Display each Player status and Player Hand
		
		for (int playerNumber = 1; playerNumber <= Controller.MAX_NUMBER_OF_PLAYERS; playerNumber++)
		{
			System.out.println("Player #" + (playerNumber));
			System.out.println("Player Type (1 = Human, 2 = AI): " + game.getPlayerType(playerNumber));
			System.out.println("Player Status (1 = Playing, 2 = Stood, 3 = Busted): " + game.getPlayerStatus(playerNumber));
			System.out.println("Player Points: " + game.getPlayerPoints(playerNumber));
			System.out.println("Player Cards On Hand: " + game.getPlayerCardsOnHand(playerNumber));
			System.out.println("Player Cards:");
			
			for (int cardNumber = 1; cardNumber <= game.getPlayerCardsOnHand(playerNumber); cardNumber++)
			{
				System.out.println("Suit " + game.getPlayerHand(playerNumber, cardNumber).getSuit() + "\t" + "Number " + game.getPlayerHand(playerNumber, cardNumber).getNumber());
			}
			
			System.out.println("\n");
		}
		
		// Deal each Player until end of Game
		
		int playerNumber = 0;
		
		System.out.println("Number of Players Playing: " + game.getPlayersCurrentlyPlaying() + "\n");
		
		System.out.println("Test Player Turns" + "\n");
		
		while (!game.isGameOver())
		{
			playerNumber = game.getCurrentPlayer();
			
			game.play();
			
			System.out.println("Player #" + (playerNumber));
			System.out.println("Player Type (1 = Human, 2 = AI): " + game.getPlayerType(playerNumber));
			System.out.println("Player Status (1 = Playing, 2 = Stood, 3 = Busted): " + game.getPlayerStatus(playerNumber));
			System.out.println("Player Points: " + game.getPlayerPoints(playerNumber));
			System.out.println("Player Cards On Hand: " + game.getPlayerCardsOnHand(playerNumber));
			System.out.println("Player Cards:");
			
			for (int cardNumber = 1; cardNumber <= game.getPlayerCardsOnHand(playerNumber); cardNumber++)
			{
				System.out.println("Suit " + game.getPlayerHand(playerNumber, cardNumber).getSuit() + "\t" + "Number " + game.getPlayerHand(playerNumber, cardNumber).getNumber());
			}
			
			System.out.println("\n" + "Number of Players Playing: " + game.getPlayersCurrentlyPlaying() + "\n");
			
			if (game.getPlayersCurrentlyPlaying() < 0)
			{
				break;
			}
		}
		
		// Redundant to check
		
		if (game.isGameOver())
		{
			System.out.println("Game Over" + "\n");
		}
	}
}
