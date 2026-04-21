package com.sudoku.model;

import com.sudoku.constant.SudokuConstant;

/**
 * @author TheintSandi 
 * @implNote Construct Model to represent a single cell in the Sudoku grid. A
 *         cell holds a character value and tracks whether it is pre-filled.
 */
public class Cell {

	private char value;
	private final boolean prefilled;

	/**
	 * Constructs a Cell with a given value and prefilled status.
	 *
	 * @param value     the character value ('1'-'9' or '_' for empty)
	 * @param prefilled true if this cell was pre-filled at game start
	 */
	public Cell(char value, boolean prefilled) {
		this.value = value;
		this.prefilled = prefilled;
	}

	/**
	 * Returns the current value of this cell.
	 *
	 * @return the char value of this cell
	 */
	public char getValue() {
		return value;
	}

	/**
	 * Check whether this cell is pre-filled.
	 *
	 * @return true if cell is pre-filled and cannot be modified
	 */
	public boolean isPrefilled() {
		return prefilled;
	}

	/**
	 * Check whether this cell is empty.
	 *
	 * @return true if cell value is '_'
	 */
	public boolean isEmpty() {
		return value == SudokuConstant.EMPTY_CELL;
	}

	/**
	 * Sets the value of this cell.
	 *
	 * @param value the char value to set
	 * @return true if value was set successfully,
	 *         false if cell is pre-filled
	 */
	public boolean setValue(char value) {
		if (prefilled)
			return false;
		this.value = value;
		return true;
	}

	/**
	 * Clears the value of this cell.
	 *
	 * @return true if cell was cleared successfully,
	 *         false if cell is pre-filled
	 */
	public boolean clear() {
	    if (prefilled) return false;
	    this.value = SudokuConstant.EMPTY_CELL;
	    return true;
	}

	/**
	 * Returns string representation of this cell.
	 *
	 * @return the value as a String
	 */
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}