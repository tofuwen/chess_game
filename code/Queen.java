/**
 * 
 */
package code;

/**
 * @author Yewen Fan
 *
 */
public class Queen extends ChessPiece {

	/**
	 * Construct a Queen.
	 * @param _player the player's name that owns this queen.
	 */
	public Queen(String _player) {
		super(_player);
	}

	/**
	 * @see code.ChessPiece#canMove(code.Board, code.Point, code.Point)
	 */
	@Override
	public boolean canMove(Board board, Point from, Point to) throws Exception {
		return this.generalCheckForMove(board, from, to) && 
				(from.isSameDiagonal(to) || from.isSameRowOrCol(to)) &&
				board.isClearFromPoints(from, to);
	}
	
	/**
	 * @see code.ChessPiece#deepCopy()
	 */
	@Override
	public ChessPiece deepCopy() {
		return new Queen(player);
	}

}
