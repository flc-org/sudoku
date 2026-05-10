package com.flc.org.sudoku.engine.impl;

import com.flc.org.sudoku.engine.SudokuEngine;
import com.flc.org.sudoku.engine.SudokuPuzzleGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Service responsible for generating new Sudoku puzzles.
 * this service input 30 random valid moves to create a new puzzle.
 * The generated puzzle is not guaranteed to be solvable, and may not be unique.
 */
@Service
@Slf4j
public class RandomSudokuPuzzleGenerationService extends SudokuPuzzleGenerator {
    private final SudokuEngine sudokuEngine;

    public RandomSudokuPuzzleGenerationService(SudokuEngine sudokuEngine, @Value("${sudoku.puzzle.clues:30}") int noOfClues) {
        super(sudokuEngine, noOfClues);
        this.sudokuEngine = sudokuEngine;
    }

    public void generatePuzzle() {
        int count=1;
       while (count<=30) {
           int row = (int) (Math.random() * 8);
           int col = (int) (Math.random() * 8);
           int value = (int) (Math.random() * 8) + 1;
           if(sudokuEngine.canPrefill(row, col, value)) {
               log.info("Trying to prefill cell {}{} with value {}", row, col, value);
               sudokuEngine.setCell(row, col, value,true);
               count++;
           }
       }
    }
}
