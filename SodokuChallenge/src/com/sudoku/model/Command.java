package com.sudoku.model;

/**
 * Represents a parsed user command entered on the console.
 *
 * <p>
 * Supported command types:
 * <ul>
 * <li>PLACE - place a number in a cell (e.g. B3 7)</li>
 * <li>CLEAR - clear a cell (e.g. C5 clear)</li>
 * <li>HINT - reveal one correct number</li>
 * <li>CHECK - validate the current board</li>
 * <li>QUIT - exit the game</li>
 * <li>INVALID - unrecognized input</li>
 * </ul>
 */
public class Command {

	/**
	 * Enum representing all supported command types.
	 */
	public enum Type {
		PLACE, CLEAR, HINT, CHECK, QUIT, INVALID
	}

	private final Type type;
	private final int row; // 0-8, -1 if not applicable
	private final int col; // 0-8, -1 if not applicable
	private final int number; // 1-9, -1 if not applicable

	/**
	 * Private constructor — use factory methods to create commands.
	 *
	 * @param type   the command type
	 * @param row    row index (0-8) or -1
	 * @param col    column index (0-8) or -1
	 * @param number number (1-9) or -1
	 */
	private Command(Type type, int row, int col, int number) {
		this.type = type;
		this.row = row;
		this.col = col;
		this.number = number;
	}

	// ── Factory methods ───────────────────────────────────────

	/**
	 * Creates a PLACE command.
	 *
	 * @param row    row index (0-8)
	 * @param col    column index (0-8)
	 * @param number number to place (1-9)
	 * @return a PLACE Command
	 */
	public static Command place(int row, int col, int number) {
		return new Command(Type.PLACE, row, col, number);
	}

	/**
	 * Creates a CLEAR command.
	 *
	 * @param row row index (0-8)
	 * @param col column index (0-8)
	 * @return a CLEAR Command
	 */
	public static Command clear(int row, int col) {
		return new Command(Type.CLEAR, row, col, -1);
	}

	/**
	 * Creates a HINT command.
	 *
	 * @return a HINT Command
	 */
	public static Command hint() {
		return new Command(Type.HINT, -1, -1, -1);
	}

	/**
	 * Creates a CHECK command.
	 *
	 * @return a CHECK Command
	 */
	public static Command check() {
		return new Command(Type.CHECK, -1, -1, -1);
	}

	/**
	 * Creates a QUIT command.
	 *
	 * @return a QUIT Command
	 */
	public static Command quit() {
		return new Command(Type.QUIT, -1, -1, -1);
	}

	/**
	 * Creates an INVALID command for unrecognised input. Row greater than I or Col
	 * greater than 9
	 *
	 * @return an INVALID Command
	 */
	public static Command invalid() {
		return new Command(Type.INVALID, -1, -1, -1);
	}

	// ── Getters ───────────────────────────────────────────────

	/**
	 * Returns the type of this command.
	 *
	 * @return the command Type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Returns the row index.
	 *
	 * @return row index (0-8) or -1 if not applicable
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column index.
	 *
	 * @return column index (0-8) or -1 if not applicable
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Returns the number to place.
	 *
	 * @return number (1-9) or -1 if not applicable
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Returns the number as a char for direct use in Board. Example: 7 becomes '7'
	 *
	 * @return number as char
	 */
	public char getNumberAsChar() {
		return (char) ('0' + number);
	}

	/**
	 * Returns string representation of this command.
	 *
	 * @return command details as String
	 */
	@Override
	public String toString() {
		return "Command{type=" + type + ", row=" + row + ", col=" + col + ", number=" + number + "}";
	}
}