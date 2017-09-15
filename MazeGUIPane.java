package gui;

import data.Bull;
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
	                
	                else if (keyEvent.getCode() == KeyCode.RIGHT && pamplona[n][m + 1].getTileType() != 'W') {
	                	labels[n][m].getStyleClass().clear(); 
	                	labels[n][m].getStyleClass().add("space");
	                	labels[n][m + 1].getStyleClass().clear(); 
	                	labels[n][m + 1].getStyleClass().add("fool");
	                	fool.moveRight(pamplona);
	                	moves++;
	                }
	                else if (keyEvent.getCode() == KeyCode.DOWN && pamplona[n + 1][m].getTileType() != 'W') {
	                	labels[n][m].getStyleClass().clear(); 
	                	labels[n][m].getStyleClass().add("space");
	                	labels[n + 1][m].getStyleClass().clear(); 
	                	labels[n + 1][m].getStyleClass().add("fool");
	                	fool.moveDown(pamplona);
	                	checkVictory(fool);
	                	moves++;
	                }
	                else if (keyEvent.getCode() == KeyCode.LEFT && pamplona[n][m - 1].getTileType() != 'W') {
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
                		checkLoss();
                		moveBull();
                		
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
	
	private void moveBull() {
		int i = 0, j = 0, k = 0, l = 0;
		int n = bull.getRow();
		int m = bull.getCol();
		
		while (pamplona[n][m + k].getTileType() != 'W') {
			if (pamplona[n][m + k].getTileType() == 'F') {
				labels[n][m].getStyleClass().clear(); 
            	labels[n][m].getStyleClass().add("space");
            	labels[n][m + 1].getStyleClass().clear(); 
            	labels[n][m + 1].getStyleClass().add("bull");
            	pamplona[n][m].setTileType(' ');
            	pamplona[n][m + 1].setTileType('B');
            	bull.setCol(m + 1);
            	bull.setRow(n);
            	checkLoss();
			}
			k++;
		}
		while (pamplona[n][m - l].getTileType() != 'W') {
			if (pamplona[n][m - l].getTileType() == 'F') {
				labels[n][m].getStyleClass().clear(); 
            	labels[n][m].getStyleClass().add("space");
            	labels[n][m - 1].getStyleClass().clear(); 
            	labels[n][m - 1].getStyleClass().add("bull");
            	pamplona[n][m].setTileType(' ');
            	pamplona[n][m - 1].setTileType('B');
            	bull.setCol(m - 1);
            	bull.setRow(n);
            	checkLoss();
			}
			l++;
		}

		while (pamplona[n + i][m].getTileType() != 'W') {
			if (pamplona[n + i][m].getTileType() == 'F') {
				labels[n][m].getStyleClass().clear(); 
            	labels[n][m].getStyleClass().add("space");
            	labels[n + 1][m].getStyleClass().clear(); 
            	labels[n + 1][m].getStyleClass().add("bull");
            	pamplona[n][m].setTileType(' ');
            	pamplona[n + 1][m].setTileType('B');
            	bull.setRow(n + 1);
            	bull.setCol(m);
            	checkLoss();
			}
			i++;
		}
		while (pamplona[n - j][m].getTileType() != 'W') {
			if (pamplona[n - j][m].getTileType() == 'F') {
				labels[n][m].getStyleClass().clear(); 
            	labels[n][m].getStyleClass().add("space");
            	labels[n - 1][m].getStyleClass().clear(); 
            	labels[n - 1][m].getStyleClass().add("bull");
            	pamplona[n][m].setTileType(' ');
            	pamplona[n - 1][m].setTileType('B');
            	bull.setRow(n - 1);
            	bull.setCol(m);
            	checkLoss();
			}
			j++;
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
