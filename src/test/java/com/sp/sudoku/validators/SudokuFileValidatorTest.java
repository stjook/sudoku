package com.sp.sudoku.validators;

import com.sp.sudoku.Sudoku;
import com.sp.sudoku.exception.SudokuValidationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SudokuFileValidatorTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private ClassLoader classLoader = getClass().getClassLoader();

	private Validator sudokuFileValidator;

	@Before
	public void setUp() {
		sudokuFileValidator = new SudokuFileValidator();
	}

	@Test
	public void givenValidSudoku_whenValidate_thenIsValid() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("ok/sudoku.csv").getPath());

		// When
		sudokuFileValidator.validate(sudoku);
	}

	@Test
	public void givenNotAllowedExtention_whenValidate_thenThrowException() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("error/sudoku.ext").getPath());
		thrown.expect(SudokuValidationException.class);
		thrown.expectMessage("Invalid extention .ext.");

		// When
		sudokuFileValidator.validate(sudoku);
	}

	@Test
	public void givenNoExistFile_whenValidate_thenThrowException() {
		// Given
		Sudoku sudoku = new Sudoku("no-exist-sudoku.csv");
		thrown.expect(SudokuValidationException.class);
		thrown.expectMessage("File does not exist.");

		// When
		sudokuFileValidator.validate(sudoku);
	}
}
