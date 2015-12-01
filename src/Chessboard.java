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
	 * Given the coordinates of a square, returns the piece on that square
	 * @param x the x coordinate of the square
	 * @param y the y coordinates of the square
	 * @return the piece on that square, if any. Null if none exists.
	 */
	public Piece getPiece(int x, int y) {
		Piece p = null;
		Square sq = getSquare(x,y);
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
		Square sq = getSquare(x,y);
		String loc = sq.getLoc();
		if (sq != null) {
			for (Piece p : getPiecesOwnedBy(player)) {
				if (p.getValidMoves().contains(loc)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns a list of empty squares up to and including the first square to contain a unit
	 * @param x the x-coordinate to start from  
	 * @param y the y-coordinate to start from
	 * @param direction the direction to move in
	 * @return a list of empty squares up to and including the first square to contain a unit
	 */
	public List<Square> getEmptySquaresToPiece(int x, int y, int direction) {
		List<Square> listOfSquares = new LinkedList<Square>();
		boolean pieceFound = false;
		Square curr = getSquare(x,y);
		curr = getNextSquare(curr, direction);
		while (!pieceFound && curr != null) {
			listOfSquares.add(curr);
			if (curr.hasPiece()) {
				pieceFound = true;
			}
			curr = getNextSquare(curr, direction);
		}
		return listOfSquares;
	}
	
	/**
	 * Given a square and a direction, returns the square directly adjacent to it, 
	 * in the given direction
	 * @param sq the specified square
	 * @param direction the direction to go in
	 * @return the square next to it in the given direction. Null if it doesn't exist.
	 */
	public Square getNextSquare(Square sq, int direction) {
		int x = sq.getX();
		int y = sq.getY();
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
		return getSquare(x,y);
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
	
	
}
