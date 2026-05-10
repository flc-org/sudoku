package com.flc.org.sudoku.engine.impl;

import com.flc.org.sudoku.engine.BoardPlayerInterface;
import com.flc.org.sudoku.engine.HintService;
import com.flc.org.sudoku.engine.ValidationTrackingService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

@Service
public class RandomHintGeneratorService implements HintService {

    private final ValidationTrackingService validationTrackingService;
    private final BoardPlayerInterface boardPlayerInterface;

    public RandomHintGeneratorService(ValidationTrackingService validationTrackingService, BoardPlayerInterface boardPlayerInterface) {
        this.validationTrackingService = validationTrackingService;
        this.boardPlayerInterface = boardPlayerInterface;
    }

    @Override
    public String getHint(Set<String> emptyCells) {
        Iterator<String> nextInvalidCell = validationTrackingService.getInvalidCells();
        while (nextInvalidCell.hasNext()) {
            String cell = nextInvalidCell.next();
            String[] parts = cell.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            if (!boardPlayerInterface.canClearValueAt(row, col)) {
                continue;
            }
            return String.valueOf((char) (row + 'A')) + (col + 1) + " clear";
        }
        //try brute force on empty cells
        for (String cell : emptyCells) {
            String[] parts = cell.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            for (int value = 1; value <= 9; value++) {
                if (validationTrackingService.canSetValue(row, col, value)) {
                    return String.valueOf((char) (row + 'A')) + (col + 1) + " " + value;
                }
            }
        }
        return "No solutions available.";
    }
}
