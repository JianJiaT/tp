package brokeculator.parser;

import brokeculator.command.Command;
import brokeculator.command.InvalidCommand;
import brokeculator.command.ViewSingleEventCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ViewSingleEventParserTest {
    @Test
    void parseInput_validInput_viewSingleEventCommand() {
        Command command = ViewSingleEventParser.parseInput("viewEvent /i 1");
        assertInstanceOf(ViewSingleEventCommand.class, command);
    }

    @Test
    void parseInput_missingKeyword_invalidCommand() {
        Command command = ViewSingleEventParser.parseInput("viewEvent 1");
        assertInstanceOf(InvalidCommand.class, command);
    }

    @Test
    void parseInput_nonIntegerIndex_invalidCommand() {
        Command command = ViewSingleEventParser.parseInput("viewEvent /i a");
        assertInstanceOf(InvalidCommand.class, command);
    }
    
    @Test
    void parseInput_invalidKeyword_invalidCommand() {
        Command command = ViewSingleEventParser.parseInput("viewEvent /a 1");
        assertInstanceOf(InvalidCommand.class, command);
    }

    @Test
    void parseInput_missingIndex_invalidCommand() {
        Command command = ViewSingleEventParser.parseInput("viewEvent /i");
        assertInstanceOf(InvalidCommand.class, command);
    }
}
