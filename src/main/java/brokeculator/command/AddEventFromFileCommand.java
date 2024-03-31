package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.event.Event;
import brokeculator.frontend.UI;

public class AddEventFromFileCommand extends Command{
    private final String fileString;

    public AddEventFromFileCommand(String fileString) {
        this.fileString = fileString;
    }

    @Override
    public void execute(Dashboard dashboard) {
        try {
            Event event = Event.getEventFromFile(this.fileString);
            dashboard.getEventManager().addEvent(event);
        } catch (Exception e) {
            UI.prettyPrint(fileString + " is not a valid event entry");
        }
    }
}
