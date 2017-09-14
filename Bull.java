package data;

public class Bull {
	private Coordinate bull;
	
	public Coordinate makeBull(Coordinate[][] map) {
		bull = map[1][1];
		
		return bull;
	}
}
