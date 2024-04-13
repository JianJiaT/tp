package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.expense.Expense;
import brokeculator.frontend.UI;

public class AddCommand extends Command{
    private Expense expenseToAdd;

    public AddCommand(Expense expenseToAdd) {
        this.expenseToAdd = expenseToAdd;
    }

    /**
     * Adds an expense to be tracked and prints out an acknowledgement.
     *
     * @param dashboard The dashboard that contains the expense manager that will track the new expense.
     */
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        try {
            String feedback = dashboard.getExpenseManager().add(expenseToAdd);
            ui.prettyPrint(feedback);
        } catch (Exception e) {
            ui.prettyPrint("There was an error adding the expense. Please try again.");
        }
    }
}
