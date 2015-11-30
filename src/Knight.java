import java.util.List;

/**
 * Created by Zippy on 25/11/2015.
 * The Knight is a versatile piece in Chess. How it moves is rather unique.
 * - It moves in an 'L' shape: two spaces in a 4-cardinal direction, then 1 space perpendicular to the that direction.
 * - It bypasses 'block' - cannot impede it's movement with another piece in the way
 */
public class Knight implements Piece{
    private String location;
    private Chessboard chessboard;
    private int colour;

    public Knight (Chessboard chessboard, String location, int colour) {
        this.location = location;
        this.chessboard = chessboard;
        this.colour = colour;
    }

    @Override
    public String getLocation() {
        return location;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPlayer() {
		return colour;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
