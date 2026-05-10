package com.flc.org.sudoku.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTrackingServiceTests {
    private ValidationTrackingService service;

    @BeforeEach
    void setup() {
        service = new ValidationTrackingService();
        service.initialize();
    }

    @Test
    void shouldAllowSettingValueWhenNoConflicts() {
        assertTrue(service.canSetValue(0, 0, 5));
    }

    @Test
    void shouldPreventSettingValueInSameRow() {
        service.setValue(0, 0, 5);
        assertFalse(service.canSetValue(0, 5, 5));
    }

    @Test
    void shouldPreventSettingValueInSameColumn() {
        service.setValue(0, 0, 5);
        assertFalse(service.canSetValue(5, 0, 5));
    }

    @Test
    void shouldPreventSettingValueInSameBox() {
        service.setValue(0, 0, 5);
        assertFalse(service.canSetValue(1, 1, 5));
    }

    @Test
    void shouldAllowSettingValueInDifferentRowColumnAndBox() {
        service.setValue(0, 0, 5);
        assertTrue(service.canSetValue(3, 3, 5));
    }

    @Test
    void shouldReturnTrueWhenValidValueSet() {
        assertTrue(service.setValue(0, 0, 5));
    }

    @Test
    void shouldReturnFalseWhenConflictingValueSetInRow() {
        service.setValue(0, 0, 5);
        assertFalse(service.setValue(0, 1, 5));
    }

    @Test
    void shouldReturnFalseWhenConflictingValueSetInColumn() {
        service.setValue(0, 0, 5);
        assertFalse(service.setValue(1, 0, 5));
    }

    @Test
    void shouldReturnFalseWhenConflictingValueSetInBox() {
        service.setValue(0, 0, 5);
        assertFalse(service.setValue(2, 2, 5));
    }

    @Test
    void shouldDecrementEmptyCellsWhenValueSet() {
        int initialEmpty = service.emptyCells;
        service.setValue(0, 0, 5);
        assertEquals(initialEmpty - 1, service.emptyCells);
    }

    @Test
    void shouldIncrementEmptyCellsWhenValueCleared() {
        service.setValue(0, 0, 5);
        int afterSet = service.emptyCells;
        service.clearValue(0, 0, 5);
        assertEquals(afterSet + 1, service.emptyCells);
    }

    @Test
    void shouldAllowResettingValueAfterClear() {
        service.setValue(0, 0, 5);
        service.clearValue(0, 0, 5);
        assertTrue(service.canSetValue(0, 0, 5));
    }

    @Test
    void shouldDetectCellAsInvalidWhenRowHasConflict() {
        service.setValue(0, 0, 5);
        service.setValue(0, 1, 5);
        assertTrue(service.isCellInvalid(0, 0, 5));
    }

    @Test
    void shouldDetectCellAsInvalidWhenColumnHasConflict() {
        service.setValue(0, 0, 5);
        service.setValue(1, 0, 5);
        assertTrue(service.isCellInvalid(0, 0, 5));
    }

    @Test
    void shouldDetectCellAsInvalidWhenBoxHasConflict() {
        service.setValue(0, 0, 5);
        service.setValue(1, 1, 5);
        assertTrue(service.isCellInvalid(0, 0, 5));
    }

    @Test
    void shouldNotDetectCellAsInvalidWhenNoConfliert() {
        service.setValue(0, 0, 5);
        assertFalse(service.isCellInvalid(0, 0, 5));
    }

    @Test
    void shouldNotEndGameWhenCellsAreEmpty() {
        assertFalse(service.isGameEnd());
    }

    @Test
    void shouldNotEndGameWhenValidCellsRemain() {
        for (int i = 0; i < 80; i++) {
            service.setValue(i / 9, i % 9, (i % 9) + 1);
        }
        assertFalse(service.isGameEnd());
    }



    @Test
    void shouldNotEndGameWhenAllCellsFilledWithConflicts() {
        for (int i = 0; i < 80; i++) {
            service.setValue(i / 9, i % 9, 1);
        }
        assertFalse(service.isGameEnd());
    }

    @Test
    void shouldReturnInvalidCellsWhenConflictsExist() {
        service.setValue(0, 0, 5);
        service.setValue(0, 1, 5);
        var iterator = service.getInvalidCells();
        List<String> invalidCells = new ArrayList<>();
        iterator.forEachRemaining(invalidCells::add);
        assertFalse(invalidCells.isEmpty());
        assertTrue(invalidCells.contains("0,0") || invalidCells.contains("0,1"));
    }

    @Test
    void shouldReturnEmptyInvalidCellsWhenNoConflicts() {
        service.setValue(0, 0, 5);
        var iterator = service.getInvalidCells();
        assertFalse(iterator.hasNext());
    }

    @Test
    void shouldReportNoViolationsWhenBoardIsValid() {
        service.setValue(0, 0, 5);
        List<String> errors = service.getAllValidationErrors();
        assertEquals(1, errors.size());
        assertEquals("No rule violations detected.", errors.get(0));
    }

    @Test
    void shouldReportRowConflictError() {
        service.setValue(0, 0, 5);
        service.setValue(0, 1, 5);
        List<String> errors = service.getAllValidationErrors();
        assertTrue(errors.stream().anyMatch(e -> e.contains("Row") && e.contains("5")));
    }

    @Test
    void shouldReportColumnConflictError() {
        service.setValue(0, 0, 5);
        service.setValue(1, 0, 5);
        List<String> errors = service.getAllValidationErrors();
        assertTrue(errors.stream().anyMatch(e -> e.contains("Column") && e.contains("5")));
    }

    @Test
    void shouldReportBoxConflictError() {
        service.setValue(0, 0, 5);
        service.setValue(1, 1, 5);
        List<String> errors = service.getAllValidationErrors();
        assertTrue(errors.stream().anyMatch(e -> e.contains("Box") && e.contains("5")));
    }

    @Test
    void shouldTrackMultipleIndependentValues() {
        service.setValue(0, 0, 1);
        service.setValue(0, 1, 2);
        service.setValue(0, 2, 3);
        assertFalse(service.canSetValue(0, 3, 1));
        assertFalse(service.canSetValue(0, 4, 2));
        assertFalse(service.canSetValue(0, 5, 3));
        assertTrue(service.canSetValue(0, 6, 4));
    }

    @Test
    void shouldHandleBoxCalculationForAllBoxes() {
        service.setValue(0, 0, 1);
        assertFalse(service.canSetValue(2, 2, 1));

        service.setValue(3, 3, 2);
        assertFalse(service.canSetValue(5, 5, 2));

        service.setValue(6, 6, 3);
        assertFalse(service.canSetValue(8, 8, 3));
    }

    @Test
    void shouldClearConflictStatusAfterRemovingDuplicateValue() {
        service.setValue(0, 0, 5);
        service.setValue(0, 1, 5);
        assertTrue(service.isCellInvalid(0, 0, 5));

        service.clearValue(0, 1, 5);
        assertFalse(service.isCellInvalid(0, 0, 5));
    }

}

