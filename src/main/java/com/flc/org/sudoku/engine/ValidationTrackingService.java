package com.flc.org.sudoku.engine;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service to track the validation state of the Sudoku board, including which values are invalid in each row, column, and box.
 * It also tracks the number of empty cells and provides methods to check if the game has ended or to retrieve invalid cells.
 */
@Service
public class ValidationTrackingService {
    int emptyCells = 81;
    private ValidationGroup[] rows = new ValidationGroup[9];
    private ValidationGroup[] cols = new ValidationGroup[9];
    private ValidationGroup[] boxes = new ValidationGroup[9];

    @PostConstruct
    void initialize() {
        for (int i = 0; i < 9; i++) {
            rows[i] = new ValidationGroup();
            cols[i] = new ValidationGroup();
            boxes[i] = new ValidationGroup();
        }
    }

    public boolean canSetValue(int row, int col, int value) {
        int boxIndex = (row / 3) * 3 + (col / 3);
        return rows[row].canSetValue(value) && cols[col].canSetValue(value) && boxes[boxIndex].canSetValue(value);
    }

    public boolean setValue(int row, int col, int value) {
        int boxIndex = (row / 3) * 3 + (col / 3);
        String cell = row + "," + col;
        emptyCells--;
        boolean isValidRow = rows[row].setValue(value, cell);
        boolean isValidCol = cols[col].setValue(value, cell);
        boolean isValidBox = boxes[boxIndex].setValue(value, cell);
        return isValidRow && isValidCol && isValidBox;
    }

    public void clearValue(int row, int col, int value) {
        emptyCells++;
        rows[row].clearValue(value, row + "," + col);
        cols[col].clearValue(value, row + "," + col);
        int boxIndex = (row / 3) * 3 + (col / 3);
        boxes[boxIndex].clearValue(value, row + "," + col);
    }

    public boolean isCellInvalid(int row, int col, int value) {
        int boxIndex = (row / 3) * 3 + (col / 3);
        return rows[row].invalidValues.contains(value) || cols[col].invalidValues.contains(value) || boxes[boxIndex].invalidValues.contains(value);
    }

    public boolean isGameEnd() {
        if (emptyCells > 0) {
            return false;
        }
        for (int i = 0; i < 9; i++) {
            if (!rows[i].invalidValues.isEmpty() || !cols[i].invalidValues.isEmpty() || !boxes[i].invalidValues.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Iterator<String> getInvalidCells() {
        List<String> invalidCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int value : rows[i].invalidValues) {
                invalidCells.addAll(rows[i].cells[value]);
            }
            for (int value : cols[i].invalidValues) {
                invalidCells.addAll(cols[i].cells[value]);
            }
            for (int value : boxes[i].invalidValues) {
                invalidCells.addAll(boxes[i].cells[value]);
            }
        }
        return invalidCells.iterator();
    }

    public List<String> getAllValidationErrors() {
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int value : rows[i].invalidValues) {
                errors.add("Number " + value + " already exists in Row " + ((char) (i + 'A')) + ".");
            }
            for (int value : cols[i].invalidValues) {
                errors.add("Number " + value + " already exists in Column " + (i + 1) + ".");
            }
            for (int value : boxes[i].invalidValues) {
                errors.add("Number " + value + " already exists in Box same 3x3 grid");
            }
        }
        if (errors.isEmpty()) {
            errors.add("No rule violations detected.");
        }
        return errors;
    }

}
