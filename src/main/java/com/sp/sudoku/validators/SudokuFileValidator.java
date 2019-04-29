package com.sp.sudoku.validators;


import com.sp.sudoku.Sudoku;
import com.sp.sudoku.exception.SudokuValidationException;

import java.io.File;

public class SudokuFileValidator implements Validator {

	public void validate(Sudoku sudoku) {
		File file = new File(sudoku.getFilePath());
		if (file!=null && file.exists()) {
			String name = file.getName();
			String extension = name.substring(name.lastIndexOf("."));
			if (sudoku.ALLOWED_FILE_EXTENTION.contains(extension))
				return;
			else
				throw new SudokuValidationException(String.format("Invalid extention %s.", extension));
		}
		throw new SudokuValidationException("File does not exist.");
	}

}
