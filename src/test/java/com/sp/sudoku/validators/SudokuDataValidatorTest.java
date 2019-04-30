package com.sp.sudoku.validators;

import com.sp.sudoku.Sudoku;
import com.sp.sudoku.exception.SudokuValidationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.startsWith;

public class SudokuDataValidatorTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private ClassLoader classLoader = getClass().getClassLoader();

	private Validator dataValidator;

	@Before
	public void setUp() {
		dataValidator = new SudokuDataValidator();
	}

	@Test
	public void givenValidSudoku_whenValidate_thenIsValid() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("ok/sudoku.csv").getPath());

		// When
		dataValidator.validate(sudoku);
	}

	@Test
	public void givenValidEmptySudoku_whenValidate_thenIsValid() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("ok/emptySudoku.txt").getPath());

		// When
		dataValidator.validate(sudoku);
	}

	@Test
	public void givenDuplicateInRowSudoku_whenValidate_thenThrowException() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("error/dublicateInRowSudoku.csv").getPath());
		thrown.expect(SudokuValidationException.class);
		thrown.expectMessage("Invalid item or duplicate found.");

		// When
		dataValidator.validate(sudoku);
	}

	@Test
	public void givenDuplicateInColumnSudoku_whenValidate_thenThrowException() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("error/dublicateInColumnSudoku.csv").getPath());
		thrown.expect(SudokuValidationException.class);
		thrown.expectMessage("Invalid item or duplicate found.");

		// When
		dataValidator.validate(sudoku);
	}

	@Test
	public void givenDuplicateInSubBoxSudoku_whenValidate_thenThrowException() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("error/dublicateInSubBoxSudoku.csv").getPath());
		thrown.expect(SudokuValidationException.class);
		thrown.expectMessage("Invalid item or duplicate found.");

		// When
		dataValidator.validate(sudoku);
	}

	@Test
	public void givenInvalidItemInSudoku_whenValidate_thenThrowException() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("error/invalidItemInSudoku.csv").getPath());
		thrown.expect(SudokuValidationException.class);
		thrown.expectMessage("Invalid item or duplicate found.");

		// When
		dataValidator.validate(sudoku);
	}

	@Test
	public void givenNoExistFileSudoku_whenValidate_thenThrowException() {
		// Given
		Sudoku sudoku = new Sudoku("no-exist-sudoku.csv");
		thrown.expect(SudokuValidationException.class);
		thrown.expectMessage(startsWith("no-exist-sudoku.csv"));

		// When
		dataValidator.validate(sudoku);
	}

	@Test
	public void givenInvalidPuzzleSize1_whenValidate_thenThrowException() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("error/invalidPuzzleSize1.csv").getPath());
		thrown.expect(SudokuValidationException.class);
		thrown.expectMessage("Invalid puzzle size.");

		// When
		dataValidator.validate(sudoku);
	}

	@Test
	public void givenInvalidPuzzleSize2_whenValidate_thenThrowException() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("error/invalidPuzzleSize2.csv").getPath());
		thrown.expect(SudokuValidationException.class);
		thrown.expectMessage("Invalid puzzle size.");

		// When
		dataValidator.validate(sudoku);
	}
}
