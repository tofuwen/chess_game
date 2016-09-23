/**
 * 
 */
package code;

/**
 * @author Yewen Fan
 *
 */
public class Rook extends ChessPiece {

	/**
	 * Construct a Rook.
	 * @param _player the player's name that owns this rook.
	 */
	public Rook(String _player) {
		super(_player);
	}

	/**
	 * @see code.ChessPiece#canMove(code.Board, code.Point, code.Point)
	 */
	@Override
	public boolean canMove(Board board, Point from, Point to) throws Exception {
		return this.generalCheckForMove(board, from, to) && from.isSameRowOrCol(to) && board.isClearFromPoints(from, to);
	}
	
	/**
	 * @see code.ChessPiece#deepCopy()
	 */
	@Override
	public ChessPiece deepCopy() {
		return new Rook(player);
	}

}
