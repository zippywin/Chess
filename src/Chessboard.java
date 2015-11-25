/**
 * A class to represent the chessboard
 * @author Chris
 *
 */
public class Chessboard {
	
	private Square[][] board;
	
	/**
	 * Initializes a default chessboard
	 */
	public Chessboard() {
		board = new Square[8][8];
		for (int i = 0; i < 8; i++) {
			char file = 'A';
			file += i;
			for (int j = 0; j < 8; j++) {
				int rank = j;
				board[i][j] = new Square(file,rank);
			}
		}
		
	}
	
	/**
	 * Given a square, returns the piece on that square
	 * @param square the square being queried
	 * @return the piece on that square, if any. Null if none exists
	 */
	public Piece getPiece(String square) {
		return null;
	}

	/**
	 *  Given two squares, moves the piece in square 1 to square2
	 */
	public void makeMove(String square1, String square2) {
		
	}

	/**
	 * Prints out the chessboard
	 */
	public void printBoard() {
		
	}
	
	
}
