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
    	int winner = g.getWinner();
    	if (winner == Game.NOONE) {
    		System.out.println("Stalemate! No-one wins!");
    	} else if (winner == Game.WHITE) {
    		System.out.println("Checkmate! The winner is White Player!");
    	} else {
    		System.out.println("Checkmate! The winner is Black Player!");
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
			System.out.print(colour+" Player. Choose a piece to move: ");
			String src = sc.nextLine();
			Piece p = g.getPiece(src);
			if (p == null) {
				System.out.println("No piece found.");
			} else if (p.getPlayer() != g.getActivePlayer()){
				System.out.println("That piece is not yours.");
			} else {
				List<String> validMoves = g.getValidMoves(p);
				//If validMoves is null, then no piece was found, or the input was invalid
				if (validMoves.size() == 0) {
					System.out.println("That piece has no valid moves. Choose another.");
				} else {	
				    System.out.println("Valid Moves:" + validMoves);
				    boolean validInput = false;
				    //Loop until user inputs a valid input
			    	while (validInput == false) {
			    		System.out.print("Choose a square to move to (q to back): ");
					    String dest = sc.nextLine();
					    dest = dest.toUpperCase();
					    if (dest.equals("Q")) {
					    	validInput = true; //Exits inner loop and asks for another piece
					    } else if (validMoves.contains(dest)) {
					    	g.makeMove(src, dest);
					    	validInput = true;
					    	moveMade = true;
					    } else {
					    	System.out.println("Error, invalid move");
					    }
				    }			
				}
			}
		}
	}
    
    public static void unitTests() {
    	Game g = new Game();
    	Chessboard board = g.getBoard();
    	
    	System.out.println("--Testing squares--");
    	
    	System.out.println("  Creating an empty square");
    	Square sq = new Square('A',1);
    	assert(sq.getFile()=='A');
    	assert(sq.getRank()==1);
    	assert(sq.getX()==0);
    	assert(sq.getY()==0);
    	assert(sq.hasPiece()==false);
    	assert(sq.getLoc().equals("A1"));
    	System.out.println("  square:"+sq);
    	
    	Square sq2 = new Square(3,3);
    	assert(sq2.getX()==3);
    	assert(sq2.getY()==3);
    	assert(sq2.getFile()=='D');
    	assert(sq2.getRank()==4);
    	assert(sq2.hasPiece()==false);
    	assert(sq2.isThreatenedBy(Game.WHITE)==false);
    	assert(sq2.isThreatenedBy(Game.BLACK)==false);
    	
    	Pawn pawn = new Pawn(board, 0,0, Game.WHITE);
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
    	Knight knight = new Knight(board,1,2, Game.BLACK);
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
    	assert(board.getPiece("D1") instanceof Queen);
    	assert(board.getPiece("C2") instanceof Pawn);
    	assert(board.getPiece("A8") instanceof Rook);
    	
    	g.makeMove("D2", "D4");
    	System.out.println("  Moving a pawn from D2 to D4");
    	assert(board.getPiece("D2") == null);
    	assert(board.getPiece(3,2) == null);
    	assert(board.getPiece("D4") instanceof Pawn);   	
    	
    	board.printBoard();
    	
    	System.out.println("Passed!");
    	
    	System.out.println("Testing emptySquaresToPiece method");
    	List<Square> squares = board.getEmptySquaresToPiece(4, 4, Chessboard.NORTH);
    	assert(squares.size()==2);
    	assert(!squares.contains(board.getSquare(4,4)));
    	assert(squares.contains(board.getSquare(4,5)));
    	assert(squares.contains(board.getSquare(4,6)));
    	assert(!squares.contains(board.getSquare(4,7)));
    	squares = board.getEmptySquaresToPiece(3, 3, Chessboard.NORTHEAST);
    	assert(squares.size()==3);
    	assert(squares.contains(board.getSquare(4,4)));
    	assert(squares.contains(board.getSquare(5,5)));
    	assert(squares.contains(board.getSquare(6,6)));
    	squares = board.getEmptySquaresToPiece(3, 0, Chessboard.NORTH);
    	assert(squares.contains(board.getSquare(3,1)));
    	assert(squares.contains(board.getSquare(3,2)));
    	assert(squares.contains(board.getSquare(3,3)));
    	
    	System.out.println("Passed!");
    	
    	System.out.println("==Testing validMoves==");
    	Piece p = board.getSquare(3,0).getPiece();
    	assert(p instanceof Queen);
    	List<String> queenValidMoves = p.getValidMoves();
    	assert(queenValidMoves.size() == 2);
    	assert(queenValidMoves.contains("D2"));
    	assert(queenValidMoves.contains("D3"));
    	assert(!queenValidMoves.contains("D4"));
    	
    	System.out.println("Moving pawn from E7 to E5");
    	g.makeMove("E7", "E5");
    	assert(board.getSquare("E5").hasPiece());
    	assert(board.getSquare("E5").getPiece() instanceof Pawn);
    	assert(board.getSquare("E5").getPiece().getLocation().equals("E5"));
    	
    	assert(board.getSquare("D4").hasPiece());
    	assert(board.getSquare("D4").getPiece() instanceof Pawn);
 
    	p = board.getSquare("D4").getPiece();
    	assert(p.getLocation().equals("D4"));
    	List<String> pawnValidMoves = g.getValidMoves(p);
    	assert(pawnValidMoves.size() == 2);
    	assert(pawnValidMoves.contains("D5"));
    	assert(pawnValidMoves.contains("E5"));
    	assert(!pawnValidMoves.contains("D6"));
    	board.printBoard();
    	
    	System.out.println("Capturing the pawn on E5");
    	g.makeMove("D4", "E5");
    	assert(board.getSquare("E5").hasPiece());
    	assert(board.getSquare("E5").getPiece().getPlayer() == Game.WHITE);
    	
    	System.out.println("Moving pawn from D7 to D5");
    	g.makeMove("D7", "D5");
    	board.printBoard();
    	assert(board.getSquare("D5").getPiece() instanceof Pawn);
    	assert(board.getSquare("D6").enPassantAvailable() == true);
    	pawnValidMoves = board.getSquare("E5").getPiece().getValidMoves();
    	assert(pawnValidMoves.size() == 2);
    	assert(pawnValidMoves.contains("E6"));
    	assert(pawnValidMoves.contains("D6"));
    	
    	System.out.println("Moving pawn E2 to E4");
    	g.makeMove("E2", "E4");
    	System.out.println("Moving a knight from B8 to A6");
    	g.makeMove("B8", "A6");
    	board.printBoard();
    	
    	assert(board.getSquare("D6").enPassantAvailable() == false);
    	pawnValidMoves = board.getSquare("E5").getPiece().getValidMoves();
    	assert(pawnValidMoves.size() == 1);
    	assert(pawnValidMoves.contains("E6"));
    	
    	p = board.getPiece("F1");
    	assert(p instanceof Bishop);
    	List<String> bishopValidMoves = p.getValidMoves();
    	assert(bishopValidMoves.size() == 5);
    	assert(bishopValidMoves.contains("E2"));
    	assert(bishopValidMoves.contains("D3"));
    	assert(bishopValidMoves.contains("C4"));
    	assert(bishopValidMoves.contains("B5"));
    	assert(bishopValidMoves.contains("A6"));
    	
    	System.out.println("Passed!");
    	
    	System.out.println("==Testing checks==");
    	System.out.println("Moving Bishop from F1 to B5");
    	g.makeMove("F1", "B5");
    	board.printBoard();
    	assert(p.getLocation().equals("B5"));
    	assert(board.squareIsThreatened(4, 7, Game.WHITE) == true);
    	assert(g.isInCheck(Game.BLACK) == true);
    	assert(g.isInCheck(Game.WHITE) == false);
    	assert(g.getValidMoves(board.getPiece("B7")).size() == 0);
    	assert(g.getValidMoves(board.getPiece("C8")).size() == 1);
    	assert(g.getValidMoves(board.getPiece("E8")).size() == 1);
    	assert(g.getValidMoves(board.getPiece("D8")).size() == 1);
    	assert(g.getValidMoves(board.getPiece("F8")).size() == 0);
    	
    	g.makeMove("C8", "D7");
    	g.makeMove("G1", "F3");
    	
    	g.printGame();
    	assert(g.isInCheck(Game.BLACK) == false);
    	assert(g.willResultInCheck("D7", "C6") == false);
    	assert(g.willResultInCheck("D7", "C8") == true);
    	assert(g.willResultInCheck("D7", "B5") == false);
    	assert(g.getValidMoves(board.getPiece("B7")).size() == 1);
    	assert(g.getValidMoves(board.getPiece("C7")).size() == 2);
    	assert(g.getValidMoves(board.getPiece("D7")).size() == 2);
    	assert(g.getValidMoves(board.getPiece("E8")).size() == 1);
    	assert(g.getValidMoves(board.getPiece("D8")).size() == 6);
    	assert(g.getValidMoves(board.getPiece("F8")).size() == 5);
    	
    	System.out.println("Passed!");
    	
    	g.makeMove("H7","H6");
    	
    	System.out.println("Testing Castling + En Passant again");
    	assert(g.getPiece("E1").hasMoved() == false);
    	assert(g.getPiece("H1").hasMoved() == false);
    	assert(board.squareIsThreatened(4, 0, Game.BLACK) == false);
    	assert(g.getValidMoves(board.getPiece("E1")).contains("G1"));
    	g.makeMove("E1", "G1");
    	assert(board.getPiece("E1") == null);
    	assert(board.getPiece("H1") == null);
    	assert(board.getPiece("G1") instanceof King);
    	assert(board.getPiece("F1") instanceof Rook);
    	g.printGame();
    	
    	g.makeMove("F7", "F5");
    	g.printGame();
    	assert(g.getValidMoves(board.getPiece("E5")).contains("F6"));
    	assert(g.getPiece("F5") instanceof Pawn);
    	g.makeMove("E5", "F6");
    	assert(g.getPiece("F5") == null);
    	g.printGame();
    	
    	System.out.println("Passed!");
    	
    	System.out.println("Testing checkmate");
    	g = new Game();
    	board = g.getBoard();
    	g.makeMove("E2", "E4");
    	g.makeMove("E7", "E5");
    	g.makeMove("D1", "H5");
    	g.makeMove("B8", "C6");
    	g.makeMove("F1", "C4");
    	g.makeMove("G8", "F6");
    	g.makeMove("H5", "F7");
    	g.printGame();
    	assert(g.getValidMoves(g.getKing(Game.BLACK)).isEmpty());
    	assert(g.isInCheck(Game.BLACK));
    	assert(g.noMovesLeft(Game.BLACK));
    	assert(g.gameIsOver() == true);
    	assert(g.getWinner() == Game.WHITE);
    	
    	System.out.println("All tests passed! You are awesome!");
    }
}
