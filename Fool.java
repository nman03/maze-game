package data;

public class Fool {

	public Fool(Coordinate[][] map) {
		Coordinate fool = map[1][1];
		fool.setRow(1);
		fool.setCol(1);
		fool.setTileType('F');
	}
	
	public void moveUp(Coordinate fool, Coordinate[][] map) {
		map[fool.getRow()][fool.getCol()].setTileType(' ');
    	map[fool.getRow()][fool.getCol() + 1].setTileType('F');
    	fool.setCol(fool.getCol() + 1);
	}
	public void moveRight(Coordinate fool, Coordinate[][] map) {
		
	}
	
	public void moveDown(Coordinate fool, Coordinate[][] map) {
		
	}
	
	public void moveLeft(Coordinate fool, Coordinate[][] map) {
		
	}
}
