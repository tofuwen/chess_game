/**
 * 
 */
package controller;

import ui.*;
import game.*;
import code.*;
import exceptions.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;


/**
 * @author Yewen Fan
 * reference: https://github.com/mlli2/Chess/blob/master/src/gui/Controller.java
 */
public class ChessController implements MouseListener {
	
	private Game game;
	private BoardUI gui;
	private Point firstClick = null;
	private Point secondClick = null;

	/**
	 * Construct chess controller.
	 */
	public ChessController() throws OutOfBoardException {
		game = new Game();
		gui = new BoardUI(this, game.getBoard());
		addNewGameListener();
		addCustomGameListener();
		addForfeitGameListener();
		addUndoListener();
		game.startGame(false);
	}
	
	public void addNewGameListener() {
    	gui.addNewGameListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event) {
    			try {
    				gui.resetBoard(new Board());
    			} catch (Exception e) {
    				e.printStackTrace();
    				System.out.println("You should never see exceptions here! Something went wrong!");
    				System.exit(1);
    			}
    			game.startGame(false);
    	    }
    	});
    }
	
	public void addCustomGameListener() {
    	gui.addCustomGameListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event) {
    			try {
    				Board board = new Board();
    				board.customGame();
    				gui.resetBoard(board);
    			} catch (Exception e) {
    				e.printStackTrace();
    				System.out.println("You should never see exceptions here! Something went wrong!");
    				System.exit(1);
    			}
    			game.startGame(true);
    	    }
    	});
    }
    
    public void addForfeitGameListener() {
    	gui.addForfeitGameListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event) {
    			game.forfeitGame();
    	    }
    	});
    }

    public void addUndoListener() {
    	gui.addUndoListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event) {
    			try {
	    			if (game.undo())
	    				gui.undo(game.getBoard());
    			} catch (Exception e) {
    				e.printStackTrace();
    				System.out.println("Exception occurs when undo.");
    				System.exit(1);
    			}
    	    }
    	});
    }
    

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * @param arg0 the mouse event
	 * Handle the logic when user clicks the chess piece. 
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		JPanel panel = (JPanel)arg0.getSource();
		if (firstClick == null) {
			firstClick = gui.getPanelPosition(panel);
			System.out.println(firstClick.getX());
			System.out.println(firstClick.getY());
			if (firstClick.getX() < 0) {
				System.out.println("Something wrong!");
				System.exit(1);
			}
		} else {
			secondClick = gui.getPanelPosition(panel);
			System.out.println(secondClick.getX());
			System.out.println(secondClick.getY());
			if (secondClick.getX() < 0) {
				System.out.println("Something wrong!");
				System.exit(1);
			}
			Board board = game.getBoard();
			try {
				Board lastBoard = board.deepCopy();
				boolean success = board.move(firstClick, secondClick);
				System.out.println(success);
				String player1 = board.getPlayer1();
				String player2 = board.getPlayer2();
				if (board.isCheck(player1)) {
					System.out.println(player1 + " is on check.");
				}
				if (board.isCheck(player2)) {
					System.out.println(player2 + " is on check.");
				}
				if (!success) {
					System.out.println("Invalid move. Retry!.");
					firstClick = null;
				} else {
					firstClick = null;
					secondClick = null;
					gui.resetBoard(board);
					game.setLastBoard(lastBoard);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Something went wrong when you try to move the chess!");
				System.exit(1);
			}
		}
	}

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 * Implement for interface.
	 * Do nothing.
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 * Implement for interface.
	 * Do nothing.
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 * Implement for interface.
	 * Do nothing.
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {}

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 * Implement for interface.
	 * Do nothing.
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
