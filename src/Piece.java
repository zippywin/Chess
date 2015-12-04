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
     * Returns a list of all the valid squares a piece can move to.
     * @return  list of squares a piece can move to
     */
	public List<String> getValidMoves();
	
	/**
	 * Moves the piece to the provided square
	 * Assuming that it is a valid move
     * Specifically, updates the Piece's location field
	 * @param x the 'file' coordinate from A-H
     * @param y the 'rank' coordinate from 1-8
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

    /**
     * Returns whether or not the piece has moved
     * @return true if the piece has moved, false otherwise
     */
    public boolean hasMoved();
    
    /**
     * Returns true if this piece has been taken
     * @return true if this piece has been taken
     */
    public boolean hasBeenTaken();
    
    /**
     * Sets the taken status of this piece to the specified value
     * @param taken the status to set to
     */
    public void setTaken(boolean taken);
}
