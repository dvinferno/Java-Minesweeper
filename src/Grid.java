import java.util.Random;

// https://youtu.be/B7qsJL6Csg8

public class Grid {
	private boolean[][] bombGrid;
	private int[][] countGrid;
	private int numRows;
	private int numColumns;
	private int numBombs;
	
	public Grid() {
		this.numRows = 10;
		this.numColumns = 10;
		this.numBombs = 25;
		createBombGrid();
		createCountGrid();
	}
	
	public Grid(int rows, int columns) {
		this.numRows = rows;
		this.numColumns = columns;
		this.numBombs = 25;
		createBombGrid();
		createCountGrid();
	}
	
	public Grid(int rows, int columns, int numBombs) {
		this.numRows = rows;
		this.numColumns = columns;
		this.numBombs = numBombs;
		createBombGrid();
		createCountGrid();
	}
	
	public int getNumRows() {
		return this.numRows;
	}
	
	public int getNumColumns() {
		return this.numColumns;
	}
	
	public int getNumBombs() {
		return this.numBombs;
	}
	
	public boolean[][] getBombGrid() {
		boolean[][] currentArray = this.bombGrid;
		boolean[][] newArray = new boolean[this.numRows][this.numColumns];
		
		for (int i = 0; i < currentArray.length; i++) {
            for (int j = 0; j < currentArray[i].length; j++) {
            	newArray[i][j] = currentArray[i][j];
            }
        }
		
		return newArray;
	}
	
	public int[][] getCountGrid() {
		int[][] currentArray = this.countGrid;
		int[][] newArray = new int[this.numRows][this.numColumns];
		
		for (int i = 0; i < currentArray.length; i++) {
            for (int j = 0; j < currentArray[i].length; j++) {
            	newArray[i][j] = currentArray[i][j];
            }
        }
		
		return newArray;
	}
	
	public boolean isBombAtLocation(int row, int column) {
		return this.bombGrid[row][column];
	}
	
	public int getCountAtLocation(int row, int column) {
		return this.countGrid[row][column];
	}
	
	private void createBombGrid() {
		this.bombGrid = new boolean[this.numRows][this.numColumns];
		Random rand = new Random();
		
		//Fill the grid with True Values
		for (int i = 0; i < this.bombGrid.length; i++) {
            for (int j = 0; j < this.bombGrid[i].length; j++) {
            	this.bombGrid[i][j] = false;
            }
        }
		
		//Fill in bomb spaces
		int bombsToAdd = this.numBombs;
		
		while (bombsToAdd > 0) {
			int randomRow = rand.nextInt(this.numRows);
			int randomColumn =  rand.nextInt(this.numColumns);
			
			if (!this.isBombAtLocation(randomRow, randomColumn)) {
				this.bombGrid[randomRow][randomColumn] = true;
				bombsToAdd--;
			}
			
		}
		
	}
	
	
	public int checkCell(boolean[][] arr, int x, int y){
	    int count = 0;
		for(int i = y - 1; i < y + 2; i++){ // Check row
	        if(i < 0 || i >= arr.length) continue; // Check if cell is out of bounds
	        for(int j = x - 1; j < x + 2; j++){ // Check column
	            if(j < 0 || j >= arr[i].length) continue; // Check if out of bounds
	            if(arr[i][j]){
	                count++; // Count bombs in neighborhood
	            }
	        }
	    }
		return count;
	}
	
	
	private void createCountGrid() {
		this.countGrid = new int[this.numRows][this.numColumns];
		int total = 0;
		for(int i = 0; i < this.bombGrid.length; i++){
		    for(int j = 0; j < this.bombGrid[i].length; j++){
		        total = checkCell(this.bombGrid, j, i);
		        this.countGrid[i][j] = total;
		    }
		}
	}
	
	
}
