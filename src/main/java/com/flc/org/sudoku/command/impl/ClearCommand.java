package com.flc.org.sudoku.command.impl;

import com.flc.org.sudoku.command.Command;
import com.flc.org.sudoku.engine.BoardPlayerInterface;
import com.flc.org.sudoku.engine.SudokuRendered;
import org.springframework.stereotype.Component;

@Component("clearCellCommand")
public class ClearCommand implements Command {
    private final BoardPlayerInterface boardPlayer;
    private final SudokuRendered renderer;

    public ClearCommand(BoardPlayerInterface boardPlayer, SudokuRendered renderer) {
        this.boardPlayer = boardPlayer;
        this.renderer = renderer;
    }

    @Override
    public boolean execute(String input) {
        String[] parts = input.split(" ");
        String cell = parts[0];
        int row = cell.charAt(0) - 'A';
        int col = cell.charAt(1) - '1';
        if(boardPlayer.clearValueAt(row, col)) {
            System.out.println("Move accepted.");
            System.out.println();
            System.out.println("Current board:");
            renderer.render();
        } else {
            System.out.println("Invalid move. "+ cell +"  is pre-filled.");
        }
        return false;
    }

    @Override
    public boolean matches(String input) {
        return input.matches("[A-I][1-9] clear");
    }
}
