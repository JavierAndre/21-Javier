package twentyOne;

public class App
{
	public static void main(String[] args)
	{
		// Create an instance of the Controller class
		
		Game controller = new Game();
		
		// Create an instance of the View class
		
		View view = new View(controller);
	}
}
