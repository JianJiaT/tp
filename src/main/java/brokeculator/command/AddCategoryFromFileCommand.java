package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.enumerators.Category;
import brokeculator.frontend.UI;

public class AddCategoryFromFileCommand extends Command {
    private final String fileString;
    public AddCategoryFromFileCommand(String fileString) {
        this.fileString = fileString;
    }
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        String category = fileString.toUpperCase();
        Category.addCategory(category);
    }
}
