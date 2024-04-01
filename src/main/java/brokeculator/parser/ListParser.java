package brokeculator.parser;

import brokeculator.command.Command;
import brokeculator.command.InvalidCommand;
import brokeculator.command.ListCommand;

public class ListParser {
    private static final int LIST_ALL_EXPENSES = -1;

    /**
     * Returns a ListCommand specifying which expenses to list if user input is valid, otherwise
     * returns an InvalidCommand with relevant error message
     * @param userInput User input
     * @return ListCommand if user input is valid, InvalidCommand otherwise
     */
    public static Command parseInput(String userInput) {
        String[] userInputAsArray = userInput.split(" ");
        int amountToList = 0;
        if (userInputAsArray.length == 1) {
            amountToList = LIST_ALL_EXPENSES;
        } else {
            try {
                amountToList = Integer.parseInt(userInputAsArray[1]);
            } catch (NumberFormatException e) {
                return new InvalidCommand("Invalid integer for list command.");
            }
        }

        return new ListCommand(amountToList);
    }
}
