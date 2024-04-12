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
    private final UI ui;
    public Logic(Dashboard dashboard, UI ui) {
        this.dashboard = dashboard;
        this .ui = ui;
    }
    public void run() {
        loadFiles();
        saveFiles();
        ui.greetUser();
        while (true) {
            try {
                String userInput = ui.getUserInput();
                assert userInput != null;
                Command command = GeneralInputParser.getCommandFromUserInput(userInput);
                assert command != null : "command should not be null";
                command.execute(dashboard, ui);
                saveFiles();
            } catch (BrokeculatorException b) {
                ui.prettyPrint("Brokeculator error occurred. " + b.getMessage());
            } catch (Exception e) {
                ui.prettyPrint("Oops, your command is not recognized!. ");
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
            ui.println("continuing without file");
            return;
        }
        while (dashboard.getFileManager().hasNextLine()) {
            String line = dashboard.getFileManager().readNextLine();
            Command loadCommand = GeneralFileParser.getCommandFromFileInput(line);
            loadCommand.execute(dashboard, ui);
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
            ui.prettyPrint("file save error occurred" + e.getMessage());
        }
    }
    private void saveCategoriesToFile() {
        try {
            String categoryListToSave = Category.getCategoriesStringRepresentation();
            dashboard.getFileManager().saveCategories(categoryListToSave);
        } catch (Exception e) {
            ui.prettyPrint("file save error occurred" + e.getMessage());
        }
    }
    private void saveEventsToFile() {
        try {
            String eventListToSave = dashboard.getEventManager().getEventsStringRepresentation();
            dashboard.getFileManager().saveEvents(eventListToSave);
        } catch (Exception e) {
            ui.prettyPrint("file save error occurred" + e.getMessage());
        }
    }
    private void saveConnectionsToFile() {
        try {
            String connectionListToSave = dashboard.getDataIntegrityManager().getConnectionsStringRepresentation();
            dashboard.getFileManager().saveConnections(connectionListToSave);
        } catch (Exception e) {
            ui.prettyPrint("file save error occurred" + e.getMessage());
        }
    }

}
