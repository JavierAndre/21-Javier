package twentyOne;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

/*
 * View Class
 * 
 * MVC: View
 * 
 */

public class View extends JFrame
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private Controller 				game;
	
	/*
	 * Class Constants
	 * 
	 */
	
	public static final String	GAME_TITLE 		= "Game of 21";	
	public static final int		FRAME_WIDTH		= 6;					// 3 pixels each left and right frame margins
	public static final int		FRAME_HEIGHT 	= 28;					// 28 pixels in frame height total, probably 3 pixels at the bottom and 25 for the title bar at top
	
	/*
	 * Constructor Method
	 * 
	 */
	
	public View(Controller controller)
	{
		this.game = controller;
		
		// Get the Screen Dimension
		
		Rectangle screenDimension = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		
		// Calculate the Board Dimension. This is the dimension of the Content Pane, full Screen, less the frame borders. 
		
		Dimension tableDimension = new Dimension(screenDimension.width - FRAME_WIDTH, screenDimension.height - FRAME_HEIGHT);
		
		// Set up the frame title
		
		setTitle(GAME_TITLE + " - " + Controller.VERSION);
		
		// Specify the action of the frame close button
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the frame not resizable
		
		setResizable(false);
		
		// Create the instance of the TableView class and add it to the frame's content pane
		
		TableView tableView = new TableView(controller, tableDimension);
		
		add(tableView);
		
		// Build the window
		
		pack();
		
		// Center the frame on the screen (must be placed AFTER pack)
		
		setLocationRelativeTo(null);
				
		// Display the window
		
		setVisible(true);
	}
}
