package brokeculator.frontend;

import brokeculator.exceptions.BrokeculatorException;

public class UI {
    private static final String STRING_DECORATION = "------------------------------------";
    private static final String PROMPT = "\t-> ";

    private final TerminalHandler terminalHandler;

    public UI() {
        terminalHandler = new TerminalHandler();
    }

    public String prettify(String message, String topLineDecoration, String bottomLineDecoration) {
        return topLineDecoration + System.lineSeparator()
                + message + System.lineSeparator() + bottomLineDecoration;
    }

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

    public void prettyPrint(String message) {
        terminalHandler.println(prettify(message.trim(), STRING_DECORATION, STRING_DECORATION));
    }

    public void greetUser() {
        prettyPrint("Hello! I'm Brokeculator!"
                + System.lineSeparator()
                + "If this is your first time using me, type 'help' to see what I can do for you.");
    }

    public void println(String message) {
        terminalHandler.println(message);
    }
}
