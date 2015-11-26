import java.util.List;

/**
 * Created by Zippy on 25/11/2015.
 * The Bishop is a flexible piece in chess.
 * It can move along any of the diagonal directions, any number of spaces.
 */
public class Bishop implements Piece {
    private String location;
    private Chessboard chessboard;
    private int colour;

    public Bishop (Chessboard chessboard, String location, int colour) {
        this.location = location;
        this.chessboard = chessboard;
        this.colour = colour;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public boolean isValidMove(char file, int rank) {
        return true;
    }

	@Override
	public void move(char file, int rank) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

}
