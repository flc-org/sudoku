package com.flc.org.sudoku.engine;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * ValidationGroup is a helper class that tracks the values set in a specific group of cells (e.g., a row, column, or 3x3 box) and identifies any duplicates.
 * in each validation group, we maintain a set of cells for each value (1-9). When a value is set in a cell, we add that cell to the corresponding set.
 * If the set size exceeds 1, it means there is a duplicate value in that group, and we add that value to the invalidValues set.
 */
public class ValidationGroup {
    Set<String>[] cells = new Set[10];
    Set<Integer> invalidValues = new HashSet<>();

    /**
     * Checks if a value can be set in the group without causing a duplicate.
     * @param value
     * @return
     */
    boolean canSetValue(int value) {
        if (Objects.nonNull(cells[value])) {
            return cells[value].isEmpty();
        }
        return true;
    }

    /**
     * Sets a value in the group and updates the tracking of cells and invalid values.
     * @param value
     * @param cell
     * @return true if the value was set successfully without causing a duplicate, false otherwise.
     */
    boolean setValue(int value, String cell) {
        if (Objects.isNull(cells[value])) {
            cells[value] = new HashSet<>();
        }
        cells[value].add(cell);
        if (cells[value].size() > 1) {
            invalidValues.add(value);
            return false;
        }
        return true;
    }

    /**
     * Clears a value from the group and updates the tracking of cells and invalid values accordingly.
     * @param value
     * @param cell
     */
    void clearValue(int value, String cell) {
        if (Objects.nonNull(cells[value])) {
            cells[value].remove(cell);
        }
        if (cells[value] == null || cells[value].size() <= 1) {
            invalidValues.remove(value);
        }
    }

}



