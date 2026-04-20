package com.sudoku.test;

import com.sudoku.service.RowMappingService;
import com.sudoku.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TDD Test Suite for SudokuValidator.
 *
 * Tests cover:
 * 1. Board length validation    - must be 9x9
 * 2. Column length validation   - no row exceeds 9
 * 3. Duplicate detection        - row, column, box
 * 4. Null and exception safety  - null board, null row
 * 5. Valid characters only      - '1'-'9' or '_'
 * 6. Edge cases                 - empty board, boundaries, underscore handling
 */
class SudokuValidatorTest {

    private ValidationService validator;
    private RowMappingService rowMappingService;

    // ─────────────────────────────────────────────────────────
    // Reusable boards
    // ─────────────────────────────────────────────────────────

    private final char[][] VALID_COMPLETE_BOARD = {
        {'5','3','4','6','7','8','9','1','2'},
        {'6','7','2','1','9','5','3','4','8'},
        {'1','9','8','3','4','2','5','6','7'},
        {'8','5','9','7','6','1','4','2','3'},
        {'4','2','6','8','5','3','7','9','1'},
        {'7','1','3','9','2','4','8','5','6'},
        {'9','6','1','5','3','7','2','8','4'},
        {'2','8','7','4','1','9','6','3','5'},
        {'3','4','5','2','8','6','1','7','9'}
    };

    private final char[][] VALID_INCOMPLETE_BOARD = {
        {'5','3','_','_','7','_','_','_','_'},
        {'6','_','_','1','9','5','_','_','_'},
        {'_','9','8','_','_','_','_','6','_'},
        {'8','_','_','_','6','_','_','_','3'},
        {'4','_','_','8','_','3','_','_','1'},
        {'7','_','_','_','2','_','_','_','6'},
        {'_','6','_','_','_','_','2','8','_'},
        {'_','_','_','4','1','9','_','_','5'},
        {'_','_','_','_','8','_','_','7','9'}
    };

    private final char[][] EMPTY_BOARD = {
        {'_','_','_','_','_','_','_','_','_'},
        {'_','_','_','_','_','_','_','_','_'},
        {'_','_','_','_','_','_','_','_','_'},
        {'_','_','_','_','_','_','_','_','_'},
        {'_','_','_','_','_','_','_','_','_'},
        {'_','_','_','_','_','_','_','_','_'},
        {'_','_','_','_','_','_','_','_','_'},
        {'_','_','_','_','_','_','_','_','_'},
        {'_','_','_','_','_','_','_','_','_'}
    };

    @BeforeEach
	void setUp() {
		rowMappingService = new RowMappingService();
		validator = new ValidationService(rowMappingService);
	}

    // ═════════════════════════════════════════════════════════
    // 1. BOARD LENGTH — must be exactly 9 rows
    // ═════════════════════════════════════════════════════════

    @Test
    void shouldThrowWhenBoardHasLessThanNineRows() {
        char[][] board = new char[8][9];
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }

    @Test
    void shouldThrowWhenBoardHasMoreThanNineRows() {
        char[][] board = new char[10][9];
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }

    @Test
    void shouldThrowWhenBoardHasOnlyOneRow() {
        char[][] board = new char[1][9];
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }


    // ═════════════════════════════════════════════════════════
    // 2. COLUMN LENGTH — each row must be exactly 9
    // ═════════════════════════════════════════════════════════

