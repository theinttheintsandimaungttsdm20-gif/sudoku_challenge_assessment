package com.sudoku.constant;

/**
 * @author TheintSandi
 * @implNote Enum to provide result messages in the game to user.
 * Uses String.format patterns for dynamic messages.
 */
public enum ResultMessage {

    // Success
    SUCCESS             ("No rule violations detected."),
    GAME_COMPLETE       ("Congratulations! Puzzle complete!"),
    CELL_CLEARED        ("Cell cleared successfully."),
    VALID_CHAR          ("Number %c is placed at %s%d"),
    HINT_GIVEN          ("Hint: Cell %s%d = %c"), // Hint: Cell E5 = 5
    HINT_NONE           ("No hints available"),

    // Commands
    INVALID_COMMAND     ("Invalid command. Try 'B3 7' or 'hint' or 'C5 clear'"),
    GOODBYE             ("Goodbye!"),

    // Board structure invalid
    NULL_BOARD          ("Board cannot be null"),
    NULL_ROW            ("Row %s cannot be null"),
    INVALID_ROW_LENGTH  ("Board must have exactly 9 rows, found: %d"),
    INVALID_COL_LENGTH  ("Row %s must have exactly 9 columns"),

    // Cell errors
    PREFILLED_CELL_PLACING      ("Invalid move. %s%d is pre-filled."),
    PREFILLED_CELL_CLEAR      ("Cannot clear the cell. %s%d is pre-filled."),
    INVALID_CHAR        ("Invalid character %c at row %s column %d"),

    // Rule violations message
    INVALID_ROW         ("Number %c is already existed in Row %s"),
    INVALID_COL         ("Number %c is already existed in Column %d"),
    INVALID_BOX         ("Number %c is already existed in the same 3x3 grid!");

    private final String message;

    ResultMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the raw message template.
     *
     * @return message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns formatted message with given arguments.
     *
     * @param args format arguments
     * @return formatted message string
     */
    public String format(Object... args) {
        return String.format(message, args);
    }
}
