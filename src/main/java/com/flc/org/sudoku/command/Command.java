package com.flc.org.sudoku.command;
/**
 * Command interface for handling user input in the Sudoku application.
 * Each command should implement this interface to define its execution logic and matching criteria.
 */
public interface Command {
        boolean execute(String input);
        boolean matches(String input);
}
