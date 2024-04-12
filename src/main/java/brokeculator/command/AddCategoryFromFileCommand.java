package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.enumerators.Category;
import brokeculator.frontend.UI;

public class AddCategoryFromFileCommand extends Command {
    private final String fileString;
    public AddCategoryFromFileCommand(String fileString) {
        this.fileString = fileString;
    }
    /**
     * Adds a category to the list of categories, converting it to uppercase
     * @param dashboard the dashboard that contains the category manager
     * @param ui the user interface that will display the feedback
     */
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        String category = fileString.toUpperCase();
        Category.addCategory(category);
    }
}
