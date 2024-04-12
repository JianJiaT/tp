package brokeculator.frontend;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.logging.Logger;

public class TerminalHandler {
    private Terminal terminal;
    private LineReader lineReader;

    public TerminalHandler() {
        try {
            terminal = TerminalBuilder.builder().system(true).build();
            lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        } catch (Exception e) {
            Logger logger = Logger.getLogger(TerminalHandler.class.getName());
            logger.severe("Error creating terminal: " + e.getMessage());
            System.exit(1);
        }
    }

    public String readLine(String prompt) {
        return lineReader.readLine(prompt);
    }

    public void print(String message) {
        terminal.writer().print(message);
        terminal.flush();
    }

    public void println(String message) {
        terminal.writer().println(message);
        terminal.flush();
    }
}
