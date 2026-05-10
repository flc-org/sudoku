package com.flc.org.sudoku.command.impl;

import com.flc.org.sudoku.command.Command;

public class HintCommand implements Command {
    @Override
    public boolean execute(String input) {
        System.out.println("Hint feature is not implemented yet.");
        return false;
    }

    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("hint");
    }
}
