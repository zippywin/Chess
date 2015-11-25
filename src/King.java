/**
 * Created by Zippy on 24/11/2015.
 * - The King is a special piece in chess. It has the following special properties:
 * - The King can move one space in any square surrounding it (including diagonals)
 * - The King has the ability to castle (that comes with its own set of rules)
 * - The King cannot move into check, making the move invalid.
 * - If the King is in check, it must be put out of check on that turn.
 * - If the King is in check, and it does not have any valid moves that put it out of check,
 * it is checkmate and the game ends.
 */
public class King implements Piece {
    private String location;
    private Chessboard chessboard;
    private int colour;

    public King(Chessboard chessboard, String location, int colour) {
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
