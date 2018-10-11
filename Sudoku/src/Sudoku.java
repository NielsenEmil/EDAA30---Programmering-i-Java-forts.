
public class Sudoku {

	private int[][] sudokuMatrix;
	private Cell firstCell;
	private boolean inputValid;

	/**
	 * Creates a sudoku object with a 9*9 int matrix and @param input.
	 */
	public Sudoku(int[][] input, int a, int b) {
		sudokuMatrix = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				setValue(i, j, input[i][j]);
			}
		}
		firstCell = new Cell(a, b);
		inputValid = inputValid(input);
	}

	/**
	 * Class to abstract the representation of a cell. Cell => (x, y)
	 */
	static class Cell {

		int row, col;

		public Cell(int row, int col) {
			super();
			this.row = row;
			this.col = col;
		}

	};

	/**
	 * Utility function to check whether
	 * 
	 * @param value
	 *            is valid for
	 * @param cell
	 * @return true if value is valid for cell
	 */

	public boolean isValid(Cell cell, int value) {

		if (sudokuMatrix[cell.row][cell.col] != 0) {
			throw new RuntimeException("Cannot call for cell which already has a value");
		}

		// Checks if value exists in the same row
		for (int c = 0; c < 9; c++) {
			if (getValue(cell.row, c) == value)
				return false;
		}

		// Checks if value exists in the same column
		for (int r = 0; r < 9; r++) {
			if (getValue(r, cell.col) == value)
				return false;
		}

		// Checks if value exists in the same region
		// To get the grid we should calculate (x1,y1) (x2,y2)
		int x1 = 3 * (cell.row / 3);
		int y1 = 3 * (cell.col / 3);
		int x2 = x1 + 2;
		int y2 = y1 + 2;

		for (int x = x1; x <= x2; x++)
			for (int y = y1; y <= y2; y++)
				if (getValue(x, y) == value)
					return false;

		// if value not present in row, column and region, return true
		return true;
	}

	// Returns next cell after @param cell.
	static Cell getNextCell(Cell cell) {

		int row = cell.row;
		int col = cell.col;

		col++;

		// reached end of row, go to next row
		if (col > 8) {
			col = 0;
			row++;
		}

		// reached end of matrix, return null
		if (row > 8)
			return null; // reached end

		Cell next = new Cell(row, col);
		return next;
	}

	// Checks if input from GUI is valid to the rules of sudoku
	private boolean inputValid(int[][] input) {
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input.length; j++) {

				int valueOfCell = input[i][j];

				if (valueOfCell >= 1 && valueOfCell <= 9) {

					// Checks if value exists in the same row
					for (int c = 0; c < 9; c++) {
						if (input[i][c] == valueOfCell) {
							if (c != j) {
								return false;
							}
						}

					}

					// Checks if value exists in the same column
					for (int r = 0; r < 9; r++) {
						if (input[r][j] == valueOfCell) {
							if (r != i) {
								return false;
							}
						}

					}

					// Checks if value exists in the same region
					// To get the grid we should calculate (x1,y1) (x2,y2)
					int x1 = 3 * (i / 3);
					int y1 = 3 * (j / 3);
					int x2 = x1 + 2;
					int y2 = y1 + 2;

					for (int x = x1; x <= x2; x++) {
						for (int y = y1; y <= y2; y++) {
							if (input[x][y] == valueOfCell) {
								if (x != i && y != j) {
									return false;
								}
							}
						}
					}
				}

			}
		}
		// if value not present in row, column and region => input valid
		return true;
	}

	/**
	 * @return true if @param sudoku has a solution
	 */
	public boolean solveFirst(Sudoku sudoku) {
		return solve(sudoku.firstCell);
	}

	/**
	 * Solves the sudoku if there is a solution with recursive backtracking method
	 * starting at @param cell.
	 */
	public boolean solve(Cell cell) {

		if (inputValid) {

			// If the cell is null, we have reached the end
			if (cell == null)
				return true;

			// If current cell already has a value: move to next cell
			if (getValue(cell.row, cell.col) != 0) {
				return solve(getNextCell(cell));
			}

			// Checks if any value between 1-9 is valid for this cell by the
			// rules
			// of sudoku
			for (int i = 1; i <= 9; i++) {
				// check if valid, if valid, then update
				boolean valid = isValid(cell, i);

				if (!valid) // If not valid for this cell, try other values
					continue; // Continue with next value in for loop

				// Assign the value to the cell
				setValue(cell.row, cell.col, i);

				// Continue with next cell
				boolean solved = solve(getNextCell(cell));
				// if solved, return true, else try other values
				if (solved)
					return true;
				else
					setValue(cell.row, cell.col, 0); // Reset
				// Continue with other possible values
			}
		}
		// if you reach here, then no value from 1 - 9 for this cell can solve
		// return false
		return false;

	}

	/**
	 * Sets @param value at position (row, column)(@param i, @param j)
	 */
	public void setValue(int i, int j, int value) {
		sudokuMatrix[i][j] = value;
	}

	/**
	 * @return value of position (row, column)(@param i, @param j)
	 */
	public int getValue(int i, int j) {
		return sudokuMatrix[i][j];
	}

}
