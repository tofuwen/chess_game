/**
 * 
 */
package code;

/**
 * @author Yewen Fan
 *
 */
public class Knight extends ChessPiece {

	/**
	 * Construct a Knight.
	 * @param _player the player's name that owns this knight.
	 */
	public Knight(String _player) {
		super(_player);
	}

	/**
	 * @see code.ChessPiece#canMove(code.Board, code.Point, code.Point)
	 */
	@Override
	public boolean canMove(Board board, Point from, Point to) throws Exception {
		return this.generalCheckForMove(board, from, to) && this.checkMoveForKnight(from, to);
	}
	
	/**
	 * @see code.ChessPiece#deepCopy()
	 */
	@Override
	public ChessPiece deepCopy() {
		return new Knight(player);
	}

}
