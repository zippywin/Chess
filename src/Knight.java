import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zippy on 25/11/2015.
 * The Knight is a versatile piece in Chess. How it moves is rather unique.
 * - It moves in an 'L' shape: two spaces in a 4-cardinal direction, then 1 space perpendicular to the that direction.
 * - It bypasses 'block' - cannot impede it's movement with another piece in the way
 */
public class Knight implements Piece{
    private Chessboard chessboard;
    private int colour;
    private int x;
    private int y;
    private boolean hasMoved;
    private boolean taken;

    public Knight (Chessboard chessboard, int x, int y, int colour) {
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
    public List<String> getValidMoves() {
    	List<String> validMoves = new LinkedList<String>();
    	if (this.hasBeenTaken() == false) {
	    	List<Square> possibleSquares = new LinkedList<Square>();
	    	
	    	possibleSquares.add(chessboard.getSquare(x+2,y+1));
	    	possibleSquares.add(chessboard.getSquare(x-2,y+1));
	    	possibleSquares.add(chessboard.getSquare(x-2,y-1));
	    	possibleSquares.add(chessboard.getSquare(x+2,y-1));
	    	possibleSquares.add(chessboard.getSquare(x+1,y+2));
	    	possibleSquares.add(chessboard.getSquare(x-1,y+2));
	    	possibleSquares.add(chessboard.getSquare(x-1,y-2));
	    	possibleSquares.add(chessboard.getSquare(x+1,y-2));
	    	
	    	for (Square sq : possibleSquares) {
	    		if (sq != null && (!sq.hasPiece() || sq.getPiece().getPlayer() != colour)) {
	    			validMoves.add(sq.getLoc());
	    		}
	    	}
    	}
    	return validMoves;
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
