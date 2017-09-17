package data;

public class Bull extends Coordinate {

	public Bull(int row, int col) {
		super(row, col, 'B');
	}

	public void spawn(Coordinate[][] map) {
		map[1][1].setTileType('B');
	}

	public Vision scan(Coordinate[][] map) {
		int n = getRow();
		int m = getCol();
		int u, d, l, r;
		u = d = l = r = 0;
	
		while (map[n + d][m].getTileType() != 'W') {
			if (map[n + d][m].getTileType() == 'F') {
				return new Vision(true, map[n + d][m]);
			}
			d++;
		}
		
		while (map[n][m + r].getTileType() != 'W') {
			if (map[n][m + r].getTileType() == 'F') {
				return new Vision(true, map[n][m + r]);
			}
			r++;
		}
		
		while (map[n][m - l].getTileType() != 'W') {
			if (map[n][m - l].getTileType() == 'F') {
				return new Vision(true, map[n][m - l]);
			}
			l++;
		}
		
		while (map[n - u][m].getTileType() != 'W') {
			if (map[n - u][m].getTileType() == 'F') {
				return new Vision(true, map[n - u][m]);
			}
			u++;
		}
		
		return new Vision(false, map[n][m]);
	}
	
	public void moveUp(Coordinate[][] map) {
		map[getRow()][getCol()].setTileType(' ');
    		map[getRow() - 1][getCol()].setTileType('B');
    		setRow(getRow() - 1);
	}
	
	public void moveRight(Coordinate[][] map) {
		map[getRow()][getCol()].setTileType(' ');
    		map[getRow()][getCol() + 1].setTileType('B');
    		setCol(getCol() + 1);
	}
	
	public void moveDown(Coordinate[][] map) {
		map[getRow()][getCol()].setTileType(' ');
    		map[getRow() + 1][getCol()].setTileType('B');
    		setRow(getRow() + 1);
	}
	
	public void moveLeft(Coordinate[][] map) {
		map[getRow()][getCol()].setTileType(' ');
    			map[getRow()][getCol() - 1].setTileType('B');
    	setCol(getCol() - 1);
	}
	
	public class Vision {
		boolean isVisible;
		Coordinate location;
		
		public Vision(boolean isVisible, Coordinate location) {
			this.isVisible = isVisible;
			this.location = location;
		}
		
		public boolean getVisible() {
			return isVisible;
		}
		
		public Coordinate getLocation() {
			return location;
		}
		
	}	
}
