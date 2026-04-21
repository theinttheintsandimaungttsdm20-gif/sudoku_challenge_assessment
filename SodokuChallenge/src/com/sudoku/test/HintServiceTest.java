package com.sudoku.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sudoku.constant.SudokuConstant;
import com.sudoku.model.Board;
import com.sudoku.model.ValidationResult;
import com.sudoku.service.HintService;
import com.sudoku.service.PuzzleService;

/**
 * @author TheintSandi
 * @impleNote Sudoku giving hint test cases that covers: <br>
 * - Hint Reveal. <br>
 * - Filling Correct number in cell. <br>
 * - Filling number when board is complete.
 */
public class HintServiceTest {

	private HintService hintService;
	private Board board;

	@BeforeEach
	void setUp() {
		board = new Board(PuzzleService.generatePuzzle());
		hintService = new HintService(PuzzleService.getSolution());
	}

	// Hint Reveal
	@Test
	void shouldReturnSuccessWhenHintIsRevealed() {
		ValidationResult result = hintService.revealHint(board);
		assertTrue(result.isValid());
	}

	// search and fill correct value
	@Test
	void shouldFillCorrectValueFromSolution() {
		char[][] before = board.getCells();
		int hintRow = -1, hintCol = -1;
		outer: for (int row = 0; row < 9; row++)
			for (int col = 0; col < 9; col++)
				if (before[row][col] == SudokuConstant.EMPTY_CELL) {
					hintRow = row;
					hintCol = col;
					break outer;
				}
		hintService.revealHint(board);
		assertEquals(PuzzleService.getSolution()[hintRow][hintCol], board.getCell(hintRow, hintCol).getValue());
	}

	@Test
	void shouldReturnFailureWhenBoardIsComplete() {
		Board completeBoard = new Board(PuzzleService.getSolution());
		ValidationResult result = hintService.revealHint(completeBoard);
		assertFalse(result.isValid());
	}
}
