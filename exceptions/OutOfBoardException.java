/**
 * 
 */
package exceptions;

/**
 * @author Yewen Fan
 *
 */
public class OutOfBoardException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public OutOfBoardException() { super(); }
	public OutOfBoardException(String message) { super(message); }
	public OutOfBoardException(String message, Throwable cause) { super(message, cause); }
	public OutOfBoardException(Throwable cause) { super(cause); }
}
