package com.sudoku.model;

import com.sudoku.constant.ResultMessage;

/**
 * @author TheintSandi
 * @implNote Represent the outcome of a validation check service. Wraps a
 *           success/failure flag and a formatted result message.
 */
public class ValidationResult {

	private final boolean valid;
	private final ResultMessage resultMessage;
	private final String formattedMessage;

	/**
	 * Private constructor by using factory methods.
	 *
	 * @param valid            true if validation passed
	 * @param resultMessage    the ResultMessage enum value
	 * @param formattedMessage the formatted human-readable message
	 */
	private ValidationResult(boolean valid, ResultMessage resultMessage, String formattedMessage) {
		this.valid = valid;
		this.resultMessage = resultMessage;
		this.formattedMessage = formattedMessage;
	}

	// ── Factory methods ───────────────────────────────────────

	/**
	 * Creates a successful ValidationResult with a custom message.
	 *
	 * @param formattedMessage the formatted message string
	 * @return successful ValidationResult
	 */
	public static ValidationResult success(String formattedMessage) {
		return new ValidationResult(true, ResultMessage.SUCCESS, formattedMessage);
	}

	/**
	 * Creates a successful ValidationResult with no message to continue playing.
	 *
	 * @return successful ValidationResult
	 */
	public static ValidationResult success() {
		return new ValidationResult(true, null, "");
	}

	/**
	 * Creates a failed ValidationResult with a formatted message.
	 *
	 * @param resultMessage the ResultMessage enum value
	 * @param args          format arguments
	 * @return failed ValidationResult
	 */
	public static ValidationResult failure(ResultMessage resultMessage, Object... args) {
		return new ValidationResult(false, resultMessage, resultMessage.format(args));
	}

	// ── Getters ───────────────────────────────────────────────

	/**
	 * Returns whether validation passed.
	 *
	 * @return true if valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Returns the ResultMessage enum value.
	 *
	 * @return ResultMessage
	 */
	public ResultMessage getResult() {
		return resultMessage;
	}

	/**
	 * Returns the formatted human-readable message.
	 *
	 * @return formatted message string
	 */
	public String getMessage() {
		return formattedMessage;
	}
}