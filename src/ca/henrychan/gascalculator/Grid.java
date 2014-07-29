/**
 * model for keeping the grid
 * @author Henry
 * July 26/2014
 */
package ca.henrychan.gascalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Grid implements Cloneable{
	private Cell[][] cells;
	private Cell en; // starting cell
	private Cell ex; // ending cell
	private Cell car; // car's current position
	private ArrayList<Cell> path = new ArrayList<Cell>();

	
	public Cell[][] getCells() {
		return cells;
	}
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	public Cell getEn() {
		return en;
	}
	public void setEn(Cell en) {
		this.en = en;
	}
	public Cell getEx() {
		return ex;
	}
	public void setEx(Cell ex) {
		this.ex = ex;
	}
	
	public int getN() {
		return cells.length;		
	}
	
	public int getM() {
		return cells[0].length;
	}
	
	
	public Cell getCar() {
		return car;
	}
	public void setCar(Cell car) {
		this.car = car;
	}
	
	// creates empty cells
	public void createCells(int n, int m) {
		cells = new Cell[n][m];	
		for (int nLoop = 0; nLoop < n; nLoop++) {
			for (int mLoop = 0; mLoop < m; mLoop++) {
				cells[nLoop][mLoop] = new Cell(nLoop, mLoop);
			}
		}
	}
	
	// calculate possible moves
	// a possible move from car's current position 
	// * cannot be out of bounds, 
	// * cannot be of a cell value of -1
	// * cannot be of a cell visited before
	public ArrayList<Cell> getPossibleCellsFromCurrentPosition() {
		return getPossibleCellsFromPosition(car.getX(), car.getY());
	}
	
	public ArrayList<Cell> getPossibleCellsFromPosition(int x, int y) {
		ArrayList<Cell> retval = new ArrayList<Cell>();
		
		
		// right
		if (x+1 < getN() && cells[x+1][y].isValidMove(path)) {
			retval.add(cells[x+1][y]);
		}
		//down
		if (y+1 < getM() && cells[x][y+1].isValidMove(path)) {
			retval.add(cells[x][y+1]);
		}
				
		//left
		if (x-1 >= 0 && cells[x-1][y].isValidMove(path)) {
			retval.add(cells[x-1][y]);
		}
		
		//up
		if (y-1 >= 0 && cells[x][y-1].isValidMove(path)) {
			retval.add(cells[x][y-1]);
		}
		
		 Collections.sort(retval, new ClosestCellToXComparator(this));
		//retval = sortPossibleCellsFromCurrentPosition(retval);
		
		return retval;		
	}
	
	
	private ArrayList<Cell> sortPossibleCellsFromCurrentPosition(
			ArrayList<Cell> possibleCellsFromCurrentPosition) {
		ArrayList<Cell> retval = new ArrayList<Cell>();
		Iterator<Cell> possibleCellsFromCurrentPositionIterator = null;
		try {
		possibleCellsFromCurrentPositionIterator = possibleCellsFromCurrentPosition.iterator();
		} catch (java.lang.StackOverflowError e) {
			System.out.println(possibleCellsFromCurrentPosition.size());
		}
		int numLoops = possibleCellsFromCurrentPosition.size();
		for (int i = 0; i < numLoops; i++) {
			possibleCellsFromCurrentPositionIterator = possibleCellsFromCurrentPosition.iterator();
			Cell bestCell = null;
			while (possibleCellsFromCurrentPositionIterator.hasNext()) {
				Cell cell = possibleCellsFromCurrentPositionIterator.next();
				if (bestCell == null) {
					bestCell = cell;
				} else {
					int bestCellxDelta = Math.abs(bestCell.getX() - this.getEx().getX());
					int bestCellyDelta = Math.abs(bestCell.getY() - this.getEx().getY());
					int bestCellLeastPossibleMoves = bestCellxDelta + bestCellyDelta;
					
					int cellxDelta = Math.abs(cell.getX() - this.getEx().getX());
					int cellyDelta = Math.abs(cell.getY() - this.getEx().getY());
					int cellLeastPossibleMoves = cellxDelta + cellyDelta;
					
					if (cellLeastPossibleMoves < bestCellLeastPossibleMoves) {
						bestCell = cell;
					}
				}			
			}
			possibleCellsFromCurrentPosition.remove(bestCell);
			retval.add(bestCell);
		}
		return retval;
	}
	
	
	public ArrayList<Cell> getPath() {
		return path;
	}
	public void setPath(ArrayList<Cell> path) {
		this.path = path;
	}
	@Override
	public Grid clone() throws CloneNotSupportedException {
		Grid retval = (Grid) super.clone();
		// deep copy
		retval.setCells(new Cell[getN()][getM()]);
		for (int nLoop = 0; nLoop < getN(); nLoop++) {
			for (int mLoop = 0; mLoop < getM(); mLoop++) {
				retval.cells[nLoop][mLoop] = cells[nLoop][mLoop].clone();
			}
		}
		
		retval.path = new ArrayList<Cell>();

		
		    for(Cell cell: path) {
		    	retval.path .add(retval.cells[cell.getX()][cell.getY()]);
		    }
		
				
		retval.en = retval.cells[this.en.getX()][this.en.getY()];
		retval.ex = retval.cells[this.ex.getX()][this.ex.getY()];
		retval.car = retval.cells[this.car.getX()][this.car.getY()];
		return retval;
	}
	
	
	// updates the cell after a car has visited that location
	public void addToVisited(Cell cell) {
		path.add(cell);		
	}
	
	private int cacheSummationOfGas = -1;
	public int getSummationOfGas() {
		int retval = 0;
		if (cacheSummationOfGas == -1) {
			cacheSummationOfGas = 0;
			for (Cell cell : path) {
				cacheSummationOfGas += cell.getValue();
			}
		}
		retval = cacheSummationOfGas;
		return retval;
	}
	
	public PathInfo getPathInfoFromStart(int x, int y) {
		PathInfo retval = new PathInfo();
		boolean found = false;
		for (Cell cell : path) {
			retval.setVisitedCount(retval.getVisitedCount()+1);
			retval.setTotalGas(retval.getTotalGas()+cell.getValue());
			if (cell.getX() == x && cell.getY() == y) {
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("HUH?!?");
			retval = null;
		}
		return retval;
	}
	
	private HashMap<Integer, ArrayList<Cell>> pathToEndCache = new HashMap<Integer, ArrayList<Cell>>();	
	// does not include Cell(x,y)
	public ArrayList<Cell> getPathToEnd(int x, int y) {
		ArrayList<Cell> retval = new ArrayList<Cell>();
	
		int keyToHash = x*1000 + y;
		if (pathToEndCache.get(keyToHash) == null) {
			boolean found = false;
			for (Cell cell : path) {
				if (found) {
					retval.add(cell);
				}
				if (cell.getX() == x && cell.getY() == y) {
					found = true;
				}			
			}
			if (!found) {
				System.out.println("not found getPathToEnd " + x + ":" + y);
			}
		} else {
			retval = pathToEndCache.get(keyToHash);
		}
		
		return retval;
	}
	
	private HashMap<Integer, ArrayList<Cell>> pathFromStartCache = new HashMap<Integer, ArrayList<Cell>>();	
	// does include the Cell(x,y)
	public ArrayList<Cell> getPathFromStart(int x, int y) {
		ArrayList<Cell> retval = new ArrayList<Cell>();
		int keyToHash = x*1000 + y;
		if (pathFromStartCache.get(keyToHash) == null) {
			boolean found = false;
			for (Cell cell : path) {			
				retval.add(cell);			
				if (cell.getX() == x && cell.getY() == y) {
					found = true;
					break;
				}			
			}
			if (!found) {
				System.out.println("not found getPathToEnd " + x + ":" + y);
			}
		} else {
			retval = pathFromStartCache.get(keyToHash);
		}
		return retval;
	}
}
