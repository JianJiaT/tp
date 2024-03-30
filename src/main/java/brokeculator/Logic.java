package brokeculator;
import brokeculator.dashboard.Dashboard;
import brokeculator.enumerators.Category;
import brokeculator.exceptions.BrokeculatorException;
import brokeculator.frontend.UI;
import brokeculator.command.Command;
import brokeculator.storage.parsing.GeneralFileParser;
import brokeculator.storage.parsing.SaveableType;
import brokeculator.parser.GeneralInputParser;


//@@author yeozhishen
public class Logic {
    private final Dashboard dashboard;
    public Logic(Dashboard dashboard) {
        this.dashboard = dashboard;
    }
    public void run() {
        loadFiles();
        saveFiles();
        UI.greetUser();
        while (true) {
            try {
                String userInput = UI.getUserInput();
                assert userInput != null;
                Command command = GeneralInputParser.getCommandFromUserInput(userInput);
                assert command != null : "command should not be null";
                command.execute(dashboard);
                saveFiles();
            } catch (BrokeculatorException b) {
                UI.prettyPrint("Brokeculator error occurred. " + b.getMessage());
            } catch (Exception e) {
                UI.prettyPrint("Exception caught in main loop. " + e.getMessage());
            }
        }
    }

    private void loadFiles() {
        loadFile(SaveableType.CATEGORY);
        loadFile(SaveableType.EXPENSE);
        loadFile(SaveableType.EVENT);
        loadFile(SaveableType.CONNECTION);
    }
    private void loadFile(SaveableType saveableType) {
        boolean isFileErrorFree = dashboard.getFileManager().openFile(saveableType);
        if (!isFileErrorFree) {
            UI.println("continuing without file");
            return;
        }
        while (dashboard.getFileManager().hasNextLine()) {
            String line = dashboard.getFileManager().readNextLine();
            Command loadCommand = GeneralFileParser.getCommandFromFileInput(line);
            loadCommand.execute(dashboard);
        }
    }
    private void saveFiles() {
        saveExpensesToFile();
        saveCategoriesToFile();
        saveEventsToFile();
        saveConnectionsToFile();
    }
    private void saveExpensesToFile() {
        try {
            String expenseListToSave = dashboard.getExpenseManager().getExpensesStringRepresentation();
            dashboard.getFileManager().saveExpenses(expenseListToSave);
        } catch (Exception e) {
            UI.prettyPrint("file save error occurred" + e.getMessage());
        }
    }
    private void saveCategoriesToFile() {
        try {
            String categoryListToSave = Category.getCategoriesStringRepresentation();
            dashboard.getFileManager().saveCategories(categoryListToSave);
        } catch (Exception e) {
            UI.prettyPrint("file save error occurred" + e.getMessage());
        }
    }
    private void saveEventsToFile() {
        try {
            String eventListToSave = dashboard.getEventManager().getEventsStringRepresentation();
            dashboard.getFileManager().saveEvents(eventListToSave);
        } catch (Exception e) {
            UI.prettyPrint("file save error occurred" + e.getMessage());
        }
    }
    private void saveConnectionsToFile() {
        try {
            String connectionListToSave = dashboard.getDataIntegrityManager().getConnectionsStringRepresentation();
            dashboard.getFileManager().saveConnections(connectionListToSave);
        } catch (Exception e) {
            UI.prettyPrint("file save error occurred" + e.getMessage());
        }
    }

}
