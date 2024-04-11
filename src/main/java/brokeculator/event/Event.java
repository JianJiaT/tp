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

    private String eventName;
    private String eventDescription;
    private ArrayList<Expense> expenses;

    public Event(String eventName, String eventDescription) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.expenses = new ArrayList<>();
    }

    public boolean containsExpense(Expense expense) {
        return expenses.contains(expense);
    }
    public void addExpense(Expense expense) {
        if (containsExpense(expense)) {
            return;
        }
        expenses.add(expense);
    }
    public boolean hasExpenses() {
        return expenses.size() > 0;
    }
    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }
    public int getExpenseCount() {
        return expenses.size();
    }

    @Override
    public String toString() {
        return eventName + " (" + eventDescription + ")";
    }

    public String listExpenses() {
        StringBuilder sb = new StringBuilder();
        for (Expense expense : expenses) {
            sb.append(expense).append(System.lineSeparator());
        }
        return sb.toString();
    }


    @Override
    public String getStringRepresentation() {
        return NAME_KEYWORD.keywordMarker + eventName + DESCRIPTION_KEYWORD.keywordMarker + eventDescription;
    }

    public static Event getEventFromFile(String stringRepresentation) throws Exception {
        String[] eventDetails = OrderParser.parseOrder(stringRepresentation, Event.SAVING_KEYWORDS);
        return new Event(eventDetails[0], eventDetails[1]);
    }
}
