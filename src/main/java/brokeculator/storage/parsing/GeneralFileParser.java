package brokeculator.storage.parsing;

import brokeculator.command.AddExpenseFromFileCommand;
import brokeculator.command.Command;
import brokeculator.command.InvalidCommand;
import brokeculator.command.AddCategoryFromFileCommand;
import brokeculator.command.AddEventFromFileCommand;

public class GeneralFileParser {
    
    /**
     * Parses the file input to identify the type of saveable and returns the corresponding command
     * @param fileString File input
     * @return InvalidCommand if the input is invalid, AddExpenseFromFileCommand or AddCategoryFromFileCommand otherwise
     */
    public static Command getCommandFromFileInput(String fileString) {

        SaveableType saveableType = FileKeyword.getSaveableType(fileString);
        if (saveableType == null) {
            return new InvalidCommand("Saved file isn't recognized. Please check the file format.");
        }
        String fileStringWithoutKeyword = FileKeyword.removeKeyword(fileString);
        switch (saveableType) {
        case EXPENSE:
            return new AddExpenseFromFileCommand(fileStringWithoutKeyword);
        case CATEGORY:
            return new AddCategoryFromFileCommand(fileStringWithoutKeyword);
        case EVENT:
            return new AddEventFromFileCommand(fileStringWithoutKeyword);
        default:
            return new InvalidCommand("Saved file isn't recognized. Please check the file format.");
        }
    }
}
