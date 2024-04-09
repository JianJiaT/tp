package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.frontend.UI;

import java.time.LocalDate;

public class SummariseCommand extends Command {
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String category;
    private final int beginIndex;
    private final int endIndex;

    public SummariseCommand(String name, LocalDate startDate, LocalDate endDate, String category,
                            int beginIndex, int endIndex) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    /**
     * Summarises expenses and prints out the summary
     * @param dashboard The dashboard that contains the expense manager from which expenses will be summarised
     */
    @Override
    public void execute(Dashboard dashboard) {
        double summary = dashboard
                .getExpenseManager()
                .summariseExpenses(name, startDate, endDate, category, beginIndex, endIndex);
        UI.prettyPrint("The total is $" + summary);
    }
}
