import java.util.Iterator;
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
	private Square enPassantableSquare;
	
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
		//First check if en passant was available, and if it was, unmark it.
		Square previousEnPassantableSquare = enPassantableSquare;
		if (enPassantableSquare != null) {
			enPassantableSquare.setEnPassantable(false);
			enPassantableSquare = null;
		}
		//Then check if a pawn was double stepped. If it was, mark the square
		//in between the double step for an en passant move
		if (p instanceof Pawn) {
			checkIfPawnDoubleStepped(src, dest, p.getPlayer());
			checkIfPawnEnPassanted(src, dest, previousEnPassantableSquare, (Pawn) p);
			//checkifPawnPromoted(p);
		}

        //Castling logic here
        if (p instanceof King) {
            if (src.getX() - 2 == dest.getX()) {
                Square leftSquare = board.getSquare(src.getX() - 4, src.getY());
                Piece leftRook = leftSquare.removePiece();
                Square leftCastleRookDest = board.getSquare(src.getX() - 1, src.getY());
                leftRook.move(leftCastleRookDest.getX(), leftCastleRookDest.getY());
                leftCastleRookDest.placePiece(leftRook);

            } else if (src.getX() + 2 == dest.getX()) {
                Square rightSquare = board.getSquare(src.getX() + 3, src.getY());
                Piece rightRook = rightSquare.removePiece();
                Square rightCastleRookDest = board.getSquare(src.getX() + 1, src.getY());
                rightRook.move(rightCastleRookDest.getX(), rightCastleRookDest.getY());
                rightCastleRookDest.placePiece(rightRook);
            }
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
				enPassantableSquare = board.getSquare(src.getX(), src.getY() + 1);
				enPassantableSquare.setEnPassantable(true);
			}
		} else {
			if (src.getX() == dest.getX() && src.getY() - 2 == dest.getY()) {
				enPassantableSquare = board.getSquare(src.getX(), src.getY() - 1);
				enPassantableSquare.setEnPassantable(true);
			}
		}
	}
	
	/**
	 * Checks if the move made was an en passant move, and if it is, removes the pawn that should be taken
	 * @param src the src square
	 * @param dest the dest square
	 * @param previousEnPassantableSquare the square previously marked enpassantable
	 * @param p the pawn
	 */
	private void checkIfPawnEnPassanted(Square src, Square dest, Square previousEnPassantableSquare, Pawn p) {
		if (previousEnPassantableSquare == dest) {
			if (src.getX() == dest.getX() + 1 || src.getX() == dest.getX() - 1) {
				if (p.getPlayer() == WHITE) {
					takePiece(board.getSquare(dest.getX(), dest.getY() - 1));
				} else {
					takePiece(board.getSquare(dest.getX(), dest.getY() + 1));
				}
			}
		}
	}
	
	/**
	 * Takes the piece at the specified square, marking it as taken
	 * @param sq the square containing the piece to take
	 */
	public void takePiece(Square sq) {
		Piece p = sq.removePiece();
		int player = p.getPlayer();
		if (player == WHITE) {
			takenWhitePieces.add(p);
		} else {
			takenBlackPieces.add(p);
		}
		p.setTaken(true);
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
	 * @param p the location of a selected piece
	 * @return a list of valid moves. (Null if a piece wasn't found, or if the input is invalid)
	 */
	public List<String> getValidMoves(Piece p) {
		List<String> moves = null;
		if (p != null) {
			moves = p.getValidMoves();
		}
		Iterator<String> iter = moves.iterator();
		while (iter.hasNext()) {
			String move = iter.next();
			if (willResultInCheck(p.getLocation(), move)) {
				iter.remove();
			}
		}
		if (p instanceof King) {
			King k = (King) p;
			checkForCastling(k, moves);
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
	
	public void checkForCastling(King k, List<String> moves) {
		if (!k.hasMoved()) {
			int x = k.getX();
			int y = k.getY();
			int opponent = opponent(k.getPlayer());
            if (!board.squareIsThreatened(x, y, opponent)) {
                Square leftCastle = board.getSquare(x - 2, y);
                Square rightCastle = board.getSquare(x + 2, y);

                if (!board.getSquare(x - 1, y).hasPiece() && !board.getSquare(x - 2, y).hasPiece() && !board.getSquare(x - 3, y).hasPiece()) {
                    if (board.getSquare(x - 4, y).hasPiece()) {
                        if (!board.getSquare(x - 4, y).getPiece().hasMoved()) {
                            if (!board.squareIsThreatened(x - 1, y, opponent) && !board.squareIsThreatened(x - 2, y, opponent)) {
                                moves.add(leftCastle.getLoc());
                            }
                        }
                    }
                }
                if (!board.getSquare(x + 1, y).hasPiece() && !board.getSquare(x + 2, y).hasPiece()) {
                    if (board.getSquare(x + 3, y).hasPiece()) {
                        if (!board.getSquare(x + 3, y).getPiece().hasMoved()) {
                            if (!board.squareIsThreatened(x + 1, y, opponent) && !board.squareIsThreatened(x + 2, y, opponent)) {
                                moves.add(rightCastle.getLoc());
                            }
                        }
                    }
                }
            }
        }
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
		Piece takenPiece = dest.removePiece();
		if (takenPiece != null) {
			takenPiece.setTaken(true);
		}
		dest.placePiece(p);
		boolean check = isInCheck(p.getPlayer());
		dest.removePiece();
		if (takenPiece != null) {
			takenPiece.setTaken(false);
			dest.placePiece(takenPiece);
		}
		src.placePiece(p);
		return check;
	}
	
	public Piece getPiece(String loc) {
		return board.getPiece(loc);
	}
	
	/**
	 * Returns true if the specified player has no moves left
	 * @return true if the specified player has no moves left
	 */
	public boolean noMovesLeft(int player) {
		return false;
	}
}
