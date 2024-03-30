package brokeculator.dashboard;

import brokeculator.event.EventExpenseDataIntegrityManager;
import brokeculator.event.EventManager;
import brokeculator.expense.ExpenseManager;
import brokeculator.storage.FileManager;

public class Dashboard {

    private final ExpenseManager expenseManager;
    private final FileManager fileManager;
    private final EventManager eventManager;
    private final EventExpenseDataIntegrityManager dataIntegrityManager;

    public Dashboard(ExpenseManager expenseManager, FileManager fileManager,
                     EventManager eventManager, EventExpenseDataIntegrityManager dataIntegrityManager) {
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

    public EventExpenseDataIntegrityManager getDataIntegrityManager() {
        return dataIntegrityManager;
    }
}
