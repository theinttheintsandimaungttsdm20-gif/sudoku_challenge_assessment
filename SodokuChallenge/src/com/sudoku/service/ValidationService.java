package com.sudoku.service;

import java.util.HashSet;
import java.util.Set;

import com.sudoku.constant.SudokuConstant;

/**
 * @author TheintSandi
 * @implNote Implementation to validate Sudoku rules: <br>
 *           1. Row rule: Each row must contain digits 1–9 without repetition
 *           <br>
 *           2. Column rule: Each column must contain digits 1–9 without
 *           repetition <br>
 *           3. 3x3 Box rule: Each of the nine 3x3 sub-boxes must contain digits
 *           1–9 without repetition <br>
 *           4. Valid box size must be row 9, column 9 and characters must be
 *           only 1-9 and empty
 */
public class ValidationService {

	/**
	 * Service use to transform row index to Char A - I
	 */
	private final RowMappingService rowMappingService;

	public ValidationService(RowMappingService rowMappingService) {
		this.rowMappingService = rowMappingService;
	}

	public boolean isValidSudoku(char[][] board) {
		// Track seen numbers for rows, columns, and boxes
		validate(board);
		Set<String> seen = new HashSet<>();

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				char cell = board[row][col];
				// Create unique identifiers and expose positions for each rule e.g. row2:3,
				// col3:3, box01:3
				String rowKey = String.format(SudokuConstant.ROW_KEY_ID, row, cell);
				String colKey = String.format(SudokuConstant.COL_KEY_ID, col, cell);
				String boxKey = String.format(SudokuConstant.BOX_KEY_ID, row / 3, col / 3, cell);

				if (cell != SudokuConstant.EMPTY_CELL) {
					// Check if any rule is violated
					if (seen.contains(rowKey)) {
						System.out.println(String.format(SudokuConstant.MSG_INVALID_ROW, cell,
								rowMappingService.getRowLabel(row)));
						return false;
					}
					if (seen.contains(colKey)) {
						System.out.println(String.format(SudokuConstant.MSG_INVALID_COL, cell, (col + 1)));
						return false;
					}
					if (seen.contains(boxKey)) {
						System.out.println(String.format(SudokuConstant.MSG_INVALID_BOX, cell));
						return false;
					}

					// Add all identifiers to seen set
					seen.add(rowKey);
					seen.add(colKey);
					seen.add(boxKey);
				}
			}
		}
		return true;
	}

	private void validate(char[][] board) {
		checkNullValues(board);
		checkInvalidRowCount(board);
		checkInvalidColumnCount(board);
		checkInvalidCharacter(board);
	}

	private void checkInvalidCharacter(char[][] board) {
		for (int row = 0; row < SudokuConstant.BOARD_SIZE; row++)
			for (int col = 0; col < SudokuConstant.BOARD_SIZE; col++)
				if (!isValidChar(board[row][col]))
					throw new IllegalArgumentException(String.format(SudokuConstant.MSG_INVALID_CHAR, board[row][col],
							rowMappingService.getRowLabel(row), col));

	}

	private void checkInvalidColumnCount(char[][] board) {
		for (int row = 0; row < board.length; row++) {
			if (board[row] == null)
				// Row A, B, C
				throw new IllegalArgumentException(
						String.format(SudokuConstant.MSG_NULL_ROW, rowMappingService.getRowLabel(row)));
			if (board[row].length != SudokuConstant.BOARD_SIZE)
				throw new IllegalArgumentException(
						String.format(SudokuConstant.MSG_INVALID_COL_LENGTH, rowMappingService.getRowLabel(row)));
		}

	}

	private void checkInvalidRowCount(char[][] board) {
		if (board.length != SudokuConstant.BOARD_SIZE)
			throw new IllegalArgumentException(String.format(SudokuConstant.MSG_INVALID_ROW_LENGTH + board.length));

	}

	private void checkNullValues(char[][] board) {
		if (board == null)
			throw new IllegalArgumentException(String.format(SudokuConstant.MSG_NULL_BOARD));

	}

	private boolean isValidChar(char c) {
		return c == SudokuConstant.EMPTY_CELL || (c >= '1' && c <= '9');
	}
}
