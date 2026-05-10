package com.flc.org.sudoku.command.impl;

import com.flc.org.sudoku.command.Command;
import com.flc.org.sudoku.engine.HintService;
import com.flc.org.sudoku.engine.SudokuEngine;
import org.springframework.stereotype.Component;

@Component
public class HintCommand implements Command {
    private final HintService hintService;
    private final SudokuEngine sudokuEngine;

    public HintCommand(HintService hintService, SudokuEngine sudokuEngine) {
        this.hintService = hintService;
        this.sudokuEngine = sudokuEngine;
    }

    @Override
    public boolean execute(String _input) {
        System.out.println(hintService.getHint(sudokuEngine.getEmptyCells()));
        return false;
    }

    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("hint");
    }
}
