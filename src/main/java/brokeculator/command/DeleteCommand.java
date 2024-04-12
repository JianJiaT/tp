package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.event.EventExpenseDataIntegrityManager;
import brokeculator.expense.Expense;
import brokeculator.frontend.UI;

public class DeleteCommand extends Command {
    private int indexToDelete;

    public DeleteCommand(int indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

    /**
     * Deletes an expense and prints out an acknowledgement
     * @param dashboard The dashboard that contains the expense manager from which an expense will be deleted
     */
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        boolean isValidExpenseIndex = dashboard.getExpenseManager().isExpenseIndexValid(indexToDelete);
        if (!isValidExpenseIndex) {
            ui.prettyPrint("Invalid expense index provided");
            return;
        }
        Expense expense = dashboard.getExpenseManager().getExpense(indexToDelete);
        EventExpenseDataIntegrityManager.removeConnectionFromOwningEvent(expense);
        dashboard.getExpenseManager().delete(indexToDelete);
        ui.prettyPrint("Deleted expense at index " + indexToDelete);
    }
}
