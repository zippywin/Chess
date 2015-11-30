import java.util.LinkedList;
import java.util.List;
import java.util.regex.*;

/**
 * A class to represent the chessboard
 * The coordinates used by the chessboard is in x-y coordinates space,
 * 0-indexed. 
 * @author Chris
 *
 */
public class Chessboard {
	
	private Square[][] board;
	
	private King blackKing;
	private King whiteKing;
	
	private List<Piece> blackPieces;
	private List<Piece> whitePieces;
	
	public static int NORTH = 0;
	public static int NORTHEAST = 1;
	public static int EAST = 2;
	public static int SOUTHEAST = 3;
	public static int SOUTH = 4;
	public static int SOUTHWEST = 5;
	public static int WEST = 6;
	public static int NORTHWEST = 7;
	
	/**
	 * Creates an empty chessboard.
	 */
	public Chessboard() {
		board = new Square[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new Square(i,j);
			}
		}
		whitePieces = new LinkedList<Piece>();
		blackPieces = new LinkedList<Piece>();
	}
	
	/**
	 * Initializes the chessboard to the starting state of a game.
	 */
	public void init() {
		//Placing pawns
		for (int i = 0; i < 8; i++) {
			place(new Pawn(this,i,1,Game.WHITE),i,1);
			place(new Pawn(this,i,6,Game.BLACK),i,6);
		}
		//Initing the back row
		whiteKing = new King(this,3,0,Game.WHITE);
		blackKing = new King(this,3,7,Game.BLACK);
		
		place(new Rook(this,"A1",Game.WHITE),0,0);
		place(new Knight(this,"B1",Game.WHITE),1,0);
		place(new Bishop(this,"C1",Game.WHITE),2,0);
		place(new Queen(this,"D1",Game.WHITE),3,0);
		place(whiteKing,4,0);
		place(new Bishop(this,"F1",Game.WHITE),5,0);
		place(new Knight(this,"G1",Game.WHITE),6,0);
		place(new Rook(this,"H1",Game.WHITE),7,0);
		
		//Initing the front row
		place(new Rook(this,"A8",Game.BLACK),0,7);
		place(new Knight(this,"B8",Game.BLACK),1,7);
		place(new Bishop(this,"C8",Game.BLACK),2,7);
		place(new Queen(this,"D8",Game.BLACK),3,7);
		place(blackKing,4,7);
		place(new Bishop(this,"F8",Game.BLACK),5,7);
		place(new Knight(this,"G8",Game.BLACK),6,7);
		place(new Rook(this,"H8",Game.BLACK),7,7);
	}
	
	/**
	 * Given a square, returns the piece on that square
	 * @param square the square being queried
	 * @return the piece on that square, if any. Null if none exists
	 */
	public Piece getPiece(String square) {
		Piece p = null;
		Square sq = getSquare(square);
		if (sq != null) {
			p = sq.getPiece();
		}
		return p;
	}
	
	/**
	 * Places a piece on the board
	 * @param p the piece to be placed
	 * @param x the x coordinate of the square to place
	 * @param y the y coordinate of the square to place
	 */
	public void place(Piece p, int x, int y) {
		board[x][y].placePiece(p);
		if (p.getPlayer()==Game.WHITE) {
			whitePieces.add(p);
		} else {
			blackPieces.add(p);
		}
	}
	
	/**
	 * Given a location of a square in chess notation,
	 * returns the square object corresponding to it.
	 * @param loc the location of a square on a chessboard
	 * @return the square object corresponding to that loc. null if not found.
	 */
	public Square getSquare(String loc) {
		Pattern p = Pattern.compile("([A-Ha-h])([1-8])");
		Matcher m = p.matcher(loc);
		Square sq = null;
		if (m.matches()) {
			char file = m.group(1).charAt(0);
			int x;
			if (file >= 'a' && file <= 'h') {
				x = file-'a';
			} else {
				x = file-'A';
			}
			int y = Integer.parseInt(m.group(2))-1;
			sq = board[x][y];
		} else {
			System.out.println("Error: Invalid chess notation given: "+loc);
		}
		return sq;
	}
	
	/**
	 * Given a location of a square's x and y coordinates,
	 * returns the corresponding square object 
	 * @param x the square's x coordinates
	 * @param y the square's y coordinates
	 * @return the square object corresponding to the specified file and rank
	 */
	public Square getSquare(int x, int y) {
		Square sq = null;
		if (0 <= x && x <= 7 && 0 <= y && y <= 7) {
			sq = board[x][y];
		}
		return sq;
	}
	
	/**
	 *  Given two squares, moves the piece in square 1 to square2
	 *  Does not check validity
	 * @param square1 the square containing the piece
	 * @param square2 the square to move to
	 */
	public void makeMove(String square1, String square2) {
		Piece p = getSquare(square1).removePiece();
		//Code to update the squares the piece used to threaten
		getSquare(square2).placePiece(p);
		//Code to update the squares the piece is now threatening.
	}

	/**
	 * Prints out the chessboard
	 */
	public void printBoard() {
		System.out.println("  ABCDEFGH  ");
		System.out.println("  --------  ");
		for (int rank = 8; rank > 0; rank--) {
			System.out.print(rank+"|");
			for (int file = 0; file < 8; file++) {
				System.out.print(board[file][rank-1]);
			} 
			System.out.println("|"+rank);
		}
		System.out.println("  --------  ");
		System.out.println("  ABCDEFGH  ");
	}
	
	/**
	 * Returns true if the square at the specified coordinates is being threatened by a piece
	 * owned by the specified player
	 * @param x the x-coordinate of the square
	 * @param y the y-coordinate of the square
	 * @param player the player owning the piece - integer defined by Game. 
	 * @return true if the square is being threatened
	 */
	public boolean squareIsThreatened(int x, int y, int player) {
		boolean threatened = false;
		Square sq = getSquare(x,y);
		if (sq != null) {
			for (Piece p : getPiecesOwnedBy(player)) {
				if (p.isValidMove(x, y));
			}
		}
		return threatened;
	}
	
	public List<Square> getEmptySquares(int x, int y, int direction) {
		List<Square> emptySquares = new LinkedList<Square>();
		boolean pieceFound = false;
		while (!pieceFound && 0 < y && y < 7 && 0 < x && x < 7) {
			if (direction == NORTH) {
				y++;
			} else if (direction == NORTHEAST) {
				y++;
				x++;
			} else if (direction == EAST) {
				x++;
			} else if (direction == SOUTHEAST) {
				x++;
				y--;
			} else if (direction == SOUTH) {
				y--;
			} else if (direction == SOUTHWEST) {
				y--;
				x--;
			} else if (direction == WEST) {
				x--;
			} else if (direction == NORTHWEST) {
				y++;
				x--;
			}
			Square sq = getSquare(x,y);
			if (sq.hasPiece()) {
				emptySquares.add(sq);
			} else {
				pieceFound = true;
			}
		}
		return emptySquares;
	}
	
	/**
	 * Returns a list of pieces owned by the specified player
	 * currently on the board.
	 * @param player the specified player
	 * @return a list of pieces owned by the specified player.
	 */
	public List<Piece> getPiecesOwnedBy(int player) {
		if (player == Game.WHITE) {
			return whitePieces;
		} else {
			return blackPieces;
		}
	}
	
	/**
	 * Returns true if the current player is in check.
	 * @param player the player to check for check. Defined by Game
	 * @return true if the player is in check.
	 */
	public boolean isInCheck(int player) {
		King king;
		int opponent;
		if (player == Game.WHITE) {
			king = whiteKing;
			opponent = Game.BLACK;
		} else {
			king = blackKing;
			opponent = Game.WHITE;
		}
		return squareIsThreatened(king.getX(),king.getY(),opponent);
	}
}
