package com.sudoku.service;

import com.sudoku.constant.ResultMessage;
import com.sudoku.constant.SudokuConstant;
import com.sudoku.helper.RowMappingHelper;
import com.sudoku.model.Board;
import com.sudoku.model.ValidationResult;

/**
 * @author TheintSandi
 * @implNote Service responsible for revealing hints to the user.
 */
public class HintService {

	private final char[][] solution;

	/**
	 * Constructs HintService with the complete solution board.
	 *
	 * @param solution the complete 9x9 solution char array
	 */
	public HintService(char[][] solution) {
		this.solution = solution;
	}

	/**
	 * Finds the first empty cell and fills it with the correct value.
	 *
	 * @param board the current game board
	 * @return ValidationResult with hint position message or no hint message
	 */
	public ValidationResult revealHint(Board board) {
		RowMappingHelper rowMapperHelper = new RowMappingHelper();
		for (int row = 0; row < SudokuConstant.BOARD_SIZE; row++) {
			for (int col = 0; col < SudokuConstant.BOARD_SIZE; col++) {
				if (board.getCell(row, col).isEmpty()) {
					board.setValue(row, col, solution[row][col]);
					return ValidationResult.success(ResultMessage.HINT_GIVEN.format(rowMapperHelper.getRowLabel(row),
							col + 1, solution[row][col]));
				}
			}
		}
		return ValidationResult.failure(ResultMessage.HINT_NONE);
	}
}