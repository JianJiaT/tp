package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.expense.Expense;
import brokeculator.frontend.UI;

public class AddExpenseFromFileCommand extends Command{
    private final String fileString;

    public AddExpenseFromFileCommand(String fileString) {
        this.fileString = fileString;
    }

    /**
     * Creates an expense from the file string
     * Adds the expense to the dashboard's expense manager
     * @param dashboard the dashboard that contains the expense manager
     */
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        try {
            Expense expense = Expense.getExpenseFromFile(this.fileString);
            dashboard.getExpenseManager().add(expense);
        } catch (Exception e) {
            ui.println(e.toString());
        }
    }
}
