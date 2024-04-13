package brokeculator.frontend;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.logging.Logger;

public class TerminalHandler {
    private Terminal terminal;
    private LineReader lineReader;

    /**
     * Creates a terminal and lineReader to handle user input and output with extended features.
     */
    public TerminalHandler() {
        System.setProperty("org.jline.terminal.exec.redirectPipeCreationMode", "native");
        try {
            terminal = TerminalBuilder.builder().system(true).dumb(true).build();
            lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        } catch (Exception e) {
            Logger logger = Logger.getLogger(TerminalHandler.class.getName());
            logger.severe("Error creating terminal: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Reads a line of input from the user.
     *
     * @param prompt the prompt to display to the user.
     * @return the line of input from the user.
     */
    public String readLine(String prompt) {
        return lineReader.readLine(prompt);
    }

    /**
     * Prints a message to the terminal.
     *
     * @param message the message to print.
     */
    public void print(String message) {
        terminal.writer().print(message);
        terminal.flush();
    }

    /**
     * Prints a message to the terminal followed by a newline.
     *
     * @param message the message to print.
     */
    public void println(String message) {
        terminal.writer().println(message);
        terminal.flush();
    }
}
