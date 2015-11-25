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
	 * Returns true if the piece can move to the given square
	 * @param square the square to move to
	 * @return true if moving to the square is valid 
	 */
	public boolean isValidMove(String square);
	
	/**
	 * Moves the piece to the provided square
	 * Assuming that it is a valid move
	 * @param square the square to move to
	 */
	public void move(String square);
	
	/**
	 * Returns the player that owns this piece as an int
	 * 0 - White, 1 - Black. 
	 * @return the player that owns this piece
	 */
	public int getPlayer();
}
