/**
 * 
 */
package code;

/**
 * @author Yewen Fan
 *
 */
public class Empress extends ChessPiece {

	/**
	 * Construct a Empress. (a fairy chess piece)
	 * @param _player the player's name that owns this empress.
	 */
	public Empress(String _player) {
		super(_player);
	}

	/**
	 * @see code.ChessPiece#canMove(code.Board, code.Point, code.Point)
	 */
	@Override
	public boolean canMove(Board board, Point from, Point to) throws Exception {
		boolean isRookMove = from.isSameRowOrCol(to) && board.isClearFromPoints(from, to);
		boolean isKnightMove = this.checkMoveForKnight(from, to);
		return this.generalCheckForMove(board, from, to) && (isRookMove || isKnightMove);
	}
	
	/**
	 * @see code.ChessPiece#deepCopy()
	 */
	@Override
	public ChessPiece deepCopy() {
		return new Empress(player);
	}

}
