package brokeculator.event;

import brokeculator.expense.Expense;
import brokeculator.expense.ExpenseManager;
import brokeculator.parser.util.Keyword;
import brokeculator.parser.util.OrderParser;
import brokeculator.storage.parsing.FileKeyword;
import brokeculator.storage.parsing.SaveableType;

import java.util.ArrayList;

public class EventExpenseDataIntegrityManager {

    private static final Keyword EXPENSE_KEYWORD = new Keyword("|__EXPENSE__|:", "expense", false);
    private static final Keyword EVENT_KEYWORD = new Keyword("|__EVENT__|:", "event", false);
    private static final Keyword[] CONNECTION_KEYWORDS = {EXPENSE_KEYWORD, EVENT_KEYWORD};

    private final EventManager eventManager;
    private final ExpenseManager expenseManager;

    public EventExpenseDataIntegrityManager(EventManager eventManager, ExpenseManager expenseManager) {
        this.eventManager = eventManager;
        this.expenseManager = expenseManager;
    }

    /**
     * Removes the connection between the expense and the event it belongs to.
     * If the expense does not belong to any event, nothing happens.
     *
     * @param expense Expense to be removed from the event.
     */
    public static void removeConnectionFromOwningEvent(Expense expense) {
        assert expense != null : "Expense cannot be null";
        Event owningEvent = expense.getOwningEvent();
        if (owningEvent == null) {
            return;
        }
        owningEvent.removeExpense(expense);
        expense.removeOwningEvent();
    }

    /**
     * Builds a connection between the expense and the event.
     * If the expense already belongs to an event, it is removed from that event.
     *
     * @param expense Expense to be connected to the event.
     * @param event Event to which the expense is to be connected.
     */
    public static void buildConnection(Expense expense, Event event) {
        assert expense != null : "Expense cannot be null";
        assert event != null : "Event cannot be null";
        removeConnectionFromOwningEvent(expense);
        event.addExpense(expense);
        expense.setOwningEvent(event);
    }

    /**
     * Returns a string representation of all connections between expenses and events.
     */
    public String getConnectionsStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Expense> expenses = expenseManager.getExpenses();
        for (Expense expense : expenses) {
            Event owningEvent = expense.getOwningEvent();
            if (owningEvent == null) {
                continue;
            }
            int expenseIndex = expenseManager.getExpenseIndex(expense);
            int eventIndex = eventManager.getEventIndex(owningEvent);
            String connectionString = EXPENSE_KEYWORD.keywordMarker + expenseIndex 
                    + EVENT_KEYWORD.keywordMarker + eventIndex;
            sb.append(FileKeyword.formatWithKeyword(SaveableType.CONNECTION, connectionString));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Loads connection between expenses and events from a string representation.
     *
     * @param connectionStringRepresentation String representation of a connection.
     * @throws Exception If an invalid connection is detected.
     */
    public void loadConnectionFromStringRepresentation(String connectionStringRepresentation) throws Exception {
        String[] connectionDetails = OrderParser.parseOrder(connectionStringRepresentation, CONNECTION_KEYWORDS);
        int expenseIndex = Integer.parseInt(connectionDetails[0]);
        int eventIndex = Integer.parseInt(connectionDetails[1]);
        Expense expense = expenseManager.getExpense(expenseIndex);
        Event event = eventManager.getEvent(eventIndex);
        if (expense == null || event == null) {
            throw new Exception("Invalid connection detected");
        }
        buildConnection(expense, event);
    }
}
