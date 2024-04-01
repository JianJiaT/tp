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
     * Adds an expense to be tracked
     * @param dashboard The dashboard that contains the expense manager that will track the new expense
     */
    @Override
    public void execute(Dashboard dashboard) {
        try {
            String feedback = dashboard.getExpenseManager().add(expenseToAdd);
            UI.prettyPrint(feedback);
        } catch (Exception e) {
            UI.println("There was an error adding the expense. Please try again.");
        }
    }
}
