package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.frontend.UI;

public class AddConnectionFromFileCommand extends Command {
    private final String fileString;

    public AddConnectionFromFileCommand(String fileString) {
        this.fileString = fileString;
    }
    
    /**
     * Builds a connection between an event and an expense represented by the file string
     * @param dashboard the dashboard that contains the data integrity manager that will build the connection
     */
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        try {
            dashboard.getDataIntegrityManager().loadConnectionFromStringRepresentation(fileString);
        } catch (Exception e) {
            ui.prettyPrint(fileString + " is not a valid connection entry");
        }
    }
}
