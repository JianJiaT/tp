package brokeculator.event;

import brokeculator.expense.Expense;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import brokeculator.expense.ExpenseManager;
import brokeculator.storage.parsing.FileKeyword;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class EventExpenseDataIntegrityManagerTest {
    LocalDate date = LocalDate.now();
    Expense expense1 = new Expense("ex1", 1, date, null);
    Expense expense2 = new Expense("ex2", 2, date, null);
    Event event1 = new Event("event 1 name", "event 1 description");
    Event event2 = new Event("event 2 name", "event 2 description");

    EventManager eventManager = EventManager.getInstance();
    ExpenseManager expenseManager = new ExpenseManager();
    EventExpenseDataIntegrityManager manager = new EventExpenseDataIntegrityManager(eventManager, expenseManager);

    @Test
    void addExpenseToEvent_validExpenseAndEvent_eventContainsExpense() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        assertTrue(event1.hasExpense(expense1));
    }

    @Test
    void addExpenseToEvent_validExpenseAndEvent_expenseConnectedToEvent() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        assertSame(expense1.getOwningEvent(), event1);
    }

    @Test
    void addExpenseToEvent_multipleExpenses_eventContainsAllExpenses() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.buildConnection(expense2, event1);
        assertTrue(event1.hasExpense(expense1) && event1.hasExpense(expense2));
    }

    @Test
    void removeConnectionFromOwningEvent_validExpenseAndEvent_eventDoesNotContainExpense() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.removeConnectionFromOwningEvent(expense1);
        assertFalse(event1.hasExpense(expense1));
    }

    @Test
    void removeConnectionFromOwningEvent_validExpenseAndEvent_expenseNotConnectedToEvent() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.removeConnectionFromOwningEvent(expense1);
        assertNull(expense1.getOwningEvent());
    }

    @Test
    void removeConnectionFromOwningEvent_multipleExpenses_eventDoesNotContainAnyExpenses() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.buildConnection(expense2, event1);
        EventExpenseDataIntegrityManager.removeConnectionFromOwningEvent(expense1);
        EventExpenseDataIntegrityManager.removeConnectionFromOwningEvent(expense2);
        assertFalse(event1.hasExpense(expense1) && event1.hasExpense(expense2));
    }

    @Test
    void addExpenseToEvent_expenseAddedToNewEvent_oldEventDoesNotContainExpense() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.buildConnection(expense1, event2);
        assertFalse(event1.hasExpense(expense1));
    }

    @Test
    void addExpenseToEvent_expenseAddedToNewEvent_newEventContainsExpense() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.buildConnection(expense1, event2);
        assertTrue(event2.hasExpense(expense1));
    }

    @Test
    void addExpenseToEvent_expenseAddedToNewEvent_expenseConnectedToNewEvent() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.buildConnection(expense1, event2);
        assertSame(expense1.getOwningEvent(), event2);
    }

    @Test
    void getConnectionsStringRepresentation_noConnections_emptyString() {
        assertTrue(manager.getConnectionsStringRepresentation().isEmpty());
    }

    @Test
    void loadConnectionFromStringRepresentation_singleConnection_connectionRebuilt() {
        eventManager.addEvent(event1);
        expenseManager.add(expense1);
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);

        String stringRepresentation = manager.getConnectionsStringRepresentation();
        EventExpenseDataIntegrityManager.removeConnectionFromOwningEvent(expense1);
        String originalStringRepresentation = FileKeyword.removeKeyword(stringRepresentation);

        try {
            manager.loadConnectionFromStringRepresentation(originalStringRepresentation);
            assertTrue(event1.hasExpense(expense1));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void loadConnectionFromStringRepresentation_invalidConnection_exceptionThrown() {
        String invalidConnection = "some random connection string";
        assertThrows(Exception.class, () -> manager.loadConnectionFromStringRepresentation(invalidConnection));
    }
}
