import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
	
	private Maze maze;
	
	/**
	 * Constructor for the controller for the player.
	 * @param maze the maze which the controller is to be set to
	 */
	public Controller(Maze maze){
		this.maze = maze;	//set the controller to the input maze
	}
		
	/**
	 * Override method for keyPressed.
	 * If arrow keys are pressed,
	 * the player's location in the maze is updated
	 * if the move is valid.
	 * Check Maze for more information about validity of a player move
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		 int dx = 0;	//movement in the x direction
		 int dy = 0;	//movement in the y direction
		 
		 if ( e.getKeyCode() == KeyEvent.VK_RIGHT){
			 //System.out.println("right");
			 dx = 1;	//move right
			 dy = 0;
		 } else if ( e.getKeyCode() == KeyEvent.VK_LEFT){
			 //System.out.println("left");
			 dx = -1;	//move left
			 dy = 0;
		 } else if (e.getKeyCode() == KeyEvent.VK_UP) {
			 //System.out.println("up");
			 dx = 0;	//move up
			 dy = -1;
		 } else if ( e.getKeyCode() == KeyEvent.VK_DOWN){
			 //System.out.println("down");
			 dx = 0;	//move down
			 dy = 1;
		 } else {	//ignore other presses
			 return;
		 }
		 
		 //Update the player location if a valid move was made
		 if (maze.isValid(dx, dy)) {
			 maze.updatePlayerLoc(dx, dy);
			 this.maze.showMaze();
		 }
	}

	/**
	 * Empty override method for keyReleased.
	 * No action required when key is released.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		//no action if key is released	
	}

	/**
	 * Empty override method for keyTyped.
	 * No action required when key is typed.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		//no action if key is typed
	}
}
