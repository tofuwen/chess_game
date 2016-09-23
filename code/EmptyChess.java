/**
 * 
 */
package code;

/**
 * @author Yewen Fan
 *
 */
public class EmptyChess extends ChessPiece {

	/**
	 * Construct a EmptyChess.
	 */
	public EmptyChess() {
		// For the empty chess, the name of player does NOT matter.
		super("Empty Chess");
	}

	/**
	 * @see code.ChessPiece#canMove(code.Board, code.Point, code.Point)
	 */
	@Override
	public boolean canMove(Board board, Point from, Point to) {
		return false;
	}
	
	/**
	 * @see code.ChessPiece#deepCopy()
	 */
	@Override
	public ChessPiece deepCopy() {
		return new EmptyChess();
	}

}
