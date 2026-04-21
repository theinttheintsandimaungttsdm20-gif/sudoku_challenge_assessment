package com.sudoku.constant;

public class SudokuConstant {
	// Sudoku challenge rules
	public static final int BOARD_SIZE = 9;
	public static final int BOX_SIZE = 3; // 3x3
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 9;
	public static final int PREFILLED_COUNT = 30;
	public static final int EMPTY_CELL = '_';

	// Row labels (9 rows from A to I)
	public static final char ROW_START = 'A'; // A=0
	public static final char ROW_END = 'I'; // I=8

	// Game commands
	public static final String CMD_HINT = "HINT";
	public static final String CMD_CHECK = "CHECK";
	public static final String CMD_QUIT = "QUIT";
	public static final String CMD_CLEAR = "CLEAR";

	// Static message to show users (Use Constant message to make easy to modify)
	public static final String MSG_WELCOME = "Welcome to Sudoku!";
	public static final String MSG_VALID_BOARD = "No rule violations detected.";
	public static final String MSG_GAME_COMPLETE = "Congratulations! Puzzle complete!";
	public static final String MSG_INVALID_NUMBER = "Number must be between 1 and 9";
	public static final String MSG_INVALID_CMD = "Invalid command. Try 'B3 7' or 'hint' or 'C5 clear'";
	public static final String MSG_GOODBYE = "Goodbye!";
	public static final String MSG_ASK_PLAY_AGAIN = "Press ENTER to play again or type 'quit' to exit...";
	public static final String MSG_HINT_NONE = "No hints available";

	// Row, Col, Box key ID
	public static final String ROW_KEY_ID = "ROW%d:%c"; // "row" + row + ":" + cell;
	public static final String COL_KEY_ID = "COL%d:%c";
	public static final String BOX_KEY_ID = "BOX%d%d:%c";

	// Message to users when Exception or Illegal argument found in initial
	// validation
	public static final String MSG_NULL_BOARD = "Board cannot be null";
	public static final String MSG_NULL_ROW = "Row %s cannot be null";
	public static final String MSG_INVALID_COL_LENGTH = "Row %s must have exactly 9 columns";
	public static final String MSG_INVALID_ROW_LENGTH = "Board must have exactly 9 rows, found: %d";

}
