/**
 * 
 */
package code;

/**
 * @author Yewen Fan
 *
 */
public class Bishop extends ChessPiece {

	/**
	 * Construct a Bishop.
	 * @param _player the player's name that owns this bishop.
	 */
	public Bishop(String _player) {
		super(_player);
	}

	/**
	 * @see code.ChessPiece#canMove(code.Board, code.Point, code.Point)
	 */
	@Override
	public boolean canMove(Board board, Point from, Point to) throws Exception {
		return this.generalCheckForMove(board, from, to) && from.isSameDiagonal(to) && board.isClearFromPoints(from, to);
	}
	
	/**
	 * @see code.ChessPiece#deepCopy()
	 */
	@Override
	public ChessPiece deepCopy() {
		return new Bishop(player);
	}

}
