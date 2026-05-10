package com.flc.org.sudoku.engine;

import java.util.Set;

/**
 * Service for providing hints to the player during Sudoku gameplay.
 * currently only brute force hint is implemented, which finds the first empty cell and returns a valid value for it.
 * In the future, more advanced hint strategies can be implemented, such as: Algorithm X + Dancing Links, pattern recognition, etc.
 */

public interface HintService {
    public String getHint(Set<String> emptyCells);
}
