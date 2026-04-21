package com.sudoku.model;

import com.sudoku.constant.SudokuConstant;

/**
 * @author TheintSandi
 * @implNote Represents the 9x9 Sudoku grid.
 * Assumes the initial board has already been validated
 * by ValidationService before construction.
 */
public class Board {

    private final Cell[][] grid;

    /**
     * Constructs a Board from a validated 9x9 char array.
     *
     * @param initialBoard validated 9x9 char array
     */
    public Board(char[][] initialBoard) {
        this.grid = new Cell[SudokuConstant.BOARD_SIZE][SudokuConstant.BOARD_SIZE];
        initGrid(initialBoard);
    }

    /**
     * Initialises the grid from the given char array.
     * Non-empty cells are marked as pre-filled.
     *
     * @param initialBoard the 9x9 char array
     */
    private void initGrid(char[][] initialBoard) {
        for (int row = 0; row < SudokuConstant.BOARD_SIZE; row++)
            for (int col = 0; col < SudokuConstant.BOARD_SIZE; col++) {
                char value     = initialBoard[row][col];
                boolean filled = value != SudokuConstant.EMPTY_CELL;
                grid[row][col] = new Cell(value, filled);
            }
    }

    /**
     * Returns the Cell at the given position.
     *
     * @param row row index (0-8)
     * @param col column index (0-8)
     * @return the Cell at that position
     */
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Places a value in the cell at the given position.
     * Returns false if cell is pre-filled — no exception thrown.
     *
     * @param row   row index (0-8)
     * @param col   column index (0-8)
     * @param value char value to place ('1'-'9')
     * @return true if placed successfully, false if pre-filled
     */
    public boolean setValue(int row, int col, char value) {
        return grid[row][col].setValue(value);
    }

    /**
     * Clears the cell at the given position.
     * Returns false if cell is pre-filled — no exception thrown.
     *
     * @param row row index (0-8)
     * @param col column index (0-8)
     * @return true if cleared successfully, false if pre-filled
     */
    public boolean clearValue(int row, int col) {
        return grid[row][col].clear();
    }

    /**
     * Returns the board as a 9x9 char array.
     * Used by ValidationService for validation checks.
     *
     * @return 9x9 char array of current board state
     */
    public char[][] getCells() {
        char[][] cells =
            new char[SudokuConstant.BOARD_SIZE][SudokuConstant.BOARD_SIZE];
        for (int row = 0; row < SudokuConstant.BOARD_SIZE; row++)
            for (int col = 0; col < SudokuConstant.BOARD_SIZE; col++)
                cells[row][col] = grid[row][col].getValue();
        return cells;
    }

    /**
     * Checks whether the board is completely filled.
     * Does not check validity — only checks no empty cells remain.
     *
     * @return true if no empty cells remain
     */
    public boolean isComplete() {
        for (int row = 0; row < SudokuConstant.BOARD_SIZE; row++)
            for (int col = 0; col < SudokuConstant.BOARD_SIZE; col++)
                if (grid[row][col].isEmpty()) return false;
        return true;
    }

    /**
     * Reveals one hint by filling the first empty cell
     * with the correct value from the solution.
     *
     * @param solution the complete solution 9x9 char array
     * @return true if hint was revealed, false if no empty cells remain
     */
    public boolean revealHint(char[][] solution) {
        for (int row = 0; row < SudokuConstant.BOARD_SIZE; row++)
            for (int col = 0; col < SudokuConstant.BOARD_SIZE; col++)
                if (grid[row][col].isEmpty()) {
                    grid[row][col].setValue(solution[row][col]);
                    return true;
                }
        return false;
    }

    /**
     * Returns a formatted string representation of the board.
     *
     * @return formatted Sudoku grid as String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n    1   2   3   4   5   6   7   8   9\n");
        sb.append("  +-------+-------+-------+\n");
        String[] rowLabels = {"A","B","C","D","E","F","G","H","I"};
        for (int row = 0; row < SudokuConstant.BOARD_SIZE; row++) {
            sb.append(rowLabels[row]).append(" | ");
            for (int col = 0; col < SudokuConstant.BOARD_SIZE; col++) {
                sb.append(grid[row][col]).append("   ");
                if (col == 2 || col == 5) sb.append("| ");
            }
            sb.append("|\n");
            if (row == 2 || row == 5 || row == 8)
                sb.append("  +-------+-------+-------+\n");
        }
        return sb.toString();
    }
}