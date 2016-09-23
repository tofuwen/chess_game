/**
 * 
 */
package code;

/**
 * @author Yewen Fan
 *
 */
public class Princess extends ChessPiece {

	/**
	 * Construct a Princess. (a fairy chess piece)
	 * @param _player the player's name that owns this princess.
	 */
	public Princess(String _player) {
		super(_player);
	}

	/**
	 * @see code.ChessPiece#canMove(code.Board, code.Point, code.Point)
	 */
	@Override
	public boolean canMove(Board board, Point from, Point to) throws Exception {
		boolean isBishopMove = from.isSameDiagonal(to) && board.isClearFromPoints(from, to);
		boolean isKnightMove = this.checkMoveForKnight(from, to);
		return this.generalCheckForMove(board, from, to) && (isBishopMove || isKnightMove);
	}
	
	/**
	 * @see code.ChessPiece#deepCopy()
	 */
	@Override
	public ChessPiece deepCopy() {
		return new Princess(player);
	}

}