    @Test
    void shouldThrowWhenFirstRowHasLessThanNineColumns() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1'},       // 8 columns
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }

    @Test
    void shouldThrowWhenMiddleRowHasMoreThanNineColumns() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1','5'}, // 10 columns — row E
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }

    @Test
    void shouldThrowWhenLastRowHasLessThanNineColumns() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7'}          // 8 columns — row I
        };
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }


    // ═════════════════════════════════════════════════════════
    // 3. DUPLICATE DETECTION — row, column, box
    // ═════════════════════════════════════════════════════════

    // --- Row duplicates ---

    @Test
    void shouldReturnFalseWhenFirstRowHasDuplicate() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','5'}, // duplicate 5 in row A
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertFalse(validator.isValidSudoku(board));
    }

    @Test
    void shouldReturnFalseWhenMiddleRowHasDuplicate() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','4','1'}, // duplicate 4 in row E
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertFalse(validator.isValidSudoku(board));
    }

    @Test
    void shouldReturnFalseWhenLastRowHasDuplicate() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','3','9'}  // duplicate 3 in row I
        };
        assertFalse(validator.isValidSudoku(board));
    }

    // --- Column duplicates ---

    @Test
    void shouldReturnFalseWhenFirstColumnHasDuplicate() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'5','4','3','2','8','6','1','7','9'}  // duplicate 5 in col 1
        };
        assertFalse(validator.isValidSudoku(board));
    }

    @Test
    void shouldReturnFalseWhenLastColumnHasDuplicate() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','2'}  // duplicate 2 in col 9
        };
        assertFalse(validator.isValidSudoku(board));
    }

    // --- Box duplicates ---

    @Test
    void shouldReturnFalseWhenTopLeftBoxHasDuplicate() {
        char[][] board = {
            {'5','3','5','6','7','8','9','1','2'}, // duplicate 5 in top-left box
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertFalse(validator.isValidSudoku(board));
    }

    @Test
    void shouldReturnFalseWhenBottomRightBoxHasDuplicate() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','5','9'}  // duplicate 5 in bottom-right box
        };
        assertFalse(validator.isValidSudoku(board));
    }


    // ═════════════════════════════════════════════════════════
    // 4. NULL AND EXCEPTION SAFETY
    // ═════════════════════════════════════════════════════════

    @Test
    void shouldThrowWhenBoardIsNull() {
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(null));
    }

    @Test
    void shouldThrowWhenRowInsideBoardIsNull() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','2'},
            null,                                   // row B is null
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }


    // ═════════════════════════════════════════════════════════
    // 5. VALID CHARACTERS — only '1'-'9' or '_'
    // ═════════════════════════════════════════════════════════

    @Test
    void shouldThrowWhenCellContainsZero() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','0'}, // '0' invalid
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }

    @Test
    void shouldThrowWhenCellContainsLetter() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','X'}, // 'X' invalid
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }

    @Test
    void shouldThrowWhenCellContainsSpecialCharacter() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','@'}, // '@' invalid
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }

    @Test
    void shouldThrowWhenCellContainsSpace() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1',' '}, // ' ' invalid
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertThrows(IllegalArgumentException.class,
            () -> validator.isValidSudoku(board));
    }


    // ═════════════════════════════════════════════════════════
    // 6. EDGE CASES
    // ═════════════════════════════════════════════════════════

    @Test
    void shouldReturnTrueForValidCompleteBoard() {
        assertTrue(validator.isValidSudoku(VALID_COMPLETE_BOARD));
    }

    @Test
    void shouldReturnTrueForValidIncompleteBoard() {
        assertTrue(validator.isValidSudoku(VALID_INCOMPLETE_BOARD));
    }

    @Test
    void shouldReturnTrueWhenBoardIsCompletelyEmpty() {
        // all underscores — no conflicts possible
        assertTrue(validator.isValidSudoku(EMPTY_BOARD));
    }

    @Test
    void shouldNotTreatUnderscoreAsDuplicateInRow() {
        char[][] board = {
            {'_','3','4','6','7','8','9','1','_'}, // two '_' in row A — valid
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertTrue(validator.isValidSudoku(board));
    }

    @Test
    void shouldNotTreatUnderscoreAsDuplicateInColumn() {
        char[][] board = {
            {'_','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'_','2','6','8','5','3','7','9','1'}, // two '_' in col 1 — valid
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','9'}
        };
        assertTrue(validator.isValidSudoku(board));
    }

    @Test
    void shouldReturnFalseWhenSingleCellBreaksValidBoard() {
        char[][] board = {
            {'5','3','4','6','7','8','9','1','2'},
            {'6','7','2','1','9','5','3','4','8'},
            {'1','9','8','3','4','2','5','6','7'},
            {'8','5','9','7','6','1','4','2','3'},
            {'4','2','6','8','5','3','7','9','1'},
            {'7','1','3','9','2','4','8','5','6'},
            {'9','6','1','5','3','7','2','8','4'},
            {'2','8','7','4','1','9','6','3','5'},
            {'3','4','5','2','8','6','1','7','5'}  // only last cell wrong
        };
        assertFalse(validator.isValidSudoku(board));
    }
}