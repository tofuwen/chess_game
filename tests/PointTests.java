package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.*;
import exceptions.OutOfBoardException;

public class PointTests {
	/**
	 * This test checks that a Point is properly initialized: checking that 
	 * its x position and y position attributes are set properly.
	 */
	@Test
	public void instantiatePoint() throws OutOfBoardException {
		int x = 5;
		int y = 4;
		Point point = new Point(x, y, new Board());
		assertEquals(x, point.getX());
	    assertEquals(y, point.getY());
	}
	
	/**
	 * This test checks that different Points are not equal.
	 */
	@Test
	public void checkEquals() throws OutOfBoardException {
		int x = 5;
		int y = 4;
		Point point1 = new Point(x, y, new Board());
		Point point2 = new Point(x, y, new Board());
		Point point3 = new Point(x+1, y, new Board());
		assertTrue(point1.equals(point2));
	    assertFalse(point2.equals(point3));
	}
	
	/**
	 * This test checks the relative position of two points.
	 */
	@Test
	public void checkPositions() throws OutOfBoardException {
		int x = 5;
		int y = 4;
		Point point1 = new Point(x, y, new Board());
		Point point2 = new Point(x-1, y-1, new Board());
		Point point3 = new Point(x+1, y, new Board());
		assertTrue(point1.isSameDiagonal(point2));
	    assertFalse(point2.isSameDiagonal(point3));
	    assertTrue(point1.isSameRowOrCol(point3));
	}
}
