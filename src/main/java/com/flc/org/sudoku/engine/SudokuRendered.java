package com.flc.org.sudoku.engine;

public abstract class SudokuRendered {
    protected final ValidationTrackingService validationTrackingService;
    protected final BoardReader reader;

    protected SudokuRendered(ValidationTrackingService validationTrackingService, BoardReader reader) {
        this.validationTrackingService = validationTrackingService;
        this.reader = reader;
    }

    public void render() {
        renderBoard(reader);
    }

    protected abstract void renderBoard(BoardReader reader);
}
