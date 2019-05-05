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
			if (validators!=null && validators.length>0) {
				Arrays.stream(validators).forEach(v -> v.validate(this));
			} else
				throw new SudokuValidationException("It is not given any validator to be processed.");
		} catch (SudokuValidationException ex) {
			System.out.println(ex.getMessage());
			return 1;
		}
		return 0;
	}
}
