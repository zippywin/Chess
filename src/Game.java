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
	public static int WHITE = 0;
	public static int BLACK = 1;
	
	/**
	 * Creates a new game
	 */
	public Game() {
		board = new Chessboard();
		turn = 0;
		activePlayer = Game.WHITE;
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
		if (activePlayer == Game.WHITE) {
			activePlayer = Game.BLACK;
		} else {
			activePlayer = Game.WHITE;
		}
	}
	
	/**
	 * Returns true if the game is over
	 * @return true if the game is over
	 */
	public boolean gameIsOver() {
		return true;
	}
	
	/**
	 * Prints out the chessboard 
	 */
	public void printGame() {
		board.printBoard();
	}
}
