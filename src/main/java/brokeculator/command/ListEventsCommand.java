package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.frontend.UI;

public class ListEventsCommand extends Command {
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        String eventsPrintString = dashboard.getEventManager().getEventsPrintString();
        ui.prettyPrint(eventsPrintString);
    }
}
