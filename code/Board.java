package code;

import exceptions.*;

public class Board {
	
	/**
	 * The height of the board.
	 */
	private int height = 8;
	
	/**
	 * The width of the board.
	 */
	private int width = 8;
	
	/**
	 * The name of the first player.
	 */
	private String player1 = "Player 1";
	/**
	 * The name of the second player.
	 */
	private String player2 = "Player 2";
	
	/**
	 * The 2d array representing the positions of each chess pieces.
	 */
	private ChessPiece board[][] = new ChessPiece[8][8];
	
	/**
	 * The name of player that should play in this turn.
	 */
	private String turn;
	
	/**
	 * @param x the x position
	 * @param y the y position
	 * @return true the point represented by (x, y) is out of board.
	 */
	public boolean isOutOfBoard(int x, int y) {
		if (x >= width || y >= height || x < 0 || y < 0)
			return true;
		return false;
	}
	
	/**
	 * Helper function for the constructor
	 * Will only be used when we use the default height and width, namely, height = width = 8
	 * @param indexPawn the initial row index for the Pawn
	 * @param indexSpecial the initial row index for other chess pieces
	 * @param player the name of player that owns these chess pieces
	 */
	private void constructBoardHelper(int indexPawn, int indexSpecial, String player) {
		board[0][indexSpecial] = board[7][indexSpecial] = new Rook(player);
		board[1][indexSpecial] = board[6][indexSpecial] = new Knight(player);
		board[2][indexSpecial] = board[5][indexSpecial] = new Bishop(player);
		board[3][indexSpecial] = new Queen(player);
		board[4][indexSpecial] = new King(player);
		for (int i=0; i<8; i++) {
			board[i][indexPawn] = new Pawn(player);
		}
	}
	
	/**
	 * Constructor for the Board.
	 */
	public Board() {
		this("Player 1", "Player 2");
	}
	
	/**
	 * Constructor for the Board.
	 * @param _player1 the name of the first player
	 * @param _player2 the name of the second player
	 */
	public Board(String _player1, String _player2) {
		// If the names of two players are the same, we add suffix. 
		if (_player1.equals(_player2)) {
			_player1 += "_1";
			_player2 += "_2";
		}
		this.player1 = _player1;
		this.player2 = _player2;
		this.turn = player1;
		for (int i=0; i<width; i++)
			for (int j=0; j<height; j++)
				board[i][j] = new EmptyChess();
		constructBoardHelper(1, 0, player1);
		constructBoardHelper(6, 7, player2);
		
	}
	
	/**
	 * Constructor for the Board.
	 * Should have a well-defined position for each kind of chess.
	 * Not defined and implemented yet.
	 * @param _height the height of the board
	 * @param _width the width of the board
	 */
	public Board(int _height, int _width) {
		this.height = _height;
		this.width = _width;
		this.turn = player1;
	}
	
