package com.sp.sudoku;

public class Application {

	public static void main(String[] args) {
		final Sudoku sudoku = new Sudoku(args[0]);
		System.out.println(sudoku.validate());
	}

}
