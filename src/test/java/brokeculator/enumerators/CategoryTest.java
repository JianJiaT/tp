package brokeculator.enumerators;

import brokeculator.dashboard.Dashboard;
import brokeculator.event.EventManager;
import brokeculator.expense.Expense;
import brokeculator.expense.ExpenseManager;
import brokeculator.storage.FileManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {
    @Test
    public void addCategory_singleWordString_expectAddString() {
        String categoryString = "test1";
        String feedback = Category.addCategory(categoryString);
        assertEquals("Category added: " + categoryString.toUpperCase(), feedback);
    }
    @Test
    public void addCategory_existingCategory_expectExistingString() {
        String categoryString1 = "test1 test2 test3";
        String categoryString2 = "test1 test2 test3";
        Category.addCategory(categoryString1);
        String feedback = Category.addCategory(categoryString2);
        assertEquals("Category already exists", feedback);
    }
    @Test
    public void addCategory_multipleWordString_expectAddString() {
        String categoryString = "test1 test2 test3";
        String feedback = Category.addCategory(categoryString);
        assertEquals("Category added: " + categoryString.toUpperCase(), feedback);
    }
    @Test
    public void removeCategory_singleWordStringNoExpenses_expectRemoveString() {
        ExpenseManager expenseManager = new ExpenseManager();
        EventManager eventManager = EventManager.getInstance();
        FileManager fileManager = new FileManager();
        Category.setDashboard(new Dashboard(expenseManager, fileManager, eventManager));
        String categoryString = "test1";
        Category.addCategory(categoryString);
        String feedback = Category.removeCategory(categoryString);
        assertEquals("Category removed: " + categoryString.toUpperCase(), feedback);
    }
    @Test
    public void removeCategory_multipleWordStringNoExpenses_expectRemoveString() {
        ExpenseManager expenseManager = new ExpenseManager();
        EventManager eventManager = EventManager.getInstance();
        FileManager fileManager = new FileManager();
        Category.setDashboard(new Dashboard(expenseManager, fileManager, eventManager));
        String categoryString = "test1 test2 test3";
        Category.addCategory(categoryString);
        String feedback = Category.removeCategory(categoryString);
        assertEquals("Category removed: " + categoryString.toUpperCase(), feedback);
    }
    @Test
    public void removeCategory_multipleWordStringExistingExpenses_expectFailureString() {
        ExpenseManager expenseManager = new ExpenseManager();
        EventManager eventManager = EventManager.getInstance();
        FileManager fileManager = new FileManager();
        Category.setDashboard(new Dashboard(expenseManager, fileManager, eventManager));
        String categoryString = "test1 test2 test3";
        Category.addCategory(categoryString);
        expenseManager.add(new Expense("test", 1.0, "date", "test1 test2 test3"));
        String feedback = Category.removeCategory(categoryString);
        assertEquals("Cannot remove category that is in use", feedback);
    }
}
