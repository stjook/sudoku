package com.sp.sudoku;


import com.sp.sudoku.exception.SudokuValidationException;
import com.sp.sudoku.validators.Validator;

import java.util.Arrays;

public final class Sudoku {

	private final String filePath;

	private final Validator[] validators;

	public Sudoku(String filePath, Validator... validators) {
		this.filePath = filePath;
		this.validators = validators;
	}

	public String getFilePath() {
		return filePath;
	}

	public int validate() {
		try {
			Arrays.stream(validators).forEach(v -> v.validate(this));
		} catch (SudokuValidationException ex) {
			System.out.println(ex.getMessage());
			return 0;
		}
		return 1;
	}
}