	/**
	 * Constructor for the Board.
	 * Should have a well-defined position for each kind of chess.
	 * Not defined and implemented yet.
	 * @param _height the height of the board
	 * @param _width the width of the board
	 * @param _player1 the name of the first player
	 * @param _player2 the name of the second player
	 */
	public Board(int _height, int _width, String _player1, String _player2) {
		if (_player1.equals(_player2)) {
			_player1 += "_1";
			_player2 += "_2";
		}
		this.player1 = _player1;
		this.player2 = _player2;
		this.turn = player1;
		this.height = _height;
		this.width = _width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public String getPlayer1() {
		return this.player1;
	}
	
	public String getPlayer2() {
		return this.player2;
	}
	
	public String getTurn() {
		return this.turn;
	}
	
	public void setTurn(String turn) {
		this.turn = turn;
	}
	
	/**
	 * Get the chess piece in the position p
	 * @param p the position where we want to get the chess piece
	 * @return the chess piece in the position p
	 */
	public ChessPiece getChessPieceByPosition(Point p) {
		return board[p.getX()][p.getY()];
	}
	
	/**
	 * 
	 * @param from the start position
	 * @param to the ending position
	 * @return true if the path between "from" and "to" (exclude "from" and "to") contains no chess.
	 * @throws Exception when call false helper function or create an illegal point.
	 */
	public boolean isClearFromPoints(Point from, Point to) throws Exception {
		if (from.equals(to))
			return true;
		if (!from.isSameDiagonal(to) && !from.isSameRowOrCol(to)) {
			throw new FalseHelperFunctionCallException("You call this function wrong!");
		}
		int dx = to.getX() - from.getX();
		int dy = to.getY() - from.getY();
		int disX = Math.abs(dx);
		int disY = Math.abs(dy);
		int steps = Math.max(disX, disY);
		Point step = new Point(dx/steps, dy/steps);
		Point from_copy = new Point(from.getX(), from.getY(), this);
		from_copy.plus(this, step);
		while (!from_copy.equals(to)) {
			if (!(this.getChessPieceByPosition(from_copy) instanceof EmptyChess))
				return false;
			from_copy.plus(this, step);
		}
		return true;
	}
	
	/**
	 * Change the turn.
	 */
	private void flipTurn() {
		if (turn.equals(player1)) 
			turn = player2;
		else
			turn = player1;
	}
	
	/**
	 * Handle the logic if we want to move the chess in position "from" to position "to".
	 * @param from the start position
	 * @param to the ending position
	 * @return true if we successfully move the chess.
	 * False otherwise. That means the user specify the wrong start and ending position.
	 * @throws Exception when create an illegal point
	 */
	public boolean move(Point from, Point to) throws Exception {
		ChessPiece chessFrom = this.getChessPieceByPosition(from);
		if (!chessFrom.canMove(this, from, to))
			return false;
		if (chessFrom instanceof Pawn)
			((Pawn)chessFrom).alreadyMoved();
		board[from.getX()][from.getY()] = new EmptyChess();
		board[to.getX()][to.getY()] = chessFrom;
		this.flipTurn();
		return true;
	}
	
	/**
	 * Get the position of King. 
	 * Used to decide whether the current board is in check. 
	 * @param player the name of the owner of the King.
	 * @return the position of the King you specified.
	 * @throws Exception when create an illegal point
	 */
	private Point getKingPosition(String player) throws Exception {
		for (int i=0; i<width; i++)
			for (int j=0; j<height; j++)
				if (board[i][j] instanceof King && board[i][j].getName().equals(player))
					return new Point(i, j, this);
		// Will throw a exception. You should never be here.
		return new Point(-1, -1, this);
	}
	
	/**
	 * Determine whether "player" is in check or not.
	 * @param player the name of player that want to check
	 * @return true if the player is in check
	 */
	public boolean isCheck(String player) throws Exception {
		Board newBoard = this.deepCopy();
		newBoard.flipTurn();
		return newBoard.isGoingLose(player);
	}
	
	/**
	 * Determine whether "player" is going to lose or not, 
	 * which means the king will get captured in this turn.
	 * @param player the name of player that want to check the losing condition
	 * @return true if "player" is going to lose.
	 */
	public boolean isGoingLose(String player) throws Exception {
		Point kingPosition = this.getKingPosition(player);
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				if (board[i][j].canMove(this, new Point(i, j, this), kingPosition)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Determine whether the player loses.
	 * @param player the name of player that want to check the losing condition
	 * @return true if "player" loses
	 * @throws Exception
	 */
	public boolean isLose(String player) throws Exception {
		if (!isCheck(player)) {
			return false;
		}
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				for (int ii=0;ii<width;ii++) {
					for (int jj=0;jj<height;jj++) {
						Point from = new Point(i, j, this);
						Point to = new Point(ii, jj, this);
						Board newBoard = this.deepCopy();
						if (newBoard.move(from, to) && !newBoard.isGoingLose(player)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean isStalemate() throws Exception {
		if (isCheck(turn)) {
			return false;
		}
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				for (int ii=0;ii<width;ii++) {
					for (int jj=0;jj<height;jj++) {
						Point from = new Point(i, j, this);
						Point to = new Point(ii, jj, this);
						Board newBoard = this.deepCopy();
						if (newBoard.move(from, to) && !newBoard.isGoingLose(turn)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Put chess on position p
	 * For test only
	 * @param p the position the chess is put
	 * @param chess the chess piece that you want to put on the board
	 */
	public void putChessOn(Point p, ChessPiece chess) {
		board[p.getX()][p.getY()] = chess;
	}
	
	/**
	 * @return a deep copy of the current board.
	 */
	public Board deepCopy() {
		Board newBoard = new Board(player1, player2);
		newBoard.setTurn(turn);
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				newBoard.putChessOn(new Point(i, j), board[i][j].deepCopy());
			}
		}
		return newBoard;
	}
	
	public void customGame() {
		this.putChessOn(new Point(0, 2), new Empress(player1));
		this.putChessOn(new Point(7, 2), new Princess(player1));
		this.putChessOn(new Point(7, 5), new Empress(player2));
		this.putChessOn(new Point(0, 5), new Princess(player2));
	}
}
