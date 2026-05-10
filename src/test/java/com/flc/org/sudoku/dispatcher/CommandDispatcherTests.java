package com.flc.org.sudoku.dispatcher;

import com.flc.org.sudoku.command.Command;
import com.flc.org.sudoku.engine.SudokuPuzzleGenerator;
import com.flc.org.sudoku.engine.SudokuRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandDispatcherTests {
    @Mock
    private List<Command> commands;
    @Mock
    private SudokuPuzzleGenerator puzzleGenerator;
    @Mock
    private SudokuRenderer sudokuRenderer;
    @Mock
    private Command command;

    private CommandDispatcher dispatcher;

    @BeforeEach
    void setup() {
        dispatcher = new CommandDispatcher(commands, puzzleGenerator, sudokuRenderer);
    }

    @Test
    void shouldGeneratePuzzleOnInitialization() {
        dispatcher.initialize();
        verify(puzzleGenerator).generatePuzzle();
    }

    @Test
    void shouldExecuteMatchingCommandAndReturnItsResult() {
        when(commands.iterator()).thenReturn(List.of(command).iterator());
        when(command.matches("valid input")).thenReturn(true);
        when(command.execute("valid input")).thenReturn(true);

        dispatcher.dispatch("valid input");

        verify(command).execute("valid input");
    }

    @Test
    void shouldReturnTrueWhenCommandExecutionSucceeds() {
        when(commands.iterator()).thenReturn(List.of(command).iterator());
        when(command.matches("valid input")).thenReturn(true);
        when(command.execute("valid input")).thenReturn(true);

        boolean result = dispatcher.dispatch("valid input");

        assert result;
    }

    @Test
    void shouldReturnFalseWhenCommandExecutionFails() {
        when(commands.iterator()).thenReturn(List.of(command).iterator());
        when(command.matches("valid input")).thenReturn(true);
        when(command.execute("valid input")).thenReturn(false);

        boolean result = dispatcher.dispatch("valid input");

        assert !result;
    }

    @Test
    void shouldPrintInvalidMessageForUnmatchedInput() {
        when(commands.iterator()).thenReturn(List.of(command).iterator());
        when(command.matches("invalid input")).thenReturn(false);

        boolean result = dispatcher.dispatch("invalid input");

        assert !result;
        // Note: System.out.println is not easily verifiable in unit tests without capturing output
    }

    @Test
    void shouldCheckAllCommandsUntilMatchFound() {
        Command command1 = mock(Command.class);
        Command command2 = mock(Command.class);
        when(commands.iterator()).thenReturn(List.of(command1, command2).iterator());
        when(command1.matches("input")).thenReturn(false);
        when(command2.matches("input")).thenReturn(true);
        when(command2.execute("input")).thenReturn(true);

        dispatcher.dispatch("input");

        verify(command1).matches("input");
        verify(command2).matches("input");
        verify(command2).execute("input");
        verify(command1, never()).execute("input");
    }

}
