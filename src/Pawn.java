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
    private String location;
    private Chessboard chessboard;
    private int colour;

    public Pawn (Chessboard chessboard, String location, int colour) {
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
