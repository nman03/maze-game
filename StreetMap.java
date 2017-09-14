/*
 	Author: Neil Manimtim
 */

package data;

public class StreetMap {

	public Coordinate[][] makeStreetMap() {
		Coordinate[][] streetMap = new Coordinate[25][25];

		for (int row = 0 ; row < 25 ; row++) {
			for (int col = 0 ; col < 25 ; col++) {
				if (row == 0 && col == 1) {
					streetMap[row][col] = new Coordinate(row, col, 'S'); // makes start tile
				} else if (row == 24 && col == 23) {
					streetMap[row][col] = new Coordinate(row, col, 'E'); // makes end tile
				} else if (row == 0 || col == 0 || row == 24 || col == 24) {
					streetMap[row][col] = new Coordinate(row, col, 'W'); // makes outer walls
				} else {
					if ((row < 3 && col == 1) || (row > 21 && col == 23)) {
						streetMap[row][col] = new Coordinate(row, col, ' '); // make first two tiles in front of the start and end always start as space
					}	
					int num = (int)(Math.random() * (5)); // randomizer choosing from 0 to 4 
					if (num == 4) {
						streetMap[row][col] = new Coordinate(row, col, 'W'); // make walls have a 20 percent chance of occurring inside 
					} 	
					else {
						streetMap[row][col] = new Coordinate(row, col, ' '); // make the rest occur as empty spaces
					}
				}	
			}
		}
		return streetMap;
	}
	
	public Coordinate[][] copyStreetMap(Coordinate[][] map) {
		int rowSize = map.length;
		int colSize = map[0].length;
		Coordinate[][] newMap = new Coordinate[rowSize][colSize];
		
		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {
				newMap[row][col] = new Coordinate(row, col, map[row][col].getTileType());
			}
		}
		
		return newMap;
	}
}
