package com.sudoku.service;

import java.util.HashMap;
import java.util.Map;

public class RowMappingService {
	private static final String firstRow = "A";
	private static final String secondRow = "B";
	private static final String thirdRow = "C";
	private static final String fourthRow = "D";
	private static final String fifthRow = "E";
	private static final String sixthRow = "F";
	private static final String seventhRow = "G";
	private static final String eighthRow = "H";
	private static final String ninthRow = "I";
	

	public String getRowLabel(int row){
		Map<Integer, String> rowChar = new HashMap<Integer, String>();
		String rowLabel = "";
		rowChar.put(0, firstRow);
		rowChar.put(1, secondRow);
		rowChar.put(2, thirdRow);
		rowChar.put(3, fourthRow);
		rowChar.put(4, fifthRow);
		rowChar.put(5, sixthRow);
		rowChar.put(6, seventhRow);
		rowChar.put(7, eighthRow);
		rowChar.put(8, ninthRow);
		if(rowChar.containsKey(row)) {
			rowLabel = rowChar.get(row);
			return rowLabel;
		}
		return rowLabel;
	}
}
