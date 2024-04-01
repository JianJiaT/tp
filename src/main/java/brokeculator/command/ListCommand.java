package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.frontend.UI;

public class ListCommand extends Command {
    private int amountToList;

    public ListCommand(int amountToList) {
        this.amountToList = amountToList;
    }

    @Override
    public void execute(Dashboard dashboard) {
        boolean isListEmpty = dashboard.getExpenseManager().getNumberOfExpensesTracked() == 0;
        if (isListEmpty) {
            UI.prettyPrint("You have no added expenses!");
            return;
        }
        String expensesListString = dashboard.getExpenseManager().getExpensesListString(0, amountToList);
        UI.prettyPrint(expensesListString);
    }
}
