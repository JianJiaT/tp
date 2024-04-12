package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.enumerators.Category;
import brokeculator.frontend.UI;

import java.util.ArrayList;

public class CategoryCommand extends Command {
    public static final String ADD_SUBCOMMAND = "add";
    public static final String DELETE_SUBCOMMAND = "delete";
    public static final String LIST_SUBCOMMAND = "list";
    private String subcommand;
    private String value;
    /**
     * Creates a new CategoryCommand with the specified subcommand, this constructor is for the list subcommand
     * @param subcommand the subcommand to execute
     */
    public CategoryCommand(String subcommand) {
        this.subcommand = subcommand;
    }
    /**
     * Creates a new CategoryCommand with the specified subcommand and category string,
     * this constructor is for the add and delete subcommands
     * @param subcommand the subcommand to execute
     * @param categoryString the category string to execute the subcommand on
     */
    public CategoryCommand(String subcommand, String categoryString) {
        this.subcommand = subcommand;
        this.value = categoryString;
    }
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        switch (subcommand) {
        case ADD_SUBCOMMAND:
            addCategory(value.toUpperCase(), ui);
            break;
        case DELETE_SUBCOMMAND:
            deleteCategory(value.toUpperCase(), ui);
            break;
        case LIST_SUBCOMMAND:
            listCategories(ui);
            break;
        default:
            break;
        }
    }
    private void addCategory(String value, UI ui) {
        String feedback = Category.addCategory(value);
        ui.prettyPrint(feedback);
    }
    private void deleteCategory(String value, UI ui) {
        String feedback = Category.removeCategory(value);
        ui.prettyPrint(feedback);
    }
    private void listCategories(UI ui) {
        ArrayList<String> categoryList = Category.getCategoryList();
        if (categoryList.isEmpty()) {
            ui.prettyPrint("No categories found");
            return;
        }
        String categoryListSting = Category.getCategoryListString();
        ui.prettyPrint("Categories:" + System.lineSeparator() + categoryListSting);
    }
}
