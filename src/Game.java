public class Game {



	private Maze maze;
    private Player player;	//the current player of the maze
    private Controller c;	//controller for player
    
    private int difficulty;	//difficulty setting
    public int stamina;	//player's score
    public int level;//current level in the maze game

	private boolean finishedLevel;	//whether or not current level has been completed
    
    //Flow control
    private volatile boolean inGame;	//whether or not the game has not been exited yet
    private volatile boolean isGameOver;	//whether or not player has lost the game
    										//or has passed all the levels
    //Game constants
    /**Describes constant for the width of the maze at the starting level (on medium difficulty).*/
    private static final int START_LEVEL_WIDTH = 11;
    /**Describes constant for the height of the maze at the starting level (on medium difficulty).*/
    private static final int START_LEVEL_HEIGHT = 11;
    private static final int POINTS_ENEMY_KILLED = 10;
    /**Describes constant for the maximum number of levels in the maze game.*/
    public static final int MAX_LEVEL = 10;
    
    //Difficulty constants
    /**Describes constant for easy difficulty.*/
    public static final int EASY = -1;
    /**Describes constant for medium difficulty.*/
    public static final int MEDIUM = 0;
    /**Describes constant for hard difficulty.*/
    public static final int HARD = 1;  
    
    /**
     * Constructor for creating a maze game.
     * All fields set to default values, with a new player
     * created with the default name and character.
     * Difficulty is set on medium by default.
     */
    public Game() {
        isGameOver = false;
        inGame = false;
        //Make one player
		player = new Player("Default", "link");
        stamina = 12;	//initially
        level = 0;	//initially zero level (shown as Level 1, as counting from 1)

		difficulty = MEDIUM;	//default difficulty is medium

    }
    
    /**
     * Create a maze which scales in size based on game level and difficulty.
     */
    public void createMaze() {
    	//creates a new maze
    	this.maze =  new Maze(START_LEVEL_WIDTH + ((difficulty)),
    						START_LEVEL_HEIGHT + ((difficulty)), player);
		c = new Controller(maze);	//set controller to the maze

    }
    
    /**
     * Updates the status of whether the game is over or not.
     * The game is over when the player dies or
     * all levels are completed.
     * @param isGameOver if true, the game is over;
     * if false, the game is still continuing.
     */
    public void setIsGameOver(boolean isGameOver) {

    	this.isGameOver = isGameOver;
    }
    
    /**
     * Checks if the game is over or not.
     * The game is over when the player has lost the game
  	 * or has passed all the levels.
     * @return true if game has been finished.
     */
    public boolean isGameOver() {
        return isGameOver;
    }
       
    /**
     * Updates the status of whether the player is still in the maze game or not.
     * @param inGame if true, the game should be on the home screen;
     * if false, the maze game should still be on.
     */
    public void setIsInGame(boolean inGame) {
    	this.inGame = inGame;
    }
    
    /**
     * Checks if the player is still in the maze game or not.
     * @return true if the player is still in the maze game.
     */
    public boolean isInGame() {
    	return inGame;
    }
    
    /**
     * Sets the controller for the player.
     * @param c the controller for the player.
     */
    public void setController(Controller c) {
    	this.c = c;
    }
    
    /**
     * Gets the controller for the player.
     * @return c the controller for the player.
     */
    public Controller getController() {
    	return c;
    }
    
    /**
     * Gets the player of the game.
     * @return the player of the game.
     */
    public Player getPlayer() {
    	return this.player;
    }
    
    /**
     * Gets the maze of the game.
     * @return the maze of the game.
     */
    public Maze getMaze() {

    	return this.maze;
    }
    
    /**
     * Updates the game score.
     * The game is dependent on the number of treasure collected
     * by the player, and the number of enemies killed.
     */
    public void updateScore(){
    	//2 point for each treasure collected

    	stamina = player.getNumTreasureCollected() ;
    }



    
    /**
     * Gets the current score of the game.
     * @return the current score of the game.
     */
	public int getScore() {
		return stamina;
	}

	/**
	 * Checks if the game should be on the next level,
	 * and updates the game state accordingly.
	 * Next level should be reached when player exits the room.
	 */
	public void checkNextLevel () {
		 //If level is complete
		 if (maze.exitedMaze()) {
			finishedLevel = true;	//current level has been completed
			level++;
			player.clearInventory();	//clear inventory once next level
			 if (level == MAX_LEVEL) {
				 setIsGameOver(true);	//end game if passed all levels
				 setIsInGame(false);
			 } else {
				 createMaze();	//create a new maze for the next level
			 }
		 }
	}

	/**
	 * Get the current level of the game.
	 * @return the current level of the game.
	 */
	public int getLevel () {
		return level;
	}
	/**
	 * Sets the difficulty of the game.
	 * @param difficulty the difficulty of the game.
	 * Defined in Game.
	 */
	public void setDifficulty (int difficulty) {
		this.difficulty = difficulty;
	}
	
	/**
	 * Checks if the maze of the current level has been completed.
	 * This information is mainly used by GameManager to determine
	 * what screens to show.
	 * @return true if the maze of the current level has been completed.
	 */
	public boolean getFinishedLevel() {
		return finishedLevel;
	}
	
	/**
	 * Sets the status that the maze of the current level has been completed.
	 * @param b if true, the maze of the current level has been completed;
	 * if false, the maze has not yet been completed.
	 */
	public void setFinishedLevel(boolean b) {
		finishedLevel = b;
	}
	
	/**
	 * Clears the progress of the player in the game.
	 */
	public void clearGame() {
    	//set score and level back to defaulty.
    	stamina = 12;
    	level = 0;
	}



	/* For testing only
	public void setLevel(int setLevel) {
		level = setLevel;
	}*/
}