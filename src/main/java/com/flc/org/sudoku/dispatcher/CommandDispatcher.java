package com.flc.org.sudoku.dispatcher;

import com.flc.org.sudoku.command.Command;
import com.flc.org.sudoku.engine.SudokuPuzzleGenerator;
import com.flc.org.sudoku.engine.SudokuRenderer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

/**
 * CommandDispatcher is responsible for managing user input and dispatching commands to the appropriate handlers.
 * It implements CommandLineRunner to start the command loop when the application runs.
 */
@Component
@RequiredArgsConstructor
public class CommandDispatcher implements CommandLineRunner {
    private final List<Command> commands;
    private final SudokuPuzzleGenerator puzzleGenerator;
    private final SudokuRenderer sudokuRenderer;


    @PostConstruct
    public void initialize() {
        puzzleGenerator.generatePuzzle();
    }

    public boolean dispatch(String input) {
        for (Command command : commands) {
            if (command.matches(input)) {
                return command.execute(input);
            }
        }
        System.out.println("Invalid command. Please try again.");
        return false;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Sudoku!");
        System.out.println("");
        System.out.println("Here is your puzzle:");
        sudokuRenderer.render();
        while (true) {
            System.out.println("Enter command (e.g., A3 4, C5 clear, hint, check, quit):");
            String input = scanner.nextLine();
            if (dispatch(input)) {
                break;
            }
        }
        scanner.close();
    }
}
