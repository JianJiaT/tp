package brokeculator.dashboard;

import brokeculator.event.EventExpenseManager;
import brokeculator.event.EventManager;
import brokeculator.expense.ExpenseManager;
import brokeculator.storage.FileManager;

public class Dashboard {

    private final ExpenseManager expenseManager;
    private final FileManager fileManager;
    private final EventManager eventManager;
    private final EventExpenseManager dataIntegrityManager;

    public Dashboard(ExpenseManager expenseManager, FileManager fileManager,
                     EventManager eventManager, EventExpenseManager dataIntegrityManager) {
        this.expenseManager = expenseManager;
        this.fileManager = fileManager;
        this.eventManager = eventManager;
        this.dataIntegrityManager = dataIntegrityManager;
    }

    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public EventExpenseManager getDataIntegrityManager() {
        return dataIntegrityManager;
    }
}
