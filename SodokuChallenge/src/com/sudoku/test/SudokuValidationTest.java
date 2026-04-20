package com.sudoku.test;

import com.sudoku.service.RowMappingService;
import com.sudoku.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
	private RowMappingService rowMappingService;
	
	@BeforeEach
	void setUp() {
		rowMappingService = new RowMappingService();
		sudokuValidationService = new ValidationService(rowMappingService);
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
		assertFalse(sudokuValidationService.isValidSudoku(SudokuTestBoards.DUPLICATE_ROW));
	}

	@Test
	void shouldReturnFalseWhenColumnHasDuplicate() {
		assertFalse(sudokuValidationService.isValidSudoku(SudokuTestBoards.DUPLICATE_COL));
	}

	@Test
	void shouldReturnFalseWhenBoxHasDuplicate() {
		assertFalse(sudokuValidationService.isValidSudoku(SudokuTestBoards.DUPLICATE_BOX));
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
	void shouldThrowWhenCellContainsZero() {
		assertThrows(IllegalArgumentException.class,
				() -> sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_CELL_ZERO));
	}

	@Test
	void shouldThrowWhenCellContainsSpace() {
		assertThrows(IllegalArgumentException.class,
				() -> sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_CELL_SPACE));
	}

	@Test
	void shouldThrowWhenCellContainsSpecialChar() {
		assertThrows(IllegalArgumentException.class,
				() -> sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_CELL_SPECIAL_CHAR));
	}

	@Test
	void shouldThrowWhenCellContainsInvalidChar() {
		assertThrows(IllegalArgumentException.class,
				() -> sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_CELL_CHAR));
	}

	@Test
	void shouldThrowWhenBoardHasEmptyWithInvalid() {
		assertThrows(IllegalArgumentException.class,
				() -> sudokuValidationService.isValidSudoku(SudokuTestBoards.INVALID_EMPTY_CHAR));
	}

	// -- Edge cases: check True, when board is with '_' incomplete or complete
	// happy valid
	// board or all empty cell
	@Test
	void shouldReturnTrueForValidAndCompleteBoard() {
		assertTrue(sudokuValidationService.isValidSudoku(SudokuTestBoards.COMPLETE));
	}

	@Test
	void shouldReturnTrueForValidIncompleteBoard() {
		assertTrue(sudokuValidationService.isValidSudoku(SudokuTestBoards.INITIAL));
	}

	@Test
	void shouldReturnTrueWhenBoardIsAllEmpty() {
		assertTrue(sudokuValidationService.isValidSudoku(SudokuTestBoards.EMPTY));
	}

}
