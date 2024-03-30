package brokeculator.event;

import brokeculator.expense.Expense;

public class EventExpenseDataIntegrityManager {
    public static void removeConnectionFromOwningEvent(Expense expense) {
        Event owningEvent = expense.getOwningEvent();
        if (owningEvent == null) {
            return;
        }
        owningEvent.removeExpense(expense);
        expense.removeOwningEvent();
    }

    public static void buildConnection(Expense expense, Event event) {
        removeConnectionFromOwningEvent(expense);
        event.addExpense(expense);
        expense.setOwningEvent(event);
    }
}
