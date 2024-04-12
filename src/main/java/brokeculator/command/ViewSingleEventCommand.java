package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.event.Event;
import brokeculator.frontend.UI;

public class ViewSingleEventCommand extends Command {
    private int eventIndex;
    
    public ViewSingleEventCommand(int eventIndex) {
        this.eventIndex = eventIndex;
    }
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        boolean isValidEventIndex = dashboard.getEventManager().isEventIdxValid(eventIndex);
        if (!isValidEventIndex) {
            ui.prettyPrint("Invalid event index provided");
            return;
        }
        Event event = dashboard.getEventManager().getEvent(eventIndex);
        assert event != null : "Event is null";

        int expenseCount = event.getExpenseCount();
        if (expenseCount == 0) {
            String string = event + System.lineSeparator()
                    + "Event has no expenses";
            ui.prettyPrint(string);
            return;
        }

        String string = event + System.lineSeparator()
                + "Event has " + expenseCount + " expenses:"
                + System.lineSeparator()
                + event.listExpenses();
        ui.prettyPrint(string);
    }
}
