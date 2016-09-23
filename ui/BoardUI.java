/**
 * 
 */
package ui;

import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.*;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import code.*;
import exceptions.*;
import controller.ChessController;

/**
 * 
 * @author Yewen Fan
 * image from http://i.stack.imgur.com/memI0.png
 * reference: http://stackoverflow.com/questions/21142686/making-a-robust-resizable-swing-chess-gui
 * reference: http://forgetcode.com/Java/848-Chess-game-Swing
 * reference: https://github.com/mlli2/Chess/blob/master/src/gui/Gui.java
 */
public class BoardUI {
	
	private JFrame window;
	private JPanel chessboard[][];
	private JMenuItem newGame;
	private JMenuItem forfeitGame;
	private JMenuItem undo;
	private JMenuItem customGame;
	
	/**
	 * Chess piece id for each kind of chess
	 * Image for each chess piece is stored in chessPieceImages with index being corresponding id.
	 */
	public static final int KING = 0, QUEEN = 1, ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
	public static final int EMPRESS = 6, PRINCESS = 7;
	
	/**
	 * Store the image of each chess piece
	 */
	private BufferedImage[][] chessPieceImages = new BufferedImage[2][8];
	/**
	 * the width of the board
	 */
	private int width = 8;
	/**
	 * the height of the board
	 */
	private int height = 8;
 
	/**
	 * Construct the board GUI.
	 * @throws OutOfBoardException
	 */
    public BoardUI(ChessController controller, Board board) throws OutOfBoardException {
    	width = board.getWidth();
    	height = board.getHeight();
    	this.getChessPieceImages();
    	this.setLayout();
    	this.setBackground(controller, board);
    }
    
    /**
     * Set chess board layout. 
     */
    private void setLayout() {
    	window = new JFrame("Chess Application");
    	chessboard = new JPanel[width][height];
        window.setSize(720, 720);
        window.setLayout(new GridLayout(width, height));
        window.setResizable(false);
    }
    
    /**
     * Set chess board background, including chess pieces.
     * @throws OutOfBoardException
     */
    private void setBackground(ChessController controller, Board board) throws OutOfBoardException {
    	for (int j=height-1; j>=0; j--) {
    		for (int i=0; i<width; i++) {
    			chessboard[i][j] = new JPanel();
                chessboard[i][j].addMouseListener(controller);
                chessboard[i][j].setBackground( (i+j) % 2 == 0 ? Color.black : Color.white );
                window.add(chessboard[i][j]); 
    		}
    	}
    	setChessPieces(board);
    	setUpMenu(window);
    	window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void setUpMenu(JFrame window) {
    	JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        newGame = new JMenuItem("New");
        customGame = new JMenuItem("Custom");
        forfeitGame = new JMenuItem("Forfeit");
        undo = new JMenuItem("Undo");
        file.add(newGame);
        file.add(customGame);
        file.add(forfeitGame);
        file.add(undo);
        menubar.add(file);
        window.setJMenuBar(menubar);
    }
    
    public JPanel getPanel(int x, int y) {
    	return chessboard[x][y];
    }
    
    private void setChessPieces(Board board) throws OutOfBoardException {
    	for (int i=0; i<width; i++) {
    		for (int j=0; j<height; j++) {    
    			chessboard[i][j].removeAll();
    			chessboard[i][j].validate();
        		chessboard[i][j].repaint();
    			ChessPiece chess = board.getChessPieceByPosition(new Point(i, j, board));
    			String player = chess.getName();
    			int playerId = this.transformPlayerNameToPlayerId(player, board);
    			int chessId = this.transformChessTypeToChessId(chess); 
    			if (chessId >= 0) {
	    			JLabel piece = new JLabel(new ImageIcon(chessPieceImages[playerId][chessId]));
	    	    	chessboard[i][j].add(piece);
	    	    	chessboard[i][j].validate();
	        		chessboard[i][j].repaint();
    			}
    		}
    	}
    }
    
    public void undo(Board lastBoard) throws OutOfBoardException {
    	resetBoard(lastBoard);
    }
    
    public void resetBoard(Board board) throws OutOfBoardException {
    	setChessPieces(board);
    }
    
    /**
     * Get the chess piece images from web.
     * Store the images in chessPieceImages.
     */
    private void getChessPieceImages() {
    	try {
            URL url = new URL("http://i.stack.imgur.com/memI0.png");
            BufferedImage bi = ImageIO.read(url);
            for (int i=0; i<2; i++) {
                for (int j=0; j<6; j++) {
                    chessPieceImages[i][j] = bi.getSubimage(j * 64, i * 64, 64, 64);
                }
            }
            for (int i=0;i<2;i++) {
            	chessPieceImages[i][EMPRESS] = this.imageUpsideDown(chessPieceImages[i][KNIGHT]);
            	chessPieceImages[i][PRINCESS] = this.imageUpsideDown(chessPieceImages[i][BISHOP]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void addNewGameListener(ActionListener a) {
    	newGame.addActionListener(a);
    }
    
    public void addCustomGameListener(ActionListener a) {
    	customGame.addActionListener(a);
    }
    
    public void addForfeitGameListener(ActionListener a) {
    	forfeitGame.addActionListener(a);
    }
    
    public void addUndoListener(ActionListener a) {
    	undo.addActionListener(a);
    }

    /**
     * Given player name, return player id.
     * @param player the name of the player
     * @return the player id
     */
    private int transformPlayerNameToPlayerId(String player, Board board) {
    	String player1 = board.getPlayer1();
    	if (player.equals(player1))
    		return 1;
    	return 0;
    }
    
    /**
     * Given the chess piece, return the chess type id of that chess piece
     * @param chess the chess piece that you want to get the type id
     * @return the type id of the given chess piece
     */
    private int transformChessTypeToChessId(ChessPiece chess) {
    	if (chess instanceof Queen)
    		return QUEEN;
    	if (chess instanceof King)
    		return KING;
    	if (chess instanceof Rook)
    		return ROOK;
    	if (chess instanceof Bishop)
    		return BISHOP;
    	if (chess instanceof Knight)
    		return KNIGHT;
    	if (chess instanceof Pawn)
    		return PAWN;
    	if (chess instanceof Empress)
    		return EMPRESS;
    	if (chess instanceof Princess)
    		return PRINCESS;
    	return -1; // empty chess
    }
    
    private BufferedImage imageUpsideDown(BufferedImage image) {
    	AffineTransform transform = new AffineTransform();
        transform.rotate(Math.PI, image.getWidth()/2, image.getHeight()/2);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }
    
    /**
     * Given the panel, return its position in the board.
     * @param panel
     * @return the position of the input panel.
     */
    public Point getPanelPosition(JPanel panel) {
    	for (int i=0;i<width;i++) {
    		for (int j=0;j<height;j++) {
    			if (chessboard[i][j] == panel) {
    				return new Point(i, j);
    			}
    		}
    	}
    	return new Point(-1, -1);
    }
    
}
