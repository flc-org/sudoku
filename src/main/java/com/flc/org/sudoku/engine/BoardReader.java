package com.flc.org.sudoku.engine;

/**
 * Interface for reading values from the Sudoku board.
 * This provides an abstract layer on top of the Sudoku engine to allow read-only access to the board for rendering and validation purposes.
 * */
public interface BoardReader {
    int getValueAt(int row, int col);
}
