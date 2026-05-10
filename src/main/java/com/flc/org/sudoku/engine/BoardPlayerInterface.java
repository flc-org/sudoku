package com.flc.org.sudoku.engine;

public interface BoardPlayerInterface {
        boolean setValueAt(int row, int col, int value);
        boolean clearValueAt(int row, int col);
        boolean isBoardComplete();

        boolean canClearValueAt(int row, int col);
}
