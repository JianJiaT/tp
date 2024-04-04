package brokeculator.expense;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Logger;

import brokeculator.event.Event;
import brokeculator.parser.DateParser;
import brokeculator.parser.util.Keyword;
import brokeculator.parser.util.OrderParser;

/**
 * Represents an expense in the expense tracker.
 */
public class Expense implements Saveable {

    private static final Keyword DESCRIPTION_KEYWORD =
            new Keyword("|__EXPENSE_DESCRIPTION__|:", "expense description", false);
    private static final Keyword DATE_KEYWORD =
            new Keyword("|__EXPENSE_DATE__|:", "expense date", false);
    private static final Keyword AMOUNT_KEYWORD =
            new Keyword("|__EXPENSE_AMOUNT__|:", "expense amount", false);
    private static final Keyword CATEGORY_KEYWORD =
            new Keyword("|__EXPENSE_CATEGORY__|:", "expense category", true);
    private static final Keyword[] SAVING_KEYWORDS
            = {DESCRIPTION_KEYWORD, DATE_KEYWORD, AMOUNT_KEYWORD, CATEGORY_KEYWORD};

    private static final String EXPENSE_DATE_FORMAT = "EEEE, dd MMMM yyyy";

    private static final Logger logger = Logger.getLogger(Expense.class.getName());

    private final String description;
    private final LocalDate date;
    private final double amount;
    private final String category;
    private Event owningEvent;

    /**
     * Constructs an Expense object with the given description, amount, date and category.
     *
     * @param description the description of the expense.
     * @param amount the amount of the expense.
     * @param date the date of the expense.
     * @param category the category of the expense.
     */
    public Expense(String description, double amount, LocalDate date, String category) {
        this.description = description.trim();
        this.amount = amount;
        this.date = date;
        this.category = category == null ? null : category.trim().toUpperCase();
    }

    /**
     * Returns the description of the expense.
     * @return the description of the expense.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the amount of the expense.
     * @return the amount of the expense.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the date of the expense.
     * @return the date of the expense.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the category of the expense.
     * @return the category of the expense.
     */
    public String getCategory() {
        return category;
    }

    public boolean hasOwningEvent() {
        return owningEvent != null;
    }
    public Event getOwningEvent() {
        return owningEvent;
    }
    public void removeOwningEvent() {
        this.owningEvent = null;
    }
    public void setOwningEvent(Event event) {
        this.owningEvent = event;
    }

    /**
     * Returns a string representation of the expense.
     * @return a string representation of the expense.
     */
    public String getStringRepresentation() {
        String stringRepresentation =
                DESCRIPTION_KEYWORD.keywordMarker + this.description
                        + DATE_KEYWORD.keywordMarker + this.date.format(DateParser.DATE_FORMATTER)
                        + AMOUNT_KEYWORD.keywordMarker + this.amount;
        if (this.category != null) {
            stringRepresentation += (CATEGORY_KEYWORD.keywordMarker + this.category);
        }
        return stringRepresentation;
    }

    public static Expense getExpenseFromFile(String stringRepresentation) throws Exception {
        String[] expenseDetails = OrderParser.parseOrder(stringRepresentation, Expense.SAVING_KEYWORDS);
        assert expenseDetails.length == 4;

        String description = expenseDetails[0];
        String dateString = expenseDetails[1];
        String amountString = expenseDetails[2];
        String category = expenseDetails[3];

        try {
            double amount = Double.parseDouble(amountString);
            LocalDate date = DateParser.parseDate(dateString);
            return new Expense(description, amount, date, category);
        } catch (Exception e) {
            logger.warning("Expense file is corrupted.");
            throw new Exception("Expense string: " + stringRepresentation + " is corrupted");
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(EXPENSE_DATE_FORMAT, Locale.ENGLISH);

        if (category == null) {
            return String.format("%s $%.2f (%s)", description, amount, date.format(formatter));
        }
        return String.format("%s $%.2f (%s) [%s]", description, amount, date.format(formatter), category.toUpperCase());
    }
}
