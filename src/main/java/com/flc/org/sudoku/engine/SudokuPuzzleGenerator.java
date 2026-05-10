package com.flc.org.sudoku.engine;

import org.springframework.beans.factory.annotation.Value;

public abstract class   SudokuPuzzleGenerator {
    protected final SudokuEngine sudokuEngine;
    protected final int noOfClues;

    protected SudokuPuzzleGenerator(SudokuEngine sudokuEngine, int noOfClues) {
        this.sudokuEngine = sudokuEngine;
        this.noOfClues = noOfClues;
    }

    public  abstract void generatePuzzle();
}
