package brokeculator.enumerators;

import brokeculator.dashboard.Dashboard;
import brokeculator.event.EventExpenseDataIntegrityManager;
import brokeculator.event.EventManager;
import brokeculator.expense.ExpenseManager;
import brokeculator.frontend.UI;
import brokeculator.storage.FileManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {
    Dashboard dashboard;
    CategoryTest() {
        UI ui = new UI();
        ExpenseManager expenseManager = new ExpenseManager();
        FileManager fileManager = new FileManager(ui);
        EventManager eventManager = EventManager.getInstance();
        EventExpenseDataIntegrityManager dataIntegrityManager
                = new EventExpenseDataIntegrityManager(eventManager, expenseManager);
        this.dashboard = new Dashboard(expenseManager, fileManager, eventManager, dataIntegrityManager);
        Category.setDashboard(dashboard);
    }
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
        String categoryString = "test1test2test3";
        Category.addCategory(categoryString);
        String feedback = Category.removeCategory(categoryString);
        assertEquals("Category removed: " + categoryString.toUpperCase(), feedback);
    }
}
