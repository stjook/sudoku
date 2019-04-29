package com.sp.sudoku;


import com.sp.sudoku.exception.SudokuValidationException;
import com.sp.sudoku.validators.SudokuFileValidator;
import com.sp.sudoku.validators.SudokuDataValidator;
import com.sp.sudoku.validators.Validator;

import java.util.Arrays;
import java.util.List;

public class Sudoku {

	private final String filePath;

	public static final List<String> ALLOWED_FILE_EXTENTION = Arrays.asList(".txt", ".csv");

	private static final List<Validator> VALIDATORS = Arrays.asList(
			new SudokuFileValidator(),
			new SudokuDataValidator()
	);

	public Sudoku(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public int validate() {
		try {
			VALIDATORS.stream().forEach(v -> v.validate(this));
		} catch (SudokuValidationException ex) {
			System.out.println(ex.getMessage());
			return 0;
		}
		return 1;
	}
}
