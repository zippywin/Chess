import java.util.List;

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
	private boolean gameOver;
	private int winner;
	private King whiteKing;
	private King blackKing;
	
	/**
	 *  The integer representing white player
	 */
	public static int WHITE = 0;
	
	/**
	 * The integer representing black player
	 */
	public static int BLACK = 1;
	
	public static int NOONE = -1;
	
	/**
	 * Creates a new game
	 */
	public Game() {
		board = new Chessboard();
		initChessboard(board);
		turn = 0;
		activePlayer = WHITE;
		winner = NOONE;
		gameOver = false;
	}
	
	/**
	 * Initializes the chessboard to the starting state of a game.
	 */
	public void initChessboard(Chessboard board) {
		//Placing pawns
		for (int i = 0; i < 8; i++) {
			board.place(new Pawn(board,i,1,WHITE),i,1);
			board.place(new Pawn(board,i,6,BLACK),i,6);
		}
		//Initing the back row
		whiteKing = new King(board, 4, 0, WHITE);
		blackKing = new King(board, 4, 7, BLACK);
		
		board.place(new Rook(board, 0, 0, WHITE), 0, 0);
		board.place(new Knight(board, 1, 0, WHITE), 1, 0);
		board.place(new Bishop(board, 2, 0, WHITE), 2, 0);
		board.place(new Queen(board, 3, 0, WHITE), 3, 0);
		board.place(whiteKing, 4, 0);
		board.place(new Bishop(board, 5, 0, WHITE), 5, 0);
		board.place(new Knight(board, 6, 0, WHITE), 6, 0);
		board.place(new Rook(board, 7, 0, WHITE), 7, 0);
		
		//Initing the front row
		board.place(new Rook(board, 0, 7, BLACK), 0, 7);
		board.place(new Knight(board, 1, 7, BLACK), 1, 7);
		board.place(new Bishop(board, 2, 7, BLACK), 2, 7);
		board.place(new Queen(board, 3, 7, BLACK), 3, 7);
		board.place(blackKing, 4, 7);
		board.place(new Bishop(board, 5, 7, BLACK), 5, 7);
		board.place(new Knight(board, 6, 7, BLACK), 6, 7);
		board.place(new Rook(board, 7, 7, BLACK), 7, 7);
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
	 * Given two squares, moves a piece from square1 to square 2
	 * Then advances the game to the next turn. 
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
		List<String> moves = null;
		if (p != null) {
			moves = p.getValidMoves();
		}
		return moves;
	}
	
	/**
	 * Returns the winner of the game, if the game is finished
	 * @return the winner of the game, if the game is finished
	 */
	public int getWinner() {
		return winner;
	}
	
	/**
	 * Returns true if the current player is in check.
	 * @param player the player to check for check. Defined by Game
	 * @return true if the player is in check.
	 */
	public boolean isInCheck(int player) {
		King king = getKing(player);
		return board.squareIsThreatened(king.getX(),king.getY(),opponent(player));
	}

	/**
	 * Returns the opponent of the given player
	 * @param player the given player
	 * @return the opponent
	 */
	private int opponent(int player) {
		if (player == Game.WHITE) {
			return Game.BLACK;
		} else {
			return Game.WHITE;
		}
	}
	
	public King getKing(int player) {
		if (player == WHITE) {
			return whiteKing;
		} else {
			return blackKing;
		}
	}

	public Chessboard getBoard() {
		return board;
	}
}
