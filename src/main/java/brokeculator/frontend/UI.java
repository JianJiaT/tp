package brokeculator.frontend;

import brokeculator.exceptions.BrokeculatorException;

public class UI {
    private static final String STRING_DECORATION = "------------------------------------";
    private static final String PROMPT = "\t-> ";

    private final TerminalHandler terminalHandler;

    /**
     * Creates a UI object to handle user input and output.
     */
    public UI() {
        terminalHandler = new TerminalHandler();
    }

    /**
     * Prettifies a message with a top and bottom decoration.
     *
     * @param message the message to prettify.
     * @param topLineDecoration the decoration to add to the top of the message.
     * @param bottomLineDecoration the decoration to add to the bottom of the message.
     * @return the prettified message.
     */
    public String prettify(String message, String topLineDecoration, String bottomLineDecoration) {
        return topLineDecoration + System.lineSeparator()
                + message + System.lineSeparator() + bottomLineDecoration;
    }

    /**
     * Reads a line of input from the user.
     *
     * @return the line of input from the user.
     * @throws BrokeculatorException if there is an error reading user input.
     */
    public String getUserInput() throws BrokeculatorException {
        try {
            String userInput;
            do {
                userInput = terminalHandler.readLine(PROMPT);
            } while (userInput.isBlank());
            return userInput;
        } catch (Exception e) {
            throw new BrokeculatorException("Error reading user input.");
        }
    }

    /**
     * Prints a message to the terminal with a top and bottom decoration.
     *
     * @param message the message to print.
     */
    public void prettyPrint(String message) {
        terminalHandler.println(prettify(message.trim(), STRING_DECORATION, STRING_DECORATION));
    }

    /**
     * Greets the user with a welcome message.
     */
    public void greetUser() {
        prettyPrint("Hello! I'm Brokeculator!"
                + System.lineSeparator()
                + "If this is your first time using me, type 'help' to see what I can do for you.");
    }

    public void println(String message) {
        terminalHandler.println(message);
    }

    public void print(String message) {
        terminalHandler.print(message);
    }
}
