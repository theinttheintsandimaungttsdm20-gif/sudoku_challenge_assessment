package com.sudoku.test;

import com.sudoku.constant.SudokuConstant;
import com.sudoku.model.Command;
import com.sudoku.service.CommandService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TheintSandi
 * @impleNote Sudoku giving command test cases that covers: <br>
 * - User command validation. <br>
 * - Clear command working. <br>
 */
public class CommandServiceTest {

    @Test
    void shouldParsePlaceCommand() {
        Command command = CommandService.parse("B3 7");
        assertEquals(Command.Type.PLACE, command.getType());
        assertEquals(1, command.getRow());  // B = 1
        assertEquals(2, command.getCol());  // 3 = 2
        assertEquals(7, command.getNumber());
    }

    @Test
    void shouldParseLowercasePlaceCommand() {
        Command command = CommandService.parse("b3 7");
        assertEquals(Command.Type.PLACE, command.getType());
        assertEquals(1, command.getRow());
        assertEquals(2, command.getCol());
    }

    @Test
    void shouldParseClearCommand() {
        Command command = CommandService.parse("C5 clear");
        assertEquals(Command.Type.CLEAR, command.getType());
        assertEquals(2, command.getRow()); // C = 2
        assertEquals(4, command.getCol()); // 5 = 4
    }

    @Test
    void shouldParseSingleCommands() {
        assertEquals(Command.Type.HINT,  CommandService.parse(SudokuConstant.CMD_HINT).getType());
        assertEquals(Command.Type.CHECK, CommandService.parse(SudokuConstant.CMD_CHECK).getType());
        assertEquals(Command.Type.QUIT,  CommandService.parse(SudokuConstant.CMD_QUIT).getType());
    }

    @Test
    void shouldReturnInvalidForBadInput() {
        assertEquals(Command.Type.INVALID, CommandService.parse(null).getType());
        assertEquals(Command.Type.INVALID, CommandService.parse("").getType());
        assertEquals(Command.Type.INVALID, CommandService.parse("B3 0").getType());
        assertEquals(Command.Type.INVALID, CommandService.parse("Z3 7").getType());
        assertEquals(Command.Type.INVALID, CommandService.parse("B3 X").getType());
    }
}