package brokeculator.event;

import brokeculator.expense.Expense;

public class EventExpenseDataIntegrityManager {

    /**
     * Removes the connection between the expense and the event it belongs to
     * If the expense does not belong to any event, nothing happens
     * @param expense Expense to be removed from the event
     */
    public static void removeConnectionFromOwningEvent(Expense expense) {
        Event owningEvent = expense.getOwningEvent();
        if (owningEvent == null) {
            return;
        }
        owningEvent.removeExpense(expense);
        expense.removeOwningEvent();
    }

    /**
     * Builds a connection between the expense and the event
     * If the expense already belongs to an event, it is removed from that event
     * @param expense Expense to be connected to the event
     * @param event Event to which the expense is to be connected
     */
    public static void buildConnection(Expense expense, Event event) {
        removeConnectionFromOwningEvent(expense);
        event.addExpense(expense);
        expense.setOwningEvent(event);
    }
}
