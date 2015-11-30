import java.util.LinkedList;
import java.util.List;


/**
 * A class to represent a single square on a chessboard
 * @author Chris
 *
 */
public class Square {
	
	private Piece piece;
	private int x;
	private int y;
	private char file;
	private int rank;
	private List<Piece> potentialMovers;
	
	/**
	 * Creates a square at the chosen location
	 * @param x the letter representing the column of the square
	 * @param y the number representing the row of the square
	 */
	public Square(int x, int y) {
		this.x=x;
		this.y=y;
		this.file = 'A';
		this.file += x;
		this.rank = y+1;
		potentialMovers = new LinkedList<Piece>();
	}
	
	public Square(char file, int rank) {
		this.file = file;
		this.rank = rank;
		this.x = file - 'A';
		this.y = rank - 1;
		potentialMovers = new LinkedList<Piece>();
	}
	
	/**
	 * Places a chess piece on this square.
	 * If there is already a piece on this square, nothing happens.
	 * @param piece the piece to be placed
	 */
	public void placePiece(Piece piece) {
		if (this.piece == null) {
			this.piece = piece;
		}
	}
	
	/**
	 * Removes the piece on this square
	 * @return the piece being removed
	 */
	public Piece removePiece() {
		Piece p = this.piece;
		this.piece = null;
		return p;
	}
	
	/**
	 * Returns true if this square has a piece.
	 * @return true if this square has a piece.
	 */
	public boolean hasPiece() {
		if (piece != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the piece currently occupying this square, or null if there isn't any
	 * @return the piece currently occupying this square.
	 */
	public Piece getPiece() {
		return piece;
	}
	
	@Override
	public String toString() {
		String s = ".";
		if (piece != null) {
			if (piece instanceof Pawn) {
				s = "p";
			} else if (piece instanceof Rook) {
				s = "R";
			} else if (piece instanceof Bishop) {
				s = "B";
			} else if (piece instanceof Knight) {
				s = "N";
			} else if (piece instanceof King) {
				s = "K";
			} else if (piece instanceof Queen) {
				s = "Q";
			}
		}
		return s;
	}

	/**
	 * Returns the square's coordinates on the chessboard
	 * as a string in chess notation. E.G. A1
	 * @return the square's location on the chessboard
	 */
	public String getLoc() {
		return ""+getFile()+getRank();
	}
	
	/**
	 * Returns the column represented as a letter from A-H
	 * @return the file of the square
	 */
	public char getFile() {
		return file;
	}
	
	/**
	 * Returns the row the square is on
	 * @return the row the square is on
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Returns the x coordinates of this square
	 * @return the x coordinates of this square
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y coordinates of this square
	 * @return the y coordinates of this square
	 */
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Square) {
			Square other = (Square)obj;
			if (this.file==other.file && this.rank==other.rank) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return file+23*rank;
	}
	
	/**
	 * Tells this square that it is currently being threatened by a piece
	 * Or, in other words, that a piece might potentially move onto this square
	 * @param p the threatening piece
	 */
	public void threaten(Piece p) {
		potentialMovers.add(p);
	}
	
	/**
	 * Removes piece p as a potential unit that could've moved onto this square
	 * @param p the piece to be removed
	 */
	public void removeThreat(Piece p) {
		potentialMovers.remove(p);
	}
	
	/**
	 * Returns true if this square is being threatened by one or more pieces
	 * controlled by the specified player.
	 * @param player the specified player
	 * @return true if this square is being threatened by one or more pieces
	 */
	public boolean isThreatenedBy(int player) {
		if (!potentialMovers.isEmpty()) {
			for (Piece p: potentialMovers) {
				if (p.getPlayer()==player) {
					return true;
				}
			}
		}
		return false;
	}
}
