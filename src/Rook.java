import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zippy on 25/11/2015.
 * The Rook is a major piece in chess.
 * - Can move in any of the four cardinal directions, any number of spaces.
 * - It is involved in the process of castling.
 */
public class Rook implements Piece {
    private Chessboard chessboard;
    private int colour;
    private int x;
    private int y;
    private boolean hasMoved;
    private boolean taken;
    
    public Rook (Chessboard chessboard, int x, int y, int colour) {
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

        List<String> possibleMoves = new ArrayList<String>();
        List<Square> emptySquares = new ArrayList<Square>();
        
        if (this.hasBeenTaken() == false) {
	        emptySquares.addAll(chessboard.getEmptySquaresToPiece(x, y, Chessboard.NORTH));
	        emptySquares.addAll(chessboard.getEmptySquaresToPiece(x, y, Chessboard.EAST));
	        emptySquares.addAll(chessboard.getEmptySquaresToPiece(x, y, Chessboard.SOUTH));
	        emptySquares.addAll(chessboard.getEmptySquaresToPiece(x, y, Chessboard.WEST));
	
	        for (Square square : emptySquares) {
	            if (!square.hasPiece()) {
	                possibleMoves.add(square.getLoc());
	            } else if (square.getPiece().getPlayer() != colour) {
	                possibleMoves.add(square.getLoc());
	            }
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
