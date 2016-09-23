/**
 * 
 */
package code;

/**
 * @author Yewen Fan
 *
 */
public class Pawn extends ChessPiece {
	
	/**
	 * Determine whether we already moved this Pawn or not
	 */
	private boolean isNeverMove = true;

	/**
	 * Construct a Pawn.
	 * @param _player the player's name that owns this pawn.
	 */
	public Pawn(String _player) {
		super(_player);
	}
	
	/**
	 * Set this pawn to be already moved.
	 * So it cannot move two steps forward anymore.
	 */
	public void alreadyMoved() {
		this.isNeverMove = false;
	}

	/**
	 * @see code.ChessPiece#canMove(code.Board, code.Point, code.Point)
	 */
	@Override
	public boolean canMove(Board board, Point from, Point to) throws Exception {
		if (!this.generalCheckForMove(board, from, to))
			return false;
		ChessPiece chessFrom = board.getChessPieceByPosition(from);
		ChessPiece chessTo = board.getChessPieceByPosition(to);
		// For different player's pawn, the direction it moves is different.
		int sign = chessFrom.getName() == board.getPlayer1() ? 1 : -1;
		int dx = to.getX() - from.getX();
		int dy = (to.getY() - from.getY()) * sign;
		if (chessTo instanceof EmptyChess) {
			if (dx == 0 && (dy == 1 || (dy == 2 && this.isNeverMove)))
				return true;
		} else {
			if (dy == 1 && Math.abs(dx) == 1)
				return true;
		}
		return false;
	}
	
	/**
	 * @see code.ChessPiece#deepCopy()
	 */
	@Override
	public ChessPiece deepCopy() {
		return new Pawn(player);
	}

}
