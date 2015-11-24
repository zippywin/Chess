/**
 * A class responsible for handling user actions 
 * and keeping track of player/game information
 * @author Chris
 *
 */
public class Game {

	private Chessboard board;
	private int turn;
	private int activePlayer;
	
	/**
	 * Creates a new game
	 */
	public Game() {
		board = new Chessboard();
		turn = 0;
		activePlayer = 1;
	}
	
	/**
	 * Returns the player whose turn it is
	 * @return the player whose turn it is
	 */
	public int getActivePlayer() {
		return activePlayer;
	}
	
	/**
	 * Returns the number of turns passed since the beginning of the game
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}
	
	/**
	 * Given two squares, attempts to move a piece from square1 to square 2
	 * @param square1 the square containing the piece to be moved
	 * @param square2 the destination square
	 */
	public void makeMove(String square1, String square2) {
		board.makeMove(square1, square2);
	}
	
	/**
	 * Returns true if the game is over
	 * @return true if the game is over
	 */
	public boolean gameIsOver() {
		return true;
	}
	
}
