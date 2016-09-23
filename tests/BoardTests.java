package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.*;

public class BoardTests {
	/**
	 * This test checks that a Board is properly initialized: checking that 
	 * its height, width and board attributes are set properly.
	 */
	@Test
	public void instantiateBoard() throws Exception	{
		Board board = new Board();
        assertEquals(8, board.getHeight());
        assertEquals(8, board.getWidth());
        assertTrue(board.getChessPieceByPosition(new Point(0,0, board)) instanceof Rook);
        assertTrue(board.getChessPieceByPosition(new Point(0,1, board)) instanceof Pawn);
        assertTrue(board.getChessPieceByPosition(new Point(5,5, board)) instanceof EmptyChess);
        assertTrue(board.getChessPieceByPosition(new Point(4,7, board)) instanceof King);
        assertTrue(board.getChessPieceByPosition(new Point(3,0, board)) instanceof Queen);
	}
	
	/**
	 * This test checks basic pawn moves.
	 */
	@Test
	public void moveOnBoardPawnTest1() throws Exception	{
		Board board = new Board();
		assertFalse(board.move(new Point(0, 6, board), new Point(0, 4, board))); // It's player1's turn.
        assertTrue(board.move(new Point(0, 1, board), new Point(0, 2, board)));
        assertTrue(board.getChessPieceByPosition(new Point(0, 1, board)) instanceof EmptyChess);
        assertTrue(board.getChessPieceByPosition(new Point(0, 2, board)) instanceof Pawn);
        assertTrue(board.move(new Point(0, 6, board), new Point(0, 4, board)));
        assertFalse(board.move(new Point(0, 2, board), new Point(0, 4, board)));
        assertTrue(board.getChessPieceByPosition(new Point(0, 2, board)) instanceof Pawn);
	}
	
	/**
	 * This test checks pawn cannot move if other piece lie in its way.
	 */
	@Test
	public void moveOnBoardPawnTest2() throws Exception	{
		Board board = new Board();
        assertTrue(board.move(new Point(0, 1, board), new Point(0, 3, board)));
        assertTrue(board.getChessPieceByPosition(new Point(0, 1, board)) instanceof EmptyChess);
        assertTrue(board.getChessPieceByPosition(new Point(0, 3, board)) instanceof Pawn);
        assertTrue(board.getChessPieceByPosition(new Point(1, 6, board)) instanceof Pawn);
        assertTrue(board.move(new Point(0, 6, board), new Point(0, 4, board)));
        assertFalse(board.move(new Point(0, 3, board), new Point(0, 2, board)));
        assertFalse(board.move(new Point(0, 3, board), new Point(0, 4, board)));
        assertFalse(board.move(new Point(1, 6, board), new Point(1, 4, board)));
	}
	
	/**
	 * This test checks killing chess pieces.
	 */
	@Test
	public void moveOnBoardKillTest() throws Exception	{
		Board board = new Board();
		assertFalse(board.move(new Point(0, 0, board), new Point(0, 1, board)));
        assertTrue(board.move(new Point(0, 1, board), new Point(0, 3, board)));
        assertTrue(board.move(new Point(1, 6, board), new Point(1, 4, board)));
        assertTrue(board.move(new Point(0, 3, board), new Point(1, 4, board))); // kill the pawn
        assertTrue(board.getChessPieceByPosition(new Point(0, 3, board)) instanceof EmptyChess);
        assertTrue(board.getChessPieceByPosition(new Point(1, 4, board)) instanceof Pawn);
        assertTrue(board.move(new Point(1, 7, board), new Point(0, 5, board))); // knight (1,7) -> (0,5)
        assertFalse(board.move(new Point(0, 0, board), new Point(0, 6, board))); // cannot kill the pawn, since there is knight in the middle
        assertTrue(board.move(new Point(0, 0, board), new Point(0, 5, board))); // kill the knight
	}
	
	/**
	 * This test checks putting king in check and going to win the game.
	 */
	@Test
	public void moveOnBoardCheckAndWinTest() throws Exception	{
		Board board = new Board();
        assertTrue(board.move(new Point(0, 1, board), new Point(0, 3, board)));
        assertTrue(board.move(new Point(1, 6, board), new Point(1, 4, board)));
        assertTrue(board.move(new Point(0, 3, board), new Point(1, 4, board))); // kill the pawn
        assertTrue(board.move(new Point(1, 7, board), new Point(2, 5, board))); // knight (1,7) -> (2,5)
        assertTrue(board.move(new Point(0, 0, board), new Point(0, 6, board))); // kill the pawn
        assertTrue(board.move(new Point(2, 7, board), new Point(1, 6, board))); // bishop (2,7) -> (1,6)
        assertTrue(board.move(new Point(0, 6, board), new Point(0, 7, board))); // kill the rook
        assertFalse(board.move(new Point(2, 6, board), new Point(2, 5, board))); // cannot move pawn (2,6) -> (2,5). Knight in (2,5)
        assertTrue(board.move(new Point(3, 6, board), new Point(3, 5, board)));
        assertTrue(board.move(new Point(0, 7, board), new Point(3, 7, board))); // kill the queen
        assertFalse(board.isCheck(board.getPlayer1()));
        assertTrue(board.isCheck(board.getPlayer2()));
        assertTrue(board.move(new Point(4, 6, board), new Point(4, 5, board)));
        assertFalse(board.isGoingLose(board.getPlayer1()));
        assertTrue(board.isGoingLose(board.getPlayer2()));
	}
	
	/**
	 * This test checks losing the game.
	 */
	@Test
	public void loseGameTest() throws Exception	{
		Board board = new Board();
		String player1 = board.getPlayer1();
		String player2 = board.getPlayer2();
		board.putChessOn(new Point(4, 0), new EmptyChess());
		board.putChessOn(new Point(7, 4), new King(player1));
		board.putChessOn(new Point(5, 3), new Queen(player2));
		board.putChessOn(new Point(5, 4), new Queen(player2));
		board.putChessOn(new Point(5, 5), new Queen(player2));
		assertTrue(board.getTurn().equals(player1));
		assertTrue(board.isLose(player1));
	}
	
	/**
	 * This test checks stalemate.
	 */
	@Test
	public void stalemateTest() throws Exception	{
		Board board = new Board();
		String player1 = board.getPlayer1();
		String player2 = board.getPlayer2();
		for (int i=0;i<8;i++) {
			for (int j=0;j<2;j++) {
				board.putChessOn(new Point(i, j), new EmptyChess());
			}
		}
		board.putChessOn(new Point(7, 4), new King(player1));
		board.putChessOn(new Point(5, 3), new Queen(player2));
		board.putChessOn(new Point(5, 5), new Queen(player2));
		assertTrue(board.getTurn().equals(player1));
		assertFalse(board.isLose(player1));
		assertTrue(board.isStalemate());
	}
}
