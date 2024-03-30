package brokeculator.parser.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderParserTest {
    Keyword keyword1 = new Keyword("/from", "From date", false);
    Keyword keyword2 = new Keyword("/to", "To date", false);
    Keyword keyword3 = new Keyword("/by", "Sort by", true);
    Keyword keyword4 = new Keyword("/on", "On date", true);

    @Test
    void parseOrder_validInput_correctParsedInput() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword2, keyword3, keyword4};
        String userInput = "/from 01/01/2021 /to 01/02/2021 /by name /on 01/03/2021";
        String[] expectedParsedInput = new String[] {"01/01/2021", "01/02/2021", "name", "01/03/2021"};
        try {
            String[] parsedInput = OrderParser.parseOrder(userInput, keywords);
            assertArrayEquals(expectedParsedInput, parsedInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void parseOrder_missingMandatoryKeyword_exceptionThrown() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword2, keyword3, keyword4};
        String userInput = "/from 01/01/2021 /by name /on 01/03/2021";
        assertThrows(Exception.class, () -> OrderParser.parseOrder(userInput, keywords));
    }

    @Test
    void parseOrder_missingMultipleMandatoryKeyword_exceptionThrown() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword2, keyword3, keyword4};
        String userInput = "/by name /on 01/03/2021";
        assertThrows(Exception.class, () -> OrderParser.parseOrder(userInput, keywords));
    }

    @Test
    void parseOrder_missingOptionalKeyword_correctParsedInput() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword2, keyword3, keyword4};
        String userInput = "/from 01/01/2021 /to 01/02/2021 /on 01/03/2021";
        String[] expectedParsedInput = new String[] {"01/01/2021", "01/02/2021", null, "01/03/2021"};
        try {
            String[] parsedInput = OrderParser.parseOrder(userInput, keywords);
            assertArrayEquals(expectedParsedInput, parsedInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void parseOrder_missingMultipleOptionalKeyword_correctParsedInput() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword2, keyword3, keyword4};
        String userInput = "/from 01/01/2021 /to 01/02/2021";
        String[] expectedParsedInput = new String[] {"01/01/2021", "01/02/2021", null, null};
        try {
            String[] parsedInput = OrderParser.parseOrder(userInput, keywords);
            assertArrayEquals(expectedParsedInput, parsedInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void parseOrder_emptyMandatoryKeyword_exceptionThrown() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword2, keyword3, keyword4};
        String userInput = "/from 01/01/2021 /to  /by name /on 01/03/2021";
        assertThrows(Exception.class, () -> OrderParser.parseOrder(userInput, keywords));
    }

    @Test
    void parseOrder_emptyOptionalKeyword_exceptionThrown() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword2, keyword3, keyword4};
        String userInput = "/from 01/01/2021 /to 01/02/2021 /by name /on ";
        assertThrows(Exception.class, () -> OrderParser.parseOrder(userInput, keywords));
    }

    @Test
    void parseOrder_wrongOrder_exceptionThrown() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword2, keyword3, keyword4};
        String userInput = "/from 01/01/2021 /to 01/02/2021 /on 01/03/2021 /by name";
        assertThrows(Exception.class, () -> OrderParser.parseOrder(userInput, keywords));
    }

    @Test
    void parseOrder_multipleWordsInputs_correctParsedInput() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword2, keyword3, keyword4};
        String userInput = "/from 01/01/2021 today /to 01/02/2021 sometimes later /by name /on 01/03/2021";
        String[] expectedParsedInput =
                new String[] {"01/01/2021 today", "01/02/2021 sometimes later", "name", "01/03/2021"};
        try {
            String[] parsedInput = OrderParser.parseOrder(userInput, keywords);
            assertArrayEquals(expectedParsedInput, parsedInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void parseOrder_inputsWithSpaces_correctParsedInputWithSpacesTrimmed() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword2, keyword3, keyword4};
        String userInput = "/from 01/01/2021     /to 01/02/2021        /by name /on 01/03/2021";
        String[] expectedParsedInput =
                new String[] {"01/01/2021", "01/02/2021", "name", "01/03/2021"};
        try {
            String[] parsedInput = OrderParser.parseOrder(userInput, keywords);
            assertArrayEquals(expectedParsedInput, parsedInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void parseOrder_mandatoryOptionalMixed_correctParsedInput() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword3, keyword2, keyword4};
        String userInput = "/from 01/01/2021 /by name /to 01/02/2021 /on 01/03/2021";
        String[] expectedParsedInput = new String[] {"01/01/2021", "name", "01/02/2021", "01/03/2021"};
        try {
            String[] parsedInput = OrderParser.parseOrder(userInput, keywords);
            assertArrayEquals(expectedParsedInput, parsedInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void parseOrder_mandatoryOptionalMixedMissingMandatoryKeyword_exceptionThrown() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword3, keyword2, keyword4};
        String userInput = "/by name /to 01/02/2021 /on 01/03/2021";
        assertThrows(Exception.class, () -> OrderParser.parseOrder(userInput, keywords));
    }

    @Test
    void parseOrder_mandatoryOptionalMixedMissingOptionalKeyword_correctParsedInput() {
        Keyword[] keywords = new Keyword[] {keyword1, keyword3, keyword2, keyword4};
        String userInput = "/from 01/01/2021 /to 01/02/2021 /on 01/03/2021";
        String[] expectedParsedInput = new String[] {"01/01/2021", null, "01/02/2021", "01/03/2021"};
        try {
            String[] parsedInput = OrderParser.parseOrder(userInput, keywords);
            assertArrayEquals(expectedParsedInput, parsedInput);
        } catch (Exception e) {
            fail();
        }
    }
}