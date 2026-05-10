package com.flc.org.sudoku.engine.impl;

import com.flc.org.sudoku.engine.BoardReader;
import com.flc.org.sudoku.engine.SudokuRenderer;
import com.flc.org.sudoku.engine.ValidationTrackingService;
import org.springframework.stereotype.Component;

@Component
public class SudokuConsoleRenderer extends SudokuRenderer {


    protected SudokuConsoleRenderer(ValidationTrackingService validationTrackingService, BoardReader reader) {
        super(validationTrackingService, reader);
    }

    @Override
    protected void renderBoard(BoardReader reader) {
        printColumnHeader();
        System.out.println();
        System.out.println("+-+-------+-------+-------+");
        for (int i = 0; i < 9; i++) {
            System.out.print((char)('A' + i) + " | ");
            for (int j = 0; j < 9; j++) {
                int value = reader.getValueAt(i, j);
                boolean isInvalid = value != 0 && validationTrackingService.isCellInvalid(i, j, value);
                if (isInvalid) {
                    System.out.print("\u001B[31m");
                }
                System.out.print((value == 0 ? "_" : value) + " ");
                if (isInvalid) {
                    System.out.print("\u001B[0m");
                }
                if(j % 3 == 2) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if(i % 3 == 2) {
                System.out.println("+-+-------+-------+-------+");
            }
        }
    }

    private static void printColumnHeader() {
        System.out.println("+-+-------+-------+-------+");
        System.out.print("  | ");
        for (int j = 1; j <= 9; j++) {
            System.out.print(j + " ");
            if(j % 3 == 0) {
                System.out.print("| ");
            }
        }
    }


}
