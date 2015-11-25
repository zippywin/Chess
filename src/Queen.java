/**
 * Created by Zippy on 24/11/2015.
 * The Queen is a powerful piece in chess.
 * - It can move in any of the eight directions for any number of spaces.
 */
public class Queen implements Piece {
    private String location;
    private Chessboard chessboard;
    private int colour;

    public Queen(Chessboard chessboard, String location, int colour) {
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
