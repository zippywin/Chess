import java.util.List;
import java.util.Scanner;

/**
 * The main class which handles the i/o 
 * @author Chris
 *
 */
public class Main {

    public static void main(String[] args) {
    	unitTests();
    	Game g = new Game();
    	Scanner sc = new Scanner(System.in);
    	while (g.gameIsOver()==false) {
    		g.printGame();
    		queryInput(g,sc);
    	}
    }
    
    /**
	 * Queries the active player for their move
	 * @param sc A scanner from which input is accepted.
	 */
	public static void queryInput(Game g, Scanner sc) {
		String colour;
		if (g.getActivePlayer() == Game.WHITE) {
			colour = "White";
		} else {
			colour = "Black";
		}
		boolean moveMade = false;
		while (moveMade == false) {
			System.out.print(colour+" Player. Choose a piece to move:");
			String src = sc.nextLine();
			List<String> validMoves = g.getValidMoves(src);
			//If validMoves is null, then no piece was found, or the input was invalid
			if (validMoves != null) {
			    System.out.println("Valid Moves:" + validMoves);
			    boolean validInput = false;
			    //Loop until user inputs a valid input
		    	while (validInput == false) {
		    		System.out.print("Choose a square to move to (q to back): ");
				    String dest = sc.nextLine();
				    if (dest.equals("q")) {
				    	validInput = true; //Exits inner loop and asks for another piece
				    } else if (validMoves.contains(dest)) {
				    	g.makeMove(src, dest);
				    	validInput = true;
				    	moveMade = true;
				    } else {
				    	System.out.println("Error, invalid move");
				    }
			    }
		    } else {
				System.out.println("No piece found.");
				//Asks again
			}
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
    	
    	System.out.println("  Threatening the square with a knight");
    	Knight knight = new Knight(board,"B3", Game.BLACK);
    	sq.threaten(knight);
    	assert(sq.isThreatenedBy(Game.BLACK));
    	assert(!sq.isThreatenedBy(Game.WHITE));
    	sq.removeThreat(knight);
    	assert(!sq.isThreatenedBy(Game.BLACK));
    	assert(!sq.isThreatenedBy(Game.WHITE));
    	
    	System.out.println("  Moving the knight onto it.");
    	knight.move('A',1); //Not tested
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
    	assert(board.getSquare("A5").getFile()=='A');
    	assert(board.getSquare("c6").getFile()=='C');
    	assert(board.getSquare("E2").getRank()==2);
    	assert(board.getSquare("A1").getRank()==1);
    	assert(board.getPiece("A1")!= null);
    	assert(board.getPiece("A1") instanceof Rook);
    	assert(board.getPiece("E1") instanceof Queen);
    	assert(board.getPiece("C2") instanceof Pawn);
    	assert(board.getPiece("A8") instanceof Rook);
    	
    	board.makeMove("D2", "D4");
    	System.out.println("  Moving a pawn from D2 to D4");
    	assert(board.getPiece("D2") == null);
    	assert(board.getPiece("D4") instanceof Pawn);
    	
    	board.printBoard();
    	
    	System.out.println("Passed!");
    	
    	System.out.println("All tests passed! You are awesome!");
    }
}
