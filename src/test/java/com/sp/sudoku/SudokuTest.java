package com.sp.sudoku;

import com.sp.sudoku.exception.SudokuValidationException;
import com.sp.sudoku.validators.SudokuDataValidator;
import com.sp.sudoku.validators.SudokuFileValidator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.mockito.InOrder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SudokuTest {

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	private ClassLoader classLoader = getClass().getClassLoader();

	private SudokuFileValidator fileValidator;
	private SudokuDataValidator dataValidator;

	@Before
	public void setUp() {
		fileValidator = mock(SudokuFileValidator.class);
		dataValidator = mock(SudokuDataValidator.class);
	}

	@Test
	public void givenSudoku_whenValidate_thenValidatorsGetCalledOnceAndInSequence() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("ok/sudoku.csv").getPath(), fileValidator, dataValidator);
		doNothing().when(fileValidator).validate(sudoku);
		doNothing().when(dataValidator).validate(sudoku);

		// When
		sudoku.validate();
		InOrder inOrder = inOrder(fileValidator, dataValidator);

		// Then
		inOrder.verify(fileValidator, times(1)).validate(sudoku);
		inOrder.verify(dataValidator, times(1)).validate(sudoku);
	}

	@Test
	public void givenSudoku_whenValidateAndValidatorThrowsException_thenFirstValidatorCalledAndReturn0() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("ok/sudoku.csv").getPath(), fileValidator, dataValidator);
		doThrow(new SudokuValidationException("Exception thrown")).when(fileValidator).validate(sudoku);
		doNothing().when(dataValidator).validate(sudoku);

		// When
		int validationResult = sudoku.validate();
		InOrder inOrder = inOrder(fileValidator, dataValidator);

		// Then
		inOrder.verify(fileValidator, times(1)).validate(sudoku);
		inOrder.verify(dataValidator, times(0)).validate(sudoku);
		assertEquals(1, validationResult);
	}

	@Test
	public void givenNullValidatorsAndSudoku_whenValidate_thenPrintExceptionMsgAndReturn1() {
		// Given
		Sudoku sudoku = new Sudoku(classLoader.getResource("ok/sudoku.csv").getPath(), null);

		// When
		int validationResult = sudoku.validate();

		// Then
		assertEquals("It is not given any validator to be processed." + System.lineSeparator(), systemOutRule.getLog());
		assertEquals(1, validationResult);
	}
}
