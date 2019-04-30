package com.sp.sudoku;


import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Test
	public void givenNoApplicationArgument_whenMain_thenPrint0AndMessage() throws Exception {
		// Given
		String[] args = new String[]{};

		// When
		Application.main(args);

		// Then
		assertEquals("File is not provided.\n0", systemOutRule.getLog());
	}
}
