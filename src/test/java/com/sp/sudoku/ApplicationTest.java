package com.sp.sudoku;


import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	private ClassLoader classLoader = getClass().getClassLoader();

	@Test
	public void givenValidApplicationArg_whenMain_thenPrint1() {
		// Given
		String[] args = new String[]{ classLoader.getResource("ok/sudoku.csv").getPath() };

		// When
		Application.main(args);

		// Then
		assertEquals("0", systemOutRule.getLog());
	}

	@Test
	public void givenNoApplicationArgument_whenMain_thenPrint0AndMessage() {
		// Given
		String[] args = new String[]{};

		// When
		Application.main(args);

		// Then
		assertEquals("File is not provided." + System.lineSeparator() + "1", systemOutRule.getLog());
	}
}
