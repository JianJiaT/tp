package brokeculator.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import brokeculator.command.AddExpenseToEventCommand;
import brokeculator.command.Command;
import brokeculator.command.InvalidCommand;

import org.junit.jupiter.api.Test;

class AddExpenseToEventParserTest {

    @Test
    void parseInput_validInput_addExpenseToEventCommand() {
        Command command = AddExpenseToEventParser.parseInput("addexev /exi 1 /evi 2");
        assertInstanceOf(AddExpenseToEventCommand.class, command);
    }

    @Test
    void parseInput_missingIndex_invalidCommand() {
        Command command = AddExpenseToEventParser.parseInput("addexev /exi 1 /evi");
        assertInstanceOf(InvalidCommand.class, command);
    }

    @Test
    void parseInput_nonIntegerIndex_invalidCommand() {
        Command command = AddExpenseToEventParser.parseInput("addexev /exi 1 /evi a");
        assertInstanceOf(InvalidCommand.class, command);
    }

    @Test 
    void parseInput_missingKeyword_invalidCommand() {
        Command command = AddExpenseToEventParser.parseInput("addexev /exi 1");
        assertInstanceOf(InvalidCommand.class, command);
    }

    @Test
    void parseInput_tooManyArguments_invalidCommand() {
        Command command = AddExpenseToEventParser.parseInput("addexev /exi 1 /evi 2 /exi 3");
        assertInstanceOf(InvalidCommand.class, command);
    }
}
