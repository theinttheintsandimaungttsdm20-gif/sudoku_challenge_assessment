package com.sudoku.service;

import com.sudoku.constant.SudokuConstant;

/**
 * @author TheintSandi
 * @implNote Implementation for generating Sudoku puzzles.
 * Provides a hardcoded initial board with 30 pre-filled numbers
 * and its corresponding complete solution.
 */
public class PuzzleService {

    //Complete solution board
    private static final char[][] SOLUTION = {
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

    // Initial board with 30 pre-filled, rest empty '_'
    private static final char[][] INITIAL_BOARD = {
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

    /**
     * Returns a deep copy of the initial puzzle board.
     * Contains 30 pre-filled numbers and empty cells represented by '_'.
     *
     * @return 9x9 char array of the initial board
     */
    public static char[][] generatePuzzle() {
        return immutableCopy(INITIAL_BOARD);
    }

    /**
     * Returns a deep copy of the complete solution board.
     * Used by HintService to reveal correct values.
     *
     * @return 9x9 char array of the solution board
     */
    public static char[][] getSolution() {
        return immutableCopy(SOLUTION);
    }

    // ── Private helpers ───────────────────────────────────────

    /**
     * Creates a deep copy of a 9x9 char array.
     * Prevents external mutation of internal board state.
     *
     * @param original the original 9x9 char array
     * @return deep copy of the original
     */
    private static char[][] immutableCopy(char[][] original) {
        char[][] copy = new char[SudokuConstant.BOARD_SIZE][SudokuConstant.BOARD_SIZE];
        for (int row = 0; row < SudokuConstant.BOARD_SIZE; row++)
            copy[row] = original[row].clone();
        return copy;
    }
}
