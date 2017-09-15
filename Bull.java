package data;

public class Bull extends Coordinate {

	public Bull(int row, int col) {
		super(row, col, 'B');
	}

	public void spawn(Coordinate[][] map) {
		map[1][1].setTileType('B');
	}

	public void scan(Coordinate[][] map) {

	}
}
