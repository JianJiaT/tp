package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.event.EventExpenseManager;
import brokeculator.expense.Expense;
import brokeculator.frontend.UI;

public class DeleteExpenseFromEventCommand extends Command {

    private int expenseIdx;

    public DeleteExpenseFromEventCommand(int expenseIdx) {
        this.expenseIdx = expenseIdx;
    }

    /**
     * Removes the connection between an expense and an event
     * 
     * If the expense index is invalid, an error message will be printed
     * If the expense does not belong to any event, an error message will be printed
     * @param dashboard the dashboard that contains the data integrity manager that will remove the connection
     */
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        boolean isValidExpenseIdx = dashboard.getExpenseManager().isExpenseIndexValid(expenseIdx);
        if (!isValidExpenseIdx) {
            ui.prettyPrint("Indexes provided are invalid");
            return;
        }
        Expense expense = dashboard.getExpenseManager().getExpense(expenseIdx);
        if (!expense.hasOwningEvent()) {
            ui.prettyPrint("Expense does not belong to any event");
            return;
        }
        EventExpenseManager.removeConnectionFromOwningEvent(expense);
        ui.prettyPrint("Expense removed from event successfully");
    }
}
