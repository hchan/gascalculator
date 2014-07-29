package ca.henrychan.gascalculator;

import java.util.ArrayList;

public class SolnGrid {
	private Grid bestSoln = null;
	private ArrayList<Grid> grids = new ArrayList<Grid>();
	public Grid getBestSoln() {
		return bestSoln;
	}
	public void setBestSoln(Grid bestSoln) {
		this.bestSoln = bestSoln;
	}
	public ArrayList<Grid> getGrids() {
		return grids;
	}
	public void setGrids(ArrayList<Grid> grids) {
		this.grids = grids;
	}
	
	
}
