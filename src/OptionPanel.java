import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class OptionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JButton setLink;	//buttons for selecting characters
	private JTextField nameField;	//place where user enters name
	
	private JRadioButton easyDifficulty;	//buttons for choosing difficulty
	private JRadioButton mediumDifficulty;
	private JRadioButton hardDifficulty;

	/**
	 * Construct the JPanel containing elements for the option panel.
	 * @param g the Game object describing the game state
	 */
	public OptionPanel(Game g){
		final Game game = g;
		this.setLayout(new GridBagLayout());		
		GridBagConstraints gbc = new GridBagConstraints();
		
		Sprite linkPanel = new Sprite(MazeFrame.LinkSprite, 48, 48);

		setLink = new JButton("Player1", linkPanel.getSprite() );

		//set up selecting character when user clicks the corresponding button
		setLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.getPlayer().setCharacter(MazeFrame.LinkSprite);
			}
		});
		this.add(setLink);	//add choose character buttons to panel

		nameField = new JTextField("Enter your Name");
		nameField.setPreferredSize(new Dimension(100,20));
		
		// Move to next line
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(20,20,0,0);
		
		JLabel nameText = new JLabel("Introduce yourself");
		this.add(nameText, gbc);
		
		gbc.gridx = 1;
		this.add(nameField, gbc);

		// Move to next line
		gbc.gridy = 2;
		gbc.gridx = 0;
		JLabel difficultyText = new JLabel("Choose difficulty:");
		this.add(difficultyText, gbc);
		
		//Add setting difficulty option
		ButtonGroup difficultyButtons = new ButtonGroup();
		easyDifficulty = new JRadioButton("0");
		mediumDifficulty = new JRadioButton("5", true);
		hardDifficulty = new JRadioButton("10");
		difficultyButtons.add(easyDifficulty);
		difficultyButtons.add(mediumDifficulty);
		difficultyButtons.add(hardDifficulty);
		
		//Move to next line, and add buttons
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.insets = new Insets(10,10,0,0);
		this.add(easyDifficulty,gbc);
		
		gbc.gridx = 1;
		this.add(mediumDifficulty,gbc);
		
		gbc.gridx = 2;
		this.add(hardDifficulty,gbc);
		
		//set up changing game difficulty when radio button is selected
		easyDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDifficulty(Game.EASY);
			}
		});
		mediumDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDifficulty(Game.MEDIUM);
			}
		});
		hardDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDifficulty(Game.HARD);
			}
		});
	}
	
	/**
	 * Gets the name user entered in the text field
	 * @return the name user entered in the text field
	 */
	public String getName(){
		return nameField.getText();
	}
}
