package code;

public abstract class ChessPiece {
	
	/**
	 * The name of player that owns this piece of chess.
	 */
	protected String player;
	
	/**
	 * Construct a ChessPiece.
	 * @param _player the player's name that owns this chess piece.
	 */
	public ChessPiece(String _player) {
	   player = _player;
	}
	
	/**
	 * @return true if this piece of chess can move from "from" to "to" in this "board".
	 */
	public abstract boolean canMove(Board board, Point from, Point to) throws Exception; 
	
	/**
	 * @return a deep copy of this chess piece.
	 */
	public abstract ChessPiece deepCopy(); 
	
	/**
	 * Get the owner name of this chess piece.
	 */
	public String getName() {
		return player;
	}

	/**
	 * Helper function for canMove()
	 * @param board the current state of this board.
	 * @param from the location where this piece of chess move from.
	 * @param to the location where this piece of chess move to.
	 * @return true if this piece of chess pass the general check for all kinds of chess.
	 * if this piece of chess want to move from "from" to "to".
	 */
	protected boolean generalCheckForMove(Board board, Point from, Point to) {
		if (from.equals(to))
			return false;
		ChessPiece chessFrom = board.getChessPieceByPosition(from);
		ChessPiece chessTo = board.getChessPieceByPosition(to);
		if (!chessFrom.getName().equals(board.getTurn()))
			return false;
		if (!(chessTo instanceof EmptyChess) && chessTo.getName() == this.player)
			return false;
		return true;
	}
	
	/**
	 * Helper function for canMove() for Knight-like chess piece
	 * @param from the location where this knight-like chess piece move from.
	 * @param to the location where this knight-like chess piece move to.
	 * @return true true if this knight-like chess piece pass the check for knight-like move.
	 */
	protected boolean checkMoveForKnight(Point from, Point to) {
		int disX = Math.abs(from.getX() - to.getX());
		int disY = Math.abs(from.getY() - to.getY());
		if (disX + disY == 3 && disX < 3 && disY < 3)
			return true;
		return false;
	}
}
