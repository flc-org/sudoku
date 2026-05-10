package com.flc.org.sudoku.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SudokuEngineTests {
    @Mock
    private ValidationTrackingService validationTrackingService;

    private SudokuEngine sudokuEngine;

    @BeforeEach
    void setup() {
        sudokuEngine = new SudokuEngine(validationTrackingService);
    }

    @Test
    void shouldInitializeBoardWithAllCellsEmpty() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(0, sudokuEngine.getValueAt(i, j));
            }
        }
    }

    @Test
    void shouldInitializeEmptyCellsWithAllPositions() {
        assertEquals(81, sudokuEngine.getEmptyCells().size());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertTrue(sudokuEngine.getEmptyCells().contains(i + "," + j));
            }
        }
    }

    @Test
    void shouldAllowPrefillWhenCellIsEmptyAndValueIsValid() {
        when(validationTrackingService.canSetValue(0, 0, 5)).thenReturn(true);
        assertTrue(sudokuEngine.canPrefill(0, 0, 5));
    }

    @Test
    void shouldPreventPrefillWhenCellIsOccupied() {
        sudokuEngine.setCell(0, 0, 3, true);
        assertFalse(sudokuEngine.canPrefill(0, 0, 5));
    }

    @Test
    void shouldPreventPrefillWhenValueIsInvalid() {
        when(validationTrackingService.canSetValue(0, 0, 5)).thenReturn(false);
        assertFalse(sudokuEngine.canPrefill(0, 0, 5));
    }

    @Test
    void shouldSetPrefillCellAndUpdateState() {
        when(validationTrackingService.setValue(0, 0, 5)).thenReturn(true);
        assertTrue(sudokuEngine.setCell(0, 0, 5, true));
        assertEquals(5, sudokuEngine.getValueAt(0, 0));
        assertTrue(sudokuEngine.getEmptyCells().size() < 81);
        assertFalse(sudokuEngine.getEmptyCells().contains("0,0"));
    }

    @Test
    void shouldPreventSettingNonPrefillOnPrefilledCell() {
        sudokuEngine.setCell(0, 0, 3, true);
        assertFalse(sudokuEngine.setCell(0, 0, 5, false));
    }

    @Test
    void shouldAllowSettingNonPrefillOnEmptyCell() {
        lenient().when(validationTrackingService.canSetValue(0, 0, 5)).thenReturn(true);
        lenient().when(validationTrackingService.setValue(0, 0, 5)).thenReturn(true);
        assertTrue(sudokuEngine.setValueAt(0, 0, 5));
        assertEquals(5, sudokuEngine.getValueAt(0, 0));
    }

    @Test
    void shouldClearOldValueWhenSettingNewValue() {
        sudokuEngine.setCell(0, 0, 3, false);
        when(validationTrackingService.setValue(0, 0, 5)).thenReturn(true);
        sudokuEngine.setCell(0, 0, 5, false);
        verify(validationTrackingService).clearValue(0, 0, 3);
        assertEquals(5, sudokuEngine.getValueAt(0, 0));
    }

    @Test
    void shouldAllowClearingNonPrefilledCell() {
        sudokuEngine.setCell(0, 0, 5, false);
        assertTrue(sudokuEngine.clearCell(0, 0));
        assertEquals(0, sudokuEngine.getValueAt(0, 0));
        assertTrue(sudokuEngine.getEmptyCells().contains("0,0"));
    }



    @Test
    void shouldReturnBoardValueAtPosition() {
        sudokuEngine.setCell(1, 2, 7, false);
        assertEquals(7, sudokuEngine.getValueAt(1, 2));
    }

    @Test
    void shouldDelegateSetValueAtToSetCell() {
        when(validationTrackingService.setValue(2, 3, 4)).thenReturn(true);
        assertTrue(sudokuEngine.setValueAt(2, 3, 4));
        assertEquals(4, sudokuEngine.getValueAt(2, 3));
    }

    @Test
    void shouldDelegateClearValueAtToClearCell() {
        sudokuEngine.setCell(2, 3, 4, false);
        assertTrue(sudokuEngine.clearValueAt(2, 3));
        assertEquals(0, sudokuEngine.getValueAt(2, 3));
    }

    @Test
    void shouldDelegateIsBoardCompleteToValidationService() {
        when(validationTrackingService.isGameEnd()).thenReturn(true);
        assertTrue(sudokuEngine.isBoardComplete());
        verify(validationTrackingService).isGameEnd();
    }



    @Test
    void shouldPreventClearingPrefilledCell() {
        sudokuEngine.setCell(0, 0, 5, true);
        assertFalse(sudokuEngine.canClearValueAt(0, 0));
    }

}
