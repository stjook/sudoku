package com.sp.sudoku.validators;

import com.opencsv.CSVReader;
import com.sp.sudoku.Sudoku;
import com.sp.sudoku.exception.SudokuValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class SudokuDataValidator implements Validator {

	private static final Set<String> ALLOWED_ITEMS = new HashSet<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", ""));

	// Exception Messages
	private static final String DUPLICATE_ITEM = "Duplicate item found.";
	private static final String INVALID_ITEM = "Invalid item found.";
	private static final String INVALID_PUZZLE_SIZE = "Invalid puzzle size.";

	private static final int PUZZLE_SIZE = 9;
	private static final int SUBBOX_SIZE = 3;

	public void validate(Sudoku sudoku) {
		String[][] records = getRecords(sudoku.getFilePath());
		validateRecords(records);
	}

	private void validateRecords(String[][] records) {
		validateItems(records);
		validateRows(records);
		validateColumns(records);
		validateSubBoxes(records);
	}

	private void validateItems(String[][] records) {
		for (int i = 0; i < PUZZLE_SIZE; i++) {
			String[] row = records[i];
			if(Arrays.stream(row).map(item -> item.trim())
					.anyMatch(item -> !ALLOWED_ITEMS.contains(item)))
				throw new SudokuValidationException(INVALID_ITEM);
		}
	}

	private void validateRows(String[][] records) {
		for (int i = 0; i < PUZZLE_SIZE; i++) {
			String[] row = records[i];
			checkForDuplicate(Arrays.asList(row));
		}
	}

	private void validateColumns(String[][] records) {
		for (int j = 0; j < PUZZLE_SIZE; j++) {
			final int index = j;
			String[] column = IntStream.range(0, PUZZLE_SIZE)
									  .mapToObj(i -> records[i][index])
									  .toArray(String[]::new);
			checkForDuplicate(Arrays.asList(column));
		}
	}

	private void validateSubBoxes(String[][] records) {
		for (int i = 0; i < PUZZLE_SIZE; i++) {
			for (int j = 0; j < PUZZLE_SIZE; j++) {
				Set<String> subBox = new HashSet<>();
				int startRow = i - i % SUBBOX_SIZE;
				int startCol = j - j % SUBBOX_SIZE;
				for (int row = 0; row < SUBBOX_SIZE; row++) {
					for (int col = 0; col < SUBBOX_SIZE; col++) {
						String item = records[row + startRow][col + startCol];
						if (!item.trim().isEmpty()) {
							if (!subBox.add(item))
								throw new SudokuValidationException(DUPLICATE_ITEM);
						}
					}
				}
			}
		}
	}

	private void checkForDuplicate(final List<String> items) {
		if(items.stream().map(item -> item.trim())
				   .filter(item -> !item.isEmpty())
				   .anyMatch(item -> (Collections.frequency(items, item) > 1)))
			throw new SudokuValidationException(DUPLICATE_ITEM);
	}

	private String[][] getRecords(final String filePath) {
		String[][] records = new String[PUZZLE_SIZE][];
		try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
			String[] values;
			int i=0;
			while ((values = csvReader.readNext()) != null) {
				// check row size
				if (values.length!=PUZZLE_SIZE)
					throw new SudokuValidationException(INVALID_PUZZLE_SIZE);
				records[i] = values;
				i++;
			}
			// check column size
			if (i!=PUZZLE_SIZE)
				throw new SudokuValidationException(INVALID_PUZZLE_SIZE);
		} catch (IOException ex) {
			throw new SudokuValidationException(ex.getMessage());
		}
		return records;
	}
}