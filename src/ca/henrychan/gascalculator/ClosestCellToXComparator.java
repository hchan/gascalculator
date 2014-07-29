package ca.henrychan.gascalculator;

import java.util.Comparator;

public class ClosestCellToXComparator implements Comparator<Cell> {
	private Grid grid;
	public ClosestCellToXComparator(Grid grid) {
		this.grid = grid;
	}

	@Override
	public int compare(Cell o1, Cell o2) {
		int o1xDelta = o1.getX() - grid.getEx().getX(); // negative means should move right, positive means move left, 0 means try to move up/down first
		int o1yDelta = o1.getY() - grid.getEx().getY(); // negative means should move down, positive means move up, 0 means try to move left/right first
		
		int o2xDelta = o2.getX() - grid.getEx().getX(); 
		int o2yDelta = o2.getY() - grid.getEx().getY(); 
//		
//		
//		if (car2yDelta == 0) {
//			return -1;
//		}
//				Math.abs(car2xDelta) <= Math.abs(car2yDelta) && car2xDelta != 0 || ) {
//		if (o1.getX() > o2.getX()) {
//				return -1;
//			} else {
//				return 0;
//			}
//		}
//		else {
//			return 0;
//		}
		
		int o1DistanceToX = Math.abs(o1xDelta) + Math.abs(o1yDelta);
		int o2DistanceToX = Math.abs(o2xDelta) + Math.abs(o2yDelta);
		int retval = Integer.compare(o1DistanceToX, o2DistanceToX);
		if (retval == 0) {
			retval = -Integer.compare(o1.getValue(), o2.getValue());
		}
		
		return retval;
	}

}
