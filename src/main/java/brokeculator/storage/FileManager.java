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
    private static final String DEFAULT_CONNECTION_FILE_PATH = "./data/connection.txt";

    private final File dataFile = new File(DEFAULT_DATA_FILE_PATH);
    private final File categoryFile = new File(DEFAULT_CATEGORY_FILE_PATH);
    private final File eventFile = new File(DEFAULT_EVENT_FILE_PATH);
    private final File connectionFile = new File(DEFAULT_CONNECTION_FILE_PATH);

    private Scanner scanner = null;
    private boolean hasFileErrors;

    private final UI ui;

    public FileManager(UI ui) {
        this.ui = ui;
        try {
            createFiles();
            checkFilesExist();
            this.hasFileErrors = false;
        } catch (Exception e) {
            printDataLossWarning();
            this.hasFileErrors = true;
        }
    }

    private void createFiles() throws Exception {
        if (!this.dataFile.exists()) {
            createFile(this.dataFile);
        }
        if (!this.categoryFile.exists()) {
            createFile(this.categoryFile);
        }
        if (!this.eventFile.exists()) {
            createFile(this.eventFile);
        }
        if (!this.connectionFile.exists()) {
            createFile(this.connectionFile);
        }
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

    private void checkFilesExist() throws Exception {
        if (!this.dataFile.exists() || !this.categoryFile.exists() || !this.eventFile.exists()
                || !this.connectionFile.exists()) {
            throw new Exception();
        }
    }

    private void printDataLossWarning() {
        ui.println("Errors! Your data will not be saved");
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
     * Opens a file based on the SaveableType and returns true if no errors are encountered.
     *
     * @param saveableType the type of file to open.
     * @return true if no errors are encountered.
     */
    public boolean openFile(SaveableType saveableType) {
        switch (saveableType) {
        case CATEGORY:
            return openFile(this.categoryFile);
        case EXPENSE:
            return openFile(this.dataFile);
        case EVENT:
            return openFile(this.eventFile);
        case CONNECTION:
            return openFile(this.connectionFile);
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
    public void saveConnections(String data) {
        save(data, this.connectionFile);
    }

    /**
     * Returns true if there is another line to read.
     * Reads from the file opened by openFile.
     *
     * @return true if there is another line to read, false otherwise.
     */
    public boolean hasNextLine() {
        if (this.scanner == null) {
            return false;
        }
        return this.scanner.hasNext();
    }

    /**
     * Returns the next line from the file opened by openFile.
     */
    public String readNextLine() {
        if (this.scanner == null) {
            return null;
        }
        return this.scanner.nextLine();
    }

}
