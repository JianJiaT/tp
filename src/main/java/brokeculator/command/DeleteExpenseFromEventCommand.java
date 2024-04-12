package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.event.Event;
import brokeculator.event.EventExpenseDataIntegrityManager;
import brokeculator.expense.Expense;
import brokeculator.frontend.UI;

public class DeleteExpenseFromEventCommand extends Command {

    private int expenseIdx;

    public DeleteExpenseFromEventCommand(int expenseIdx) {
        this.expenseIdx = expenseIdx;
    }

    @Override
    public void execute(Dashboard dashboard, UI ui) {
        boolean isValidExpenseIdx = dashboard.getExpenseManager().isExpenseIndexValid(expenseIdx);
        if (!isValidExpenseIdx) {
            ui.prettyPrint("Indexes provided are invalid");
            return;
        }
        Expense expense = dashboard.getExpenseManager().getExpense(expenseIdx);
        Event owningEvent = expense.getOwningEvent();

        if (owningEvent == null) {
            ui.prettyPrint("Expense does not belong to any event");
            return;
        }

        EventExpenseDataIntegrityManager.removeConnectionFromOwningEvent(expense);
        ui.prettyPrint("Expense removed from event successfully");
    }
}
