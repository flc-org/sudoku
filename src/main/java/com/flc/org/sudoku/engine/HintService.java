package com.flc.org.sudoku.engine;

import java.util.Set;

public interface HintService {
    public String getHint(Set<String> emptyCells);
}
