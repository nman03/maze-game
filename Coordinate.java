/*
 	Author: Neil Manimtim
 */

package data;

public class Coordinate {
	int row;
	int col;
	char tileType;

	public Coordinate(int row, int col, char tileType){
		this.row = row;
		this.col = col;
		this.tileType = tileType;
	}

	public int getRow(){
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}

	public int getCol(){
		return col;		
	}
	public void setCol(int col) {
		this.col = col;
	}

	public char getTileType() {
		return tileType;
	}

	public void setTileType(char tileType) {
		this.tileType = tileType;
	}
}