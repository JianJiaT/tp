package brokeculator.parser;

import brokeculator.command.Command;
import brokeculator.command.DeleteEventCommand;
import brokeculator.command.InvalidCommand;
import brokeculator.errors.ErrorMessages;
import brokeculator.parser.util.Keyword;
import brokeculator.parser.util.OrderParser;

public class DeleteEventParser {
    private static final Keyword[] KEYWORDS = {
        new Keyword(" /i ", "Event index", false)
    };

    /**
     * Parses the user input to identify the event index
     * @param userInput User input
     * @return InvalidCommand if the input is invalid, DeleteEventCommand otherwise
     */
    public static Command parseInput(String userInput) {
        String[] userInputs;
        try {
            userInputs = OrderParser.parseOrder(userInput, KEYWORDS);
        } catch (Exception e) {
            return new InvalidCommand(e.getMessage());
        }

        String idxString = userInputs[0];
        int idx;
        try {
            idx = Integer.parseInt(idxString);
        } catch (NumberFormatException e) {
            return new InvalidCommand(ErrorMessages.INVALID_INDEX);
        }

        return new DeleteEventCommand(idx);
    }
}
