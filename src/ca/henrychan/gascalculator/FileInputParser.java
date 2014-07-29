/**
 * Parses from a text file to an array of TestCases
 * @author Henry
 * July 26/2014
 */
package ca.henrychan.gascalculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileInputParser {
	public static TestCase[] parseTestCases(String fileName) throws Exception {
		TestCase[] testCases = null;
		BufferedReader br = null;
		int curTestCaseIndex = 0;
		br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		int numTestCases = Integer.parseInt(line);
		testCases = new TestCase[numTestCases];
		while (curTestCaseIndex < numTestCases) {
			line = br.readLine();
			String[] gridDimension = line.split(" ");
			int n = Integer.parseInt(gridDimension[0]);
			int m = Integer.parseInt(gridDimension[1]);
			TestCase testCase = new TestCase(n,m);
			testCases[curTestCaseIndex] = testCase;
			Grid grid = testCase.getGrid();
			grid.createCells(n, m);

			line = br.readLine();
			String[] startAndEndDimensions = line.split(" ");
			int enx = Integer.parseInt(startAndEndDimensions[0]);
			int eny = Integer.parseInt(startAndEndDimensions[1]);
			int exx = Integer.parseInt(startAndEndDimensions[2]);
			int exy = Integer.parseInt(startAndEndDimensions[3]);
			
			grid.setEn(grid.getCells()[enx][eny]);
			grid.setEx(grid.getCells()[exx][exy]);
			grid.setCar(grid.getCells()[enx][eny]);
			testCase.initSolnGrids();

			for (int nLoop = 0; nLoop < n; nLoop++) {

				line = br.readLine();
				String[] values = line.split(" ");
				for (int mLoop = 0; mLoop < m; mLoop++) {

					grid.getCells()[nLoop][mLoop].setValue(Integer
							.parseInt(values[mLoop]));
				}
			}
			curTestCaseIndex++;
		}
		br.close();
		return testCases;
	}
}
