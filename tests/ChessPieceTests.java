/**
 * 
 */
package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.*;

/**
 * @author Yewen Fan
 *
 */
public class ChessPieceTests {

	/**
	 * This test checks that queen can move properly.
	 */
	@Test
	public void queenMove() throws Exception {
		Board board = new Board();
		Point point = new Point(4, 4, board);
		Point point_d1 = new Point(4, 5, board);
		Point point_d2 = new Point(3, 4, board);
		Point point_d3 = new Point(2, 3, board);
		board.putChessOn(point, new Queen(board.getPlayer1()));
		assertFalse(board.move(point, point_d3));
		assertTrue(board.move(point, point_d1)); // (4, 4) -> (4, 5)
		// It's second player's turn.
		assertTrue(board.move(new Point(0,6,board), new Point(0,5,board)));
		assertTrue(board.move(point_d1, point_d2)); // (4, 5) -> (3, 4)
	}
	
	/**
	 * This test checks that bishop can move properly.
	 */
	@Test
	public void bishopMove() throws Exception {
		Board board = new Board();
		Point point = new Point(4, 4, board);
		Point point_d1 = new Point(4, 5, board);
		Point point_d2 = new Point(5, 5, board);
		Point point_d3 = new Point(2, 3, board);
		board.putChessOn(point, new Bishop(board.getPlayer1()));
		assertFalse(board.move(point, point_d3));
		assertFalse(board.move(point, point_d1));
		assertTrue(board.move(point, point_d2));
	}
	
	/**
	 * This test checks that rook can move properly.
	 */
	@Test
	public void rookMove() throws Exception {
		Board board = new Board();
		Point point = new Point(4, 4, board);
		Point point_d1 = new Point(4, 6, board);
		Point point_d2 = new Point(5, 5, board);
		Point point_d3 = new Point(2, 3, board);
		Point point_d4 = new Point(4, 7, board);
		board.putChessOn(point, new Rook(board.getPlayer1()));
		assertFalse(board.move(point, point_d3));
		assertFalse(board.move(point, point_d2));
		assertFalse(board.move(point, point_d4));
		assertTrue(board.move(point, point_d1));
	}
	
	/**
	 * This test checks that knight can move properly.
	 */
	@Test
	public void knightMove() throws Exception {
		Board board = new Board();
		Point point = new Point(4, 4, board);
		Point point_d1 = new Point(4, 6, board);
		Point point_d2 = new Point(4, 5, board);
		Point point_d3 = new Point(2, 3, board);
		board.putChessOn(point, new Knight(board.getPlayer1()));
		assertFalse(board.move(point, point_d1));
		assertFalse(board.move(point, point_d2));
		assertTrue(board.move(point, point_d3));
	}
	
	/**
	 * This test checks that king can move properly.
	 */
	@Test
	public void kingMove() throws Exception {
		Board board = new Board();
		Point point = new Point(4, 4, board);
		Point point_d1 = new Point(4, 6, board);
		Point point_d2 = new Point(3, 4, board);
		Point point_d3 = new Point(2, 3, board);
		board.putChessOn(point, new King(board.getPlayer1()));
		assertFalse(board.move(point, point_d1));
		assertTrue(board.move(point, point_d2)); // (4, 4) -> (3, 4)
		// It's second player's turn.
		assertTrue(board.move(new Point(0,6,board), new Point(0,5,board)));
		assertTrue(board.move(point_d2, point_d3)); // (3, 4) -> (2, 3)
	}
	
	/**
	 * This test checks that princess can move properly.
	 */
	@Test
	public void princessMove() throws Exception {
		Board board = new Board();
		Point point = new Point(4, 4, board);
		Point point_d1 = new Point(4, 6, board);
		Point point_d2 = new Point(4, 5, board);
		Point point_d3 = new Point(2, 3, board);
		board.putChessOn(point, new Princess(board.getPlayer1()));
		assertFalse(board.move(point, point_d1));
		assertFalse(board.move(point, point_d2));
		assertTrue(board.move(point, point_d3)); // (4, 4) -> (2, 3)
		// It's second player's turn.
		assertTrue(board.move(new Point(0,6,board), new Point(0,5,board)));
		assertTrue(board.move(point_d3, point_d2)); // (2, 3) -> (4, 5)
	}
	
	/**
	 * This test checks that empress can move properly.
	 */
	@Test
	public void empressMove() throws Exception {
		Board board = new Board();
		Point point = new Point(4, 4, board);
		Point point_d1 = new Point(2, 6, board);
		Point point_d2 = new Point(3, 3, board);
		Point point_d3 = new Point(2, 3, board);
		board.putChessOn(point, new Empress(board.getPlayer1()));
		assertFalse(board.move(point, point_d1));
		assertFalse(board.move(point, point_d2));
		assertTrue(board.move(point, point_d3)); // (4, 4) -> (2, 3)
		// It's second player's turn.
		assertTrue(board.move(new Point(0,6,board), new Point(0,5,board)));
		assertTrue(board.move(point_d3, point_d2)); // (2, 3) -> (2, 6)
	}
	
	/**
	 * The test checks the deep copy of chess piece.
	 */
	@Test
	public void deepCopyTest() {
		Queen q1 = new Queen("tofuwen");
		Queen q2 = (Queen)q1.deepCopy();
		Queen q3 = q1;
		assertTrue(q1 == q3);
		assertFalse(q1 == q2);
	}

}
