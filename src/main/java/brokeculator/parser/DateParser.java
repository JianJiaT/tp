package brokeculator.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class to parse Strings to Dates
 */
public class DateParser {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    /**
     * Parses a date string into a LocalDate object
     * @param date Date string
     * @return LocalDate object
     */
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }
}
