package com.flc.org.sudoku.command;

public interface Command {
        boolean execute(String input);
        boolean matches(String input);
}
