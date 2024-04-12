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
    public void execute(Dashboard dashboard, UI ui) {
        try {
            Event event = Event.getEventFromFile(this.fileString);
            dashboard.getEventManager().addEvent(event);
        } catch (Exception e) {
            ui.prettyPrint(fileString + " is not a valid event entry");
        }
    }
}
