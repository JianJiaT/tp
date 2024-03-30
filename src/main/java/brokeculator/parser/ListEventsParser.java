package brokeculator.parser;

import brokeculator.command.Command;
import brokeculator.command.ListEventsCommand;

public class ListEventsParser {

    /**
     * Always returns a ListEventsCommand
     * @param userInput User input
     * @return ListEventsCommand
     */
    public static Command parseInput(String userInput) {
        return new ListEventsCommand();
    }
}
