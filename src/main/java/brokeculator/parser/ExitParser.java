package brokeculator.parser;

import brokeculator.command.Command;
import brokeculator.command.ExitCommand;
import brokeculator.command.InvalidCommand;

public class ExitParser {
    private static final String EXIT_COMMAND = "exit";
    private static final String INVALID_EXIT_COMMAND = "Invalid command. Please enter 'exit' to exit the program.";
    public static Command parseInput(String userInput) {

        if (userInput.equalsIgnoreCase(EXIT_COMMAND)) {
            return new ExitCommand();
        } else {
            return new InvalidCommand(INVALID_EXIT_COMMAND);
        }
    }
}
