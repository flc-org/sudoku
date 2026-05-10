package com.flc.org.sudoku.command.impl;

import com.flc.org.sudoku.command.Command;
import com.flc.org.sudoku.engine.ValidationTrackingService;
import org.springframework.stereotype.Component;

@Component
public class CheckCommand implements Command {

    private final ValidationTrackingService validationTrackingService;

    public CheckCommand(ValidationTrackingService validationTrackingService) {
        this.validationTrackingService = validationTrackingService;
    }

    @Override
    public boolean execute(String input) {
        validationTrackingService.getAllValidationErrors().forEach(System.out::println);
        return false;
    }

    @Override
    public boolean matches(String input) {
        return "check".equalsIgnoreCase(input.trim());
    }
}
