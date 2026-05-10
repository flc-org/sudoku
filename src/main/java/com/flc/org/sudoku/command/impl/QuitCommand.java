package com.flc.org.sudoku.command.impl;

import com.flc.org.sudoku.command.Command;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class QuitCommand implements Command{
    private static final String QUIT_COMMAND = "quit";

    @Override
    public boolean execute(String input) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to quit? (yes/no)");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            System.out.println("Thank you for playing Sudoku! Goodbye!");
            System.exit(0);
        }
        return false;
    }

    @Override
    public boolean matches(String input) {
        return QUIT_COMMAND.equalsIgnoreCase(input.trim());
    }
}
