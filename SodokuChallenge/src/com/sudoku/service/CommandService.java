package com.sudoku.service;

import com.sudoku.constant.SudokuConstant;
import com.sudoku.model.Command;

/**
 * Parses raw user input strings into Command objects.
 *
 * <p>
 * Supported formats:
 * 
 * <pre>
 *   "B3 7"     → PLACE row=B col=3 number=7
 *   "C5 clear" → CLEAR row=C col=5
 *   "hint"     → HINT
 *   "check"    → CHECK
 *   "quit"     → QUIT
 * </pre>
 */
public class CommandService {

	/**
	 * Parses raw user input into a Command.
	 *
	 * @param input raw user input string
	 * @return parsed Command object
	 */
	public static Command parse(String input) {
		if (input == null || input.trim().isEmpty())
			return Command.invalid();

		String trimmed = input.trim().toUpperCase();

		switch (trimmed) {
		case SudokuConstant.CMD_HINT:
			return Command.hint();
		case SudokuConstant.CMD_CHECK:
			return Command.check();
		case SudokuConstant.CMD_QUIT:
			return Command.quit();
		default:
			return parseCellCommand(trimmed);
		}
	}

	// ── Private helpers ───────────────────────────────────────

	/**
	 * Parses a cell command like "B3 7" or "C5 CLEAR".
	 *
	 * @param input trimmed uppercase input
	 * @return parsed Command or invalid if format is wrong
	 */
	private static Command parseCellCommand(String input) {
		String[] parts = input.split("\\s+");
		if (parts.length != 2)
			return Command.invalid();

		String cell = parts[0].toUpperCase(); // e.g. "B3"
		String action = parts[1]; // e.g. "7" or "CLEAR"

		if (cell.length() != 2)
			return Command.invalid();

		int row = cell.charAt(0) - 'A'; // Row 0 is A
		int col = cell.charAt(1) - '1'; // Col 0 is 1

		if (row == -1 || col == -1)
			return Command.invalid();
		
		// row should be 9 -> A to I
		if (row < 0 || row > 8)
			return Command.invalid();
		
		// col should be 9 -> 1 to 9
		if (col < 0 || col > 8)
			return Command.invalid();

		if (action.equals(SudokuConstant.CMD_CLEAR))
			return Command.clear(row, col);

		return parsePlaceCommand(row, col, action);
	}

	/**
	 * Parses a place command — validates number is between 1-9.
	 *
	 * @param row    row index (0-8)
	 * @param col    column index (0-8)
	 * @param number number string to parse
	 * @return PLACE Command or invalid if number is out of range
	 */
	private static Command parsePlaceCommand(int row, int col, String number) {
		try {
			int num = Integer.parseInt(number);
			if (num < SudokuConstant.MIN_VALUE || num > SudokuConstant.MAX_VALUE)
				return Command.invalid();
			return Command.place(row, col, num);
		} catch (NumberFormatException e) {
			return Command.invalid();
		}
	}
}
