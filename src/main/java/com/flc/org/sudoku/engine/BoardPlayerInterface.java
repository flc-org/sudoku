package com.flc.org.sudoku.engine;

/**
 * Interface for player interactions with the Sudoku board.
 * this provides abstract layer on top of sudoku engine to restrict access to the board and provide only necessary methods for player interactions.
 * */
public interface BoardPlayerInterface {
        boolean setValueAt(int row, int col, int value);
        boolean clearValueAt(int row, int col);
        boolean isBoardComplete();

        boolean canClearValueAt(int row, int col);
}
