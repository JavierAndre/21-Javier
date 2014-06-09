package twentyOneUnitTests;

import java.util.Scanner;

public class App
{
	public static void main(String[] args)
	{			
		String choice 	 = "";
		Scanner keyboard = new Scanner(System.in);
		boolean finished = false;
		
		while (!finished)
		{
			System.out.println("Tests Menu" + "\n");
			
			System.out.println("1. Test Card Deck");
			System.out.println("2. Test AIPlayer Play");
			
			System.out.println("E. Exit");
			
			System.out.print("\n" + "Enter your Menu choice: ");
			choice = keyboard.nextLine();
			
			choice = choice.toUpperCase();
			
			System.out.println();
			
			switch (choice)
			{
				case "1":
				{
					TestCardDeck testCardDeck = new TestCardDeck();
					
					testCardDeck.run();
					
					break;
				}
				case "2":
				{
					TestPlay testAIPlayerInitialDeal = new TestPlay();
					
					testAIPlayerInitialDeal.run();
					
					break;
				}
				case "E":
				{
					finished = true;
					
					break;
				}
				default:
				{
					System.out.println("Invalid Menu option" + "\n");
					
					break;
				}
			}
		}
	}
}
