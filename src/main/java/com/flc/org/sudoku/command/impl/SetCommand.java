package com.flc.org.sudoku.command.impl;

import com.flc.org.sudoku.command.Command;
import com.flc.org.sudoku.engine.BoardPlayerInterface;
import com.flc.org.sudoku.engine.BoardReader;
import com.flc.org.sudoku.engine.SudokuRendered;
import org.springframework.stereotype.Component;

@Component
public class SetCommand implements Command{
    private final BoardPlayerInterface boardPlayer;
    private final SudokuRendered renderer;

    public SetCommand(BoardPlayerInterface boardPlayer, SudokuRendered renderer) {
        this.boardPlayer = boardPlayer;
        this.renderer = renderer;
    }

    @Override
    public boolean execute(String input) {
        String[] parts = input.split(" ");
        String cell = parts[0];
        int value = Integer.parseInt(parts[1]);
        int row = cell.charAt(0) - 'A';
        int col = cell.charAt(1) - '1';
        if (boardPlayer.setValueAt(row, col, value)) {
            System.out.println("Move accepted.");
            System.out.println();
            System.out.println("Current board:");
            renderer.render();
            if (boardPlayer.isBoardComplete()) {
                System.out.println("You have successfully completed the Sudoku puzzle!");
                return true;
            }
        } else {
            System.out.println("Invalid move. "+ cell +"  is pre-filled.");
        }

        return false;
    }

    @Override
    public boolean matches(String input) {
        return input.matches("[A-I][1-9] [1-9]");
    }
}

