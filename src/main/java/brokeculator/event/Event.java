package brokeculator.event;

import java.util.ArrayList;

import brokeculator.expense.Expense;
import brokeculator.expense.Saveable;
import brokeculator.parser.util.Keyword;
import brokeculator.parser.util.OrderParser;

public class Event implements Saveable {

    private static final Keyword NAME_KEYWORD
            = new Keyword("|__EVENT_NAME__|:", "event name", false);
    private static final Keyword DESCRIPTION_KEYWORD
            = new Keyword("|__EVENT_DESCRIPTION__|:", "event description", false);
    private static final Keyword[] SAVING_KEYWORDS = {NAME_KEYWORD, DESCRIPTION_KEYWORD};

    private final String eventName;
    private final String eventDescription;
    private ArrayList<Expense> expenses;

    public Event(String eventName, String eventDescription) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.expenses = new ArrayList<>();
    }

    /**
     * Checks if the event has a particular expense.
     * 
     * @param expense the expense to check for.
     * @return true if the event has the expense, false otherwise.
     */
    public boolean hasExpense(Expense expense) {
        return expenses.contains(expense);
    }

    /**
     * Adds an expense to the event.
     * If the event already has the expense, the expense will not be added.
     *
     * @param expense the expense to add.
     */
    public void addExpense(Expense expense) {
        if (hasExpense(expense)) {
            return;
        }
        expenses.add(expense);
    }

    /**
     * Checks if the event has any expenses.
     *
     * @return true if the event has expenses, false otherwise.
     */
    public boolean hasExpenses() {
        return expenses.size() > 0;
    }

    /**
     * Removes an expense from the event.
     * If the event does not have the expense, the expense will not be removed.
     *
     * @param expense the expense to remove.
     */
    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }

    /**
     * Returns the number of expenses in the event.
     *
     * @return the number of expenses in the event.
     */
    public int getExpenseCount() {
        return expenses.size();
    }

    @Override
    public String toString() {
        return eventName + " (" + eventDescription + ")";
    }

    /**
     * Returns the total amount of expenses in the event
     *
     * @return the total amount of expenses in the event
     */
    public double getTotalExpenses() {
        double total = 0;
        for (Expense expense : this.expenses) {
            total += expense.getAmount();
        }
        return total;
    }
    /**
     * Returns a string representation of all expenses in the event for printing.
     *
     * @return a string representation of all expenses in the event for printing.
     */
    public String listExpenses() {
        StringBuilder sb = new StringBuilder();
        for (Expense expense : expenses) {
            sb.append(expense).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Returns a string representation of the event details for saving.
     * Expense details will be processed separately.
     * 
     * @return String representation of the event details for saving.
     */
    @Override
    public String getStringRepresentation() {
        return NAME_KEYWORD.keywordMarker + eventName + DESCRIPTION_KEYWORD.keywordMarker + eventDescription;
    }

    /**
     * Returns an Event object from a string representation.
     * The string representation should be obtained from the getStringRepresentation method.
     * 
     * @param stringRepresentation the string representation of the event.
     * @return Event object created from the string representation.
     * @throws Exception if the string representation is invalid.
     */
    public static Event getEventFromFile(String stringRepresentation) throws Exception {
        String[] eventDetails = OrderParser.parseOrder(stringRepresentation, Event.SAVING_KEYWORDS);
        return new Event(eventDetails[0], eventDetails[1]);
    }
}
