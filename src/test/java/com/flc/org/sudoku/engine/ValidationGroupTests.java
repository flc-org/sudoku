package com.flc.org.sudoku.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationGroupTests {
    private ValidationGroup validationGroup;

    @BeforeEach
    void setup() {
        validationGroup = new ValidationGroup();
    }

    @Test
    void shouldAllowSettingValueWhenNoConflictExists() {
        assertTrue(validationGroup.canSetValue(5));
    }

    @Test
    void shouldPreventSettingValueWhenAlreadySet() {
        validationGroup.setValue(5, "0,0");
        assertFalse(validationGroup.canSetValue(5));
    }

    @Test
    void shouldReturnTrueWhenSettingValueFirstTime() {
        assertTrue(validationGroup.setValue(5, "0,0"));
    }

    @Test
    void shouldReturnFalseWhenSettingValueSecondTime() {
        validationGroup.setValue(5, "0,0");
        assertFalse(validationGroup.setValue(5, "0,1"));
    }

    @Test
    void shouldMarkValueAsInvalidAfterDuplicate() {
        validationGroup.setValue(5, "0,0");
        validationGroup.setValue(5, "0,1");
        assertTrue(validationGroup.invalidValues.contains(5));
    }

    @Test
    void shouldNotMarkValueAsInvalidImmediatelyAfterFirstSet() {
        validationGroup.setValue(5, "0,0");
        assertFalse(validationGroup.invalidValues.contains(5));
    }

    @Test
    void shouldRemoveValueFromInvalidWhenClearedToSingleOccurrence() {
        validationGroup.setValue(5, "0,0");
        validationGroup.setValue(5, "0,1");
        validationGroup.clearValue(5, "0,1");
        assertFalse(validationGroup.invalidValues.contains(5));
    }

    @Test
    void shouldRemoveValueFromInvalidWhenAllInstancesCleared() {
        validationGroup.setValue(5, "0,0");
        validationGroup.setValue(5, "0,1");
        validationGroup.clearValue(5, "0,0");
        validationGroup.clearValue(5, "0,1");
        assertFalse(validationGroup.invalidValues.contains(5));
    }

    @Test
    void shouldAllowResettingValueAfterClearing() {
        validationGroup.setValue(5, "0,0");
        validationGroup.clearValue(5, "0,0");
        assertTrue(validationGroup.canSetValue(5));
    }

    @Test
    void shouldHandleClearingNonExistentValue() {
        validationGroup.clearValue(5, "0,0");
        assertTrue(validationGroup.canSetValue(5));
    }

    @Test
    void shouldSupportMultipleDifferentValues() {
        validationGroup.setValue(1, "0,0");
        validationGroup.setValue(2, "0,1");
        validationGroup.setValue(3, "0,2");
        assertFalse(validationGroup.canSetValue(1));
        assertFalse(validationGroup.canSetValue(2));
        assertFalse(validationGroup.canSetValue(3));
        assertTrue(validationGroup.canSetValue(4));
    }

    @Test
    void shouldTrackMultipleCellsPerValue() {
        validationGroup.setValue(5, "0,0");
        validationGroup.setValue(5, "1,1");
        validationGroup.setValue(5, "2,2");
        assertTrue(validationGroup.invalidValues.contains(5));
    }

}

