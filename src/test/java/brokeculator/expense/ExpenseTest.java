package brokeculator.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ExpenseTest {
    LocalDate date = LocalDate.now();
    Expense testExpense = new Expense("Lunch", 12.50, date, "food");

    @Test
    void testGetDescription() {
        assertEquals("Lunch", testExpense.getDescription());
    }

    @Test
    void testGetAmount() {
        assertEquals(12.50, testExpense.getAmount());
    }

    @Test
    void testGetDate() {
        assertEquals(date, testExpense.getDate());
    }

    @Test
    void testGetCategory() {
        assertEquals("FOOD", testExpense.getCategory());
    }

    @Test
    void testGetExpenseFromFile() {
        try {
            String stringRepresentation = testExpense.getStringRepresentation();
            Expense expenseFromFile = Expense.getExpenseFromFile(stringRepresentation);
            assertEquals("Lunch", expenseFromFile.getDescription());
            assertEquals(12.50, expenseFromFile.getAmount());
            assertEquals(date, expenseFromFile.getDate());
            assertEquals(null, expenseFromFile.getCategory());
        } catch (Exception e) {
            fail();
        }
    }
}
