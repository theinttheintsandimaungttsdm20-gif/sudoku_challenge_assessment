package com.sudoku.config;

import com.sudoku.controller.SudokuGameController;
import com.sudoku.helper.RowMappingHelper;
import com.sudoku.model.Board;
import com.sudoku.service.HintService;
import com.sudoku.service.PuzzleService;
import com.sudoku.service.ValidationService;

/**
 * @author TheintSandi
 * @implNote Game configuration by wiring all dependencies together. Call in
 *           start of Main class clean and focused on entry point only.
 */
public class SudokuGameConfig {

	/**
	 * Initialises and returns a ready-to-start GameView.
	 *
	 * @return configured SudokuGameView
	 */
	public static SudokuGameController init() {
		char[][] initialBoard = PuzzleService.generatePuzzle();
		char[][] solution = PuzzleService.getSolution();

		Board board = new Board(initialBoard);

		RowMappingHelper rowMappingHelper = new RowMappingHelper();
		ValidationService validationService = new ValidationService(rowMappingHelper);
		HintService hintService = new HintService(solution);

		return new SudokuGameController(board, validationService, hintService, rowMappingHelper);
	}
}
