package brokeculator.parser;

import brokeculator.command.Command;
import brokeculator.command.DeleteCommand;
import brokeculator.command.InvalidCommand;
import brokeculator.errors.ErrorMessages;

public class DeleteParser {

    /**
     * Returns a DeleteCommand specifying which expenses to delete if user input is valid, otherwise
     * returns an InvalidCommand with relevant error message
     * @param userInput User input
     * @return DeleteCommand if user input is valid, InvalidCommand otherwise
     */
    public static Command parseInput(String userInput) {
        String[] userInputAsArray = userInput.trim().split("\\s+");
        int indexToDelete = 0;
        if (userInputAsArray.length == 1) {
            return new InvalidCommand("Please specify an index to delete." + System.lineSeparator()
                    + "Format: delete <index>");
        }
        try {
            indexToDelete = Integer.parseInt(userInputAsArray[1]);
        } catch (NumberFormatException e) {
            return new InvalidCommand(ErrorMessages.INVALID_INDEX);
        }
        return new DeleteCommand(indexToDelete);
    }
}
