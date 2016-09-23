package code;

import exceptions.OutOfBoardException;

public class Point {
	/**
	 * The x position of the board.
	 */
	private int x;
	/**
	 * The y position of the board.
	 */
	private int y;
	
	/**
	 * Construct the point
	 * Do not check out of board.
	 * Will be used only when calculating steps in Board.isClearFromPoints(Point from, Point to).
	 * and in BoardUI.java getPanelPosition(JPanel panel).
	 * @param _x the x location of the point
	 * @param _y the y location of the point
	 */
	public Point(int _x, int _y) {
		x = _x;
		y = _y;
	}
	
	/**
	 * Construct the point
	 * @param _x the x location of the point
	 * @param _y the y location of the point
	 * @param board	the point should lie in this board
	 * @throws OutOfBoardException
	 */
	public Point(int _x, int _y, Board board) throws OutOfBoardException {
		if (board.isOutOfBoard(_x, _y)) {
			throw new OutOfBoardException("The point you specify is out of board.");
		}
		x = _x;
		y = _y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	/**
	 * @param other the other point
	 * @return true if this point has the same position as "other".
	 */
	public boolean equals(Point other) {
		if (x == other.getX() && y == other.getY())
			return true;
		return false;
	}
	
	/**
	 * @param other the other point
	 * @return the Manhattan distance between this point and "other".
	 */
	public int maxDistance(Point other) {
		return Math.max(Math.abs(x-other.getX()), Math.abs(y-other.getY()));
	}
	
	/**
	 * Simply adding this point's coordinates with "other" respectively.
	 * Used when iterate from one point to another.
	 * @param board points should lie in this board
	 * @param other the other point
	 * @throws OutOfBoardException
	 */
	public void plus(Board board, Point other) throws OutOfBoardException {
		x += other.getX();
		y += other.getY();
		if (board.isOutOfBoard(x, y)) {
			throw new OutOfBoardException("The point is out of board. Your code is wrong! You should not see this!");
		}
	}
	
	/**
	 * @param other the other point
	 * @return true if this point and "other" are in the same row or same column.
	 */
	public boolean isSameRowOrCol(Point other) {
		int dx = x - other.getX();
		int dy = y - other.getY();
		return dx == 0 || dy == 0;
	}
	
	/**
	 * @param other the other point
	 * @return true if this point and "other" are in the same diagonal.
	 */
	public boolean isSameDiagonal(Point other) {
		int dx = x - other.getX();
		int dy = y - other.getY();
		return Math.abs(dx) == Math.abs(dy);
	}
}
