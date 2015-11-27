import java.util.List;

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
	 * @param file the 'file' coordinate
     * @param rank the 'rank' coordinate
	 * @return true if moving to the square is valid 
	 */
	public boolean isValidMove(int x, int y);
	
	public List<String> getValidMoves();
	
	/**
	 * Moves the piece to the provided square
	 * Assuming that it is a valid move
     * Specifically, updates the Piece's location field
	 * @param file the 'file' coordinate from A-H
     * @param rank the 'rank' coordinate from 1-8
	 */
	public void move(int x, int y);
	
	/**
	 * Returns the player that owns this piece as an int
	 * 0 - White, 1 - Black. 
	 * @return the player that owns this piece
	 */
	public int getPlayer();
	
	/**
	 * Returns the x coordinate of this piece.
	 * @return the x coordinate of this piece/
	 */
	public int getX();
	
	/**
	 * Returns the y coordinate of this piece
	 * @return the y coordinate of this piece
	 */
	public int getY();
}
