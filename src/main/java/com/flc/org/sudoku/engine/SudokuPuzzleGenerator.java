package com.flc.org.sudoku.engine;

import org.springframework.beans.factory.annotation.Value;
/**
 * * Abstract class for generating Sudoku puzzles.
 * currently only randome generation is supported,
 * but in future we can add more generation strategies like backtracking, Algorithm X + Dancing Links, pattern-based generation, etc.
 */
public abstract class   SudokuPuzzleGenerator {
    protected final SudokuEngine sudokuEngine;
    protected final int noOfClues;

    protected SudokuPuzzleGenerator(SudokuEngine sudokuEngine, int noOfClues) {
        this.sudokuEngine = sudokuEngine;
        this.noOfClues = noOfClues;
    }

    public  abstract void generatePuzzle();
}
