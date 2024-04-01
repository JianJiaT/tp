package brokeculator.parser;

import brokeculator.command.AddEventCommand;
import brokeculator.command.Command;
import brokeculator.command.InvalidCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class EventParserTest {
    @Test
    void parseInput_validInput_addEventCommand() {
        Command command = EventParser.parseInput("evemt /n event1 /d description1");
        assertInstanceOf(AddEventCommand.class, command);
    }

    @Test
    void parseInput_missingName_invalidCommand() {
        Command command = EventParser.parseInput("event /d description1");
        assertInstanceOf(InvalidCommand.class, command);
    }

    @Test
    void parseInput_missingDescription_invalidCommand() {
        Command command = EventParser.parseInput("event /n event1");
        assertInstanceOf(InvalidCommand.class, command);
    }

    @Test
    void parseInput_emptyName_invalidCommand() {
        Command command = EventParser.parseInput("event /n  /d description1");
        assertInstanceOf(InvalidCommand.class, command);
    }
}
