/**
 * Created by Zippy on 25/11/2015.
 * The Rook is a major piece in chess.
 * - Can move in any of the four cardinal directions, any number of spaces.
 * - It is involved in the process of castling.
 */
public class Rook implements Piece {
    private String location;
    private Chessboard chessboard;
    private int colour;

    public Rook (Chessboard chessboard, String location, int colour) {
        this.location = location;
        this.chessboard = chessboard;
        this.colour = colour;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public boolean isValidMove(String sq) {
        return true;
    }

	@Override
	public void move(String square) {
		// TODO Auto-generated method stub
		
	}
}
