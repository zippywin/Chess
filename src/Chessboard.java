import java.util.regex.*;

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
		//Creating squares
		for (int i = 0; i < 8; i++) {
			char file = 'A';
			file += i;
			for (int j = 0; j < 8; j++) {
				int rank = j+1;
				board[i][j] = new Square(file,rank);
			}
		}
		//Placing pawns
		for (int i = 0; i < 8; i++) {
			char rank = 'A';
			rank += i;
			String loc = rank+"2";
			board[i][1].placePiece(new Pawn(this,loc,Game.WHITE));
			loc = rank+"7";
			board[i][6].placePiece(new Pawn(this,loc,Game.BLACK));
		}
		//Initing the back row
		board[0][0].placePiece(new Rook(this,"A1",Game.WHITE));
		board[1][0].placePiece(new Knight(this,"B1",Game.WHITE));
		board[2][0].placePiece(new Bishop(this,"C1",Game.WHITE));
		board[3][0].placePiece(new King(this,'D',1,Game.WHITE));
		board[4][0].placePiece(new Queen(this,"E1",Game.WHITE));
		board[5][0].placePiece(new Bishop(this,"F1",Game.WHITE));
		board[6][0].placePiece(new Knight(this,"G1",Game.WHITE));
		board[7][0].placePiece(new Rook(this,"H1",Game.WHITE));
		//Initing the front row
		board[0][7].placePiece(new Rook(this,"A8",Game.BLACK));
		board[1][7].placePiece(new Knight(this,"B8",Game.BLACK));
		board[2][7].placePiece(new Bishop(this,"C8",Game.BLACK));
		board[3][7].placePiece(new King(this,'D',8,Game.BLACK));
		board[4][7].placePiece(new Queen(this,"E8",Game.BLACK));
		board[5][7].placePiece(new Bishop(this,"F8",Game.BLACK));
		board[6][7].placePiece(new Knight(this,"G8",Game.BLACK));
		board[7][7].placePiece(new Rook(this,"H8",Game.BLACK));
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
	 * Given a location of a square's file and rank, 
	 * returns the square object corresponding to it.
	 * @param file the file of the square
	 * @param rank the rank of the square
	 * @return the square object corresponding to the specified file and rank
	 */
	public Square getSquare(char file, int rank) {
		return getSquare(""+file+rank);
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
		for (int rank = 7; rank >= 0; rank--) {
			for (int file = 0; file < 8; file++) {
				System.out.print(board[file][rank]);
			} 
			System.out.println();
		}
	}
	
	/**
	 * Returns true if the square at the specified coordinates is being threatened by a piece
	 * owned by the specified player
	 * @param file the file of the square
	 * @param rank the rank of the square
	 * @param player the player owning the piece - integer defined by Game. 
	 * @return true if the square is being threatened
	 */
	public boolean squareIsThreatened(char file, int rank, int player) {
		return getSquare(file,rank).isThreatenedBy(player);
	}
}
