/**
 * This program will calculate the gas used in the shortest amount of moves from postion n to x on a n by m grid
 * See problem.txt
 * Optional main parameter: <name of input file>
 * 
 * @author Henry Chan
 * July 28/2014
 */
package ca.henrychan.gascalculator;


/*
 * main class
 * 1) does parsing to create testcases
 * 2) runs each testcase
 */
public class GasCalculator {
	public static final String MISSION_IMPOSSIBLE = "Mission Impossible.";
	public static final String DEFAULT_INPUT_FILENAME = "input-large.in";
	public static void main(String[] args) {
		try {
			
			
			TestCase[] testCases = null;
			String fileName = null;
			// parsing
			if (args.length > 0) {
				fileName = args[0];
			} else {
				fileName = DEFAULT_INPUT_FILENAME;
			}
			testCases = FileInputParser.parseTestCases(fileName);
//
//			testCases[26].calculate();
//			Grid grid2 = testCases[26].getBestSoln();
//			System.out.println(grid2.getSummationOfGas());
//			System.exit(-1);
			
			// runs each testcase
			for (int i = 0; i < testCases.length; i++) {
				TestCase testCase = testCases[i];
				
				//GridDumper.dumpGridText(testCase.getGrid());
				try {
					testCase.calculate();
					Grid grid = testCase.getBestSoln();
					System.out.print("Case #" + (i+1) + ": "); // refactor to constant? nah - if we need an overhaul, might as well use a template
					if (grid == null) {
						System.out.println(MISSION_IMPOSSIBLE);
						GridDumper.dumpGridHTML(testCases[i].getGrid(), (i+1));
					} else {
						System.out.println(grid.getSummationOfGas());
						GridDumper.dumpGridHTML(grid, (i+1));
					}
					testCases[i] = null; // free up some memory
					System.gc();
				} catch (Error error) { // alright, I admit this is a bit hacky!
					System.out.print("Case #" + (i+1) + ": ");
					System.out.println(MISSION_IMPOSSIBLE);
					System.err.println("DOH - try increasting stack size -Xss512m"); // increase stack -Xss512m ?
					
					GridDumper.dumpGridHTML(testCases[i].getGrid(), (i+1));
				} finally {
					System.gc();
				}
			}		
//			GridDumper.dumpGridHTML(testCases[4].getGrid());
//			testCases[4].calculate();
//			System.out.println(testCases[4].getBestSoln().getSummationOfGas());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
