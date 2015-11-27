import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zippy on 24/11/2015.
 * - The King is a special piece in chess. It has the following special properties:
 * - The King can move one space in any square surrounding it (including diagonals)
 * - The King has the ability to castle (that comes with its own set of rules)
 * - The King cannot move into check, making the move invalid.
 * - If the King is in check, it must be put out of check on that turn.
 * - If the King is in check, and it does not have any valid moves that put it out of check,
 * it is checkmate and the game ends.
 */
public class King implements Piece {
    private char file;
    private int rank;
    private int x;
    private int y;
    private Chessboard chessboard;
    private int colour;

    /**
     * Creates a king at the specified 0-indexed x-y coordinates,
     * @param chessboard the board the piece belongs to
     * @param x the x coordinate of the piece
     * @param y the y coordinate of the piece
     * @param colour the colour of the player that owns this piece
     */
    public King(Chessboard chessboard, int x, int y, int colour) {
        this.x = x;
        this.y = y;
        this.rank = rank;
        this.chessboard = chessboard;
        this.colour = colour;
    }

    @Override
    public String getLocation() {
        return "" + file + rank;
    }

    @Override
    public boolean isValidMove(char file, int rank) {
        /*
        Rough steps to check for valid move:
        Check if square is out of bounds
        Check if square's destination is already occupied by another piece of the same colour
        Check if square is within Piece's movement range
        Check for obstructions on the way to destination
        KING Exceptions:
        Check if the move will put it in check
        Check castle requirements
         */

        Piece destinationSquare = chessboard.getPiece(""+file+rank);
        List<Square> listOfSquares = new ArrayList<Square>();
        listOfSquares.add(new Square(this.file--, this.rank--));
        listOfSquares.add(new Square(this.file--, this.rank));
        listOfSquares.add(new Square(this.file--, this.rank++));
        listOfSquares.add(new Square(this.file, this.rank--));
        listOfSquares.add(new Square(this.file, this.rank));
        listOfSquares.add(new Square(this.file, this.rank++));
        listOfSquares.add(new Square(this.file++, this.rank--));
        listOfSquares.add(new Square(this.file++, this.rank));
        listOfSquares.add(new Square(this.file++, this.rank++));

        if (file > 'H' || file < 'A' || rank > 8 || rank < 1) {
            return false;

        } else if (destinationSquare != null && destinationSquare.getPlayer() == colour ) {
            return false;

        } else if (!listOfSquares.contains(new Square(file, rank))) {
            return false;
        } else {
            return true;
        }
    }

	@Override
	public void move(char file, int rank) {
		// TODO Auto-generated method stub
            this.file = file;
            this.rank = rank;
	}

    @Override
    public int getPlayer() {
        return colour;
    }

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
}
