/**
 * Created by Zippy on 25/11/2015.
 * The Bishop is a flexible piece in chess.
 * It can move along any of the diagonal directions, any number of spaces.
 */
public class Bishop implements Piece {
    private String location;
    private Chessboard chessboard;

    public Bishop (Chessboard chessboard, String location) {
        this.location = location;
        this.chessboard = chessboard;
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
