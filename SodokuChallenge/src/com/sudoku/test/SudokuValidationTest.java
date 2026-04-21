package com.sudoku.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sudoku.helper.RowMappingHelper;
import com.sudoku.model.ValidationResult;
import com.sudoku.service.ValidationService;

/**
 * @author TheintSandi
 * @implNote Sudoku validation test cases that covers: <br>
 *           - Board length validation. <br>
 *           - Column length validation <br>
 *           - Duplication Detection <br>
 *           - Null and exception safety <br>
 *           - Detect invalid characters <br>
 *           - Check edge cases
 */
public class SudokuValidationTest {
	private ValidationService sudokuValidationService;
	private RowMappingHelper rowMappingHelper;

	@BeforeEach
	void setUp() {
		rowMappingHelper = new RowMappingHelper();
		sudokuValidationService = new ValidationService(rowMappingHelper);
	}

	// -- check invalid row length
	@Test
	void shouldThrowWhenBoardHasLessThanNineRows() {
		char[][] board = new char[7][9];
		assertThrows(IllegalArgumentException.class, () -> sudokuValidationService.isValidSudoku(board));
	}

	@Test
	void shouldThrowWhenBoardHasMoreThanNineRows() {
		char[][] board = new char[12][9];
		assertThrows(IllegalArgumentException.class, () -> sudokuValidationService.isValidSudoku(board));
	}

	@Test
	void shouldThrowWhenBoardHasInvalidRowLength() {
		assertThrows(IllegalArgumentException.class,
				() -> sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_LENGTH));
	}

	// check invalid col length
	@Test
	void shouldThrowWhenBoardHasLessThanNineColumns() {
		assertThrows(IllegalArgumentException.class,
				() -> sudokuValidationService.isValidSudoku(SudokuTestBoards.SEVEN_COLUMN));
	}

	@Test
	void shouldThrowWhenBoardHasMoreThanNineColumns() {
		assertThrows(IllegalArgumentException.class,
				() -> sudokuValidationService.isValidSudoku(SudokuTestBoards.ELEVEN_COLUMN));
	}

	@Test
	void shouldThrowWhenBoardHasInvalidColumnLength() {
		assertThrows(IllegalArgumentException.class,
				() -> sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_COL_LENGTH));
	}

	// check sudoku rule i.e, row, col, box has to be no duplicate
	@Test
	void shouldReturnFalseWhenRowHasDuplicate() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.DUPLICATE_ROW);
		assertFalse(result.isValid());
	}

	@Test
	void shouldReturnFalseWhenColumnHasDuplicate() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.DUPLICATE_COL);
		assertFalse(result.isValid());
	}

	@Test
	void shouldReturnFalseWhenBoxHasDuplicate() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.DUPLICATE_BOX);
		assertFalse(result.isValid());
	}

	// check null board
	@Test
	void shouldThrowWhenBoardIsNull() {
		assertThrows(IllegalArgumentException.class, () -> sudokuValidationService.isValidSudoku(null));
	}

	@Test
	void shouldThrowWhenOneRowIsNull() {
		assertThrows(IllegalArgumentException.class,
				() -> sudokuValidationService.isValidSudoku(SudokuTestBoards.ROW_NULL));
	}

	// check invalid cell values
	@Test
	void shouldReturnFalseWhenCellContainsZero() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_CELL_ZERO);
		assertFalse(result.isValid());
	}

	@Test
	void shouldReturnFalseWhenCellContainsSpace() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_CELL_SPACE);
		assertFalse(result.isValid());
	}

	@Test
	void shouldReturnFalseWhenCellContainsSpecialChar() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_CELL_SPECIAL_CHAR);
		assertFalse(result.isValid());
	}

	@Test
	void shouldReturnFalseWhenCellContainsInvalidChar() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_CELL_CHAR);
		assertFalse(result.isValid());
	}

	@Test
	void shouldReturnFalseWhenBoardHasEmptyWithInvalid() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_EMPTY_CHAR);
		assertFalse(result.isValid());
	}

	// -- Edge cases: check True, when board is with '_' incomplete or complete
	// happy valid
	// board or all empty cell
	@Test
	void shouldReturnTrueForValidAndCompleteBoard() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.COMPLETE);
		assertTrue(result.isValid());
	}

	@Test
	void shouldReturnTrueForValidIncompleteBoard() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.INITIAL);
		assertTrue(result.isValid());
	}

	@Test
	void shouldReturnTrueWhenBoardIsAllEmpty() {
		ValidationResult result = sudokuValidationService.isValidSudoku(SudokuTestBoards.EMPTY);
		assertTrue(result.isValid());
	}

}
