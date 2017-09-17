package gui;

import data.Bull;
import data.Bull.Vision;
import data.Coordinate;
import data.Fool;
import data.StreetMap;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MazeGUIPane extends BorderPane {
	private Label[][] labels;
	private HBox header;
	private GridPane gp;
	private HBox footer;
	private boolean gameStarted;
	private boolean gameOver;
	private Label gameStatus;
	private int moves;
	private Coordinate[][] pamplona;
	private Fool fool;
	private Bull bull;
	private Coordinate lastLocation;

	public void createMap() {
		pamplona = new StreetMap().makeStreetMap();
		setUpTitle();
		setUpGridPane();
		setUpFooter();
		setTop(header);
		setCenter(gp);
		setBottom(footer);
	}
	
	public void copyMap() {
		pamplona = new StreetMap().copyStreetMap(pamplona);
		setUpTitle();
		setUpGridPane();
		setUpFooter();
		setTop(header);
		setCenter(gp);
		setBottom(footer);
	}

	private void setUpTitle() {
		header = new HBox();
		header.getStyleClass().add("title");
		header.getChildren().add(new Label("Map of Pamplona"));
	}

	private void setUpGridPane() {
		gp = new GridPane();
		labels = new Label[25][25];

		for (int row = 0; row < 25; row++) {
			for (int col = 0; col < 25; col++) {
				Label tile = new Label(" ");
				labels[row][col] = tile;
				setUpLabel(tile, row, col);
				gp.add(tile, col, row);
			}
		}
	}

	private void setUpLabel(final Label tile, final int row, final int col) {
		if (row == 0 && col == 1) {
			tile.getStyleClass().add("start"); // sets up the start tile
			tile.setText("S");
		} else if (row == 24 && col == 23) {
			tile.getStyleClass().add("end"); // sets up the end tile
			tile.setText("E");
		} else if (row == 0 || col == 0 || row == 24 || col == 24) {
			tile.getStyleClass().add("wall"); // sets up the outer walls
		} else { 
			if ((row < 3 && col == 1) || (row > 21 && col == 23)) {
				tile.getStyleClass().add("space"); // makes sure first two tiles in front of start and end tiles are spaces not walls
				pamplona[row][col].setTileType(' ');
			}
			
			tile.setOnMouseClicked(new EventHandler<InputEvent>() {
				// allows user to change the tiles inside the outer walls by clicking

				@Override
				public void handle(InputEvent arg0) { 
					if (pamplona[row][col].getTileType() == ' ' && !gameStarted) {
						pamplona[row][col].setTileType('W'); // updates the StreetMap
						tile.getStyleClass().clear();
						tile.getStyleClass().add("wall");
					} else if (pamplona[row][col].getTileType() == 'W' && !gameStarted){
						pamplona[row][col].setTileType(' '); // updates the StreetMap
						tile.getStyleClass().clear(); 
						tile.getStyleClass().add("space");
					}
				}	
			});

			if (pamplona[row][col].getTileType() == 'W') {
				tile.getStyleClass().add("wall"); // sets up inside walls
			} else {
				tile.getStyleClass().add("space"); // sets up empty spaces
			}
		}
	}

	private void setUpFooter() {
		Button resetButton = new Button("Reset Map");
		resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				pamplona = new StreetMap().makeStreetMap();
				createMap(); // reset the map
				gameStarted = false;
				moves = 0;
			}
		});
		
		gameStatus = new Label(" ");
		gameStatus.getStyleClass().add("gameStat");
		
		Button runButton = new Button("Run");
		runButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				gameStarted = true;
				gameOver = false;
				startRunning();
				gameStatus.setText("Start Running!");
			}
		});
		
		footer = new HBox();
		footer.getChildren().add(resetButton);
		footer.getChildren().add(gameStatus);
		footer.getChildren().add(runButton);
		footer.getStyleClass().add("button");
	}
	
	private void startRunning() {
		copyMap();
		moves = 0;
		labels[1][1].getStyleClass().clear();
		labels[1][1].getStyleClass().add("fool");
		fool = new Fool(1, 1);
		fool.spawn(pamplona);
		
		
		setOnKeyPressed(new EventHandler<KeyEvent>()  {
			@Override
            public void handle(final KeyEvent keyEvent) {
				if (gameStarted && !gameOver) {
					int n = fool.getRow();
					int m = fool.getCol();
					
	                if (keyEvent.getCode() == KeyCode.UP && pamplona[n - 1][m].getTileType() != 'W') {
	                	labels[n][m].getStyleClass().clear(); 
	                	labels[n][m].getStyleClass().add("space");
	                	labels[n - 1][m].getStyleClass().clear(); 
	                	labels[n - 1][m].getStyleClass().add("fool");
	                	fool.moveUp(pamplona);
	                	moves++;
	                }
	                
	                if (keyEvent.getCode() == KeyCode.RIGHT && pamplona[n][m + 1].getTileType() != 'W') {
	                	labels[n][m].getStyleClass().clear(); 
	                	labels[n][m].getStyleClass().add("space");
	                	labels[n][m + 1].getStyleClass().clear(); 
	                	labels[n][m + 1].getStyleClass().add("fool");
	                	fool.moveRight(pamplona);
	                	moves++;
	                }
	                
	                if (keyEvent.getCode() == KeyCode.DOWN && pamplona[n + 1][m].getTileType() != 'W') {
	                	labels[n][m].getStyleClass().clear(); 
	                	labels[n][m].getStyleClass().add("space");
	                	labels[n + 1][m].getStyleClass().clear(); 
	                	labels[n + 1][m].getStyleClass().add("fool");
	                	fool.moveDown(pamplona);
	                	checkVictory(fool);
	                	moves++;
	                }
	                
	                if (keyEvent.getCode() == KeyCode.LEFT && pamplona[n][m - 1].getTileType() != 'W') {
	                	labels[n][m].getStyleClass().clear(); 
	                	labels[n][m].getStyleClass().add("space");
	                	labels[n][m - 1].getStyleClass().clear(); 
	                	labels[n][m - 1 ].getStyleClass().add("fool");
	                	fool.moveLeft(pamplona);
	                	moves++;
	                }
	                
	                if (moves == 5) {
	                	bull = new Bull(1, 1);
                		bull.spawn(pamplona);
                		labels[1][1].getStyleClass().clear();
                		labels[1][1].getStyleClass().add("bull");
                	}
	                
	                else if (moves > 5){
	                	Vision vision = bull.scan(pamplona);	
	                	Coordinate location = vision.getLocation();
	                	int u = bull.getRow();
	                	int v = bull.getCol();
	                	
	                	if (vision.getVisible()) {
	                		lastLocation = location;
	                	} else {
	                		location = lastLocation;
	                	}
	                	
	                	if (location.getRow() != bull.getRow()) {
	            			int i = location.getRow() - bull.getRow();
	            			if (i > 0) {
	            				labels[u][v].getStyleClass().clear(); 
	                        	labels[u][v].getStyleClass().add("space");
	                        	labels[u + 1][v].getStyleClass().clear(); 
	                        	labels[u + 1][v].getStyleClass().add("bull");
	                        	bull.moveDown(pamplona);
	            			} else {
	            				labels[u][v].getStyleClass().clear(); 
	                        	labels[u][v].getStyleClass().add("space");
	                        	labels[u - 1][v].getStyleClass().clear(); 
	                        	labels[u - 1][v].getStyleClass().add("bull");
	                        	bull.moveUp(pamplona);
	            			}
	            		} else if (location.getCol() != bull.getCol()) {
	            			int j = location.getCol() - bull.getCol();
	            			if (j > 0) {
	            				labels[u][v].getStyleClass().clear(); 
	                        	labels[u][v].getStyleClass().add("space");
	                        	labels[u][v + 1].getStyleClass().clear(); 
	                        	labels[u][v + 1].getStyleClass().add("bull");
	                        	bull.moveRight(pamplona);
	            			} else {
	            				labels[u][v].getStyleClass().clear(); 
	                        	labels[u][v].getStyleClass().add("space");
	                        	labels[u][v - 1].getStyleClass().clear(); 
	                        	labels[u][v - 1].getStyleClass().add("bull");
	                        	bull.moveLeft(pamplona);
	            			}
	            		} else {
	            			
	            		}
	                	
	                	
         	
                		checkLoss();
                		
                	}
				}
            }
		});	
		
		
	}
	
	private void checkVictory(Coordinate fool) {		
		if (fool.getRow() == 24 && fool.getCol() == 23) {
			gameOver = true;
			gameStatus.setText("You Win!");
			gameStatus.getStyleClass().add("gameWin");
			pamplona[fool.getRow()][fool.getCol()].setTileType('E');
			labels[fool.getRow()][fool.getCol()].getStyleClass().clear(); 
        	labels[fool.getRow()][fool.getCol()].getStyleClass().add("end");
			
		}
	}	
	
	private void checkLoss() {		
		if (fool.getRow() == bull.getRow() && fool.getCol() == bull.getCol()) {
			gameOver = true;
			gameStatus.setText("You Lose!");
			gameStatus.getStyleClass().add("gameLoss");
		}
	}
}
