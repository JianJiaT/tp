package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.event.Event;
import brokeculator.frontend.UI;

public class DeleteEventCommand extends Command {
    private final int idx;

    public DeleteEventCommand(int idx) {
        this.idx = idx;
    }

    /**
     * Deletes an event from the dashboard's event manager
     * 
     * If the event index is invalid, an error message will be printed
     * If the event has expenses, the event will not be deleted
     * @param dashboard the dashboard that contains the event manager
     */
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        boolean isValidEventIdx = dashboard.getEventManager().isEventIdxValid(idx);
        if (!isValidEventIdx) {
            ui.prettyPrint("Invalid event index");
            return;
        }
        Event event = dashboard.getEventManager().getEvent(idx);
        if (event.hasExpenses()) {
            ui.prettyPrint("Event has expenses. Please remove expenses before deleting event");
            return;
        }
        dashboard.getEventManager().removeEvent(idx);
        ui.prettyPrint("Event deleted");
    }
}
