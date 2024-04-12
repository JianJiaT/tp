package brokeculator.frontend;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class UITest {
    UI ui = new UI();
    @Test
    public void prettify_string_expectStringEnclosedWithThreeAsterisks() {
        String inputString = "Hello";
        String decorator = "***";
        String expectedString = "***" + System.lineSeparator()
                                + "Hello"  + System.lineSeparator() + "***";
        String decoratedString = ui.prettify(inputString, decorator, decorator);
        assertEquals(expectedString, decoratedString);
    }
}
