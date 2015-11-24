/**
 * An interface for pieces
 * @author Chris
 *
 */
public interface Piece {

	/**
	 * Returns the location of the piece
	 * represented in chess notation
	 * @return the location of the piece
	 */
	public String getLocation();
	
	/**
	 * Returns an array of valid moves
	 * @return an array of valid moves
	 */
	public String[] getValidMoves();
}
