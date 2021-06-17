import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Maze {
	private Tile[][] grid;	//all tiles in the grid
	private int width;	//width of maze including border
	private int height;	//height of maze including border
	private Player player;	//the current player

	
	/**
	 * Constructor for the maze.
	 * @param w the width of the maze.
	 * @param h the height of the maze.
	 * @param p the player of the maze.
	 */
	public Maze (int w, int h, Player p) {
		//width and height is assumed to be greater than 1
		grid = new Tile[w][h];	// maze
		this.width = w;
		this.height = h;
		
		player = p;	//use input player

		//generate maze using Prim's algorithm
		MazeGenerator mazeGenerator = new PrimMazeGenerator();
		createMaze(mazeGenerator);	//initialise all tiles including
						//player location, apple, coins, powerups, doors

	}
	
	/**
	 * Generate the maze.
	 * This should be called only once when initialising the maze.
	 */
	private void createMaze(MazeGenerator mazeGenerator) {
		grid = mazeGenerator.genMaze(width, height);
		
		//set player
		player.setLocation(grid[1][1]);	//origin at top corner,

		
		//set special tiles
		//grid[1][2].setType(Tile.DOOR);	//set tile just above the origin to a door
		grid[width-2][height-1].setType(Tile.DOOR);	//set tile just below the destination to a door

		grid[1][height-2].setType(Tile.KEY);	//set bottom left corner to key to door

		
		int j=0;
		while (true) {

			int randomX = 1 + (int) (Math.random() * ((width - 2) / 2));    //any random X in maze
			int randomY = 1 + (int) (Math.random() * ((height - 2) / 2));    //any random Y in maze
			if (grid[randomX][randomY].getType() == Tile.PATH &&    //check that the tile is walkable
					!grid[randomX][randomY].equals(player.getLocation())) {    //and not where player is
				grid[randomX][randomY].setType(Tile.ICE_POWER);
				j++;
				if (j == 5) {
					break;
				}
				//go to see if other ice powers(stramina)) are to be added

			}
		}
			int k=0;
			while (true) {

				int randomX = 1 + (int) (Math.random() * ((width - 2) / 2));    //any random X in maze
				int randomY = 1 + (int) (Math.random() * ((height - 2) / 2));    //any random Y in maze
				if (grid[randomX][randomY].getType() == Tile.PATH &&    //check that the tile is walkable
						!grid[randomX][randomY].equals(player.getLocation())) {    //and not where player is
					grid[randomX][randomY].setType(Tile.Trap);  //Trap
					k++;
					if(k==5){break;}
					//go to see if other ice powers(stramina)) are to be added

				}




			}

		
		//sets three random tiles to treasure
		//formula for number of treasure is 1 more treasure per 1.5 levels
		int i = 0;
		while (i < (int)(width-10)/3 + 3) {
			int randomX = 1 + (int)(Math.random()*((width-2)));		//any random X
			int randomY = 1 + (int)(Math.random()*((height-2)));	//any random Y
			if (grid[randomX][randomY].getType() == Tile.PATH &&	//check that the tile is walkable
				!grid[randomX][randomY].equals(player.getLocation())) {		//and not where player is
				grid[randomX][randomY].setType(Tile.TREASURE);
				i++;	//update treasure added

			}
		}
		showMaze();
	}
	
	/**
	 * Displays ASCII form of basic maze.
	 * Shows where walls and paths are,
	 * and where player is.
	 */
	public void showMaze () {

		Tile playerLoc = player.getLocation();

		for (int j = 0; j < width; j++) {
			for (int i = 0; i < height; i++) {
				if (grid[i][j].isWalkable()) {
					//order of printing is important as path contains start and dest
					if (playerLoc != null && playerLoc.equals(grid[i][j])) {
						System.out.print(" P ");
					} else if (i == (width-1)-1 && j == (height-1)-1) {
						System.out.print(" D ");
					}
					else if ( grid[i][j].getType() == Tile.TREASURE){
						System.out.print(" C ");
					}
					else if ( grid[i][j].getType() == Tile.ICE_POWER){
						System.out.print(" A ");
					}
					else if ( grid[i][j].getType() == Tile.Trap){
						System.out.print(" T ");
					}
					else {
						System.out.print(" _ ");
					}
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Finds the path from (x,y) to the destination.
	 * It stores the path in an input hash map,
	 * which maps the tile to its parent tile.
	 * Credit to http://en.wikipedia.org/wiki/Maze_solving_algorithm.
	 * Adapted from "Recursive algorithm".
	 * @return true if a path exists. It should be true
	 * as the maze is always solvable from any position.
	 */
	public boolean findPath (int x, int y, boolean[][] visited, HashMap<Tile,Tile> parent) {
		if (x == width-2 && y == height-2) return true;
		if (!grid[x][y].isWalkable() || visited[x][y]) return false;
		visited[x][y] = true;
		if (x != 0) {
			if (findPath(x-1,y,visited,parent)) {
				parent.put(grid[x-1][y], grid[x][y]);
				return true;
			}
		}
		if (x != width-1) {
			if (findPath(x+1,y,visited,parent)) {
				parent.put(grid[x+1][y], grid[x][y]);
				return true;
			}
		}
		if (y != 0) {
			if (findPath(x,y-1,visited,parent)) {
				parent.put(grid[x][y-1], grid[x][y]);
				return true;
			}
		}
		if (y != height-1) {
			if (findPath(x,y+1,visited,parent)) {
				parent.put(grid[x][y+1], grid[x][y]);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds the path from (x,y) to (x1,y1).
	 * It stores the path in an input hash map,
	 * which maps the tile to its parent tile.
	 * Credit to http://en.wikipedia.org/wiki/Maze_solving_algorithm.
	 * Adapted from "Recursive algorithm".
	 * @return true if a path exists. It should be true
	 * as the maze is always solvable from any position.
	 */
	public boolean findPath (int x, int y, int x1, int y1, boolean[][] visited, HashMap<Tile,Tile> parent) {
		if (x == x1 && y == y1) return true;
		if (!grid[x][y].isWalkable() || visited[x][y]) return false;
		visited[x][y] = true;
		if (x != 0) {
			if (findPath(x-1,y,x1,y1,visited,parent)) {
				parent.put(grid[x-1][y], grid[x][y]);
				return true;
			}
		}
		if (x != width-1) {
			if (findPath(x+1,y,x1,y1,visited,parent)) {
				parent.put(grid[x+1][y], grid[x][y]);
				return true;
			}
		}
		if (y != 0) {
			if (findPath(x,y-1,x1,y1,visited,parent)) {
				parent.put(grid[x][y-1], grid[x][y]);
				return true;
			}
		}
		if (y != height-1) {
			if (findPath(x,y+1,x1,y1,visited,parent)) {
				parent.put(grid[x][y+1], grid[x][y]);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Give first 10 steps from the current tile to the destination.
	 * If less than 10 steps away, all tiles to destination are shown
	 * @param t the current tile
	 * @return the list of 10 tiles in the path from the current tile to the destination,
	 * or all tiles to destination if less than 10 tiles away.
	 */
	public List<Tile> giveHint(Tile t) {
		//TODO change colour of tiles such that path from current player position
		//to the nearest tile part of the solution is given
		boolean[][] visited = new boolean[width][height];
		HashMap<Tile,Tile> parent = new HashMap<Tile,Tile>();
		parent.put(t, null);	//current tile has no parent as it is the starting tile of the path
		boolean pathFound = findPath(t.getX(), t.getY(), visited, parent);
		if (!pathFound) {
			return null;		//error in pathfinding
		} else {
			LinkedList<Tile> path = new LinkedList<Tile>();
			Tile curr = grid[width-2][height-2];	//backtrack from destination
			path.addFirst(curr);
			while (parent.get(curr) != null) {	//if we haven't reached the starting state yet
				Tile prev = parent.get(curr);
				path.addFirst(prev);
				curr = prev;
			}
			List<Tile> pathInit = path.subList(0,Math.min(10,path.size()));
			return pathInit;
		}
	}
	
	/**
	 * Finds the tile on the maze that the player is situated in.
	 * @return the tile in which the player is on, or null if player is dead
	 */
	public Tile getPlayerTile() {
		if (player.isDead()) {
			return null;
		}
		return player.getLocation();
	}
	
	/**
	 * Finds the tile on the maze that the enemy is situated on.
	 * @param i the ID of the enemy (ith enemy).
	 * @return the tile in which the enemy is on, or null if enemy is dead or doesn't exist.
	 */

	
	/**
	 * Checks if a tile is one where an enemy is situated on.
	 * @param t the tile of interest.
	 * @return true if the tile is one where an enemy is situated on.
	 */

	
	/**
	 * Get the tile where the destination door is situated on
	 * @return the tile where the destination door is situated on
	 */
	public Tile getDestDoor() {
		return grid[width-2][height-1];
	}
	
	/**
	 * Updates the player location if a valid move is taken.
	 * See isValid() for conditions.
	 * @param x the amount of movement in the x direction, as per number of tiles.
	 * The number is negative if the movement is to the left direction.
	 * @param y the amount of movement in the y direction, as per number of tiles.
	 * The number is negative if the movement is to the up direction.
	 */

	
	/**
	 * Begins updating enemy movement in the maze.
	 * Each move is scheduled at every 1.5 seconds.
	 */
	/**
	 * Checks if a player is dead.
	 * A player is dead if it encounters the enemy without a sword.
	 * @return true if the player is dead.
	 */
	public boolean playerDied () { return player.isDead(); }
	/**
	 * Checks if an enemy is dead.
	 * An enemy is dead if it encounters the player who has a sword.
	 * @param i stands for ith enemy
	 * @return true if the enemy is dead.
	 */


	
	/**
	 * Get number of enemies in the maze initially
	 * @return the number of enemies in the maze initially
	 */
	public boolean isValid (int x, int y) {
		Tile playerLoc = player.getLocation();
		if (x > 1 || x < -1 || y > 1 || y < -1) {	//can only move at most one tile per turn
			return false;
		} else if (x != 0 && y != 0) {	//can only move in one direction
			return false;
		} else if ((playerLoc.getX()+x) > (width-1) || (playerLoc.getY()+y) > (height-1)
			|| (playerLoc.getX()+x) < 0 || (playerLoc.getY()+y) < 0) {	//cannot move over border
			return false;
		} else if (!grid[playerLoc.getX()+x][playerLoc.getY()+y].isWalkable()) { //cannot move to wall
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the player has reached the destination.
	 * The destination is the tile right before the exit door.
	 * If the key is collected, then the door is unlocked as well;
	 * otherwise, it remains closed.
	 * To check if the player takes the exit, see exitedMaze().
	 * @return true if the player has reached the destination.
	 */
	public boolean checkReachedEnd() {
		Tile playerLoc = player.getLocation();
		if (player.isDead()) {
			return false;
		}
		//destination is at (width-2, height-2)
		boolean atEnd = false;
		if (playerLoc.getX() == (width-2) && playerLoc.getY() == (height-2)) {
			atEnd = true;
			if (itemCollected(Player.KEY)) {	//if key is collected, unlock door
				grid[width-2][height-1].setType(Tile.PATH);	//set door to walkable path
				grid[width-2][height-1].setWalkable();
			}
		}
		return atEnd;
	}
	
	/**
	 * Checks if the player is at the origin.
	 * The origin of the maze is at (1,1),
	 * just in front of the entrance door.
	 * @return true if the player is at the origin.
	 */
	public boolean atStart() {
		return (!player.isDead() && player.getLocation().getX() == 1 && player.getLocation().getY() == 1);
	}
	
	/**
	 * Checks if the player has completed the maze.
	 * It is completed when the destination door is opened by the key
	 * and the player is located on the exit, where the door was originally
	 * positioned.
	 * @return true if player has completed the maze.
	 */
	//see if player unlocked door and took the exit
	public boolean exitedMaze() {
		return (!player.isDead() && player.getLocation().getX() == (width-2) && player.getLocation().getY() == (height-1));
	}
	
	/**
	 * Get number of treasure collected.
	 * @return the number of treasure collected
	 */
	public int getNumTreasureCollected() { return player.getNumTreasureCollected(); }
	
	/**
	 * Check if item is collected.
	 * @param itemNum the ID of the item as defined in Player
	 * @return true if item is collected
	 */
	public boolean itemCollected(int itemNum) { return player.isItemCollected(itemNum); }
	
	/**
	 * Get the tile of a specific set of x and y coordinates.
	 * (0,0) is the top left corner of the maze (includes border).
	 * If a set of coordinates outside of the bounds of the maze is entered,
	 * the null tile is returned.
	 * @param x the x coordinate of the tile.
	 * @param y the y coordinate of the tile.
	 * @return the tile with the specified x and y coordinates, or null if
	 * the coordinates are outside of the maze area.
	 */
	public Tile getTile(int x, int y) {
		if (x >= 0 && y >= 0 && x <= (width-1) && y <= (height-1)) {
			return this.grid[x][y];
		} else {
			return null;
		}
	}
	/**
	 * Get the width of the maze (including border).
	 * @return the width of the maze, as per the number of tiles.
	 */
	public int getWidth() { return this.width; }
	/**
	 * Get the length of the maze (including border).
	 * @return the length of the maze, as per the number of tiles.
	 */
	public int getHeight() { return this.height; }
	/**
	 * Gets the grid of tiles of the maze.
	 * @return the grid of tiles of the maze.
	 */
	public Tile[][] getGrid() { return this.grid; }

	public void updatePlayerLoc(int x, int y) {
		if (isValid(x,y) && !player.isDead()) {
			player.setLocation(grid[player.getLocation().getX()+x][player.getLocation().getY()+y]);
			Tile playerLoc = player.getLocation();	//get updated player location


			if (playerLoc.getType() == Tile.KEY) {
				player.setItemCollected(Player.KEY, true);
				playerLoc.setType(Tile.PATH);//set key tile to normal path

			} else if (playerLoc.getType() == Tile.TREASURE) {
				player.addNumTreasureCollected();
				playerLoc.setType(Tile.PATH);	//if we collected the treasure
			} else if (playerLoc.getType() == Tile.SWORD) {
				player.setItemCollected(Player.SWORD, true);
				playerLoc.setType(Tile.PATH);
				player.addNumTreasureCollected();
			} else if (playerLoc.getType() == Tile.ICE_POWER) {
				player.setItemCollected(Player.ICE_POWER,true);
				playerLoc.setType(Tile.PATH);
				player.addNumTreasureCollected();
			}
			else {player.lessNumTreasureCollected();}
			checkReachedEnd();	//unlock door if player has reached end tile
		}
	}

}