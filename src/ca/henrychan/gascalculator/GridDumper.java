package ca.henrychan.gascalculator;

import java.io.FileWriter;
import java.util.ArrayList;

public class GridDumper {


	

	
	// helper methods to debug grid state
	public static void dumpGridTextVisited(Grid grid) {
		System.out.println("-----------------");
		int n = grid.getCells().length;
		int m = grid.getCells()[0].length;
		for (int mLoop = 0; mLoop < m; mLoop++) {
			for (int nLoop = 0; nLoop < n; nLoop++) {
				if (isInArrayList(nLoop, mLoop, grid.getPath())) {
					System.out.print("+");
				} else {
					System.out.print("-");
				}
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	
	private static boolean isInArrayList(int x, int y, ArrayList<Cell> cells) {
		boolean retval = false;
		for (Cell cell : cells) {
			if (cell.getX() == x && cell.getY() == y) {
				retval = true;
				break;
			}
		}
		return retval;
	}
	

	public static void dumpGridText(Grid grid) {
		
		int n = grid.getCells().length;
		int m = grid.getCells()[0].length;
		for (int mLoop = 0; mLoop < m; mLoop++) {
			for (int nLoop = 0; nLoop < n; nLoop++) {
				Cell cell = grid.getCells()[nLoop][mLoop];
				System.out.print(cell.getValue());
				if (grid.getEn() == cell) {
					System.out.print("(n)");
				}
				if (grid.getEx() == cell) {
					System.out.print("(x)");
				}
				
				
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	
	public static void dumpGridHTML(Grid grid, int index) {
		try {
			FileWriter fw = new FileWriter("grid_" + index + ".html");
			fw.write("<table border='1'>");
			int n = grid.getCells().length;
			int m = grid.getCells()[0].length;
			for (int mLoop = 0; mLoop < m; mLoop++) {
				fw.write("<tr>\n");
				for (int nLoop = 0; nLoop < n; nLoop++) {
					Cell cell = grid.getCells()[nLoop][mLoop];
					String style = "";
					
					for (Cell cellInPath : grid.getPath()) {
						if (cellInPath.getX() == nLoop && cellInPath.getY() == mLoop) {
							style = "style='background-color: #00ff00'";
							break;
						}
					}
					
					fw.write("<td " + style + ">");
					
					fw.write(cell.getValue() + "");
					if (grid.getEn() == cell) {
						fw.write("(n)");
					}
					if (grid.getEx() == cell) {
						fw.write("(x)");
					}
					
					fw.write("</td>");
				}
				fw.write("</tr>\n");
			}
			fw.write("</table>");
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
