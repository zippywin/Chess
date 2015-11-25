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

    public Pawn (Chessboard chessboard, String location) {
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
