/**
 * model store for each TestCase
 * also provides the engine to calculate the possible soln grid if possible (if not possible, solnGrid will be null)
 * @author Henry
 * July 26/2014
 */
package ca.henrychan.gascalculator;

import java.util.ArrayList;

public class TestCase {
	private Grid grid;

	private SolnGrid solnGrids[][]; // each slot contains the best Soln from n to x

	private int numGridsCloned = 0;
	private int n;
	private int m;

	public TestCase() {
		super();
		grid = new Grid();
	}

	public TestCase(int n, int m) {
		super();
		grid = new Grid();
		this.n = n;
		this.m = m;

	}

	public void initSolnGrids() throws CloneNotSupportedException {
		solnGrids = new SolnGrid[n][m];
		for (int mLoop = 0; mLoop < m; mLoop++) {
			for (int nLoop = 0; nLoop < n; nLoop++) {
				solnGrids[nLoop][mLoop] = new SolnGrid();
			}
		}
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public void calculate() throws CloneNotSupportedException {
		// testcase#6 - check if ex is "trapped"
		if (grid.getPossibleCellsFromPosition(grid.getEx().getX(), grid.getEx().getY()).size() == 0) {
			return;
		}
		calculatePossibleGrid(grid);		
	}

	public Grid getBestSoln() {
		return solnGrids[grid.getEn().getX()][grid.getEn().getY()]
				.getBestSoln();
	}

	private boolean isBetterSoln(Grid grid1, Grid grid2) {
		boolean retval = false;
		if (grid1.getPath().size() < grid2.getPath().size()) {
			retval = true;
		} else if (grid1.getPath().size() == grid2.getPath().size()) {
			if (grid1.getSummationOfGas() > grid2.getSummationOfGas()) {
				retval = true;
			}
		}
		return retval;
	}

	// updates the soln grid and bestSoln of each slot if necessary
	private void updateSolnGrid(Grid aGrid) {

		for (Cell cell : aGrid.getPath()) {
			SolnGrid solnGrid = solnGrids[cell.getX()][cell.getY()];
			solnGrid.getGrids().add(aGrid);
			if (solnGrid.getBestSoln() == null) {
				solnGrid.setBestSoln(aGrid);
			} else {
				// a better soln?
				if (isBetterSoln(aGrid, solnGrid.getBestSoln())) {
					// System.out.println("A better soln found!");
					solnGrid.setBestSoln(aGrid);
				}
			}
		}
		//System.out.println(getBestSoln().getSummationOfGas());
		//GridDumper.dumpGridTextVisited(aGrid);
	}

	/*
	 * tail recursion to calculate each possible grid
	 * tail recursion ends when the car is at ex
	 * for each cell that a car visits, we check if there has been a previous path to get there
	 * and its best soln (the SolnGrid)
	 * if there is, we see if the merge of this current path and the rest of the soln is better
	 * than the previous, if it is, then update the SolnGrid.
	 * Note that this grid is an n by m by o dimension grid, so we update each slot in
	 * the SolnGrid with a soln or bestSoln (or both, as the SolnGrid has an arrayList)
	 */
	private void calculatePossibleGrid(Grid aGrid)
			throws CloneNotSupportedException {
		// Cool - we found a "possible" soln
		if (aGrid.getCar() == aGrid.getEx()) {
			aGrid.addToVisited(aGrid.getEx());
			updateSolnGrid(aGrid);
		} else {
			ArrayList<Cell> possibleCellsFromCurrentPosition = aGrid
					.getPossibleCellsFromCurrentPosition();
			
			
			for (Cell cell : possibleCellsFromCurrentPosition) {
				SolnGrid solnGrid = solnGrids[cell.getX()][cell.getY()];
				Grid newGrid = aGrid.clone(); // yup, a clone
				numGridsCloned++;
				newGrid.addToVisited(newGrid.getCar());
				newGrid.setCar(newGrid.getCells()[cell.getX()][cell.getY()]);
				
				
				if (solnGrid.getBestSoln() != null) {				
					// no soln found
					newGrid.addToVisited(cell);
					newGrid.setCar(newGrid.getCells()[cell.getX()][cell.getY()]);

					ArrayList<Cell> pathToEnd = solnGrid.getBestSoln()
							.getPathToEnd(cell.getX(), cell.getY());

					
					// no need to add this as a soln
					if (newGrid.getPath().size() + pathToEnd.size() > getBestSoln().getPath().size()) {
						//System.out.println("cycles saved " +  getBestSoln().getPath().size());
						return;
					}
					
					// the merge
					for (Cell cellInPathToEnd : pathToEnd) {
					
						newGrid.getPath()
								.add(newGrid.getCells()[cellInPathToEnd.getX()][cellInPathToEnd
										.getY()]);
					}
					updateSolnGrid(newGrid);
					return;
				} else {
					int xDeltaToEnd = Math.abs(newGrid.getEx().getX() - cell.getX());
					int yDeltaToEnd = Math.abs(newGrid.getEx().getY() - cell.getY());
					int lowestNumOfMovesToEnd = xDeltaToEnd+yDeltaToEnd;
					//System.out.println("at best:" + lowestNumOfMovesToEnd + " moves to end");
					if (getBestSoln() != null && newGrid.getPath().size() + lowestNumOfMovesToEnd  > getBestSoln().getPath().size()) {
						//System.out.println("did some pruning");
						return;
					}
					calculatePossibleGrid(newGrid); // tail recursion with new
													// possible soln
				}

			}
		}

	}

}
