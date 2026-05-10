package com.flc.org.sudoku.engine;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ValidationGroup {
    Set<String>[] cells = new Set[10];
    Set<Integer> invalidValues = new HashSet<>();

    boolean canSetValue(int value) {
        if (Objects.nonNull(cells[value - 1])) {
            return cells[value].isEmpty();
        }
        return true;
    }

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

    void clearValue(int value, String cell) {
        if (Objects.nonNull(cells[value])) {
            cells[value].remove(cell);
        }
        if (cells[value] == null || cells[value].size() <= 1) {
            invalidValues.remove(value);
        }
    }

}



