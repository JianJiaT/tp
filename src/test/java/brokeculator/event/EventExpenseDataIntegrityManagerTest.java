package brokeculator.event;

import brokeculator.expense.Expense;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EventExpenseDataIntegrityManagerTest {
    Expense expense1 = new Expense("ex1", 1, "today", null);
    Expense expense2 = new Expense("ex2", 2, "today", null);
    Event event1 = new Event("event 1 name", "event 1 description");
    Event event2 = new Event("event 2 name", "event 2 description");

    @Test
    void addExpenseToEvent_validExpenseAndEvent_eventContainsExpense() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        assertTrue(event1.containsExpense(expense1));
    }

    @Test
    void addExpenseToEvent_validExpenseAndEvent_expenseConnectedToEvent() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        assertTrue(expense1.getOwningEvent() == event1);
    }

    @Test
    void addExpenseToEvent_multipleExpenses_eventContainsAllExpenses() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.buildConnection(expense2, event1);
        assertTrue(event1.containsExpense(expense1) && event1.containsExpense(expense2));
    }

    @Test
    void removeConnectionFromOwningEvent_validExpenseAndEvent_eventDoesNotContainExpense() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.removeConnectionFromOwningEvent(expense1);
        assertFalse(event1.containsExpense(expense1));
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
        assertFalse(event1.containsExpense(expense1) && event1.containsExpense(expense2));
    }

    @Test
    void addExpenseToEvent_expenseAddedToNewEvent_oldEventDoesNotContainExpense() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.buildConnection(expense1, event2);
        assertFalse(event1.containsExpense(expense1));
    }

    @Test
    void addExpenseToEvent_expenseAddedToNewEvent_newEventContainsExpense() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.buildConnection(expense1, event2);
        assertTrue(event2.containsExpense(expense1));
    }

    @Test
    void addExpenseToEvent_expenseAddedToNewEvent_expenseConnectedToNewEvent() {
        EventExpenseDataIntegrityManager.buildConnection(expense1, event1);
        EventExpenseDataIntegrityManager.buildConnection(expense1, event2);
        assertTrue(expense1.getOwningEvent() == event2);
    }
}