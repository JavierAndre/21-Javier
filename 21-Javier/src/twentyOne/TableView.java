package twentyOne;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.imgscalr.Scalr;

/*
 * TableView Class
 * 
 * MVC: View
 * 
 */

public class TableView extends JPanel
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private Game				game;
	private BufferedImage 		tableImage;
	
	private String[]			playerNames;
	private String				savePlayerName;
	private JTextField[] 		playerNameTextFields;
	private JLabel[] 			playerPointsLabels;
	private JPanel[] 			playerCardPanels;
	private JLabel[]			playerWinsLabels;
	private JLabel[]			playerLossesLabels;
	private JLabel[]			playerTiesLabels;
	
	private Dimension 			tableDimension;
	private	int 				verticalTablePanelWidth;
	private	int 				verticalTablePanelHeight;
	private	double 				percentOfReferenceWidth;
	private double				percentOfReferenceHeight;
	private int 				playerCardImageWidth;
	private int					playerCardImageHeight;

	private ImageIcon			cardBackImageIcon;
	private JButton				cardDeckButton;
	private JButton				standButton;
	private JButton				newGameButton;
	private ActionListener		newGameButtonActionListener;
	private boolean				gameInProgress;
	
	/*
	 * Class Constants
	 * 
	 */

	// The bin folder contains the Images folder and the class file folder.
	// To place the Images folder with the class files remove the leading / from IMAGE_PATH.
	
	private final boolean		TEMPORARY_BORDER					= false;
	private final int			HORIZONTAL_PANELS					= 2;
	private final int			VERTICAL_PANELS_HORIZONTALLY		= 3;					// Per Horizontal Panel
	private final int			VERTICAL_PANELS_VERTICALLY			= 2;					// Number of Horizontal Panels rows
	private final int			VERTICAL_PANELS						= 6;	
	private final String 		IMAGE_PATH							= "/Images/";
	private final String 		TABLE_IMAGE							= "BlackJackTable.jpg";
	private final String		CARD_BACK_IMAGE 					= "BlueCardBack.png";	
	private final String		CARD_DECK_IMAGE						= "BlueCardDeck.png";	// BlueCardDeck.png
	private final String		STAND_BUTTON_IMAGE					= "stand.png";
	
	private final int			REFERENCE_TABLE_DIMENSION_WIDTH		= 1360;					// For reference screen resolution 1366 width
	private final int			REFERENCE_TABLE_DIMENSION_HEIGHT	= 740;					// For reference screen resolution 768 height
	public  final int			CARD_IMAGE_WIDTH 					= 96;					// Reference width for screen resolution 1366 x 768 (table dimension width 1354)
	public  final int			CARD_IMAGE_HEIGHT 					= 128;					// Reference height for screen resolution 1366 x 768 (table dimension height 740)
	private final String		TEXT_FONT							= "Segoe Print Bold";
	private final int			TEXT_FONT_SIZE 						= 20;					// 20 pt = 26 px
	
	private final int			MAX_PLAYER_NAME_LENGTH				= 20;
	private final int			PLAYER_NAME_TEXTFIELD_WIDTH			= 330;					// Allows approx. 25 characters to be typed using font size 20 bold
	private final int			PLAYER_NAME_TEXTFIELD_HEIGHT		= 40;
	private final Color			PLAYER_NAME_TEXTFIELD_COLOR			= Color.WHITE;
	public  static final String	INITIAL_PLAYER_NAME					= "Enter your Player Name Here";
	private final Color			PLAYER_STOOD_COLOR					= Color.CYAN;
	private final String		PLAYER_STOOD_TEXT					= " Stood";
	private final Color			PLAYER_BUSTED_COLOR					= Color.RED;
	private final String		PLAYER_BUSTED_TEXT					= " Busted!";
	private final Color			NEXT_PLAYERS_TURN					= Color.GREEN;
	private final int			PLAYER_CARD_LABEL_LEFT_BORDER		= 4;					// 4-pixel left-border (only 2 pixels show) and 2-pixel right border
	private final int			PLAYER_CARD_LABEL_RIGHT_BORDER		= 2;
	private final int			PLAYER_CARDS_IN_SCROLLPANE			= 3;
	
	private final int			PLAYER_POINTS_LABEL_WIDTH			= 50;
	private final int			PLAYER_POINTS_LABEL_HEIGHT			= 20;
	private final Color			PLAYER_POINTS_COLOR					= Color.WHITE;
	private final Color			PLAYER_WINS_COLOR					= Color.CYAN;
	private final Color			PLAYER_LOSES_COLOR					= Color.RED;
	private final Color			PLAYER_TIES_COLOR					= Color.YELLOW;
	private final int			PLAYER_STATS_FONT_SIZE				= 16;
	private final Color			BLACKJACK_COLOR						= Color.GREEN;
	private final String		BLACKJACK_TEXT						= "Blackjack!!!";
	
	private final int			DEALER_NAME_TEXTFIELD				= 4;
	private final String		DEALER_NAME_TEXTFIELD_TEXT			= " Dealer ";
	private final String		DEALER_FONT							= "Algerian";
	private final int			DEALER_FONT_SIZE 					= 30;
	private final Color			DEALER_NAME_COLOR					= Color.YELLOW;
	
	private final int			CARD_BUTTON_WIDTH					= 125;					// 115
	private final int			CARD_BUTTON_HEIGHT					= 250;					// 128
	private final Color			NEW_GAME_BUTTON_COLOR				= Color.RED;
	private final String		NEW_GAME_BUTTON_TEXT				= "       New Game       ";
	private final String		NEW_GAME_BUTTON_TOOLTIP_TEXT		= "Click to Play a New Game";
	private final Color			START_GAME_BUTTON_COLOR				= Color.GREEN;
	private final String		START_GAME_BUTTON_TEXT				= "    Start the Game    ";
	private final String		START_GAME_BUTTON_TOOLTIP_TEXT		= "Click to Start the Game";
	private final int			NEW_GAME_BUTTON_WIDTH				= 150;
	private final int			NEW_GAME_BUTTON_HEIGHT				= 50;

	private final String[][]	CARD_IMAGES 						= 	{
																			{"AD.png", "2D.png", "3D.png", "4D.png", "5D.png", "6D.png", "7D.png", "8D.png", "9D.png", "10D.png", "JD.png", "QD.png", "KD.png"},		// Diamonds,
																			{"AS.png", "2S.png", "3S.png", "4S.png", "5S.png", "6S.png", "7S.png", "8S.png", "9S.png", "10S.png", "JS.png", "QS.png", "KS.png"},		// Spades,
																			{"AC.png", "2C.png", "3C.png", "4C.png", "5C.png", "6C.png", "7C.png", "8C.png", "9C.png", "10C.png", "JC.png", "QC.png", "KC.png"},		// Clubs,
																			{"AH.png", "2H.png", "3H.png", "4H.png", "5H.png", "6H.png", "7H.png", "8H.png", "9H.png", "10H.png", "JH.png", "QH.png", "KH.png"}			// Hearts
											   	   						};
	/*
	 * Constructor Method
	 * 
	 */
	
	public TableView(Game game, Dimension dimension)
	{
		// Initialize class variables
		
		this.game 					= game;
		tableDimension  			= dimension;
		gameInProgress 				= false;

		/*
		 * Setup the Table panel
		 *
		 */
		
		setPreferredSize(tableDimension);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		/*
		 * Scale the Table Panel and its components
		 * 
		 */
		
		// Calculate the percentage of the reference resolution represented by our current resolution
		
		percentOfReferenceWidth 				= (double)(tableDimension.width - REFERENCE_TABLE_DIMENSION_WIDTH) / (double)REFERENCE_TABLE_DIMENSION_WIDTH + 1;	
		percentOfReferenceHeight 				= (double)(tableDimension.height - REFERENCE_TABLE_DIMENSION_HEIGHT) / (double)REFERENCE_TABLE_DIMENSION_HEIGHT + 1;	
		
		// Scale the Vertical Table Panels
		
		verticalTablePanelWidth 				= tableDimension.width / VERTICAL_PANELS_HORIZONTALLY;
		verticalTablePanelHeight				= tableDimension.height / VERTICAL_PANELS_VERTICALLY;

		// Scale the Player Name TextFields and Struts
		
		int playerNameTextFieldWidth			= (int) (PLAYER_NAME_TEXTFIELD_WIDTH * percentOfReferenceWidth);
		int playerNameTextFieldHeight			= (int) (PLAYER_NAME_TEXTFIELD_HEIGHT * percentOfReferenceHeight);
		int playerNameHorizontalStrut 			= (verticalTablePanelWidth - playerNameTextFieldWidth) / 2;
		
		// Scale the Player Card Images
			
		playerCardImageWidth					= (int) (CARD_IMAGE_WIDTH * percentOfReferenceWidth);
		playerCardImageHeight					= (int) (CARD_IMAGE_HEIGHT * percentOfReferenceHeight);
		
		// Scale the Player Card Scroll Panes. Do not add the left and right borders of each Player Card Label to the width.
		// Add 1 pixel to the width to prevent the horizontal scrollbar from displaying with only 3 Cards.
		// Note that adding a temporary border to the Vertical Table Panels will throw off balance the Player Card Scroll Panel width.
		
		int	playerCardScrollPaneWidth 			= ((playerCardImageWidth) * PLAYER_CARDS_IN_SCROLLPANE) + 1;
		
		int	playerCardScrollPaneHeight 			= playerCardImageHeight + View.FRAME_HEIGHT;
		
		// Scale the Player Card Scroll Pane Struts
		
		int playerCardScrollPanePanelsStrut 	= (verticalTablePanelWidth - playerCardScrollPaneWidth) / 2;
		
		// Scale the Player Points Labels
		
		int playerPointsLabelWidth 				= (int) (PLAYER_POINTS_LABEL_WIDTH * percentOfReferenceWidth);
		int playerPointsLabelHeight 			= (int) (PLAYER_POINTS_LABEL_HEIGHT * percentOfReferenceHeight);
		
		// Scale the Player Scores Labels Horizontal Struts. Must subtract the width of Struts previously added!
		
		int playerScoresLabelsHorizontalStrut 	= (verticalTablePanelWidth - (playerNameHorizontalStrut * 2) - (playerCardScrollPanePanelsStrut * 2) - (playerPointsLabelWidth * 3)) / 2;
		
		// Scale the Vertical Table Panel vertical struts
		
		int verticalTablePanelStrut 			= (verticalTablePanelHeight - (playerNameTextFieldHeight + playerCardScrollPaneHeight + (playerPointsLabelHeight * 2))) / 3;
				
		// Scale the Card Deck and Stand Buttons
		
		int cardDeckButtonWidth					= (int) (CARD_BUTTON_WIDTH * percentOfReferenceWidth);		
		int cardDeckButtonHeight				= (int) (CARD_BUTTON_HEIGHT * percentOfReferenceHeight);
		
		// Scale the New Game Button
		
		int newGameButtonWidth					= (int) (NEW_GAME_BUTTON_WIDTH * percentOfReferenceWidth);	
		int newGameButtonHeight					= (int) (NEW_GAME_BUTTON_HEIGHT * percentOfReferenceHeight);	

		// Scale the Button Horizontal Panel Struts
		
		int buttonsHorizontalPanelStrut 		= (verticalTablePanelWidth - (cardDeckButtonWidth * 2)) / 3;

		// Scale the Buttons Vertical Panel Struts
		
		int buttonsVerticalPanelStrut			= (verticalTablePanelHeight - cardDeckButtonHeight - newGameButtonHeight) / 3;
		
		/*
		 * Display the Table image
		 * 
		 */

		// Step 1: create a URL to indicate where to find the image file and what its name and file type are.
		
		URL tableImageUrl = this.getClass().getResource(IMAGE_PATH + TABLE_IMAGE);
		
		try
		{
			// Step 2: try to read the image file. If found, read it and save its memory reference in the tableImage variable.
			
	 	    tableImage 	= ImageIO.read(tableImageUrl);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "System Error: unable to find the Board image file " + TABLE_IMAGE + ". Please reinstall the Game.",
										  "TableView: Constructor Method", JOptionPane.ERROR_MESSAGE);
			
			System.exit(ERROR);
		}
		
		// Step 3: scale the image file to the desired dimension.
 	    
 	    tableImage = Scalr.resize(tableImage, Scalr.Method.QUALITY, tableDimension.width, tableDimension.height, Scalr.OP_ANTIALIAS);
 	    
 	    // Step 4: the image will be displayed when the JPanel class calls our paintComponent method.
		
 	    /*
		 * Create the Horizontal and Vertical Table Panels
		 * 
		 */

		// Create the Horizontal and Vertical Table Panels
		
		JPanel[] horizontalTablePanels 	= new JPanel[HORIZONTAL_PANELS];
		JPanel[] verticalTablePanels 	= new JPanel[VERTICAL_PANELS];

		// Calculate the starting series of Vertical Table Panels
		
		int startingVerticalTablePanel	= 0;
		int endingVerticalTablePanel	= VERTICAL_PANELS_HORIZONTALLY;
		
		for (int horizontalIndex = 0; horizontalIndex < HORIZONTAL_PANELS; horizontalIndex++)
		{
			horizontalTablePanels[horizontalIndex] = new JPanel();
			horizontalTablePanels[horizontalIndex].setLayout(new BoxLayout(horizontalTablePanels[horizontalIndex], BoxLayout.X_AXIS));
			
			// Must set both for the background to be transparent

			horizontalTablePanels[horizontalIndex].setOpaque(false);
			horizontalTablePanels[horizontalIndex].setBackground(new Color(Color.TRANSLUCENT));
			
			// Add the horizontal panel to the Table panel
			
			add(horizontalTablePanels[horizontalIndex]);

			// Scale the Vertical Table Panels
			
			int	verticalTablePanelWidthRemainder	= tableDimension.width % VERTICAL_PANELS_HORIZONTALLY; 
			int verticalTablePanelHeightRemainder	= tableDimension.height % VERTICAL_PANELS_HORIZONTALLY;
			
			/*
			 * Create the Vertical Table Panels
			 * 
			 */
			
			for (int verticalIndex = startingVerticalTablePanel; verticalIndex < endingVerticalTablePanel; verticalIndex++)
			{
				verticalTablePanels[verticalIndex] = new JPanel();
				verticalTablePanels[verticalIndex].setLayout(new BoxLayout(verticalTablePanels[verticalIndex], BoxLayout.Y_AXIS));
				
				// Scale the Vertical Panels
				
				int verticalTablePanelAdditionalWidth 	= 0;
				int verticalTablePanelAdditionalHeight	= 0;
				
				if (verticalTablePanelWidthRemainder > 0)
				{
					verticalTablePanelAdditionalWidth++;
					
					verticalTablePanelWidthRemainder--;
				}
				
				if (verticalTablePanelHeightRemainder > 0)
				{
					verticalTablePanelAdditionalHeight++;
					
					verticalTablePanelHeightRemainder--;
				}
				
				// Scaled Vertical Table Panel
				
				verticalTablePanels[verticalIndex].setPreferredSize(new Dimension(verticalTablePanelWidth + verticalTablePanelAdditionalWidth, verticalTablePanelHeight + verticalTablePanelAdditionalHeight));
				
				// Must set both for the background to be transparent
				
				verticalTablePanels[verticalIndex].setOpaque(false);
				verticalTablePanels[verticalIndex].setBackground(new Color(Color.TRANSLUCENT));
				
				// FOR TEST PURPOSES ONLY
				
				if (TEMPORARY_BORDER)
				{
					verticalTablePanels[verticalIndex].setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
				}
			}
			
			// Calculate the next series of Vertical Table Panels
			
			startingVerticalTablePanel	= endingVerticalTablePanel;
			endingVerticalTablePanel	= VERTICAL_PANELS;
		}
		
		// Add the Vertical Table Panels to the Horizontal Table Panels
		
		horizontalTablePanels[0].add(verticalTablePanels[3]);		// Player 4
		horizontalTablePanels[0].add(verticalTablePanels[4]);		// Dealer		
		horizontalTablePanels[0].add(verticalTablePanels[0]);		// Player 1
		horizontalTablePanels[1].add(verticalTablePanels[2]);		// Player 3
		horizontalTablePanels[1].add(verticalTablePanels[5]);		// Buttons
		horizontalTablePanels[1].add(verticalTablePanels[1]);		// Player 2
		
		/*
		 *  Create the Player Name TextFields and the Player Name Horizontal Panels
		 *  
		 */

		// Creting Arrays:
		//
		// 1. Declare the array variable (class variable) (happens at edit and compile time)
		// 2. Create the array with "new" (happens at execution time)
		// 3. Fill the array with a for loop (happens at execution time)
		
		JPanel[] playerNameHorizontalPanels = new JPanel[Game.MAX_NUMBER_OF_PLAYERS];
		playerNameTextFields 				= new JTextField[Game.MAX_NUMBER_OF_PLAYERS];
		
		for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
		{
			playerNameTextFields[playerNumber] = new JTextField();
			
			// Scaled Player Name TextField
			
			playerNameTextFields[playerNumber].setPreferredSize(new Dimension(playerNameTextFieldWidth, playerNameTextFieldHeight));
			
			// Must set both for the background to be transparent

			playerNameTextFields[playerNumber].setOpaque(false);
			playerNameTextFields[playerNumber].setBackground(new Color(Color.TRANSLUCENT));

			playerNameTextFields[playerNumber].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.WHITE));
			
			playerNameTextFields[playerNumber].setHorizontalAlignment(JTextField.CENTER);
			playerNameTextFields[playerNumber].setFont(new Font(TEXT_FONT, Font.BOLD, TEXT_FONT_SIZE));
			playerNameTextFields[playerNumber].setForeground(PLAYER_NAME_TEXTFIELD_COLOR);
			playerNameTextFields[playerNumber].setName("Player" + playerNumber);
			playerNameTextFields[playerNumber].setText(INITIAL_PLAYER_NAME);
			playerNameTextFields[playerNumber].setToolTipText("Player " + (playerNumber + 1) + "'s Name");

			// Add Mouse and Keyboard Listeners
			
			PlayerNameTextFieldMouseListener playerNameTextFieldMouseListener = new PlayerNameTextFieldMouseListener();
			playerNameTextFields[playerNumber].addMouseListener(playerNameTextFieldMouseListener);
			
			PlayerNameTextFieldKeyboardListener playerNameTextFieldKeyboardListener = new PlayerNameTextFieldKeyboardListener();
			playerNameTextFields[playerNumber].addKeyListener(playerNameTextFieldKeyboardListener);
			
			// Create the Player Name Horizontal Panel
			
			playerNameHorizontalPanels[playerNumber] = new JPanel();
			playerNameHorizontalPanels[playerNumber].setLayout(new BoxLayout(playerNameHorizontalPanels[playerNumber], BoxLayout.X_AXIS));
			
			// Must set both for the background to be transparent

			playerNameHorizontalPanels[playerNumber].setOpaque(false);
			playerNameHorizontalPanels[playerNumber].setBackground(new Color(Color.TRANSLUCENT));
			
			// Add the Player Name TextField to the next Player Name Horizontal Panel. 
			// Add scaled Struts, rigid areas to frame the Player Name TextField.
			// Horizontal Struts must be added to a Box layout horizontal panel.

			int playerNameHorizontalStrutRemainder = (verticalTablePanelWidth - playerNameTextFieldHeight) % 2;
			
			playerNameHorizontalPanels[playerNumber].add(Box.createHorizontalStrut(playerNameHorizontalStrut + playerNameHorizontalStrutRemainder));
			
			playerNameHorizontalPanels[playerNumber].add(playerNameTextFields[playerNumber]);
			
			playerNameHorizontalPanels[playerNumber].add(Box.createHorizontalStrut(playerNameHorizontalStrut));
		}

		// Display the Dealer's Name and disable its Player Name TextField
		
		playerNameTextFields[DEALER_NAME_TEXTFIELD].setText(DEALER_NAME_TEXTFIELD_TEXT);
		playerNameTextFields[DEALER_NAME_TEXTFIELD].setDisabledTextColor(DEALER_NAME_COLOR);
		playerNameTextFields[DEALER_NAME_TEXTFIELD].setFont(new Font(DEALER_FONT, Font.BOLD, DEALER_FONT_SIZE));
		playerNameTextFields[DEALER_NAME_TEXTFIELD].setBorder(BorderFactory.createEmptyBorder());
		playerNameTextFields[DEALER_NAME_TEXTFIELD].setToolTipText(DEALER_NAME_TEXTFIELD_TEXT + "'s Name");
		playerNameTextFields[DEALER_NAME_TEXTFIELD].setEnabled(false);
		
		/*
		 *  Create the Card Back Image
		 *  
		 */

		// Step 1: create a URL to indicate where to find the image file and what its name and file type are.
		
		URL cardBackImageUrl = this.getClass().getResource(IMAGE_PATH + CARD_BACK_IMAGE);

		try
		{
			// Step 2: try to read the image file. If found, read it and save its memory reference in the cardBackImage variable.
			
			BufferedImage cardBackImage = ImageIO.read(cardBackImageUrl);
			
			// Step 3: scale the image file to the desired dimension.
			
			cardBackImage = Scalr.resize(cardBackImage, Scalr.Method.QUALITY, playerCardImageWidth, playerCardImageHeight, Scalr.OP_ANTIALIAS);
			
			// Step 4. save the scaled image as an ImageIcon
			
			cardBackImageIcon = new ImageIcon(cardBackImage);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "System Error: unable to find the Card image file " + 
												CARD_BACK_IMAGE + ". Please reinstall the Game.",
												"TableView: Constructor Method", JOptionPane.ERROR_MESSAGE);			
			System.exit(ERROR);
		}
			
		/*
		 *  Create the Player Card Panels and ScrollPanes
		 *  
		 */

		playerCardPanels 					= new JPanel[Game.MAX_NUMBER_OF_PLAYERS];
		
		JScrollPane[] playerCardScrollPanes = new JScrollPane[Game.MAX_NUMBER_OF_PLAYERS];
		
		JPanel[] playerCardScrollPanePanels	= new JPanel[Game.MAX_NUMBER_OF_PLAYERS];
		
		for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
		{
			// Create the Player Card Panel
			
	 	    playerCardPanels[playerNumber] = new JPanel();
	 	    playerCardPanels[playerNumber].setLayout(new BoxLayout(playerCardPanels[playerNumber], BoxLayout.X_AXIS));
			
			// Must set all three for the background to be transparent
			
			playerCardPanels[playerNumber].setOpaque(false);
			playerCardPanels[playerNumber].setBackground(new Color(Color.TRANSLUCENT));
			playerCardPanels[playerNumber].setBorder(null);
			
			// Create the Player Card ScrollPane
			
			playerCardScrollPanes[playerNumber] = new JScrollPane();
			playerCardScrollPanes[playerNumber].setViewportView(playerCardPanels[playerNumber]);

			// Scaled Player Card Scroll Pane
			
			playerCardScrollPanes[playerNumber].setPreferredSize(new Dimension(playerCardScrollPaneWidth, playerCardScrollPaneHeight));

			playerCardScrollPanes[playerNumber].getHorizontalScrollBar().setPreferredSize(new Dimension(playerCardScrollPaneWidth, 20));			
			playerCardScrollPanes[playerNumber].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			playerCardScrollPanes[playerNumber].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

			// Must set all four for the background to be transparent
			
			playerCardScrollPanes[playerNumber].setOpaque(false);
			playerCardScrollPanes[playerNumber].getViewport().setOpaque(false);			
			playerCardScrollPanes[playerNumber].setBackground(new Color(Color.TRANSLUCENT));
			
			// FOR TEST PURPOSES ONLY
			
			if (TEMPORARY_BORDER)
			{
				playerCardScrollPanes[playerNumber].setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
			}
			else
			{
				playerCardScrollPanes[playerNumber].setBorder(null);
			}
			
			// Create the Player Card ScrollPane Panel
			
			playerCardScrollPanePanels[playerNumber] = new JPanel();
			playerCardScrollPanePanels[playerNumber].setLayout(new BoxLayout(playerCardScrollPanePanels[playerNumber], BoxLayout.X_AXIS));
			
			// Must set all three for the background to be transparent
			
			playerCardScrollPanePanels[playerNumber].setOpaque(false);
			playerCardScrollPanePanels[playerNumber].setBackground(new Color(Color.TRANSLUCENT));
			
			// Add the Player Card Scroll Pane to the Player Card Scroll Pane Panel.
			// Add scaled Struts, rigid areas to frame the Player Card Scroll Pane.
			// Horizontal Struts must be added to a Box layout horizontal panel.

			int playerCardScrollPanePanelsStrutRemainder = (verticalTablePanelWidth - (playerNameHorizontalStrut * 2) - playerCardScrollPaneWidth) % 2;

			playerCardScrollPanePanels[playerNumber].add(Box.createHorizontalStrut(playerCardScrollPanePanelsStrut + playerCardScrollPanePanelsStrutRemainder));
			
			playerCardScrollPanePanels[playerNumber].add(playerCardScrollPanes[playerNumber]);
			
			playerCardScrollPanePanels[playerNumber].add(Box.createHorizontalStrut(playerCardScrollPanePanelsStrut));
		}
		
		/*
		 *  Create the Player Points Labels
		 *  
		 */
		
		playerPointsLabels = new JLabel[Game.MAX_NUMBER_OF_PLAYERS];
		
		for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
		{
			playerPointsLabels[playerNumber] = new JLabel();

			// Scaled Player Points Label
			
			playerPointsLabels[playerNumber].setPreferredSize(new Dimension(playerPointsLabelWidth, playerPointsLabelHeight));

			// Must set both for the background to be transparent

			playerPointsLabels[playerNumber].setOpaque(false);
			playerPointsLabels[playerNumber].setBackground(new Color(Color.TRANSLUCENT));
			
			// FOR TEST PURPOSES ONLY
			
			if (TEMPORARY_BORDER)
			{
				playerPointsLabels[playerNumber].setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
			}
			
			playerPointsLabels[playerNumber].setAlignmentX(Component.CENTER_ALIGNMENT);
			playerPointsLabels[playerNumber].setFont(new Font(TEXT_FONT, Font.BOLD, TEXT_FONT_SIZE));
			playerPointsLabels[playerNumber].setForeground(PLAYER_POINTS_COLOR);
			playerPointsLabels[playerNumber].setText(" 0 Points ");
		}
		
		/*
		 * Create the Player Scores Labels and the Player Scores Horizontal Panels
		 * 
		 */

		playerWinsLabels 						= new JLabel[Game.MAX_NUMBER_OF_PLAYERS];
		playerLossesLabels						= new JLabel[Game.MAX_NUMBER_OF_PLAYERS];
		playerTiesLabels						= new JLabel[Game.MAX_NUMBER_OF_PLAYERS];
		JPanel[] playerScoresHorizontalPanels 	= new JPanel[Game.MAX_NUMBER_OF_PLAYERS];
		
		for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
		{
			// Create a Player Scores Horizontal Panel
			
			playerScoresHorizontalPanels[playerNumber] = new JPanel();
			playerScoresHorizontalPanels[playerNumber].setLayout(new BoxLayout(playerScoresHorizontalPanels[playerNumber], BoxLayout.X_AXIS));
			
			// Must set both for the background to be transparent

			playerScoresHorizontalPanels[playerNumber].setOpaque(false);
			playerScoresHorizontalPanels[playerNumber].setBackground(new Color(Color.TRANSLUCENT));
			
			// Create the Player Wins Labels
			
			playerWinsLabels[playerNumber] = new JLabel();
			
			// Scaled Player Wins Label
			
			playerWinsLabels[playerNumber].setPreferredSize(new Dimension(playerPointsLabelWidth, playerPointsLabelHeight));
			
			// Must set both for the background to be transparent

			playerWinsLabels[playerNumber].setOpaque(false);
			playerWinsLabels[playerNumber].setBackground(new Color(Color.TRANSLUCENT));
			
			// FOR TEST PURPOSES ONLY
			
			if (TEMPORARY_BORDER)
			{
				playerWinsLabels[playerNumber].setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
			}
				
			playerWinsLabels[playerNumber].setAlignmentX(Component.CENTER_ALIGNMENT);
			playerWinsLabels[playerNumber].setFont(new Font(TEXT_FONT, Font.BOLD, PLAYER_STATS_FONT_SIZE));
			playerWinsLabels[playerNumber].setForeground(PLAYER_WINS_COLOR);
			playerWinsLabels[playerNumber].setText("Wins:   0");
			
			// Create the Player Loses Labels
			
			playerLossesLabels[playerNumber] = new JLabel();

			// Scaled Player Loses Label
			
			playerLossesLabels[playerNumber].setPreferredSize(new Dimension(playerPointsLabelWidth, playerPointsLabelHeight));
			
			// Must set both for the background to be transparent

			playerLossesLabels[playerNumber].setOpaque(false);
			playerLossesLabels[playerNumber].setBackground(new Color(Color.TRANSLUCENT));
			
			// FOR TEST PURPOSES ONLY
			
			if (TEMPORARY_BORDER)
			{
				playerLossesLabels[playerNumber].setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
			}
				
			playerLossesLabels[playerNumber].setAlignmentX(Component.CENTER_ALIGNMENT);
			playerLossesLabels[playerNumber].setFont(new Font(TEXT_FONT, Font.BOLD, PLAYER_STATS_FONT_SIZE));
			playerLossesLabels[playerNumber].setForeground(PLAYER_LOSES_COLOR);
			playerLossesLabels[playerNumber].setText("Loses:   0");
			
			// Create the Player Ties Labels
			
			playerTiesLabels[playerNumber] = new JLabel();
			
			// Scaled Player Ties Label
			
			playerTiesLabels[playerNumber].setPreferredSize(new Dimension(playerPointsLabelWidth, playerPointsLabelHeight));
			
			// Must set both for the background to be transparent
	
			playerTiesLabels[playerNumber].setOpaque(false);
			playerTiesLabels[playerNumber].setBackground(new Color(Color.TRANSLUCENT));
			
			// FOR TEST PURPOSES ONLY
			
			if (TEMPORARY_BORDER)
			{
				playerTiesLabels[playerNumber].setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
			}
			
			playerTiesLabels[playerNumber].setAlignmentX(Component.CENTER_ALIGNMENT);
			playerTiesLabels[playerNumber].setFont(new Font(TEXT_FONT, Font.BOLD, PLAYER_STATS_FONT_SIZE));
			playerTiesLabels[playerNumber].setForeground(PLAYER_TIES_COLOR);
			playerTiesLabels[playerNumber].setText("Ties:   0");
			
			// Add the Player Scores Labels to the next Player Scores Horizontal Panel.
			// Add scaled Struts, rigid areas to frame the Player Scores Labels.
			// Horizontal Struts must be added to a Box layout horizontal panel.
		
			playerScoresHorizontalPanels[playerNumber].add(playerWinsLabels[playerNumber]);

			// At most 1 pixel left over
			
			int playerScoresLabelsStrutRemainder = (verticalTablePanelWidth - (playerPointsLabelWidth * 3)) % 2;
			
			playerScoresHorizontalPanels[playerNumber].add(Box.createHorizontalStrut(playerScoresLabelsHorizontalStrut + playerScoresLabelsStrutRemainder));

			playerScoresHorizontalPanels[playerNumber].add(playerLossesLabels[playerNumber]);
			
			playerScoresHorizontalPanels[playerNumber].add(Box.createHorizontalStrut(playerScoresLabelsHorizontalStrut));

			playerScoresHorizontalPanels[playerNumber].add(playerTiesLabels[playerNumber]);			
		}
		
		/*
 	     *  Add the Player Name Horizontal Panels, the Player Card Scroll Pane Panels, the Player Points Labels
 	     *  and the Player Scores Horizontal Panels to the Player Vertical Panels
 	     *  
 	     */

		for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
		{
			int verticalTablePanelStrutRemainder = (verticalTablePanelHeight - (playerNameTextFieldHeight + playerCardScrollPaneHeight + (playerPointsLabelHeight * 2))) % 3;

			// Add a vertical strut
			
			int verticalTablePanelStrutAdditionalHeight	= 0;

			if (verticalTablePanelStrutRemainder > 0)
			{
				verticalTablePanelStrutAdditionalHeight = 1;
				
				verticalTablePanelStrutRemainder--;
			}
			
			verticalTablePanels[playerNumber].add(Box.createVerticalStrut(verticalTablePanelStrut + verticalTablePanelStrutAdditionalHeight));

			// Add Player Name Horizontal Panel
			
	    	verticalTablePanels[playerNumber].add(playerNameHorizontalPanels[playerNumber]);
	    	
	    	// Add a vertical strut
			
			verticalTablePanelStrutAdditionalHeight	= 0;

			if (verticalTablePanelStrutRemainder > 0)
			{
				verticalTablePanelStrutAdditionalHeight = 1;
				
				verticalTablePanelStrutRemainder--;
			}

			verticalTablePanels[playerNumber].add(Box.createVerticalStrut(verticalTablePanelStrut + verticalTablePanelStrutAdditionalHeight));

	    	// Add Player Card Scroll Pane Panel
	    	
	 	    verticalTablePanels[playerNumber].add(playerCardScrollPanePanels[playerNumber], JPanel.CENTER_ALIGNMENT);

	 	    // Add Player Points Labels
	 	    
	 	    verticalTablePanels[playerNumber].add(playerPointsLabels[playerNumber], JPanel.CENTER_ALIGNMENT);

	 	    // Add vertical strut
	 	    
	 	    verticalTablePanelStrutAdditionalHeight	= 0;

			if (verticalTablePanelStrutRemainder > 0)
			{
				verticalTablePanelStrutAdditionalHeight = 1;
				
				verticalTablePanelStrutRemainder--;
			}

	 	    verticalTablePanels[playerNumber].add(Box.createVerticalStrut(verticalTablePanelStrut + verticalTablePanelStrutAdditionalHeight));

	 	    // Add Player Scores Horizontal Panel
	 	    
	 	    verticalTablePanels[playerNumber].add(playerScoresHorizontalPanels[playerNumber], JPanel.CENTER_ALIGNMENT);
		}
		
		/*
		 * Create the Card Deck, Stand and New Game Buttons
		 * 
		 */

		// Create the Card Deck Button
		
		cardDeckButton = new JButton();

		// Scaled Card Deck Button
		
		cardDeckButton.setPreferredSize(new Dimension(cardDeckButtonWidth, cardDeckButtonHeight));
		
		cardDeckButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		cardDeckButton.setToolTipText("Hit Me!");

		try
		{
			BufferedImage cardDeckImage = ImageIO.read(this.getClass().getResource(IMAGE_PATH + CARD_DECK_IMAGE));

			// Scale Card Deck Image
			
			cardDeckImage = Scalr.resize(cardDeckImage, Scalr.Method.QUALITY, cardDeckButtonWidth, cardDeckButtonHeight, Scalr.OP_ANTIALIAS);
			
			cardDeckButton.setIcon(new ImageIcon(cardDeckImage));
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "System Error: unable to find the Card image file " + 
												CARD_DECK_IMAGE + ". Please reinstall the Game.",
												"TableView: Constructor Method", JOptionPane.ERROR_MESSAGE);			
			System.exit(ERROR);
		}
						
		// Remove the colored border with setContentAreaFilled(false) (leaves a transparent focus area).
		// Remove the transparent focus area with setFocusPainted(false) and setBorder(BorderFactory.createEmptyBorder()).

		cardDeckButton.setContentAreaFilled(false);
		cardDeckButton.setFocusPainted(false);
		cardDeckButton.setBorder(BorderFactory.createEmptyBorder());
		
		// Add Listeners. FocusListener does not work with JButton, it wont be triggered. Use MouseListener instead.
		
		CardDeckButtonActionListener cardDeckButtonActionListener = new CardDeckButtonActionListener();
		cardDeckButton.addActionListener(cardDeckButtonActionListener);
		CardDeckButtonMouseListener cardDeckButtonMouseListener = new CardDeckButtonMouseListener();
		cardDeckButton.addMouseListener(cardDeckButtonMouseListener);

		// Create the Stand Button
		
		standButton = new JButton();

		// Scaled Stand Button
		
		standButton.setPreferredSize(new Dimension(cardDeckButtonWidth, cardDeckButtonHeight));
		
		standButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		standButton.setToolTipText("Stand!");

		try
		{
			BufferedImage stayButtonImage = ImageIO.read(this.getClass().getResource(IMAGE_PATH + STAND_BUTTON_IMAGE));

			// Scale Card Deck Image
			
			stayButtonImage = Scalr.resize(stayButtonImage, Scalr.Method.QUALITY, cardDeckButtonWidth, cardDeckButtonHeight, Scalr.OP_ANTIALIAS);
			
			standButton.setIcon(new ImageIcon(stayButtonImage));
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "System Error: unable to find the Card image file " + 
												STAND_BUTTON_IMAGE + ". Please reinstall the Game.",
												"TableView: Constructor Method", JOptionPane.ERROR_MESSAGE);			
			System.exit(ERROR);
		}
						
		// Remove the colored border with setContentAreaFilled(false) (leaves a transparent focus area).
		// Remove the transparent focus area with setFocusPainted(false) and setBorder(BorderFactory.createEmptyBorder()).

		standButton.setContentAreaFilled(false);
		standButton.setFocusPainted(false);
		standButton.setBorder(BorderFactory.createEmptyBorder());
		
		// Add Listeners. FocusListener does not work with JButton, it wont be triggered. Use MouseListener instead.
		
		StandButtonActionListener stayButtonActionListener = new StandButtonActionListener();
		standButton.addActionListener(stayButtonActionListener);
		StandButtonMouseListener standButtonMouseListener = new StandButtonMouseListener();
		standButton.addMouseListener(standButtonMouseListener);
		
		// Create the Buttons Horizontal Panel
		
		JPanel buttonsHorizontalPanel = new JPanel();
		buttonsHorizontalPanel.setLayout(new BoxLayout(buttonsHorizontalPanel, BoxLayout.X_AXIS));
		
		// Scaled Buttons Horizontal Panel
		
		buttonsHorizontalPanel.setPreferredSize(new Dimension(verticalTablePanelWidth, cardDeckButtonHeight));
		
		// Must set both for the background to be transparent

		buttonsHorizontalPanel.setOpaque(false);
		buttonsHorizontalPanel.setBackground(new Color(Color.TRANSLUCENT));
		
		// Add the Card Deck and Stand Buttons to the Buttons Horizontal Panel. Add Struts, rigid areas to frame the Hit Me Label.
		// Horizontal Struts must be added to a Box layout horizontal panel.
	
		buttonsHorizontalPanel.add(Box.createHorizontalStrut(buttonsHorizontalPanelStrut));
		buttonsHorizontalPanel.add(cardDeckButton);
		buttonsHorizontalPanel.add(Box.createHorizontalStrut(buttonsHorizontalPanelStrut));
		buttonsHorizontalPanel.add(standButton);
		buttonsHorizontalPanel.add(Box.createHorizontalStrut(buttonsHorizontalPanelStrut));
		
		/*
		 * Create New Game - Start Game Button
		 * 
		 */

		newGameButton = new JButton();

		// Scaled New Game Button
		
		newGameButton.setPreferredSize(new Dimension(newGameButtonWidth, newGameButtonHeight));
		
		newGameButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.WHITE));		
		newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGameButton.setFont(new Font(TEXT_FONT, Font.BOLD, TEXT_FONT_SIZE));
		newGameButton.setBackground(START_GAME_BUTTON_COLOR);
		newGameButton.setForeground(Color.WHITE);
		newGameButton.setText(START_GAME_BUTTON_TEXT);
		newGameButton.setToolTipText(START_GAME_BUTTON_TOOLTIP_TEXT);

		// Add Action Listener
		
		newGameButtonActionListener = new StartGameButtonActionListener();
		newGameButton.addActionListener(newGameButtonActionListener);
		
		/*
		 *  Add the Buttons Horizontal Panel and New Game Buttons to the Vertical Panel
		 *  
		 */

		int buttonsVerticalPanelStrutRemainder = (verticalTablePanelHeight - cardDeckButtonHeight - newGameButtonHeight) % 3;
		
		int buttonsVerticalPanelStrutAdditionalHeight = 0;
		
		if (buttonsVerticalPanelStrutRemainder > 0)
		{
			buttonsVerticalPanelStrutAdditionalHeight = 1;
			buttonsVerticalPanelStrutRemainder--;
		}
		
 	    verticalTablePanels[5].add(Box.createVerticalStrut(buttonsVerticalPanelStrut + buttonsVerticalPanelStrutAdditionalHeight));

 	    verticalTablePanels[5].add(buttonsHorizontalPanel);

		buttonsVerticalPanelStrutAdditionalHeight = 0;
		
		if (buttonsVerticalPanelStrutRemainder > 0)
		{
			buttonsVerticalPanelStrutAdditionalHeight = 1;
			
			buttonsVerticalPanelStrutRemainder--;
		}
		
		verticalTablePanels[5].add(Box.createVerticalStrut(buttonsVerticalPanelStrut + buttonsVerticalPanelStrutAdditionalHeight));

		verticalTablePanels[5].add(newGameButton);
		
		verticalTablePanels[5].add(Box.createVerticalStrut(buttonsVerticalPanelStrut));
		
		// FOR TEST PURPOSES ONLY
		
		if (TEMPORARY_BORDER)
		{
			verticalTablePanels[5].setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		}
		
	}	// End of constructor method
	
	/*
	 * paintComponent Method
	 * 
	 * Called by JPanel
	 * 
	 */
	
	@Override
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	
    	g.drawImage(tableImage, 0, 0, getWidth(), getHeight(), this);
    }

	/*
	 * DisplayCard Metthod
	 * 
	 */
	
	private void displayCard(Card card, int playerNumber)
	{
		ImageIcon imageIcon = new ImageIcon();
		
		// Display the Card Back image if this is a blank Card
		
		if (card.isValid())
		{
			// Create a URL of the Card Image
			
			URL cardImageUrl = this.getClass().getResource(IMAGE_PATH + CARD_IMAGES[card.getSuit() - 1][card.getNumber() - 1]);
	
			// Load the Card image into an image variable
	
			BufferedImage cardImage	= null;
			
			try
			{
				cardImage = ImageIO.read(cardImageUrl);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "System Error: unable to find the Card image file " + 
													CARD_IMAGES[card.getSuit() - 1][card.getNumber() - 1] +
													". Please reinstall the Game.",
													"CardDeckButtonActionListener: Action Perfomed Method", JOptionPane.ERROR_MESSAGE);			
				System.exit(ERROR);
			}
			
			// Scale the image
			
			cardImage = Scalr.resize(cardImage, Scalr.Method.QUALITY, playerCardImageWidth, playerCardImageHeight, Scalr.OP_ANTIALIAS);
			
			// Save the scaled image as an ImageIcon
			
			imageIcon.setImage(cardImage);
		}
		else
		{
			// Use the Card Back Image instead
			
			imageIcon = cardBackImageIcon;
		}
		
		// Create a Player Card Label and add the scaled image to the Player Card Label as an ImageIcon
	
		JLabel playerCardLabel = new JLabel();
		playerCardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);		 	    

		// Scaled Player Card Label
		
		playerCardLabel.setPreferredSize(new Dimension(playerCardImageWidth, playerCardImageHeight));
		
		// Add a 4-pixel border left border and a 2-pixel right border allow the Scroll Pane to show precisely the PLAYER_CARD_SCROLLPANE_WIDTH number of Player Card Image Labels.
		// Top-Left-Bottom-Right.
		
		playerCardLabel.setBorder(BorderFactory.createEmptyBorder(0,PLAYER_CARD_LABEL_LEFT_BORDER,0,PLAYER_CARD_LABEL_RIGHT_BORDER));
		
		// Add the Card Image as an ImageIcon
		
		playerCardLabel.setIcon(imageIcon);
		
		// Must revalidate and repaint to display images
		
		playerCardLabel.revalidate();
		playerCardLabel.repaint();

		// Add the Player Card Label to the Player Card Panel
		
		playerCardPanels[playerNumber - 1].add(playerCardLabel);

		// Must revalidate and repaint to display images
		
		playerCardPanels[playerNumber - 1].revalidate();
		playerCardPanels[playerNumber - 1].repaint();
	}
	
	/*
	 * DisplayDealerCardInTheHole Metthod
	 * 
	 */
	
	private void displayDealerCardInTheHole()
	{
		// Remove the Dealer's second Card (Card Image Back)
		
		playerCardPanels[Game.MAX_NUMBER_OF_PLAYERS - 1].remove(1);
	
		// Get the Dealer's second Card
			
		Card card = game.getPlayerHand(Game.MAX_NUMBER_OF_PLAYERS, 2);
		
		ImageIcon imageIcon = new ImageIcon();
			
		// Check this is not a blank Card
		
		if (card.isValid())
		{
			// Create a URL of the Card Image
			
			URL cardImageUrl = this.getClass().getResource(IMAGE_PATH + CARD_IMAGES[card.getSuit() - 1][card.getNumber() - 1]);
	
			// Load the Card image into an image variable
	
			BufferedImage cardImage	= null;
			
			try
			{
				cardImage = ImageIO.read(cardImageUrl);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "System Error: unable to find the Card image file " + 
													CARD_IMAGES[card.getSuit() - 1][card.getNumber() - 1] +
													". Please reinstall the Game.",
													"CardDeckButtonActionListener: Action Perfomed Method", JOptionPane.ERROR_MESSAGE);			
				System.exit(ERROR);
			}
			
			// Scale the image
			
			cardImage = Scalr.resize(cardImage, Scalr.Method.QUALITY, playerCardImageWidth, playerCardImageHeight, Scalr.OP_ANTIALIAS);
			
			// Save the scaled image as an ImageIcon
			
			imageIcon.setImage(cardImage);
		}
		
		// Create a Player Card Label and add the scaled image to the Player Card Label as an ImageIcon
	
		JLabel playerCardLabel = new JLabel();
		playerCardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);		 	    

		// Scaled Player Card Label
		
		playerCardLabel.setPreferredSize(new Dimension(playerCardImageWidth, playerCardImageHeight));
		
		// Add a 4-pixel border left border and a 2-pixel right border allow the Scroll Pane to show precisely the PLAYER_CARD_SCROLLPANE_WIDTH number of Player Card Image Labels.
		// Top-Left-Bottom-Right.
		
		playerCardLabel.setBorder(BorderFactory.createEmptyBorder(0,PLAYER_CARD_LABEL_LEFT_BORDER,0,PLAYER_CARD_LABEL_RIGHT_BORDER));
		
		// Add the Card Image as an ImageIcon
		
		playerCardLabel.setIcon(imageIcon);
		
		// Must revalidate and repaint to display images
		
		playerCardLabel.revalidate();
		playerCardLabel.repaint();

		// Display the Dealer's second card
		
		playerCardPanels[Game.MAX_NUMBER_OF_PLAYERS - 1].add(playerCardLabel);
		
		// Must revalidate and repaint to display images
		
		playerCardPanels[Game.MAX_NUMBER_OF_PLAYERS - 1].revalidate();
		playerCardPanels[Game.MAX_NUMBER_OF_PLAYERS - 1].repaint();
	}

	private class ScrollablePanel extends JPanel implements Scrollable
	{
	    public Dimension getPreferredScrollableViewportSize()
	    {
	        return getPreferredSize();
	    }

	    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
	    {
	       return 10;
	    }

	    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
	    {
	        return ((orientation == SwingConstants.VERTICAL) ? visibleRect.height : visibleRect.width) - 10;
	    }

	    public boolean getScrollableTracksViewportWidth()
	    {
	        return true;
	    }

	    public boolean getScrollableTracksViewportHeight()
	    {
	        return false;
	    }
	}
	
	/*
	 * PlayerNameTextField Mouse Listener Class
	 * 
	 */
	
	private class PlayerNameTextFieldMouseListener implements MouseListener
    {
		@Override
        public void mouseEntered(MouseEvent event)
        {
			// Ignore it if a Game is in progress
			
			if (!gameInProgress)
			{
				// Get a copy of the Player Name TextField clicked
				
				JTextField playerNameTextField = (JTextField) event.getSource();

				// Ignore it if the TextField is disabled
				
				if (playerNameTextField.isEnabled())
				{
					// Get the Player Name TextField index
				
					int index = Integer.valueOf(playerNameTextField.getName().substring(playerNameTextField.getName().length() - 1, playerNameTextField.getName().length()));
				
					// Save the Player Name
					
					if (playerNameTextFields[index].getText().length() > 0)
					{
						savePlayerName = playerNameTextFields[index].getText();
					}
					
					// Clear the Player Name TextField
				
					playerNameTextFields[index].setText("");
					
					// Enable the Player Name TextField for user input and give it focus to allow the user to begin typing
					
					playerNameTextFields[index].setEnabled(true);
					playerNameTextFields[index].requestFocus(true);
				}
			}
        }
		
		@Override
        public void mouseExited(MouseEvent event)
        {
			// Ignore it if a Game is in progress
			
			if (!gameInProgress)
			{
				// Get a copy of the Player Name TextField clicked
				
				JTextField playerNameTextField = (JTextField) event.getSource();
	
				// Ignore it if the TextField is disabled
				
				if (playerNameTextField.isEnabled())
				{			
					// Get the Player Name TextField index
				
					int index = Integer.valueOf(playerNameTextField.getName().substring(playerNameTextField.getName().length() - 1, playerNameTextField.getName().length()));
		
					// Restore the last Player Name
					
					if (playerNameTextFields[index].getText().length() == 0)
					{
						playerNameTextFields[index].setText(savePlayerName);
					}
				}
			}
        }
		
		@Override
		public void mouseClicked(MouseEvent event)
		{
			
		}

		@Override
        public void mouseReleased(MouseEvent event)
        {
			
        }
		
		@Override
        public void mousePressed(MouseEvent event)
        {
			
        } 
    }
	
	/*
	 * PlayerNameTextField Mouse Listener Class
	 * 
	 */
	
	private class PlayerNameTextFieldKeyboardListener implements KeyListener
    {

		@Override
		public void keyTyped(KeyEvent event)
		{
			
		}

		@Override
		public void keyPressed(KeyEvent event)
		{
			// Get a copy of the Player Name TextField clicked
			
			JTextField playerNameTextField = (JTextField) event.getSource();

			// Get the current length of the Player Name TextField
			
			if (playerNameTextField.getText().length() > MAX_PLAYER_NAME_LENGTH)
			{
				// Remove the extra character
				
				playerNameTextField.setText(playerNameTextField.getText().substring(0, playerNameTextField.getText().length() - 1));
			}
		}

		@Override
		public void keyReleased(KeyEvent event)
		{
			// Get a copy of the Player Name TextField clicked
			
			JTextField playerNameTextField = (JTextField) event.getSource();

			// Get the current length of the Player Name TextField
			
			if (playerNameTextField.getText().length() > MAX_PLAYER_NAME_LENGTH)
			{
				// Remove the extra character
				
				playerNameTextField.setText(playerNameTextField.getText().substring(0, playerNameTextField.getText().length() - 1));
			}
		}
    }
	
	/*
	 * Card Deck Button Action Listener
	 * 
	 */
	
	private class CardDeckButtonActionListener implements ActionListener
    {
		/*
		 * actionPerformed Method
		 * 
		 */
		
		@Override
        public void actionPerformed( ActionEvent event )
        {
			// Ignore it if a Game is not in progress. Disabling this button until a Game begins causes the button image to be blurred.
			
			if (gameInProgress)
			{
				// Get the current player number
				
				int currentPlayerNumber = game.getCurrentPlayer();
				
				// If the current Player is an AI Player

				if (game.getPlayerType(game.getCurrentPlayer()) == Player.AI_PLAYER)
				{
					// Let the current AI Player and all AI Players that inmediately follow it play
					
					while (game.getPlayerType(game.getCurrentPlayer()) == Player.AI_PLAYER && !game.isGameOver())
					{
						// Get the current player number
						
						currentPlayerNumber = game.getCurrentPlayer();
						
						// Let the current AI Player play
						
						while(game.getPlayerStatus(currentPlayerNumber) == Player.PLAYING)
						{
							// Tell the current AI Player to play
							
							game.play();
							
							// Display the Card(s) drawn. Ignore a blank Card when the AI Player stands without drawing a Card.
							
							for (int cardNumber = 3; cardNumber <= game.getPlayerCardsOnHand(currentPlayerNumber); cardNumber++)
							{
								Card card = game.getPlayerHand(currentPlayerNumber, cardNumber);
								
								if (card.isValid())
								{
									displayCard(card, currentPlayerNumber);
								}
							}
							
							// Get the Player Points. Chekc for Blackjack.
							
							if (game.getPlayerPoints(currentPlayerNumber) == 21 && game.getPlayerCardsOnHand(currentPlayerNumber) == 2)
							{
								playerPointsLabels[currentPlayerNumber - 1].setForeground(BLACKJACK_COLOR);
								playerPointsLabels[currentPlayerNumber - 1].setText(BLACKJACK_TEXT);
							}
							else
							{
								playerPointsLabels[currentPlayerNumber - 1].setForeground(PLAYER_POINTS_COLOR);
								playerPointsLabels[currentPlayerNumber - 1].setText("" + game.getPlayerPoints(currentPlayerNumber) + " Points");
							}
							
							// Must revalidate and repaint to change colors
								
							playerPointsLabels[currentPlayerNumber - 1].revalidate();
							playerPointsLabels[currentPlayerNumber - 1].repaint();
							
							// Check if the current Player stood
							
							if (game.getPlayerStatus(currentPlayerNumber) == Player.STOOD)
							{
								// Must setDisabledTextColor in order to change its foreground color.
								// Cannot use setForeground, TextField must be enabled and stay enabled!
								
								playerNameTextFields[currentPlayerNumber - 1].setDisabledTextColor(PLAYER_STOOD_COLOR);						
								playerNameTextFields[currentPlayerNumber - 1].setText(game.getPlayerName(currentPlayerNumber) + PLAYER_STOOD_TEXT);					
							}
							
							// Check if the current Player has busted
							
							else if (game.getPlayerStatus(currentPlayerNumber) == Player.BUSTED)
							{
								// Must setDisabledTextColor in order to change its foreground color.
								// Cannot use setForeground, TextField must be enabled and stay enabled!
								
								playerNameTextFields[currentPlayerNumber - 1].setDisabledTextColor(PLAYER_BUSTED_COLOR);
								playerNameTextFields[currentPlayerNumber - 1].setText(game.getPlayerName(currentPlayerNumber) + PLAYER_BUSTED_TEXT);
							}
							else
							{
								// Remove the previous Player's hightlight
								
								playerNameTextFields[currentPlayerNumber - 1].setDisabledTextColor(PLAYER_NAME_TEXTFIELD_COLOR);					
							}

							// Must revalidate and repaint to change colors
							
							playerNameTextFields[currentPlayerNumber - 1].revalidate();
							playerNameTextFields[currentPlayerNumber - 1].repaint();
						}
					}
				}
				else
				{
					// Tell the current Human Player to draw a Card
					
					Card card = game.play();
					
					// Display the Card drawn
					
					displayCard(card, currentPlayerNumber);
					
					// Get the Player Points. Chekc for Blackjack.
					
					if (game.getPlayerPoints(currentPlayerNumber) == 21 && game.getPlayerCardsOnHand(currentPlayerNumber) == 2)
					{
						playerPointsLabels[currentPlayerNumber - 1].setForeground(BLACKJACK_COLOR);
						playerPointsLabels[currentPlayerNumber - 1].setText(BLACKJACK_TEXT);
					}
					else
					{
						playerPointsLabels[currentPlayerNumber - 1].setForeground(PLAYER_POINTS_COLOR);
						playerPointsLabels[currentPlayerNumber - 1].setText("" + game.getPlayerPoints(currentPlayerNumber) + " Points");
					}

					// Must revalidate and repaint to change colors
						
					playerPointsLabels[currentPlayerNumber - 1].revalidate();
					playerPointsLabels[currentPlayerNumber - 1].repaint();
					
					// Check if the current Player stood
					
					if (game.getPlayerStatus(currentPlayerNumber) == Player.STOOD)
					{
						// Must setDisabledTextColor in order to change its foreground color.
						// Cannot use setForeground, TextField must be enabled and stay enabled!
						
						playerNameTextFields[currentPlayerNumber - 1].setDisabledTextColor(PLAYER_STOOD_COLOR);						
						playerNameTextFields[currentPlayerNumber - 1].setText(game.getPlayerName(currentPlayerNumber) + PLAYER_STOOD_TEXT);
					}
					
					// Check if the current Player has busted
					
					else if (game.getPlayerStatus(currentPlayerNumber) == Player.BUSTED)
					{
						// Must setDisabledTextColor in order to change its foreground color.
						// Cannot use setForeground, TextField must be enabled and stay enabled!
						
						playerNameTextFields[currentPlayerNumber - 1].setDisabledTextColor(PLAYER_BUSTED_COLOR);
						playerNameTextFields[currentPlayerNumber - 1].setText(game.getPlayerName(currentPlayerNumber) + PLAYER_BUSTED_TEXT);
					}
					else
					{
						// Remove the previous Player's hightlight
						
						playerNameTextFields[currentPlayerNumber - 1].setDisabledTextColor(PLAYER_NAME_TEXTFIELD_COLOR);					
					}

					// Must revalidate and repaint to change colors
					
					playerNameTextFields[currentPlayerNumber - 1].revalidate();
					playerNameTextFields[currentPlayerNumber - 1].repaint();		
				}
				
				/*
				 *  Check if the current Game is over
				 *  
				 */
				
				if (game.isGameOver())
				{
					// Display the Dealer's face down Card (the second Card, the Card "in the hole")
					
					displayDealerCardInTheHole();
					
					// Update the Players wins, loses and ties
					
					for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
					{
						playerWinsLabels[playerNumber].setText("Wins:   " + game.getPlayerWins(playerNumber + 1));
						
						// Must revalidate and repaint to change colors
						
						playerWinsLabels[playerNumber].revalidate();
						playerWinsLabels[playerNumber].repaint();
						
						playerLossesLabels[playerNumber].setText("Loses:   " + game.getPlayerLoses(playerNumber + 1));
						
						// Must revalidate and repaint to change colors
						
						playerLossesLabels[playerNumber].revalidate();
						playerLossesLabels[playerNumber].repaint();
						
						playerTiesLabels[playerNumber].setText("Ties:   " + game.getPlayerTies(playerNumber + 1));
						
						// Must revalidate and repaint to change colors
						
						playerTiesLabels[playerNumber].revalidate();
						playerTiesLabels[playerNumber].repaint();
					}
					
					// Indicate the current Game is over
					
					gameInProgress = false;
					
					// Change the Start Game Button to the New Game Button
					
					newGameButton.setBackground(NEW_GAME_BUTTON_COLOR);
					newGameButton.setText(NEW_GAME_BUTTON_TEXT);
					newGameButton.setToolTipText(NEW_GAME_BUTTON_TOOLTIP_TEXT);
					
					// Remove Start Game Action Listener
					
					newGameButton.removeActionListener(newGameButtonActionListener);
					
					// Add Action Listener
					
					newGameButtonActionListener = new NewGameButtonActionListener();
					newGameButton.addActionListener(newGameButtonActionListener);
				}
				else
				{										
					// Hightlight the next Player's turn

					playerNameTextFields[game.getCurrentPlayer() - 1].setDisabledTextColor(NEXT_PLAYERS_TURN);
					
					// Must revalidate and repaint to change colors
					
					playerNameTextFields[game.getCurrentPlayer() - 1].revalidate();
					playerNameTextFields[game.getCurrentPlayer() - 1].repaint();
					
					// If the next Player is an AI Player, programmatically click the Card Deck button
					
					if (game.getPlayerType(game.getCurrentPlayer()) == Player.AI_PLAYER)
					{
						cardDeckButton.doClick();
					}
				}				
			}
			
        }	// End of actionPerformed method
		
    }	// End of CardDeckButtonActionListener class
	
	/*
	 * Card Deck Button Mouse Listener
	 * 
	 */
	
	private class CardDeckButtonMouseListener implements MouseListener
    {
		@Override
		public void mouseEntered(MouseEvent e)
		{
			if (gameInProgress)
			{
				// Display the button's transparent focus area
				
				cardDeckButton.setFocusPainted(true);
				cardDeckButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));
			}						
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			if (gameInProgress)
			{
				// Hide the button's transparent focus area
				
				cardDeckButton.setFocusPainted(false);
				cardDeckButton.setBorder(BorderFactory.createEmptyBorder());
			}
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			
		}
		
    }	// End of CardDeckButtonMouseListener class
	
	/*
	 * Stand Button Action Listener
	 * 
	 */
	
	private class StandButtonActionListener implements ActionListener
    {
		/*
		 * actionPerformed Method
		 * 
		 */
		
		@Override
        public void actionPerformed( ActionEvent event )
        {
			// Ignore if a Game is not in progress. Disabling this button until a Game begins causes the button image to be blurred.
			
			if (gameInProgress)
			{
				// Get the current player number
				
				int currentPlayerNumber = game.getCurrentPlayer();
				
				// Tell the current Player to stand
				
				game.stand();
				
				// Check if the current Game is over
				
				if (game.isGameOver())
				{
					// Update the Players wins, loses and ties
					
					for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
					{
						playerWinsLabels[playerNumber].setText("Wins:   " + game.getPlayerWins(playerNumber + 1));
						playerLossesLabels[playerNumber].setText("Loses:   " + game.getPlayerLoses(playerNumber + 1));
						playerWinsLabels[playerNumber].setText("Ties:   " + game.getPlayerTies(playerNumber + 1));
					}
					
					// Indicate the current Game is over
					
					gameInProgress = false;
					
					// Change the Start Game Button to the New Game Button
					
					newGameButton.setBackground(NEW_GAME_BUTTON_COLOR);
					newGameButton.setText(NEW_GAME_BUTTON_TEXT);
					newGameButton.setToolTipText(NEW_GAME_BUTTON_TOOLTIP_TEXT);
					
					// Remove Start Game Action Listener
					
					newGameButton.removeActionListener(newGameButtonActionListener);
					
					// Add Action Listener
					
					newGameButtonActionListener = new NewGameButtonActionListener();
					newGameButton.addActionListener(newGameButtonActionListener);
				}
				else
				{
					// Indicate the Player has stood
					
					playerNameTextFields[currentPlayerNumber - 1].setDisabledTextColor(PLAYER_STOOD_COLOR);
					playerNameTextFields[currentPlayerNumber - 1].setText(game.getPlayerName(currentPlayerNumber) + PLAYER_STOOD_TEXT);
					
					// Must revalidate and repaint to change colors
					
					playerNameTextFields[currentPlayerNumber - 1].revalidate();
					playerNameTextFields[currentPlayerNumber - 1].repaint();
					
					// Hightlight the next Player's turn

					playerNameTextFields[game.getCurrentPlayer() - 1].setDisabledTextColor(NEXT_PLAYERS_TURN);
					
					// Must revalidate and repaint to change colors
					
					playerNameTextFields[game.getCurrentPlayer() - 1].revalidate();
					playerNameTextFields[game.getCurrentPlayer() - 1].repaint();
					
					// If the next Player is an AI Player, programmatically click the Card Deck button
					
					if (game.getPlayerType(game.getCurrentPlayer()) == Player.AI_PLAYER)
					{
						cardDeckButton.doClick();
					}
				}
			}	
        }
		
    }	// End of StandButtonActionListener class
	
	/*
	 * Stand Button Mouse Listener
	 * 
	 */
	
	private class StandButtonMouseListener implements MouseListener
    {
		@Override
		public void mouseEntered(MouseEvent e)
		{
			if (gameInProgress)
			{
				standButton.setFocusPainted(true);
				standButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));
			}						
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			if (gameInProgress)
			{
				standButton.setFocusPainted(false);
				standButton.setBorder(BorderFactory.createEmptyBorder());
			}
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			
		}
		
    }	// End of StandButtonMouseListener class
	
	/*
	 * New Game Button Action Listener
	 * 
	 */
	
	private class NewGameButtonActionListener implements ActionListener
    {
		/*
		 * actionPerformed Method
		 * 
		 */
		
		@Override
        public void actionPerformed( ActionEvent event )
        {
			// Check if a Game is in progress. If so, ask the players to OK abandoning the current Game
			
			if (gameInProgress)
			{
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to abandone the current Game?", View.GAME_TITLE, JOptionPane.YES_NO_OPTION);
				
				if (reply == JOptionPane.YES_OPTION)
				{
					gameInProgress = false;
				}
			}

			// Ignore it if a Game is in progress
			
			if (!gameInProgress)
			{
				// Clear the Player Names TextFields and enable them (except the Dealer's)

				for (int playerNumber = 0; playerNumber < Game.NUMBER_OF_PLAYERS; playerNumber++)
				{
					playerNameTextFields[playerNumber].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.WHITE));
					playerNameTextFields[playerNumber].setText(INITIAL_PLAYER_NAME);
					playerNameTextFields[playerNumber].setEnabled(true);
				}
				
				// Display the Dealer Name
				
				playerNameTextFields[DEALER_NAME_TEXTFIELD].setText(DEALER_NAME_TEXTFIELD_TEXT);
				playerNameTextFields[DEALER_NAME_TEXTFIELD].setDisabledTextColor(DEALER_NAME_COLOR);
				
				// Must revalidate and repaint to change colors
				
				playerNameTextFields[DEALER_NAME_TEXTFIELD].revalidate();
				playerNameTextFields[DEALER_NAME_TEXTFIELD].repaint();
				
				// Remove the Player Card Images and reset the Player Point Labels
				
				for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
				{
					playerCardPanels[playerNumber].removeAll();
					
					// Must do both in order to empty every Player Card Panel and cause it to display them all as empty panels
					
					playerCardPanels[playerNumber].revalidate();
					playerCardPanels[playerNumber].repaint();
					
					playerPointsLabels[playerNumber].setForeground(PLAYER_POINTS_COLOR);
					playerPointsLabels[playerNumber].setText(" 0 Points ");
					
					// Must do both in order to empty every Player Card Panel and cause it to display them all as empty panels
					
					playerPointsLabels[playerNumber].revalidate();
					playerPointsLabels[playerNumber].repaint();
				}
				
				// Change the New Game Button to the Start Game Button
				
				newGameButton.setBackground(START_GAME_BUTTON_COLOR);
				newGameButton.setText(START_GAME_BUTTON_TEXT);
				newGameButton.setToolTipText(START_GAME_BUTTON_TOOLTIP_TEXT);

				// Remove New Game Action Listener
				
				newGameButton.removeActionListener(newGameButtonActionListener);
				
				// Add Start Game Action Listener
				
				newGameButtonActionListener = new StartGameButtonActionListener();
				newGameButton.addActionListener(newGameButtonActionListener);
			}
			
        }	// End of actionPerformed method
		
    }	// End of NewGameButtonActionListener class
	
	/*
	 * Start Game Button Action Listener
	 * 
	 */
	
	private class StartGameButtonActionListener implements ActionListener
    {
		/*
		 * actionPerformed Method
		 * 
		 */
		
		@Override
        public void actionPerformed( ActionEvent event )
        {
			// Ignore it if a Game is not in progress
			
			if (!gameInProgress)
			{
				// Save the Player Names and disable the Player Name TextFields.
				// The Controller New Game method will set the Dealer's name as the name of an AI Player.

				playerNames = new String[Game.MAX_NUMBER_OF_PLAYERS];
				
				for (int playerNumber = 0; playerNumber < Game.NUMBER_OF_PLAYERS; playerNumber++)
				{
					if (!playerNameTextFields[playerNumber].getText().equals(INITIAL_PLAYER_NAME))
					{
						playerNames[playerNumber] = playerNameTextFields[playerNumber].getText();
					}
					
					// Disable the Player Name TextField for user input and remove focus to prevent it from getting erased

					playerNameTextFields[playerNumber].setBorder(BorderFactory.createEmptyBorder());
					playerNameTextFields[playerNumber].setEnabled(false);
					playerNameTextFields[playerNumber].requestFocus(false);
				}
				
				// Tell the Controller to begin a New Game
				
				game.newGame(playerNames);

				// Display all the Player Names and disable the Player Name TextFields
				
				for (int playerNumber = 0; playerNumber < Game.NUMBER_OF_PLAYERS; playerNumber++)
				{
					playerNameTextFields[playerNumber].setDisabledTextColor(PLAYER_NAME_TEXTFIELD_COLOR);
					
					// Must revalidate and repaint to change colors
					
					playerNameTextFields[playerNumber].revalidate();
					playerNameTextFields[playerNumber].repaint();
					
					playerNameTextFields[playerNumber].setText(game.getPlayerName(playerNumber + 1));
					playerNameTextFields[playerNumber].setEnabled(false);
				}

				// Display the initial deal (2 Cards for each Player, including the Dealer)
				
				for (int cardNumber = 1; cardNumber <= 2; cardNumber++)
				{
					for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
					{
						// Display the second Dealer Card face down (Card in the Hole)
						
						if (playerNumber == Game.MAX_NUMBER_OF_PLAYERS - 1 && cardNumber == 2)
						{
							// Create a blank Card
							
							Card card = new Card();
							
							// Display the Card Back Image instead of the second Card dealt
							
							displayCard(card, playerNumber + 1);
						}
						else
						{
							// Get the next Card dealt for this Player
							
							Card card = game.getPlayerHand(playerNumber + 1, cardNumber);
							
							// Display the Card
							
							displayCard(card, playerNumber + 1);
						}
					}
				}
				
				// Display the Player Points
				
				int dealerPoints = 0;
				
				for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
				{
					// Check for the Dealer
					
					if (playerNumber == Game.MAX_NUMBER_OF_PLAYERS - 1)
					{
						// Get the Dealer's first Card
						
						Card firstCard = game.getPlayerHand(Game.MAX_NUMBER_OF_PLAYERS, 1);

						// If Dealer's first Card is an Ace, show the Dealer's points as 1 point
						
						dealerPoints = firstCard.getNumber();

						if (dealerPoints >= 11)
						{
							dealerPoints = 10;
						}
						
						// Display the adjusted Dealer points
						
						playerPointsLabels[playerNumber].setForeground(PLAYER_POINTS_COLOR);
						playerPointsLabels[playerNumber].setText("" + dealerPoints + "+ Points");
						
						// Must revalidate and repaint to change colors
						
						playerPointsLabels[playerNumber].revalidate();
						playerPointsLabels[playerNumber].repaint();
					}
					else
					{
						playerPointsLabels[playerNumber].setForeground(PLAYER_POINTS_COLOR);
						playerPointsLabels[playerNumber].setText("" + game.getPlayerPoints(playerNumber + 1) + " Points");
						
						// Must revalidate and repaint to change colors
						
						playerPointsLabels[playerNumber].revalidate();
						playerPointsLabels[playerNumber].repaint();
					}
				}

				// Display whether or not each Player has stood or busted
				
				for (int playerNumber = 0; playerNumber < Game.MAX_NUMBER_OF_PLAYERS; playerNumber++)
				{
					if (game.getPlayerStatus(playerNumber + 1) == Player.STOOD)
					{
						// Must setDisabledTextColor in order to change its foreground color.
						// Cannot use setForeground, TextField must be enabled and stay enabled!
						
						playerNameTextFields[playerNumber].setDisabledTextColor(PLAYER_STOOD_COLOR);						
						playerNameTextFields[playerNumber].setText(PLAYER_STOOD_TEXT);
						
						// Must revalidate and repaint to change colors
						
						playerNameTextFields[playerNumber].revalidate();
						playerNameTextFields[playerNumber].repaint();
					}
					else if (game.getPlayerStatus(playerNumber + 1) == Player.BUSTED)
					{
						// Must setDisabledTextColor in order to change its foreground color.
						// Cannot use setForeground, TextField must be enabled and stay enabled!
						
						playerNameTextFields[playerNumber].setDisabledTextColor(PLAYER_BUSTED_COLOR);
						playerNameTextFields[playerNumber].setText(PLAYER_BUSTED_TEXT);
						
						// Must revalidate and repaint to change colors
						
						playerNameTextFields[playerNumber].revalidate();
						playerNameTextFields[playerNumber].repaint();
					}
				}
				
				// Check if this Game is already over
				
				if (!game.isGameOver())
				{
					// Hightlight the first Player's turn
				
					playerNameTextFields[game.getCurrentPlayer() - 1].setDisabledTextColor(NEXT_PLAYERS_TURN);
					
					// Must revalidate and repaint to change colors
					
					playerNameTextFields[game.getCurrentPlayer() - 1].revalidate();
					playerNameTextFields[game.getCurrentPlayer() - 1].repaint();
					
					// Indicate the new Game is in progress
					
					gameInProgress = true;
					
					// If the first Player is an AI Player, programmatically click the Card Deck button
					
					if (game.getPlayerType(game.getCurrentPlayer()) == Player.AI_PLAYER)
					{
						cardDeckButton.doClick();
					}
				}
				else
				{
					// Change the Start Game Button to the New Game Button
					
					newGameButton.setBackground(NEW_GAME_BUTTON_COLOR);
					newGameButton.setText(NEW_GAME_BUTTON_TEXT);
					newGameButton.setToolTipText(NEW_GAME_BUTTON_TOOLTIP_TEXT);
					
					// Remove Start Game Action Listener
					
					newGameButton.removeActionListener(newGameButtonActionListener);
					
					// Add Action Listener
					
					newGameButtonActionListener = new NewGameButtonActionListener();
					newGameButton.addActionListener(newGameButtonActionListener);
				}
			}
			
        }	// End of actionPerfomed method
		
    }	// End of StartGameButtonActionListener class
	
}	// End of TableView class
