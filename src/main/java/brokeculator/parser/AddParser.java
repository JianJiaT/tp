package brokeculator.parser;

import brokeculator.command.AddCommand;
import brokeculator.command.Command;
import brokeculator.command.InvalidCommand;
import brokeculator.enumerators.CommandErrorMessages;
import brokeculator.expense.Expense;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddParser {
    public static final String[] ADD_COMMAND_OPTIONS = {" /n ", " /d ", " /a ", " /c "};
    public static final int NAME_INDEX = 0;
    public static final int DATE_INDEX = 1;
    public static final int AMOUNT_INDEX = 2;
    public static final int CATEGORY_INDEX = 3;
    public static final String AMOUNT_PATTERN = "^\\d+(\\.\\d+)?$";

    /**
     * Returns a AddCommand specifying the expense to add if user input is valid, otherwise
     * returns an InvalidCommand with relevant error message
     * @param userInput User input
     * @return AddCommand if user input is valid, InvalidCommand otherwise
     */
    public static Command parseInput(String userInput) {
        if(!isOptionsPresent(userInput) || !isOptionsAppearOnce(userInput)) {
            return new InvalidCommand(CommandErrorMessages.INVALID_ADD_COMMAND.getString());
        }
        String[] userInputAsArray = userInput.trim().split("\\s+");
        try {
            String expenseDescription = getOptionField(userInputAsArray, ADD_COMMAND_OPTIONS[NAME_INDEX]);
            String expenseDateString = getOptionField(userInputAsArray, ADD_COMMAND_OPTIONS[DATE_INDEX]);
            String expenseAmountAsString = getOptionField(userInputAsArray, ADD_COMMAND_OPTIONS[AMOUNT_INDEX]);
            String expenseCategory = null;
            if (userInput.contains(" /c ")) {
                expenseCategory = getOptionField(userInputAsArray, ADD_COMMAND_OPTIONS[CATEGORY_INDEX]);
                expenseCategory = expenseCategory.isBlank() ? null : expenseCategory;
            }
            boolean isDesiredFieldsEmpty = isFieldsEmpty(expenseDescription, expenseDateString, expenseAmountAsString);
            boolean isAmountNumeric = isAmountNumericString(expenseAmountAsString);
            if (isDesiredFieldsEmpty || !isAmountNumeric) {
                return new InvalidCommand("Expense description, date and amount cannot be empty");
            }
            double expenseAmount = Double.parseDouble(expenseAmountAsString);
            LocalDate expenseDate = DateParser.parseDate(expenseDateString);
            Expense expenseToAdd = new Expense(expenseDescription, expenseAmount, expenseDate, expenseCategory);
            return new AddCommand(expenseToAdd);
        } catch (NumberFormatException e) {
            return new InvalidCommand("Expense amount cannot be empty or non-numeric");
        } catch (DateTimeParseException e) {
            return new InvalidCommand("Invalid date format. Please enter date in the format dd-MM-yyyy");
        }
    }

    /**
     * Checks whether any of the mandatory fields is empty
     * @param expenseDescription Expense description
     * @param expenseDate Expense date
     * @param expenseAmountAsString Expense amount
     * @return True if any of the mandatory fields is empty, false otherwise
     */
    private static boolean isFieldsEmpty(String expenseDescription, String expenseDate, String expenseAmountAsString) {
        return expenseDescription.isBlank() || expenseDate.isBlank() || expenseAmountAsString.isBlank();
    }

    /**
     * Checks whether all the mandatory fields are present
     * @param userInput User input
     * @return True if all mandatory fields are present, false otherwise
     */
    private static boolean isOptionsPresent(String userInput) {
        return userInput.contains(" /n ") && userInput.contains(" /d ") && userInput.contains(" /a ");
    }

    /**
     * Checks whether expense amount is a number
     * @param expenseAmountAsString Expense amount as a String
     * @return True if expense amount is a number, false otherwise
     */
    private static boolean isAmountNumericString(String expenseAmountAsString) {
        Pattern pattern = Pattern.compile(AMOUNT_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expenseAmountAsString);
        return matcher.find();
    }

    /**
     * Checks whether the keywords are not repeated
     * @param userInput User input
     * @return True if the keywords are not repeated, false otherwise
     */
    private static boolean isOptionsAppearOnce(String userInput) {
        for (String option : ADD_COMMAND_OPTIONS) {
            if (userInput.indexOf(option) != userInput.lastIndexOf(option)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Extracts a specification about the expense to add from user input as denoted by a keyword
     * @param userInputArray User input as an array of Strings
     * @param option The keyword that denotes which information to extract
     * @return A specification about the expense to add
     */
    private static String getOptionField(String[] userInputArray, String option) {
        StringBuilder optionField = new StringBuilder();
        boolean shouldAppend = false;
        for (String s : userInputArray) {
            if (s.equals(option.trim())) {
                shouldAppend = true;
                continue;
            }
            if (isWordOption(s) && shouldAppend) {
                return optionField.toString().trim();
            }
            if (shouldAppend) {
                optionField.append(s).append(" ");
            }
        }
        return optionField.toString().trim();
    }

    /**
     * Checks whether a word is a keyword
     * @param word The word to check
     * @return True if the word is a keyword, false otherwise
     */
    private static boolean isWordOption(String word) {
        for (String option : ADD_COMMAND_OPTIONS) {
            if (word.equals(option.trim())) {
                return true;
            }
        }
        return false;
    }
}
