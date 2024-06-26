package brokeculator.parser;

import brokeculator.command.Command;
import brokeculator.command.HelpCommand;

public class GeneralInputParser {
    public static Command getCommandFromUserInput(String userInput) {
        Command commandToExecute;
        try {
            String commandKeyword = userInput.split(" ")[0];
            String normalizedKeyword = commandKeyword.toLowerCase().trim();
            switch (normalizedKeyword) {
            case "add":
                commandToExecute = AddParser.parseInput(userInput);
                break;
            case "delete":
                commandToExecute = DeleteParser.parseInput(userInput);
                break;
            case "list":
                commandToExecute = ListParser.parseInput(userInput);
                break;
            case "summarise":
                commandToExecute = SummariseParser.parseInput(userInput);
                break;
            case "exit":
                commandToExecute = ExitParser.parseInput(userInput);
                break;
            case "event":
                commandToExecute = EventParser.parseInput(userInput);
                break;
            case "category":
                commandToExecute = CategoryParser.parseInput(userInput);
                break;
            case "viewevent":
                commandToExecute = ViewSingleEventParser.parseInput(userInput);
                break;
            case "listevents":
                commandToExecute = ListEventsParser.parseInput(userInput);
                break;
            case "deleteevent":
                commandToExecute = DeleteEventParser.parseInput(userInput);
                break;
            case "addexev":
                commandToExecute = AddExpenseToEventParser.parseInput(userInput);
                break;
            case "delexev":
                commandToExecute = DeleteExpenseFromEventParser.parseInput(userInput);
                break;
            default:
                commandToExecute = new HelpCommand();
            }
        } catch (Exception e) {
            commandToExecute =  new HelpCommand();
        }
        return commandToExecute;
    }
}
