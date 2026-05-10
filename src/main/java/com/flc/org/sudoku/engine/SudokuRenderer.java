package com.flc.org.sudoku.engine;

/**
 * Abstract class for rendering the Sudoku board.  Subclasses will implement
 * the renderBoard method to provide specific rendering logic (e.g., console, GUI).
 */
public abstract class SudokuRenderer {
    protected final ValidationTrackingService validationTrackingService;
    protected final BoardReader reader;

    protected SudokuRenderer(ValidationTrackingService validationTrackingService, BoardReader reader) {
        this.validationTrackingService = validationTrackingService;
        this.reader = reader;
    }

    public void render() {
        renderBoard(reader);
    }

    protected abstract void renderBoard(BoardReader reader);
}
