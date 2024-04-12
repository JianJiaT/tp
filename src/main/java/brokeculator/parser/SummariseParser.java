package brokeculator.parser;

import brokeculator.command.Command;
import brokeculator.command.InvalidCommand;
import brokeculator.command.SummariseCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class SummariseParser {
    private static final String[] SUMMARISE_COMMAND_OPTIONS =
        {" /n ", " /start ", " /end ", " /c ", " /from ", " /to "};
    private static final int NAME_INDEX = 0;
    private static final int START_DATE_INDEX = 1;
    private static final int END_DATE_INDEX = 2;
    private static final int CATEGORY_INDEX = 3;
    private static final int FROM_INDEX = 4;
    private static final int TO_INDEX = 5;

    /**
     * Returns a SummariseCommand containing instructions on how to summarise expenses if user input is valid, otherwise
     * returns an InvalidCommand with relevant error message
     * @param userInput User input
     * @return SummariseCommand if user input is valid, InvalidCommand otherwise
     */
    public static Command parseInput(String userInput) {
        String[] userInputAsArray = userInput.trim().split("\\s+");
        String nameToSummariseBy = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        String categoryToSummariseBy = null;
        int beginIndex = 0;
        int endIndex = -1;

        String currKeywordToCheck = SUMMARISE_COMMAND_OPTIONS[NAME_INDEX];
        if (userInput.contains(currKeywordToCheck)) {
            nameToSummariseBy = getOptionField(userInputAsArray, currKeywordToCheck);
            nameToSummariseBy = nameToSummariseBy.isBlank() ? null : nameToSummariseBy;
        }

        currKeywordToCheck = SUMMARISE_COMMAND_OPTIONS[START_DATE_INDEX];
        if (userInput.contains(currKeywordToCheck)) {
            String startDateString = getOptionField(userInputAsArray, currKeywordToCheck);
            try {
                startDate = startDateString.isBlank() ? null : DateParser.parseDate(startDateString);
            } catch (DateTimeParseException e) {
                if (e.getMessage().contains("at index")) {
                    return new InvalidCommand("Invalid start date format. Please enter date in the format DD-MM-YYYY");
                } else {
                    return new InvalidCommand("Invalid start date. The date you entered does not exist");
                }
            }
        }

        currKeywordToCheck = SUMMARISE_COMMAND_OPTIONS[END_DATE_INDEX];
        if (userInput.contains(currKeywordToCheck)) {
            String endDateString = getOptionField(userInputAsArray, currKeywordToCheck);
            try {
                endDate = endDateString.isBlank() ? null : DateParser.parseDate(endDateString);
            } catch (DateTimeParseException e) {
                if (e.getMessage().contains("at index")) {
                    return new InvalidCommand("Invalid start date format. Please enter date in the format DD-MM-YYYY");
                } else {
                    return new InvalidCommand("Invalid start date. The date you entered does not exist");
                }
            }
        }

        currKeywordToCheck = SUMMARISE_COMMAND_OPTIONS[CATEGORY_INDEX];
        if (userInput.contains(currKeywordToCheck)) {
            categoryToSummariseBy = getOptionField(userInputAsArray, currKeywordToCheck);
            categoryToSummariseBy = categoryToSummariseBy.isBlank() ? null : categoryToSummariseBy.toUpperCase();
        }

        currKeywordToCheck = SUMMARISE_COMMAND_OPTIONS[FROM_INDEX];
        if (userInput.contains(currKeywordToCheck)) {
            try {
                beginIndex = getIndex(userInputAsArray, currKeywordToCheck);
            } catch (NumberFormatException e) {
                return new InvalidCommand("Invalid start index");
            }
            if (beginIndex < 0) {
                return new InvalidCommand("Start index must be one or greater");
            }
        }
        assert beginIndex >= 0 : "beginIndex should be 0 or greater";

        currKeywordToCheck = SUMMARISE_COMMAND_OPTIONS[TO_INDEX];
        if (userInput.contains(currKeywordToCheck)) {
            try {
                endIndex = getIndex(userInputAsArray, currKeywordToCheck);
            } catch (NumberFormatException e) {
                return new InvalidCommand("Invalid end index");
            }
            if (endIndex < 0) {
                return new InvalidCommand("End index must be one or greater");
            }
        }
        assert endIndex >= -1 : "endIndex should be -1 or greater";

        if (endIndex != -1 && beginIndex > endIndex) {
            return new InvalidCommand("Start index cannot be greater than end index");
        }

        return new SummariseCommand(nameToSummariseBy, startDate, endDate, categoryToSummariseBy,
                beginIndex, endIndex);
    }

    /**
     * Extracts a condition for summarising from user input as denoted by a keyword
     * @param userInputArray User input as an array of Strings
     * @param option The keyword that denotes which information to extract
     * @return A condition for summarising expenses by
     */
    private static String getOptionField(String[] userInputArray, String option) {
        StringBuilder optionField = new StringBuilder();
        boolean startAppending = false;
        for (String s : userInputArray) {
            if (s.equals(option.trim())) {
                startAppending = true;
                continue;
            }
            if (isWordOption(s) && startAppending) {
                return optionField.toString().trim();
            }
            if (startAppending) {
                optionField.append(s).append(" ");
            }
        }
        return optionField.toString().trim();
    }

    /**
     * Checks if a word is a keyword
     * @param word The word to check
     * @return True if the word is a keyword, false otherwise
     */
    private static boolean isWordOption(String word) {
        for (String option : SUMMARISE_COMMAND_OPTIONS) {
            if (word.equals(option.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the start index or end index depending on the keyword passed in
     * @param userInputArray User input as an array of Strings
     * @param currKeyword The keyword that determines whether the index is a start index or end index
     * @return Start index or end index
     */
    private static int getIndex(String[] userInputArray, String currKeyword) {
        int index;
        try {
            String indexAsString = getOptionField(userInputArray, currKeyword);
            indexAsString = indexAsString.isBlank() ? "0" : indexAsString;
            index = Integer.parseInt(indexAsString.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
        return index;
    }
}
