package com.sp.sudoku;

import com.sp.sudoku.validators.SudokuDataValidator;
import com.sp.sudoku.validators.SudokuFileValidator;

public class Application {

	public static void main(String[] args) {
		try {
			final Sudoku sudoku = new Sudoku(args[0], new SudokuFileValidator(), new SudokuDataValidator());
			System.out.print(sudoku.validate());
		} catch (IndexOutOfBoundsException ex) {
			System.out.println("File is not provided.");
			System.out.print("1");
		}
	}

}
