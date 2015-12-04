import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zippy on 25/11/2015.
 * The Pawn is a minor but essential piece in Chess. It is also one of the most complicated pieces.
 * - Can move only one square forward - torwards the opponent
 * - Can move 1 square to the adjacent diagonal if there is an enempy piece that can be captured
 * on that adjacent diagonal
 * - Can move two spaces forward on its first move
 * - Has the ability to en passant - comes with its own set of rules
 * - Reaching the opposing back rank allows it to promote to any other piece (other than Pawn and King)
 */
public class Pawn implements Piece{
    private Chessboard chessboard;
    private int colour;
    private boolean hasMoved;
    private int x;
    private int y;
    private boolean taken;
    
    public Pawn (Chessboard chessboard, int x, int y, int colour) {
        this.x = x;
        this.y = y;
        this.chessboard = chessboard;
        this.colour = colour;
        hasMoved = false;
        taken = false;
    }
    
    @Override
    public String getLocation() {
        char file = 'A';
        file += x;
        int rank = y+1;
    	return ""+file+rank;
    }

	@Override
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
		hasMoved = true;
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
	public List<String> getValidMoves() {
		List<String> validMoves = new LinkedList<String>();
		int x = this.x;
		int y = this.y;
		Square forward = null;
		Square doubleForward = null;
		if (colour == Game.WHITE) {
			y++;
			forward = chessboard.getSquare(x, y);
			doubleForward = chessboard.getSquare(x, y + 1);
		} else {
			y--;
			forward = chessboard.getSquare(x, y);
			doubleForward = chessboard.getSquare(x, y - 1);
		}
		
		if (forward != null && !forward.hasPiece()) {
			validMoves.add(forward.getLoc());
		}
		if (!hasMoved && doubleForward != null && !doubleForward.hasPiece()) {
			validMoves.add(doubleForward.getLoc());
		}
		
		Square leftDiagonal = chessboard.getSquare(x - 1, y);
		if (leftDiagonal != null && diagonalSquareValid(leftDiagonal) == true) {
			validMoves.add(leftDiagonal.getLoc());
		} 
		Square rightDiagonal = chessboard.getSquare(x + 1, y);
		if (rightDiagonal != null && diagonalSquareValid(rightDiagonal) == true) {
			validMoves.add(rightDiagonal.getLoc());
		}
		
		return validMoves;
	}
	
	public boolean diagonalSquareValid(Square diagonal) {
		if (diagonal != null) {
			if (diagonal.hasPiece() && diagonal.getPiece().getPlayer() == Game.opponent(colour)) {
				return true;
			} else if (diagonal.enPassantAvailable()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasBeenTaken() {
		return taken;
	}

	@Override
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
}
