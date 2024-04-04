package brokeculator.parser;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateParserTest {
    @Test
    void parseInput_inputValidDateFormat_localDate() {
        String dateString = "24-12-2024";
        LocalDate expectedDate = LocalDate.of(2024, 12, 24);
        LocalDate actualDate = DateParser.parseDate(dateString);
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void parseInput_inputInvalidDateFormat_runtimeException() {
        String invalidDateString = "24/12/2024"; // Invalid format
        assertThrows(RuntimeException.class, () -> DateParser.parseDate(invalidDateString));
    }

    @Test
    void parseInput_inputNonexistentDate_runtimeException() {
        String nonexistentDateString = "32-03-2024";
        assertThrows(RuntimeException.class, () -> DateParser.parseDate(nonexistentDateString));
    }
}
