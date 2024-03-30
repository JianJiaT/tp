package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.frontend.UI;

public class AddConnectionFromFileCommand extends Command {
    private final String fileString;

    public AddConnectionFromFileCommand(String fileString) {
        this.fileString = fileString;
    }

    @Override
    public void execute(Dashboard dashboard) {
        try {
            dashboard.getDataIntegrityManager().loadConnectionFromStringRepresentation(fileString);
        } catch (Exception e) {
            UI.prettyPrint(fileString + " is not a valid connection entry");
        }
    }
}
