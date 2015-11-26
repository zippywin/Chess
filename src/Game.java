import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
	private Scanner sc;
	private boolean gameOver;
	
	/**
	 *  The integer representing white player
	 */
	public static int WHITE = 0;
	
	/**
	 * The integer representing black player
	 */
	public static int BLACK = 1;
	
	/**
	 * Creates a new game
	 */
	public Game() {
		board = new Chessboard();
		turn = 0;
		activePlayer = Game.WHITE;
		sc = new Scanner(System.in);
		gameOver = false;
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
		nextTurn();
	}
	
	/**
	 * Returns true if the game is over
	 * @return true if the game is over
	 */
	public boolean gameIsOver() {
		return gameOver;
	}
	
	/**
	 * Prints out the chessboard 
	 */
	public void printGame() {
		board.printBoard();
	}
	
	/**
	 *  Advances the game to the next turn
	 */
	public void nextTurn() {
		turn++;
		if (activePlayer==Game.BLACK) {
			activePlayer = Game.WHITE;
		} else {
			activePlayer = Game.BLACK;
		}
	}

	/**
	 * Given the location of a piece, returns a list of locations the player may move to
	 * @param loc the location of a selected piece
	 * @return a list of valid moves. (Null if a piece wasn't found, or if the input is invalid)
	 */
	public List<String> getValidMoves(String loc) {
		Piece p = board.getPiece(loc);
		LinkedList<String> validMoves = null;
		if (p != null) {
			validMoves = new LinkedList<String>();
			validMoves.add("D4");
		}
		return validMoves;
	}
	
}
