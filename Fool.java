package data;

public class Fool extends Coordinate {

	public Fool(int row, int col) {
		super(row, col, 'F');
	}
	
	public void spawn(Coordinate[][] map) {
		map[1][1].setTileType('F');
	}
	
	public void moveUp(Coordinate[][] map) {
		map[getRow()][getCol()].setTileType(' ');
    		map[getRow() - 1][getCol()].setTileType('F');
    		setRow(getRow() - 1);
	}
	
	public void moveRight(Coordinate[][] map) {
		map[getRow()][getCol()].setTileType(' ');
    		map[getRow()][getCol() + 1].setTileType('F');
    		setCol(getCol() + 1);
	}
	
	public void moveDown(Coordinate[][] map) {
		map[getRow()][getCol()].setTileType(' ');
    		map[getRow() + 1][getCol()].setTileType('F');
    		setRow(getRow() + 1);
	}
	
	public void moveLeft(Coordinate[][] map) {
		map[getRow()][getCol()].setTileType(' ');
    		map[getRow()][getCol() - 1].setTileType('F');
    		setCol(getCol() - 1);
	}
}
