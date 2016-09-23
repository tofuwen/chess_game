/**
 * 
 */
package game;

import code.*;
import controller.*;
import exceptions.*;

/**
 * @author Yewen Fan
 * reference: https://github.com/mlli2/Chess/blob/master/src/game/Game.java
 */
public class Game {
	
	private Board board;
	private Board lastBoard;
	int player1_score = 0;
	int player2_score = 0;
	private boolean forfeit = false;
	private boolean quit = false;

	public static void main(String[] args) throws OutOfBoardException {
		new ChessController(); 
	}

	/**
	 * Construct a game.
	 */
	public Game() {
		this.board = new Board();
		this.lastBoard = null;
	}
	
	/**
	 * Class for the game loop.
	 * @author Yewen Fan
	 */
	public class gameLoop implements Runnable {
		public void run() {
			System.out.println("Game start");
			String player1 = board.getPlayer1();
			String player2 = board.getPlayer2();
			try {
				while(!quit) {
					if (board.isStalemate()) {
						System.out.println("Stalemate!");
						System.out.println("Please restart the game!");
						break;
					}
					if (board.getTurn() == player1 && (board.isLose(player1) || forfeit)) {
						System.out.println(player1 + " loses!");
						player2_score++;
						break;
					}	
					if (board.getTurn() == player2 && (board.isLose(player2)|| forfeit)) {
						System.out.println(player1 + " loses!");
						player1_score++;
						break;
					}
					Thread.sleep(33);
				}
				System.out.println("Game over!");
				System.out.println(player1 + "'s score: " + player1_score);
				forfeit = false;
				System.out.println(player2 + "'s score: " + player2_score);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("You should never see exceptions here! Something went wrong!");
				System.exit(1);
			}
		}
	}
	
	public void startGame(boolean isCustom) {
		this.quit = true;
		board = new Board();
		if (isCustom) {
			board.customGame();
		}
		this.quit = false;
		Thread t = new Thread(new gameLoop());
		t.start();
	}
	
	public void forfeitGame() {
		forfeit = true;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public Board getLastBoard() {
		return this.lastBoard;
	}
	
	public Board setLastBoard(Board lastBoard) {
		return this.lastBoard = lastBoard;
	}
	
	public boolean undo() {
		if (lastBoard == null) {
			System.out.println("You cannot undo!");
			return false;
		}
		this.board = this.lastBoard;
		this.lastBoard = null;
		return true;
	}

}
