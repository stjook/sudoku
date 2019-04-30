package com.sp.sudoku;

public class Application {

	public static void main(String[] args) {
		try {
			final Sudoku sudoku = new Sudoku(args[0]);
			System.out.print(sudoku.validate());
		} catch (IndexOutOfBoundsException ex) {
			System.out.println("File is not provided.");
			System.out.print("0");
		}
	}

}
