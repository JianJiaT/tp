package brokeculator.expense;

import brokeculator.enumerators.Category;
import brokeculator.frontend.UI;
import brokeculator.storage.parsing.FileKeyword;
import brokeculator.storage.parsing.SaveableType;

import java.time.LocalDate;

import java.util.ArrayList;

public class ExpenseManager {
    private final ArrayList<Expense> expenses;

    public ExpenseManager() {
        expenses = new ArrayList<>();
    }

    public ExpenseManager(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    /**
     * Adds an expense
     * @param expense The expense to add
     * @return An acknowledgement of the expense addition
     */
    public String add(Expense expense) {
        assert expense != null : "Expense cannot be null";
        if (expense.getCategory() != null && !Category.isValidCategory(expense.getCategory())) {
            return "Category does not exist";
        }
        expenses.add(expense);
        return "Added expense: " + expense;
    }

    /**
     * Checks whether a specified index is within bounds
     * @param index The specified index
     * @return True if the index is within bounds, false otherwise
     */
    public boolean isExpenseIndexValid(int index) {
        return index >= 1 && index <= expenses.size();
    }

    /**
     * Returns the expense at the specified index
     * @param index The specified index
     * @return The expense at the specified index
     */
    public Expense getExpense(int index) {
        if (!isExpenseIndexValid(index)) {
            return null;
        }
        return expenses.get(index - 1);
    }

    /**
     * Returns the index of the specified expense
     * @param expense The specified expense
     * @return The index of the specified expense
     */
    public int getExpenseIndex(Expense expense) {
        return expenses.indexOf(expense) + 1;
    }

    /**
     * Deletes an expense
     * @param index The index of the expense to be deleted
     */
    public void delete(int index) {
        if (!isExpenseIndexValid(index)) {
            return;
        }
        expenses.remove(index - 1);
    }

    /**
     * Summarises expenses
     * @param description The description an expense requires to be summarised
     * @param startDate The start date an expense requires to be summarised
     * @param endDate The end date an expense requires to be summarised
     * @param category The category an expense requires to be summarised
     * @param beginIndex The index to start summarising from
     * @param endIndex The index to end summarising at
     * @return A summary of the expenses in the form of the sum of the expenses' amounts
     */
    public double summariseExpenses(String description, LocalDate startDate, LocalDate endDate, String category,
                                    int beginIndex, int endIndex) {
        double total = 0;
        ArrayList<Expense> expensesToSummarise = getExpensesToSummarise(description, startDate, endDate, category,
                beginIndex, endIndex);

        for (Expense expense : expensesToSummarise) {
            total += expense.getAmount();
        }

        if (expensesToSummarise.isEmpty()) {
            UI.prettyPrint("Nothing to summarise!");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < expensesToSummarise.size(); i++) {
                sb.append(i + 1).append(". ").append(expensesToSummarise.get(i)).append(System.lineSeparator());
            }
            String summarisedExpensesListString = String.valueOf(sb);
            UI.prettyPrint(summarisedExpensesListString);
        }

        return total;
    }

    private ArrayList<Expense> getExpensesToSummarise(String description, LocalDate startDate, LocalDate endDate,
                                                      String category, int beginIndex, int endIndex) {
        if (endIndex == -1 || endIndex >= expenses.size()) {
            endIndex = expenses.size() - 1;
        }
        ArrayList<Expense> expensesToSummarise = new ArrayList<>();
        boolean isDescriptionNull = (description == null);
        boolean isCategoryNull = (category == null);

        for (Expense expense : expenses.subList(beginIndex, endIndex + 1)) {
            boolean isSummarizeDescription = isDescriptionNull || expense.getDescription().contains(description);
            if (!isSummarizeDescription) {
                continue;
            }

            if (!isExpenseWithinDateRange(expense, startDate, endDate)) {
                continue;
            }

            String expenseCategory = expense.getCategory();
            boolean isCategoryEquals = expenseCategory != null && expenseCategory.equalsIgnoreCase(category);
            boolean isSummarizeCategory = isCategoryNull || isCategoryEquals;
            if (!isSummarizeCategory) {
                continue;
            }

            expensesToSummarise.add(expense);
        }

        return expensesToSummarise;
    }

    private boolean isExpenseWithinDateRange(Expense expense, LocalDate startDate, LocalDate endDate) {
        boolean isStartDateNull = (startDate == null);
        boolean isEndDateNull = (endDate == null);
        LocalDate expenseDate = expense.getDate();

        if (!isStartDateNull && !isEndDateNull) {
            return !expenseDate.isBefore(startDate) && !expenseDate.isAfter(endDate);
        } else if (!isStartDateNull) {
            return !expenseDate.isBefore(startDate);
        } else if (!isEndDateNull) {
            return !expenseDate.isAfter(endDate);
        } else {
            return true;
        }
    }

    /**
     * Lists expenses
     * @param amountToList The number of expenses to list
     * @return A list of expenses
     */
    public ArrayList<Expense> listExpenses(int amountToList) {
        if (amountToList == -1) {
            return expenses;
        } else {
            return new ArrayList<>(expenses.subList(0, Math.min(amountToList, expenses.size())));
        }
    }

    /**
     * Returns the list of all expenses
     * @return The list of all expenses
     */
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Returns a list of expenses as a String with proper formatting for printing
     * @param beginIndex The index of the first expense to include in the list
     * @param endIndex The index of the last expense to include in the list
     * @return A list of expenses as a String with proper formatting for printing
     */
    public String getExpensesListString(int beginIndex, int endIndex) {
        assert !this.expenses.isEmpty();

        int lastIdxToPrint;
        if (endIndex < 0 || endIndex > this.expenses.size()) {
            lastIdxToPrint = this.expenses.size();
        } else {
            lastIdxToPrint = endIndex;
        }

        int firstIdxToPrint = Math.max(beginIndex, 0);

        StringBuilder sb = new StringBuilder();
        for (int i = firstIdxToPrint; i < lastIdxToPrint; i++) {
            sb.append(i + 1).append(". ").append(expenses.get(i)).append(System.lineSeparator());
        }

        return sb.toString();
    }

    /**
     * Returns the list of all expenses as a String with proper formatting for saving in the data file
     * @return The list of all expenses as a String with proper formatting for saving in the data file
     */
    public String getExpensesStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (Expense expense : expenses) {
            String currentExpenseString = expense.getStringRepresentation();
            String finalExpenseString = FileKeyword.formatWithKeyword(SaveableType.EXPENSE, currentExpenseString);
            sb.append(finalExpenseString);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Returns the list of all expenses as a String with proper formatting for printing
     * @return the list of all expenses as a String with proper formatting for printing
     */
    @Override
    public String toString() {
        return getExpensesListString(0, expenses.size());
    }

    /**
     * Returns the number of expenses tracked
     * @return the number of expenses tracked
     */
    public int getNumberOfExpensesTracked() {
        return expenses.size();
    }
}
