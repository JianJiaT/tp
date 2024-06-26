package brokeculator.event;
import java.util.ArrayList;
import brokeculator.storage.parsing.FileKeyword;
import brokeculator.storage.parsing.SaveableType;

public class EventManager {
    private static EventManager eventManager = null;
    private ArrayList<Event> events;

    private EventManager() {
        this.events = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of EventManager.
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

    /**
     * Returns a string representation of all events in the event manager for printing.
     */
    public String getEventsPrintString() {
        if (events.isEmpty()) {
            return "No events found";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < events.size(); i++) {
            sb.append(i + 1).append(". ").append(events.get(i));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Returns a string representation of all events in the event manager for saving.
     */
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


