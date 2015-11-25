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
    public String[] getValidMoves() {
        return new String[0];
    }
}
