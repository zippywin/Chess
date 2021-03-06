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
    private boolean hasMoved;
    private boolean taken;

    private List<Square> movementRange = new ArrayList<Square>(); //Stores basic logic of movement

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
        this.file = 'A';
        file += x-1;
        this.rank = y+1;
        this.chessboard = chessboard;
        this.colour = colour;
        hasMoved = false;
        taken = false;
        //createMovementRange();
    }

    /**
     * Helper function - creates the piece's unobstructed list of movement ranges
     */
    private void createMovementRange() {
        movementRange.add(new Square(this.file - 1, this.rank - 1));
        movementRange.add(new Square(this.file - 1, this.rank));
        movementRange.add(new Square(this.file - 1, this.rank + 1));
        movementRange.add(new Square(this.file, this.rank - 1));
        movementRange.add(new Square(this.file, this.rank + 1));
        movementRange.add(new Square(this.file + 1, this.rank - 1));
        movementRange.add(new Square(this.file + 1, this.rank));
        movementRange.add(new Square(this.file + 1, this.rank + 1));
    }

    @Override
    public String getLocation() {
    	char file = 'A';
        file += x;
        int rank = y+1;
    	return ""+file+rank;
    }

    @Override
    public List<String> getValidMoves() {
        List<String> possibleMoves = new ArrayList<String>();
        List<Square> adjacentSquares = new ArrayList<Square>();

        for (int i = -1; i < 2; i++) {
            if (x + i >= 0 && x + i < 8) {
                for (int j = -1; j < 2; j++) {
                    if (y + j >= 0 && y + j < 8) {
                        adjacentSquares.add(chessboard.getSquare(x + i, y + j));
                    }
                }
            }
        }

        for (Square square : adjacentSquares) {
            if (!square.hasPiece()) {
                possibleMoves.add(square.getLoc());
            } else if (square.getPiece().getPlayer() != colour) {
                possibleMoves.add(square.getLoc());
            }
        }

        return possibleMoves;
    }

	@Override
	public void move(int x, int y) {
            this.x = x;
            this.y = y;
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

    @Override
    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
	public boolean hasBeenTaken() {
		return taken;
	}

	@Override
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	
	@Override
	public void setLocation(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
