package com.sp.sudoku.validators;


import com.sp.sudoku.Sudoku;

import java.io.File;

public class SudokuFileValidator implements Validator {

	public void validate(Sudoku sudoku) throws RuntimeException {
		File file = new File(sudoku.getFilePath());
		if (file!=null && file.exists()) {
			String name = file.getName();
			String extension = name.substring(name.lastIndexOf("."));
			if (sudoku.ALLOWED_FILE_EXTENTION.contains(extension))
				return;
			else
				throw new RuntimeException("Invalid extention " + extension);
		}
		throw new RuntimeException("File does not exist.");
	}

}
