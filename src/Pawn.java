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
    
    public Pawn (Chessboard chessboard, int x, int y, int colour) {
        this.x = x;
        this.y = y;
        this.chessboard = chessboard;
        this.colour = colour;
        hasMoved = false;
    }
    
    @Override
    public String getLocation() {
        char file = 'A';
        file += x - 1;
        int rank = y+1;
    	return ""+file+rank;
    }
    
    @Override
    public boolean isValidMove(int x, int y) {
        return true;
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
	public List<String> getValidMoves() {
		List<String> validMoves = new LinkedList<String>();
		if (colour == Game.WHITE) {
			validMoves.add(chessboard.getSquare(x,y+1).getLoc());
			if (!hasMoved) {
				validMoves.add(chessboard.getSquare(x,y+2).getLoc());
			}
		} else {
			validMoves.add(chessboard.getSquare(x,y-1).getLoc());
			if (!hasMoved) {
				validMoves.add(chessboard.getSquare(x,y-2).getLoc());
			}
		}
		return validMoves;
	}
}
