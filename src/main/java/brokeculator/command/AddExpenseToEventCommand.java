package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.errors.ErrorMessages;
import brokeculator.event.Event;
import brokeculator.event.EventExpenseDataIntegrityManager;
import brokeculator.expense.Expense;
import brokeculator.frontend.UI;

public class AddExpenseToEventCommand extends Command {

    private final int expenseIdx;
    private final int eventIdx;

    public AddExpenseToEventCommand(int expenseIdx, int eventIdx) {
        this.expenseIdx = expenseIdx;
        this.eventIdx = eventIdx;
    }
    
    /**
     * Creates a connection between an expense and an event represented by their indices
     * 
     * If the expense index or event index is invalid, prints an error message and returns
     * If the expense is already owned by the event, prints a message and returns
     * If the expense was previously owned by another event, prints a message to inform the user
     * Removes the expense from the previous event if it was owned by one, and adds it to the new event
     * 
     * @param dashboard the dashboard that contains the data integrity manager that will build the connection
     */
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        boolean isValidExpenseIdx = dashboard.getExpenseManager().isExpenseIndexValid(expenseIdx);
        boolean isValidEventIdx = dashboard.getEventManager().isEventIdxValid(eventIdx);
        if (!isValidExpenseIdx || !isValidEventIdx) {
            ui.prettyPrint(ErrorMessages.INVALID_INDEX);
            return;
        }

        Expense expense = dashboard.getExpenseManager().getExpense(expenseIdx) ;       
        Event newOwningEvent = dashboard.getEventManager().getEvent(eventIdx);        

        assert newOwningEvent != null && expense != null : "Event or Expense is null";

        boolean isExpensedOwnedByEvent = newOwningEvent.containsExpense(expense);
        if (isExpensedOwnedByEvent) {
            ui.prettyPrint("Expense already belongs to the event");
            return;
        }

        Event originalOwningEvent = expense.getOwningEvent();
        if (originalOwningEvent != null) {
            ui.prettyPrint("Expense belonged to another event. Moving it to the new event.");
        }
        EventExpenseDataIntegrityManager.buildConnection(expense, newOwningEvent);
        ui.prettyPrint("Expense added to event successfully");
    }
}
