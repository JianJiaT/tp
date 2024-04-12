package brokeculator.expense;

import brokeculator.enumerators.Category;
import brokeculator.frontend.UI;
import brokeculator.storage.parsing.FileKeyword;
import brokeculator.storage.parsing.SaveableType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseManagerTest {
    UI ui = new UI();
    LocalDate date1 = LocalDate.of(2024, 1, 1);
    LocalDate date2 = LocalDate.of(2024, 2, 1);
    LocalDate date3 = LocalDate.of(2024, 3, 1);
    LocalDate date4 = LocalDate.of(2024, 4, 1);
    LocalDate date5 = LocalDate.of(2024, 5, 1);
    LocalDate date6 = LocalDate.of(2024, 6, 1);
    ExpenseManager expenseManager = new ExpenseManager();
    Expense expense1 = new Expense("pasta", 10.0, date1, "food");
    Expense expense2 = new Expense("bus", 20.0, date2, "transport");
    Expense expense3 = new Expense("movie", 30.0, date3, "entertainment");
    Expense expense4 = new Expense("pizza", 40.0, date4, "food");
    Expense expense5 = new Expense("grab", 50.0, date5, "transport");
    Expense expense6 = new Expense("concert", 60.0, date6, "entertainment");

    @Test
    void add_expenseAddedToExpenseManager_addedExpense() {
        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");
        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);

        assertEquals(3, expenseManager.listExpenses(-1).size());
        assertEquals(expense1, expenseManager.listExpenses(-1).get(0));
        assertEquals(expense2, expenseManager.listExpenses(-1).get(1));
        assertEquals(expense3, expenseManager.listExpenses(-1).get(2));
    }

    @Test
    void delete_expenseDeletedFromExpenseManager_deletedExpense() {
        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");
        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);
        expenseManager.delete(1);

        assertEquals(2, expenseManager.listExpenses(-1).size());
        assertEquals(expense2, expenseManager.listExpenses(-1).get(0));
        assertEquals(expense3, expenseManager.listExpenses(-1).get(1));
    }

    @Test
    void summariseExpenses_summariseEmptyList_expectedTotal() {
        double expectedTotal = 0.0;

        assertEquals(expectedTotal, expenseManager.summariseExpenses(null, null, null,
                null, 0, -1, ui));
    }
    @Test
    void summariseExpenses_summariseNoArguments_expectedTotal() {
        double expectedTotal = 210.0;

        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);
        expenseManager.add(expense4);
        expenseManager.add(expense5);
        expenseManager.add(expense6);

        assertEquals(expectedTotal, expenseManager.summariseExpenses(null, null, null,
                null, 0, -1, ui));
    }

    @Test
    void summariseExpenses_summariseByBeginIndex_expectedTotal() {
        double expectedTotal = 150.0;

        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);
        expenseManager.add(expense4);
        expenseManager.add(expense5);
        expenseManager.add(expense6);

        assertEquals(expectedTotal, expenseManager.summariseExpenses(null, null, null,
                null, 3, -1, ui));
    }

    @Test
    void summariseExpenses_summariseByEndIndex_expectedTotal() {
        double expectedTotal = 60.0;

        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);

        assertEquals(expectedTotal, expenseManager.summariseExpenses(null, null, null,
                null, 0, 2, ui));
    }

    @Test
    void summariseExpenses_summariseByBothIndices_expectedTotal() {
        double expectedTotal = 120.0;

        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);
        expenseManager.add(expense4);
        expenseManager.add(expense5);
        expenseManager.add(expense6);

        assertEquals(expectedTotal, expenseManager.summariseExpenses(null, null, null,
                null, 2, 4, ui));
    }

    @Test
    void summariseExpenses_summariseByDescription_expectedTotal() {
        double expectedTotal = 10.0;

        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);
        expenseManager.add(expense4);
        expenseManager.add(expense5);
        expenseManager.add(expense6);

        assertEquals(expectedTotal, expenseManager.summariseExpenses("pasta", null, null,
                null, 0, -1, ui));
    }

    @Test
    void summariseExpense_summariseByStartDate_expectedTotal() {
        double expectedTotal = 150.0;

        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);
        expenseManager.add(expense4);
        expenseManager.add(expense5);
        expenseManager.add(expense6);

        assertEquals(expectedTotal, expenseManager.summariseExpenses(null, date4, null,
                null, 0, -1, ui));
    }

    @Test
    void summariseExpense_summariseByEndDate_expectedTotal() {
        double expectedTotal = 60.0;

        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);
        expenseManager.add(expense4);
        expenseManager.add(expense5);
        expenseManager.add(expense6);

        assertEquals(expectedTotal, expenseManager.summariseExpenses(null, null, date3,
                null, 0, -1, ui));
    }

    @Test
    void summariseExpense_summariseByBothDates_expectedTotal() {
        double expectedTotal = 90.0;

        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);
        expenseManager.add(expense4);
        expenseManager.add(expense5);
        expenseManager.add(expense6);

        assertEquals(expectedTotal, expenseManager.summariseExpenses(null, date2, date4,
                null, 0, -1, ui));
    }

    @Test
    void summariseExpense_summariseByCategory_expectedTotal() {
        double expectedTotal = 90.0;

        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);
        expenseManager.add(expense4);
        expenseManager.add(expense5);
        expenseManager.add(expense6);

        assertEquals(expectedTotal, expenseManager.summariseExpenses(null, null, null,
                "entertainment", 0, -1, ui));
    }

    @Test
    void summariseExpense_summariseByDatesAndIndices_expectedTotal() {
        double expectedTotal = 50.0;

        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);
        expenseManager.add(expense4);
        expenseManager.add(expense5);
        expenseManager.add(expense6);

        assertEquals(expectedTotal, expenseManager.summariseExpenses(null, date1, date3,
                null, 1, 4, ui));
    }

    @Test
    void listExpenses_listAll_allExpenses() {
        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);

        assertEquals(3, expenseManager.listExpenses(-1).size());
        assertEquals(expense1, expenseManager.listExpenses(-1).get(0));
        assertEquals(expense2, expenseManager.listExpenses(-1).get(1));
        assertEquals(expense3, expenseManager.listExpenses(-1).get(2));
    }

    @Test
    void listExpenses_listByIndex_firstTwoExpenses() {
        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);

        assertEquals(2, expenseManager.listExpenses(2).size());
        assertEquals(expense1, expenseManager.listExpenses(2).get(0));
        assertEquals(expense2, expenseManager.listExpenses(2).get(1));
    }

    @Test
    void getStringRepresentation_testGetExpensesStringRepresentation_expectedExpensesStringRepresentation() {
        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);

        String actualExpensesStringRepresentation = expenseManager.getExpensesStringRepresentation();

        String expense1StringRepresentation = FileKeyword.formatWithKeyword(SaveableType.EXPENSE,
                expense1.getStringRepresentation());

        String expense2StringRepresentation = FileKeyword.formatWithKeyword(SaveableType.EXPENSE,
                expense2.getStringRepresentation());

        String expense3StringRepresentation = FileKeyword.formatWithKeyword(SaveableType.EXPENSE,
                expense3.getStringRepresentation());

        String expectedExpensesStringRepresentation = expense1StringRepresentation + System.lineSeparator()
                + expense2StringRepresentation + System.lineSeparator()
                + expense3StringRepresentation + System.lineSeparator();

        assertEquals(actualExpensesStringRepresentation, expectedExpensesStringRepresentation);
    }

    @Test
    void getExpensesListString_testGetExpensesListString_expectedExpensesListString() {
        Category.addCategory("food");
        Category.addCategory("transport");
        Category.addCategory("entertainment");

        expenseManager.add(expense1);
        expenseManager.add(expense2);
        expenseManager.add(expense3);

        String actualExpensesListString = expenseManager.getExpensesListString(0, 3);

        String expectedExpensesListString = "1. " + expense1 + System.lineSeparator()
                + "2. " + expense2 + System.lineSeparator()
                + "3. " + expense3 + System.lineSeparator();

        assertEquals(actualExpensesListString, expectedExpensesListString);
    }
}
