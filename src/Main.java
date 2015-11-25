/**
 * The main class which handles the i/o 
 * @author Chris
 *
 */
public class Main {

    public static void main(String[] args) {
    	unitTests();
    	Game g = new Game();
    	while (g.gameIsOver()==false) {
    		//output chessboard to terminal
    		//get user input;
    	}
    }
    
    public static void unitTests() {
    	Chessboard board = new Chessboard();
    	
    	System.out.println("--Testing squares--");
    	
    	System.out.println("  Creating an empty square");
    	Square sq = new Square('A',1);
    	assert(sq.getFile()=='A');
    	assert(sq.getRank()==1);
    	assert(sq.hasPiece()==false);
    	assert(sq.getLoc().equals("A1"));
    	System.out.println("  square:"+sq);
    	
    	Pawn pawn = new Pawn(board, "A1", Game.WHITE);
    	System.out.println("  Moving a pawn onto it");
    	sq.placePiece(pawn);
    	System.out.println("  square:"+sq);
    	assert(sq.hasPiece()==true);
    	assert(sq.getPiece()==pawn);
    	assert(sq.toString().equals("p"));
    	sq.removePiece();
    	System.out.println("  Removing it");
    	assert(sq.hasPiece()==false);
    	assert(sq.getPiece()==null);
    	System.out.println("  square:"+sq);
    	
    	System.out.println("  Moving a knight from somewhere else onto it");
    	Knight knight = new Knight(board,"B3", Game.BLACK);
    	knight.move("A1"); //Not tested
    	sq.placePiece(knight);
    	System.out.println("  square:"+sq);
    	assert(sq.hasPiece()==true);
    	assert(sq.getPiece() instanceof Knight);
    	assert(!(sq.getPiece() instanceof Pawn));
    	assert(sq.getFile()=='A' && sq.getRank()==1);
    	sq.removePiece();
    	assert(sq.hasPiece()==false);
    	assert(sq.getPiece()==null);
    	
    	System.out.println("Passed!");
    	
    	System.out.println("--Testing the creation of a chessboard--");
    	board.printBoard();
    	
    	System.out.println("Passed!");
    	
    	System.out.println("All tests passed! You are awesome!");
    }
}
