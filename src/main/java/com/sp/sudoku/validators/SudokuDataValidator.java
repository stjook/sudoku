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

	private static final int PUZZLE_SIZE = 9;
	private static final int SUBBOX_SIZE = 3;

	public void validate(Sudoku sudoku) {
		String[][] records = getRecords(sudoku.getFilePath());
		validateRecords(records);
	}

	private void validateRecords(String[][] records) {
		validateRows(records);
		validateColumns(records);
		validateSubBoxes(records);
	}

	private void validateRows(String[][] records) {
		for (int i = 0; i < PUZZLE_SIZE; i++) {
			String[] row = records[i];
			checkForInvalidOrDublicate(Arrays.asList(row));
		}
	}

	private void validateColumns(String[][] records) {
		for (int j = 0; j < PUZZLE_SIZE; j++) {
			final int index = j;
			String[] column = IntStream.range(0, PUZZLE_SIZE)
									  .mapToObj(i -> records[i][index])
									  .toArray(String[]::new);
			checkForInvalidOrDublicate(Arrays.asList(column));
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
							if (subBox.contains(item))
								throw new SudokuValidationException("SubBox is not valid");

							if (ALLOWED_ITEMS.contains(item))
								subBox.add(item);
							else
								throw new SudokuValidationException("SubBox is not valid");
						}
					}
				}
			}
		}
	}

	private void checkForInvalidOrDublicate(final List<String> items) {
		if(items.stream()
				   .filter(item -> !item.trim().isEmpty())
				   .anyMatch(item -> (Collections.frequency(items, item) > 1 ||
											  !ALLOWED_ITEMS.contains(item.trim()))))
			throw new SudokuValidationException("Invalid item or duplicate found");
	}

	private String[][] getRecords(final String filePath) {
		String[][] records = new String[PUZZLE_SIZE][PUZZLE_SIZE];
		try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
			String[] values;
			int i=0;
			while ((values = csvReader.readNext()) != null) {
				records[i] = values;
				i++;
			}
		} catch (IOException ex) {
			throw new SudokuValidationException(ex.getMessage());
		}
		return records;
	}
}