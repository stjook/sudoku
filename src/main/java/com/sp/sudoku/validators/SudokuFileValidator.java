package com.sp.sudoku.validators;

import com.sp.sudoku.Sudoku;
import com.sp.sudoku.exception.SudokuValidationException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SudokuFileValidator implements Validator {

	private static final List<String> ALLOWED_FILE_EXTENTION = Arrays.asList(".txt", ".csv");

	public void validate(Sudoku sudoku) {
		File file = new File(sudoku.getFilePath());
		if (file!=null && file.exists()) {
			String name = file.getName();
			String extension = name.substring(name.lastIndexOf("."));
			if (ALLOWED_FILE_EXTENTION.contains(extension))
				return;
			else
				throw new SudokuValidationException(String.format("Invalid extention [%s].", extension));
		}
		throw new SudokuValidationException("File does not exist.");
	}

}
