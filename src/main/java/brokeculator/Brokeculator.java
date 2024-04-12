package brokeculator;
import brokeculator.dashboard.Dashboard;
import brokeculator.event.EventExpenseDataIntegrityManager;
import brokeculator.event.EventManager;
import brokeculator.enumerators.Category;
import brokeculator.frontend.UI;
import brokeculator.storage.FileManager;
import brokeculator.expense.ExpenseManager;

public class Brokeculator {
    public static void main(String[] args) {
        UI ui = new UI();
        ExpenseManager expenseManager = new ExpenseManager();
        FileManager fileManager = new FileManager(ui);
        EventManager eventManager = EventManager.getInstance();
        EventExpenseDataIntegrityManager dataIntegrityManager
                = new EventExpenseDataIntegrityManager(eventManager, expenseManager);
        Dashboard dashboard
                = new Dashboard(expenseManager, fileManager, eventManager, dataIntegrityManager);
        Logic driverLogic = new Logic(dashboard, ui);
        Category.setDashboard(dashboard);
        driverLogic.run();
    }
}
