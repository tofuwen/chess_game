package code;

/**
 * @author Yewen Fan
 *
 */
public class King extends ChessPiece {
	
	/**
	 * Construct a King.
	 * @param _player the player's name that owns this king.
	 */
	public King(String _player) {
		super(_player);
	}
	
	/**
	 * @see code.ChessPiece#canMove(code.Board, code.Point, code.Point)
	 */
	@Override
	public boolean canMove(Board board, Point from, Point to) {
		return this.generalCheckForMove(board, from, to) && from.maxDistance(to) == 1; 
	}
	
	/**
	 * @see code.ChessPiece#deepCopy()
	 */
	@Override
	public ChessPiece deepCopy() {
		return new King(player);
	}
}
