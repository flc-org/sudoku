package com.flc.org.sudoku.engine;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SudokuEngine implements BoardReader, BoardPlayerInterface {
    private int[][] board;
    private final ValidationTrackingService validationTrackingService;
    Set<String> prefilledCells = new HashSet<>();
    @Getter
    Set<String> emptyCells = new HashSet<>();

    public SudokuEngine(ValidationTrackingService validationTrackingService) {
        this.validationTrackingService = validationTrackingService;
        this.board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                emptyCells.add(i + "," + j);
            }
        }
    }

    public boolean canPrefill(int row, int col, int value) {
        if (board[row][col] != 0) {
            return false;
        }
        return validationTrackingService.canSetValue(row, col, value);
    }

    public boolean setCell(int row, int col, int value, boolean isPrefill) {
        if (isPrefill) {
            prefilledCells.add(row + "," + col);
        }
        if (!isPrefill && prefilledCells.contains(row + "," + col)) {
            return false;
        }

        if (board[row][col] != 0) {
            validationTrackingService.clearValue(row, col, board[row][col]);
        }
        this.board[row][col] = value;
        validationTrackingService.setValue(row, col, value);
        emptyCells.remove(row + "," + col);
        return true;
    }

    public boolean clearCell(int row, int col) {
        if (prefilledCells.contains(row + "," + col)) {
            return false;
        }
        validationTrackingService.clearValue(row, col, board[row][col]);
        this.board[row][col] = 0;
        emptyCells.add(row + "," + col);
        return true;
    }

    @Override
    public int getValueAt(int row, int col) {
        return this.board[row][col];
    }

    @Override
    public boolean setValueAt(int row, int col, int value) {
        return this.setCell(row, col, value, false);
    }

    @Override
    public boolean clearValueAt(int row, int col) {
        return this.clearCell(row, col);
    }

    @Override
    public boolean isBoardComplete() {
        return validationTrackingService.isGameEnd();
    }

    @Override
    public boolean canClearValueAt(int row, int col) {
        return !prefilledCells.contains(row + "," + col);
    }
}
