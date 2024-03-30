package brokeculator.parser;

import brokeculator.command.Command;
import brokeculator.command.ListEventsCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ListEventsParserTest {
    @Test
    void parseInput_someInputs_listEventsCommand() {
        Command command = ListEventsParser.parseInput("listEvents nonsense");
        assertInstanceOf(ListEventsCommand.class, command);
    }

    @Test
    void parseInput_noInputs_listEventsCommand() {
        Command command = ListEventsParser.parseInput("");
        assertInstanceOf(ListEventsCommand.class, command);
    }

    @Test
    void parseInput_nullInput_listEventsCommand() {
        Command command = ListEventsParser.parseInput(null);
        assertInstanceOf(ListEventsCommand.class, command);
    }
}
