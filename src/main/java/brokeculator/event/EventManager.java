package brokeculator.event;

import java.util.ArrayList;

import brokeculator.frontend.UI;
import brokeculator.storage.parsing.FileKeyword;
import brokeculator.storage.parsing.SaveableType;

public class EventManager {
    private static EventManager eventManager = null;
    private ArrayList<Event> events;

    private EventManager() {
        this.events = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of EventManager
     * @return Singleton instance of EventManager
     */
    public static EventManager getInstance() {
        if (EventManager.eventManager == null) {
            EventManager.eventManager = new EventManager();
        }
        return EventManager.eventManager;
    }

    public void addEvent(Event event) {
        events.add(event);
    }
    public boolean isEventIdxValid(int idx) {
        return idx >= 1 && idx <= events.size();
    }
    public Event getEvent(int idx) {
        if (!isEventIdxValid(idx)) {
            return null;
        }
        return events.get(idx - 1);
    }
    public int getEventIndex(Event event) {
        return events.indexOf(event) + 1;
    }
    public void removeEvent(int idx) {
        if (!isEventIdxValid(idx)) {
            return;
        }
        events.remove(idx - 1);
    }

    public void printEvents() {
        if (events.isEmpty()) {
            UI.prettyPrint("No events found");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int idx = 0; idx < events.size(); idx++) {
            sb.append(idx + 1).append(". ").append(events.get(idx));
            sb.append(System.lineSeparator());
        }
        UI.prettyPrint(sb.toString());
    }

    public String getEventsStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (Event event : events) {
            String currentEventString = event.getStringRepresentation();
            String finalEventString = FileKeyword.formatWithKeyword(SaveableType.EVENT, currentEventString);
            sb.append(finalEventString);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}


