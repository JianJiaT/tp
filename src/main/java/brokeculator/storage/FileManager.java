package brokeculator.storage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import brokeculator.frontend.UI;
import brokeculator.storage.parsing.SaveableType;

import java.io.FileWriter;
//@@author STeng618

public class FileManager {
    private static final String DEFAULT_DATA_FILE_PATH = "./data/data.txt";
    private static final String DEFAULT_CATEGORY_FILE_PATH = "./data/category.txt";
    private static final String DEFAULT_EVENT_FILE_PATH = "./data/event.txt";

    private File dataFile;
    private File categoryFile;
    private File eventFile;

    private Scanner scanner = null;
    private boolean hasFileErrors;

    public FileManager() {
        this.dataFile = new File(DEFAULT_DATA_FILE_PATH);
        this.categoryFile = new File(DEFAULT_CATEGORY_FILE_PATH);
        this.eventFile = new File(DEFAULT_EVENT_FILE_PATH);
        try {
            if (!this.dataFile.exists()) {
                createFile(this.dataFile);
            }
            if (!this.categoryFile.exists()) {
                createFile(this.categoryFile);
            }
            if (!this.eventFile.exists()) {
                createFile(this.eventFile);
            }
            assert this.dataFile.exists();
            assert this.categoryFile.exists();
            assert this.eventFile.exists();
            printDataSavedMessage(dataFile);
            printDataSavedMessage(categoryFile);
            printDataSavedMessage(eventFile);
            this.hasFileErrors = false;
        } catch (Exception e) {
            printDataLossWarning(); 
            this.hasFileErrors = true;
        }
    }

    private boolean openFile(File file) {
        if (this.hasFileErrors) {
            return false;
        }
        try {
            this.scanner = new Scanner(file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Opens a file based on the SaveableType and returns true if no errors are encountered
     * @param saveableType
     * @return true if no errors are encountered
     */
    public boolean openFile(SaveableType saveableType) {
        switch (saveableType) {
        case CATEGORY:
            return openFile(this.categoryFile);
        case EXPENSE:
            return openFile(this.dataFile);
        case EVENT:
            return openFile(this.eventFile);
        default:
            return false;
        }
    }

    private void save(String data, File file) {
        try {
            if (this.hasFileErrors) {
                return;
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            printDataLossWarning();
        }
    }
    public void saveExpenses(String data) {
        save(data, this.dataFile);
    }
    public void saveCategories(String data) {
        save(data, this.categoryFile);
    }
    public void saveEvents(String data) {
        save(data, this.eventFile);
    }

    private void printDataLossWarning() {
        UI.println("Errors! Your data will not be saved");
    }
    private void printDataSavedMessage(File file) {
        UI.println("Data file: " + file + " successfully created!");
    }

    private void createFile(File file) throws Exception {
        boolean hasDataDirectory = file.getParentFile().exists();
        boolean isDataDirectoryReady = hasDataDirectory || file.getParentFile().mkdirs();
        if (!isDataDirectoryReady) {
            throw new Exception();
        }
        assert file.getParentFile().exists();
        boolean isDataFileCreated = file.createNewFile();
        if (!isDataFileCreated) {
            throw new Exception();
        }
        assert file.exists();
    }

    public boolean hasNextLine() {
        return this.scanner.hasNext();
    }

    public String readNextLine() {
        return this.scanner.nextLine();
    }

}
