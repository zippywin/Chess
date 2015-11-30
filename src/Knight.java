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

    public Knight (Chessboard chessboard, int x, int y, int colour) {
        this.x = x;
        this.y = y;
        this.chessboard = chessboard;
        this.colour = colour;
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
    public List<String> getValidMoves() {
        return null;
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

}
