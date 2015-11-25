
/**
 * A class to represent a single square on a chessboard
 * @author Chris
 *
 */
public class Square {
	
	public Piece piece;
	public char file;
	public int rank;
	
	/**
	 * Creates a square at the chosen location
	 * @param file the letter representing the column of the square
	 * @param rank the number representing the row of the square
	 */
	public Square(char file, int rank) {
		this.file=file;
		this.rank=rank;
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
	 */
	public void removePiece() {
		this.piece = null;
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
		return ""+file+rank;
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
}
