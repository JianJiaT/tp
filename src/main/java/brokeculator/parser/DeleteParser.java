package brokeculator.parser;

import brokeculator.command.Command;
import brokeculator.command.DeleteCommand;
import brokeculator.command.InvalidCommand;
import brokeculator.errors.ErrorMessages;
import brokeculator.enumerators.CommandErrorMessages;

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
        if (userInputAsArray.length != 3 || !userInputAsArray[1].trim().equals("/i")) {
            return new InvalidCommand(CommandErrorMessages.INVALID_DELETE_COMMAND.getString());
        }
        try {
            indexToDelete = Integer.parseInt(userInputAsArray[2]);
            if (indexToDelete <= 0) {
                return new InvalidCommand("Delete index must be one or greater");
            }
        } catch (NumberFormatException e) {
            return new InvalidCommand(ErrorMessages.INVALID_INDEX);
        }
        return new DeleteCommand(indexToDelete);
    }
}
