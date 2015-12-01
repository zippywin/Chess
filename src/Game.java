import java.util.LinkedList;
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
	private List<Piece> takenWhitePieces;
	private List<Piece> takenBlackPieces;
	private Square enPassantSquare;
	
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
		takenWhitePieces = new LinkedList<Piece>();
		takenBlackPieces = new LinkedList<Piece>();
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
	 * Given two squares, moves a piece from square1 to square 2.
	 * Then works out the consequences of moving this piece. (Such as pawn promotion.)
	 * Then advances the game to the next turn. 
	 * @param square1 the square containing the piece to be moved
	 * @param square2 the destination square
	 */
	public void makeMove(String square1, String square2) {
		Square src = board.getSquare(square1);
		Square dest = board.getSquare(square2);
		Piece p = src.removePiece();
		p.move(dest.getX(), dest.getY());
		if (dest.hasPiece()) {
			//Removes the piece from dest, and marks it as taken
			takePiece(dest);
		}
		dest.placePiece(p);
		//En Passant logic
		//First check if en passant was available, and if it was, disable it.
		if (enPassantSquare != null) {
			enPassantSquare.setEnPassantable(false);
			enPassantSquare = null;
		}
		//Then check if a pawn was double stepped. If it was, record this fact.
		if (p instanceof Pawn) {
			checkIfPawnDoubleStepped(src, dest, p.getPlayer());
		}
		nextTurn();
	}
	
	/**
	 * Checks if moving from src to dest is a double step
	 * And if it is, records this fact on the square and within this object
	 * Assumes the piece being moved is actually a pawn. 
	 * @param src the square where the pawn was
	 * @param dest the square where the pawn now is
	 * @param player the owner of the pawn
	 */
	public void checkIfPawnDoubleStepped(Square src, Square dest, int player) {
		if (player == WHITE) {
			if (src.getX() == dest.getX() && src.getY() + 2 == dest.getY()) {
				enPassantSquare = board.getSquare(src.getX(), src.getY() + 1);
				enPassantSquare.setEnPassantable(true);
			}
		} else {
			if (src.getX() == dest.getX() && src.getY() - 2 == dest.getY()) {
				enPassantSquare = board.getSquare(src.getX(), src.getY() - 1);
				enPassantSquare.setEnPassantable(true);
			}
		}
	}
	
	public void takePiece(Square sq) {
		int player = sq.getPiece().getPlayer();
		if (player == WHITE) {
			takenWhitePieces.add(sq.removePiece());
		} else {
			takenBlackPieces.add(sq.removePiece());
		}
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
	public List<String> getValidMoves(Piece p) {
		List<String> moves = null;
		if (p != null) {
			moves = p.getValidMoves();
		}
		for (String move : moves) {
			if (willResultInCheck(p.getLocation(), move)) {
				moves.remove(move);
			}
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
		return board.squareIsThreatened(king.getX(), king.getY(), opponent(player));
	}

	/**
	 * Returns the opponent of the given player
	 * @param player the given player
	 * @return the opponent
	 */
	public static int opponent(int player) {
		if (player == Game.WHITE) {
			return Game.BLACK;
		} else {
			return Game.WHITE;
		}
	}
	
	/**
	 * Returns the king piece for the specified player
	 * @param player the owner of the requested king
	 * @return the king piece for the specified player
	 */
	public King getKing(int player) {
		if (player == WHITE) {
			return whiteKing;
		} else {
			return blackKing;
		}
	}

	/**
	 * Returns the chessboard object this game is using
	 * @return the chessboard object
	 */
	public Chessboard getBoard() {
		return board;
	}
	
	/**
	 * Simulates the following move to check if this will land a player in check
	 * @param square1 the square with the piece to move
	 * @param square2 the square to simulate the move to 
	 * @return true if moving here will result in a check
	 */
	public boolean willResultInCheck(String square1, String square2) {
		Square src = board.getSquare(square1);
		Square dest = board.getSquare(square2);
		Piece p = src.removePiece();
		dest.placePiece(p);
		boolean check = isInCheck(p.getPlayer());
		dest.removePiece();
		src.placePiece(p);
		return check;
	}
	
	public Piece getPiece(String loc) {
		return board.getPiece(loc);
	}
}
