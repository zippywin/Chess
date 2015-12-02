import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zippy on 25/11/2015.
 * The Bishop is a flexible piece in chess.
 * It can move along any of the diagonal directions, any number of spaces.
 */
public class Bishop implements Piece {
    private Chessboard chessboard;
    private int colour;
    private int x;
    private int y;
    private boolean hasMoved;
    
    public Bishop (Chessboard chessboard, int x, int y, int colour) {
        this.x = x;
        this.y = y;
        this.chessboard = chessboard;
        this.colour = colour;
        hasMoved = false;
    }

    @Override
    public String getLocation() {
        char file = 'A';
        file += x;
        int rank = y+1;
    	return ""+file+rank;
    }

    @Override
    public boolean isValidMove(int x, int y) {
        return true;
    }

    @Override
    public List<String> getValidMoves() {

        List<String> possibleMoves = new ArrayList<String>();
        List<Square> emptySquares = new ArrayList<Square>();

        emptySquares.addAll(chessboard.getEmptySquaresToPiece(x, y, Chessboard.NORTHEAST));
        emptySquares.addAll(chessboard.getEmptySquaresToPiece(x, y, Chessboard.SOUTHEAST));
        emptySquares.addAll(chessboard.getEmptySquaresToPiece(x, y, Chessboard.SOUTHWEST));
        emptySquares.addAll(chessboard.getEmptySquaresToPiece(x, y, Chessboard.NORTHWEST));

        for (Square square : emptySquares) {
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
    public boolean isHasMoved() {
        return hasMoved;
    }

}
