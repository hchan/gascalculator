/**
 * model class for Cell
 * @author Henry
 * July 26/2014
 */
package ca.henrychan.gascalculator;

import java.util.ArrayList;

public class Cell implements Cloneable{
	private int value;
	
	
	private final int x;
	private final int y;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	
	}
		
	public int getX() {
		return x;
	}
	

	public int getY() {
		return y;
	}	
	


	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	// is valid?
	// only valid if the value is not -1 and not visited
	public boolean isValidMove(ArrayList<Cell> path) {				
		return value != -1 && !inPath(path);
	}
	
	private boolean inPath(ArrayList<Cell> path) {
		boolean retval = false;
		for (Cell cell : path) {
			if (x == cell.getX() && y == cell.getY()) {
				retval = true;
				break;
			}
		}
		return retval;
	}

	@Override
	public Cell clone() throws CloneNotSupportedException {
		return (Cell) super.clone();
	}

	@Override
	public String toString() {
		return "Cell [value=" + value + ", x=" + x + ", y=" + y + "]";
	}

	


}
