package com.sudoku.controller;

import com.sudoku.constant.ResultMessage;
import com.sudoku.helper.RowMappingHelper;
import com.sudoku.model.Board;
import com.sudoku.model.Command;
import com.sudoku.model.Result;
import com.sudoku.model.ValidationResult;
import com.sudoku.service.CommandService;
import com.sudoku.service.HintService;
import com.sudoku.service.ValidationService;

/**
 * @author TheintSandi
 * @implNote Get input from View, calls Service to handle result and returns
 *           Result message back to View. View -> Controller -> Service;
 *           Controller -> View
 */
public class SudokuGameController {

	private final Board board;
	private final ValidationService validationService;
	private final HintService hintService;
	private final RowMappingHelper rowMappingHelper;

	public SudokuGameController(Board board, ValidationService validationService, HintService hintService,
			RowMappingHelper rowMappingHelper) {
		this.board = board;
		this.validationService = validationService;
		this.hintService = hintService;
		this.rowMappingHelper = rowMappingHelper;
	}

	/**
	 * Handles raw user input. Returns Result — controller never formats display
	 * strings.
	 *
	 * @param input raw user input
	 * @return Result for View to display
	 */
	public Result handleInput(String input) {
		Command command = CommandService.parse(input);

		switch (command.getType()) {
		case PLACE:
			return handlePlace(command);
		case CLEAR:
			return handleClear(command);
		case HINT:
			return handleHint();
		case CHECK:
			return handleCheck();
		case QUIT:
			return Result.quit(ResultMessage.GOODBYE.getMessage());
		default:
			return Result.failure(ResultMessage.INVALID_COMMAND.getMessage());
		}
	}

	public Board getBoard() {
		return board;
	}

	// ── Handlers ──────────────────────────────────────────────

	private Result handlePlace(Command command) {
		// 1. validate move BEFORE placing
		ValidationResult validation = validationService
				.isValidSudoku(getBoardWithMove(command.getRow(), command.getCol(), command.getNumberAsChar()));

		if (!validation.isValid())
			return Result.failure(validation.getMessage());

		// 2. place only if valid
		boolean placed = board.setValue(command.getRow(), command.getCol(), command.getNumberAsChar());

		if (!placed)
			return Result.failure(ResultMessage.PREFILLED_CELL_PLACING
					.format(rowMappingHelper.getRowLabel(command.getRow()), command.getCol() + 1));

		return checkGameOver();
	}

	/**
	 * Returns a copy of the board with the proposed move applied. Used to validate
	 * before actually placing.
	 *
	 * @param row   row index
	 * @param col   col index
	 * @param value char value to place
	 * @return board copy with proposed move
	 */
	private char[][] getBoardWithMove(int row, int col, char value) {
		char[][] copy = board.getCells();
		copy[row][col] = value;
		return copy;
	}

	private Result handleClear(Command command) {
		boolean cleared = board.clearValue(command.getRow(), command.getCol());

		if (!cleared)
			return Result.failure(ResultMessage.PREFILLED_CELL_CLEAR
					.format(rowMappingHelper.getRowLabel(command.getRow()), command.getCol() + 1));

		return Result.success(ResultMessage.CELL_CLEARED.getMessage());
	}

	private Result handleHint() {
		ValidationResult result = hintService.revealHint(board);
		return result.isValid() ? Result.success(result.getMessage()) : Result.failure(result.getMessage());
	}

	private Result handleCheck() {
		ValidationResult result = validationService.isValidSudoku(board.getCells());
		return result.isValid() ? Result.success(result.getMessage()) : Result.failure(result.getMessage());
	}

	private Result checkGameOver() {
		if (board.isComplete()) {
			ValidationResult result = validationService.isValidSudoku(board.getCells());
			if (result.isValid())
				return Result.gameOver(ResultMessage.GAME_COMPLETE.getMessage());
		}
		return Result.success(ResultMessage.SUCCESS.getMessage());
	}
}